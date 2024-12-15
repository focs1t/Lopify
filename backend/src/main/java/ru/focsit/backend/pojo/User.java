package ru.focsit.backend.pojo;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.sql.DataSource;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userId")
    private Long userId;

    @NotBlank(message = "Имя пользователя обязателен")
    @Size(max = 100, message = "Имя пользователя должно быть меньше 100 символов")
    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @NotBlank(message = "Пароль пользователя обязателен")
    @Size(max = 100, message = "Пароль пользователя должен быть меньше 100 символов")
    @Column(name = "userPassword", nullable = false)
    private String password;

    @Email(message = "Неверный формат email")
    @NotBlank(message = "Email пользователя обязателен")
    @Size(max = 100, message = "Email пользователя должен быть меньше 100 символов")
    @Column(name = "userEmail", nullable = false, unique = true)
    private String userEmail;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userRoleId")
    @JsonIdentityReference
    private Role userRole;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userCountryId")
    @JsonIdentityReference
    private Country userCountry;

    @OneToMany(mappedBy = "playlistUser", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    private List<Playlist> playlists;

    @OneToMany(mappedBy = "commentUser", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    private List<Comment> comments;

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", userRole=" + (userRole != null ? userRole.getRoleId() : null) +
                ", userCountry=" + (userCountry != null ? userCountry.getCountryId() : null) +
                '}';
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(userRole.getRoleName()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @PreRemove
    @PreUpdate
    private void checkProtectedUser() {
        if (Objects.equals("Lopify", username)) {
            throw new RuntimeException("Cannot delete or update protected user");
        }
    }
}