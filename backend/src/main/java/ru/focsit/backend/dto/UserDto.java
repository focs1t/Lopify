package ru.focsit.backend.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDto {
    private Long userId;

    private String username;

    private String userEmail;

    private String password;

    private Long userCountryId;

    private Long userRoleId;
}

