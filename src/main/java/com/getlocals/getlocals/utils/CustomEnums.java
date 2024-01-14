package com.getlocals.getlocals.utils;

import lombok.Getter;

public class CustomEnums {

    @Getter
    public enum BusinessServicesEnum {
        FOOD("FOOD"),
        PERSONAL_CARE("PERSONAL_CARE"),
        OTHER("OTHER")
        ;

        String val;

        BusinessServicesEnum(String val) {
            this.val = val;
        }

    }

    @Getter
    public enum RolesEnum {
        OWNER("OWNER"),
        USER("USER"),
        MANAGER("MANAGER"),
        ADMIN("ADMIN");

        private final String val;

        RolesEnum(String val) {
            this.val = val;
        }
    }
}
