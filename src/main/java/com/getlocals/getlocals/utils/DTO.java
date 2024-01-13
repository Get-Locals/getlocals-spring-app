package com.getlocals.getlocals.utils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

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
}
