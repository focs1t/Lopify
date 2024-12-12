package ru.focsit.backend.pojo;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "roles")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "roleId")
    private Long roleId;

    @NotBlank(message = "Название роли обязательно")
    @Size(max = 100, message = "Название роли должно быть меньше 100 символов")
    @Column(name = "roleName", nullable = false, unique = true)
    private String roleName;

    @OneToMany(mappedBy = "userRole", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    private List<User> users;

    @Override
    public String toString() {
        return "Role{" +
                "roleId=" + roleId +
                ", roleName='" + roleName + '\'' +
                '}';
    }

    @PreRemove
    @PreUpdate
    private void checkProtectedRole() {
        if (List.of("ROLE_USER", "ROLE_ADMIN", "ROLE_MODERATOR").contains(roleName)) {
            throw new RuntimeException("Cannot delete or update protected role");
        }
    }
}