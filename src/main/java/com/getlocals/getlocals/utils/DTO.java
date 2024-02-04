package com.getlocals.getlocals.utils;

import lombok.*;

import java.util.Collection;

public class DTO {

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UserRegisterDTO {
        private String name;
        private String email;
        private String password;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UserAuthDTO {
        private String email;
        private String password;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UserDTO {
        private String email;
        private String name;
        private Collection<String> roles;
        private Boolean isActive;
    }


    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class BusinessRegisterDTO {

        private String name;
        private String location;
        private String currency;
        private byte[] logo;
        private String businessType;
    }


    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AddItemDTO {

        private String name;
        private float price;
        private String currency;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AddItemBusinessDTO {
        Collection<AddItemDTO> items;
        private String business;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class BusinessTypeDTO {
        private String value;
        private String label;
    }
}
