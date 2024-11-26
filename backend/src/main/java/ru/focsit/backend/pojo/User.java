package ru.focsit.backend.pojo;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;

@Entity
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long USERID;

    @Column(nullable = false, unique = true)
    private String USERLOGIN;

    @Column(nullable = false)
    private String USERPASSWORD;

    @Column(nullable = false, unique = true)
    private String USEREMAIL;

    @ManyToOne
    @JoinColumn(name = "USER_ROLE_ID")
    private Role ROLE;

    @ManyToOne
    @JoinColumn(name = "USER_COUNTRY_ID")
    private Country COUNTRY;
}

