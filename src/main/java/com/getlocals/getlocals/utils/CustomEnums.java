package com.getlocals.getlocals.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class CustomEnums {

    @Getter
    @AllArgsConstructor
    public enum RolesEnum {
        OWNER("OWNER"),
        USER("USER"),
        MANAGER("MANAGER"),
        ADMIN("ADMIN");

        private final String val;
    }

    @Getter
    @AllArgsConstructor
    public enum BusinessTimingEnum {
        OPEN("OPEN"),
        CLOSE("CLOSE");

        private final String val;
    }
    
    @Getter
    @AllArgsConstructor
    public enum BusinessImageTypeEnum {
        CAROUSEL("CAROUSEL"),
        MENU("MENU"),
        LOGO("LOGO"),
        EMPLOYEE("EMPLOYEE"),
        OWNER("OWNER");

        private final String val;
    }

    @Getter
    @AllArgsConstructor
    public enum BusinessFieldsEnum {
        ABOUT_US("ABOUT_US"),
        BUSINESS_TIMINGS("BUSINESS_TIMINGS"),
        URLS("URLS"),
        LOCATION("LOCATION");

        private final String val;
    }

    @Getter
    @AllArgsConstructor
    public enum BusinessEmployeeTypeEnum {
        OWNER("OWNER"),
        GENERAL_MANAGER("GENERAL_MANAGER"),
        DISTRICT_MANAGER("DISTRICT_MANAGER"),
        MANAGER("MANAGER"),
        EMPLOYEE("EMPLOYEE");

        private final String val;
    }
}
