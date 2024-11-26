package ru.focsit.backend.pojo;

import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;

@Entity
@Getter
@Setter
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ROLEID;

    @Column(nullable = false, unique = true)
    private String ROLENAME;
}
