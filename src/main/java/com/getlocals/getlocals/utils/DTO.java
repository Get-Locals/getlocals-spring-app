package com.getlocals.getlocals.utils;

import lombok.*;

import java.sql.Blob;
import java.util.Collection;
import java.util.List;

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
        private String aboutUs;

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

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class BusinessDTO {
        private String name;
        private String serviceType;
        private String doordashURL;
        private String skipURL;
        private String uberURL;
        private UserDTO owner;
        private UserDTO manager;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class BusinessInfoDTO {
        private String name;
        private String id;
    }


    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UserProfileDTO {
        private UserDTO user;
        private List<BusinessInfoDTO> businesses;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class StringMessage {
        private String message;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class BusinessImageDTO {
        private String id;
        private String name;
        private String image;
        private String extension;
        private CustomEnums.BusinessImageTypeEnum type;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ItemCategoryDTO {
        private String id;
        private String name;
    }

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MenuDTO {
        private String id;
        private String name;
        private float price;
        private String imageId;
        private String currency;
        private String ingredients;
        private String description;
        private BusinessImageDTO image;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class BusinessReviewDTO {
        private String id;
        private Float rating;
        private String comment;
        private String email;
        private String fullName;
        private String imageId;
        private Long phone;
        private String date;
    }
}
