package com.getlocals.getlocals.utils;

import lombok.Getter;

public class CustomEnums {

    @Getter
    public enum BusinessServicesEnum {
        FOOD("FOOD"),
        PERSONAL_CARE("PERSONAL_CARE"),
        OTHER("OTHER");

        private final String val;

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

    @Getter
    public enum BusinessTiming {
        OPEN("OPEN"),
        CLOSE("CLOSE");

        private final String val;

        BusinessTiming(String val) {
            this.val = val;
        }
    }
}
