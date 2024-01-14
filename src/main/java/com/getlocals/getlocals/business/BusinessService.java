package com.getlocals.getlocals.business;

import com.getlocals.getlocals.auth.AuthService;
import com.getlocals.getlocals.auth.AuthenticationResponse;
import com.getlocals.getlocals.business.item.Item;
import com.getlocals.getlocals.business.item.ItemRepository;
import com.getlocals.getlocals.role.Role;
import com.getlocals.getlocals.role.RoleRepository;
import com.getlocals.getlocals.user.User;
import com.getlocals.getlocals.user.UserRepository;
import com.getlocals.getlocals.user.UserService;
import com.getlocals.getlocals.utils.CustomEnums;
import com.getlocals.getlocals.utils.DTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

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


    public AuthenticationResponse registerBusiness(DTO.BusinessRegisterDTO businessRegisterDTO, String jwtToken) {
        // Get the logged-in user because only owner can create a business
        User owner = userService.getLoggedInUserEntity();

        // Create the business
        Business business = Business.builder()
                .logo(businessRegisterDTO.getLogo())
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

    public String addMenuItems(DTO.AddItemBusinessDTO itemsDTO) {
        // Get the business the items are going to be assigned to.
        Business business = businessRepository.getReferenceById(itemsDTO.getBusiness());
        Collection<Item> businessItems = business.getItems();

        // Creating items from the DTO
        itemsDTO.getItems().forEach(item -> {
            Item.ItemBuilder itemBuilder = Item.builder()
                    .name(item.getName())
                    .currency(item.getCurrency())
                    .price(item.getPrice());
            Item savedItem = itemRepository.save(itemBuilder.build());
            businessItems.add(savedItem);
        });

        // Assigning all the items to the business
        business.setItems(businessItems);
        return business.getId();
    }

    public Business getBusinessById(String businessId) {
        return businessRepository.getReferenceById(businessId);
    }
}
