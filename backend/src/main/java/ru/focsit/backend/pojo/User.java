package ru.focsit.backend.pojo;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userId")
    private Long userId;

    @NotBlank(message = "Логин пользователя обязателен")
    @Size(max = 100, message = "Логин пользователя должен быть меньше 100 символов")
    @Column(name = "userLogin", nullable = false, unique = true)
    private String userLogin;

    @NotBlank(message = "Пароль пользователя обязателен")
    @Size(max = 100, message = "Пароль пользователя должен быть меньше 100 символов")
    @Column(name = "userPassword", nullable = false)
    private String userPassword;

    @Transient
    @Column(name = "userPasswordConfirm")
    private String userPasswordConfirm;

    @Email(message = "Неверный формат email")
    @NotBlank(message = "Email пользователя обязателен")
    @Size(max = 100, message = "Email пользователя должен быть меньше 100 символов")
    @Column(name = "userEmail", nullable = false, unique = true)
    private String userEmail;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userRoleId")
    @JsonBackReference
    private Role userRole;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userCountryId")
    private Country userCountry;

    @OneToMany(mappedBy = "playlistUser", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Playlist> playlists;

    @OneToMany(mappedBy = "commentUser", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Comment> comments;

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userLogin='" + userLogin + '\'' +
                ", userPassword='" + userPassword + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", userRole=" + (userRole != null ? userRole.getRoleId() : null) +
                ", userCountry=" + (userCountry != null ? userCountry.getCountryId() : null) +
                '}';
    }
}