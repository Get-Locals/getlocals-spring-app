package com.getlocals.getlocals.business.services;

import com.getlocals.getlocals.auth.AuthService;
import com.getlocals.getlocals.auth.AuthenticationResponse;
import com.getlocals.getlocals.business.entities.Business;
import com.getlocals.getlocals.business.entities.BusinessType;
import com.getlocals.getlocals.business.entities.Item;
import com.getlocals.getlocals.business.repositories.BusinessImageRepository;
import com.getlocals.getlocals.business.repositories.BusinessRepository;
import com.getlocals.getlocals.business.repositories.BusinessTypeRepository;
import com.getlocals.getlocals.business.repositories.ItemRepository;
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
    private BusinessImageRepository businessImageRepository;


    @Transactional
    public AuthenticationResponse registerBusiness(DTO.BusinessRegisterDTO businessRegisterDTO, String jwtToken) {
        // Get the logged-in user because only owner can create a business
        User owner = userService.getLoggedInUserEntity();

        // Create the business
        Business business = Business.builder()
                .serviceType(CustomEnums.BusinessServicesEnum.valueOf(businessRegisterDTO.getBusinessType()))
//                .logo(businessRegisterDTO.getLogo())
                .name(businessRegisterDTO.getName())
                .location(businessRegisterDTO.getLocation())
                .owner(owner)
                .build();
        businessRepository.save(business);

        // Assign user the owner role
        Collection<Role> roles = owner.getRoles();
        roles.add(roleRepository.findByRole(CustomEnums.RolesEnum.OWNER.getVal()));
        owner.setRoles(roles);
        userRepository.save(owner);

        return authService.refreshToken(jwtToken);

    }

    @Transactional
    public String addMenuItems(DTO.AddItemBusinessDTO itemsDTO) {
        // Get the business the items are going to be assigned to.
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
}
