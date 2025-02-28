package com.getlocals.getlocals;

import com.getlocals.getlocals.business.entities.BusinessType;
import com.getlocals.getlocals.business.repositories.BusinessTypeRepository;
import com.getlocals.getlocals.role.Role;
import com.getlocals.getlocals.role.RoleRepository;
import com.getlocals.getlocals.user.User;
import com.getlocals.getlocals.user.UserRepository;
import com.getlocals.getlocals.utils.CustomEnums;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;


@Slf4j
@SpringBootApplication
public class GetLocalsApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(GetLocalsApplication.class, args);

        createRoles(context);
        createAdmins(context);
        createBusinessTypes(context);
    }

    private static void createBusinessTypes(ConfigurableApplicationContext context) {
        log.info("Creating Business Types if does not exist.");
        BusinessTypeRepository businessTypeRepository = context.getBean(BusinessTypeRepository.class);

        if (businessTypeRepository.findAll().isEmpty()) {
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
        }
    }

    private static void createAdmins(ConfigurableApplicationContext context) {
        log.info("Creating Admin if does not exist");
        UserRepository userRepository = context.getBean(UserRepository.class);
        Optional<User> admin = userRepository.findByEmail("kainths-admin@getlocals.ca");

        if (admin.isPresent()) {
            return;
        }
        RoleRepository roleRepository = context.getBean(RoleRepository.class);
        PasswordEncoder encoder = context.getBean(PasswordEncoder.class);

        var user = User.builder()
                .isActive(true)
                .isAdmin(true)
                .name("admin")
                .email("kainths-admin@getlocals.ca")
                .password(encoder.encode("getlocals_neo"))
                .roles(List.of(
                        roleRepository.findByRole(CustomEnums.RolesEnum.ADMIN.getVal()).orElseThrow())
                ).build();
        userRepository.save(user);
    }

    private static void createRoles(ConfigurableApplicationContext context) {
        log.info("Creating Roles if does not exist");
        RoleRepository roleRepository = context.getBean(RoleRepository.class);
        if (roleRepository.findAll().isEmpty()) {
            List<Role> roles = new LinkedList<>();
            CustomEnums.RolesEnum[] roleEnums = CustomEnums.RolesEnum.values();
            for (CustomEnums.RolesEnum userRole : roleEnums) {
                roles.add(Role.builder().role(userRole.getVal()).build());
            }
            roleRepository.saveAll(roles);
        }

    }


}
