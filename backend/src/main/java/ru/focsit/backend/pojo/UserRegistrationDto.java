package ru.focsit.backend.pojo;

import lombok.Data;

@Data
public class UserRegistrationDto {
    private String userLogin;
    private String userPassword;
    private String userPasswordConfirm;
    private String userEmail;
    private Long userRoleId;
    private Long userCountryId;
}
