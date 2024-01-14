package com.getlocals.getlocals.utils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.List;

public class DTO {

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UserRegisterDTO {
        private String name;
        private String email;
        private String password;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UserAuthDTO {
        private String email;
        private String password;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UserDTO {
        private String email;
        private String name;
        private Collection<String> roles;
        private Boolean isActive;
    }


    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class BusinessRegisterDTO {

        private String name;
        private String location;
        private String currency;
        private byte[] logo;
    }


    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AddItemDTO {

        private String name;
        private float price;
        private String currency;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AddItemBusinessDTO {
        Collection<AddItemDTO> items;
        private String business;
    }
}
