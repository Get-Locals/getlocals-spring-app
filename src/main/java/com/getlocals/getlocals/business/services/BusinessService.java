package com.getlocals.getlocals.business.services;

import com.getlocals.getlocals.auth.AuthService;
import com.getlocals.getlocals.auth.AuthenticationResponse;
import com.getlocals.getlocals.business.entities.*;
import com.getlocals.getlocals.business.repositories.*;
import com.getlocals.getlocals.role.Role;
import com.getlocals.getlocals.role.RoleRepository;
import com.getlocals.getlocals.user.User;
import com.getlocals.getlocals.user.UserRepository;
import com.getlocals.getlocals.user.UserService;
import com.getlocals.getlocals.utils.CustomEnums;
import com.getlocals.getlocals.utils.DTO;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class BusinessService {

    @Autowired
    private BusinessRepository businessRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private AuthService authService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private BusinessTypeRepository businessTypeRepository;

    @Autowired
    private BusinessTimingRepo businessTimingRepo;

    @Autowired
    private ContactRequestRepository contactRequestRepository;

    @Autowired
    private BusinessImageRepository imageRepository;

    @Autowired
    private EmployeeInfoRepository employeeInfoRepository;

    @Autowired
    private BusinessTemplateRepo templateRepo;


    @Transactional
    public AuthenticationResponse registerBusiness(DTO.BusinessRegisterDTO businessRegisterDTO, String jwtToken) {
        // Get the logged-in user because only owner can create a business
        User owner = userService.getLoggedInUserEntity();

        // Create the business
        Business business = Business.builder()
                .serviceType(businessTypeRepository.getBusinessTypeByVal(businessRegisterDTO.getBusinessType()).orElseThrow())
                .name(businessRegisterDTO.getName())
                .location(businessRegisterDTO.getLocation())
                .owner(owner)
                .build();
        business = businessRepository.save(business);

        // Assign user the owner role
        Collection<Role> roles = owner.getRoles();
        roles.add(roleRepository.findByRole(CustomEnums.RolesEnum.OWNER.getVal()).orElseThrow());
        owner.setRoles(roles);
        userRepository.save(owner);

        // Create default business timings.
        BusinessTiming timings = BusinessTiming.builder()
                .business(business)
                .today(CustomEnums.BusinessTimingEnum.OPEN.getVal())
                .tomorrow(CustomEnums.BusinessTimingEnum.OPEN.getVal())
                .monday("10:00 AM - 10:00 PM")
                .tuesday("10:00 AM - 10:00 PM")
                .wednesday("10:00 AM - 10:00 PM")
                .thursday("10:00 AM - 10:00 PM")
                .friday("10:00 AM - 10:00 PM")
                .saturday("10:00 AM - 10:00 PM")
                .sunday("10:00 AM - 10:00 PM").build();
        businessTimingRepo.save(timings);


        // Create Owner's profile
        EmployeeInfo employeeInfo = EmployeeInfo.builder()
                .firstName(owner.getName().split(" ")[0])
                .lastName(Optional.of(owner.getName().split(" ")[1]).orElse(null))
                .business(business)
                .email(owner.getEmail())
                .position(CustomEnums.BusinessEmployeeTypeEnum.OWNER)
                .build();
        employeeInfoRepository.save(employeeInfo);

        // Set the default template
        BusinessTemplate template = BusinessTemplate.builder()
                .business(business)
                .templateId("067b7d1e-eb92-42e9-9ba0-1021933f6b83")
                .build();
        templateRepo.save(template);


        return authService.refreshToken(jwtToken);

    }

    @Transactional
    public String addMenuItems(DTO.AddItemBusinessDTO itemsDTO) {
        List<Item> buildItems = new ArrayList<>();
        // Creating items from the DTO
        itemsDTO.getItems().forEach(item -> {
            Item buildItem = Item.builder()
                    .name(item.getName())
                    .currency(item.getCurrency())
                    .price(item.getPrice()).build();
            buildItems.add(buildItem);
        });

        itemRepository.saveAll(buildItems);
        return "Added Item to the Menu";
    }

    public DTO.BusinessRegisterDTO getBusinessById(String businessId) {
        Business business = businessRepository.getReferenceById(businessId);
        return DTO.BusinessRegisterDTO.builder()
                .businessType(business.getServiceType().getVal())
                .name(business.getName())
                .location(business.getLocation())
                .aboutUs(business.getAboutUs())
                .build();
    }

    public List<DTO.BusinessTypeDTO> getTypes() {
        var types = businessTypeRepository.findAll();
        List<DTO.BusinessTypeDTO> res = new ArrayList<>();
        for (BusinessType type : types) {
            res.add(DTO.BusinessTypeDTO.builder()
                    .value(type.getVal())
                    .label(type.getLabel())
                    .build());
        }
        return res;
    }

    public void createTypes(String item) {
        if (item == null) {
            businessTypeRepository.saveAll(List.of(
                    BusinessType.builder()
                            .val("FOOD")
                            .label("Food")
                            .build(),
                    BusinessType.builder()
                            .val("PERSONAL_CARE".toUpperCase())
                            .label("Personal Care")
                            .build()
            ));
        } else {
            businessTypeRepository.save(
                    BusinessType.builder()
                            .val(item.toUpperCase())
                            .label(item)
                            .build()
            );
        }
    }

    public ResponseEntity<?> updateBusiness(String aboutUs, String id) {
        Optional<Business> optionalBusiness = businessRepository.findById(id);
        if (optionalBusiness.isEmpty())
            return ResponseEntity.badRequest().body("Business not found");
        Business business = optionalBusiness.get();
        business.setAboutUs(aboutUs);
        businessRepository.save(business);
        return new ResponseEntity<>(DTO.StringMessage.builder()
                .message("Updated Successfully").build(), HttpStatus.OK);
    }

    public ResponseEntity<?> getAllContactRequests(String businessId) {
        List<ContactRequest> requests = contactRequestRepository.getAllByBusiness_Id(businessId);
        return ResponseEntity.ok(
                requests.stream().map(request -> DTO.ContactRequestDTO.builder()
                        .id(request.getId())
                        .fullName(request.getFullName())
                        .email(request.getEmail())
                        .subject(request.getSubject())
                        .message(request.getMessage())
                        .imageId(request.getImage().getId())
                        .build()));
    }

    public ResponseEntity<?> createContactRequest(String businessId, DTO.ContactRequestDTO requestDTO) {
        ContactRequest request = ContactRequest.builder()
                .business(businessRepository.getReferenceById(businessId))
                .fullName(requestDTO.getFullName())
                .email(requestDTO.getEmail())
                .message(requestDTO.getMessage())
                .subject(requestDTO.getSubject())
                .image(Optional.of(imageRepository.getReferenceById(requestDTO.getImageId())).orElse(null))
                .build();
        contactRequestRepository.save(request);
        return ResponseEntity.ok(DTO.StringMessage.builder()
                .message("Submitted request successfully!")
                .build());
    }

    public ResponseEntity<?> getTemplateInformation(String businessUsername) {
        BusinessTemplate template = templateRepo.getBusinessTemplateByBusiness_BusinessUsername(businessUsername).orElseThrow();

        return ResponseEntity.ok(
                DTO.BusinessTemplateInfoDTO.builder()
                        .id(template.getBusiness().getId())
                        .templateId(template.getTemplateId())
                        .build());
    }
}
