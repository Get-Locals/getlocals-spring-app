package com.getlocals.getlocals.utils;

import lombok.*;

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
        private String logo;
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
    public static class StringValue {
        private String value;
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
        private String employeeName;
        private String imageId;
        private Long phone;
        private String date;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class BusinessTimingDTO {
        private String monday;
        private String tuesday;
        private String wednesday;
        private String thursday;
        private String friday;
        private String saturday;
        private String sunday;
        private String today;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ContactRequestDTO {
        private String id;
        private String fullName;
        private String email;
        private String message;
        private String subject;
        private String imageId;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class EmployeeInfoDTO {
        private String id;
        private String firstName;
        private String lastName;
        private String description;
        private String email;
        private Long phoneNo;
        private CustomEnums.BusinessEmployeeTypeEnum position;
        private String imageId;
        private BusinessImageDTO imageDTO;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class BusinessTemplateInfoDTO {
        private String id;
        private String templateId;

    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class BusinessContactInformation{
        private String email;
        private String phone1;
        private String phone2;
        private String address;
        private String instagramUrl;
        private String facebookUrl;
        private String youtubeUrl;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PublicBusinessInformation{
        private String name;
        private String aboutUs;
        private String ownerImageId;
    }
}
