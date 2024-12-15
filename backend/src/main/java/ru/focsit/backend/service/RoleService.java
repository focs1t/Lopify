package ru.focsit.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.focsit.backend.pojo.Role;
import ru.focsit.backend.repository.RoleRepository;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    public Optional<Role> getRoleById(Long roleId) {
        return roleRepository.findById(roleId);
    }

    public Role createRole(@Valid Role role) {
        return roleRepository.save(role);
    }

    public Role updateRole(Long roleId, @Valid Role roleDetails) {
        Optional<Role> role = roleRepository.findById(roleId);
        if (role.isPresent()) {
            Role curRole = role.get();
            curRole.setRoleName(roleDetails.getRoleName());
            return roleRepository.save(curRole);
        }
        return null;
    }

    public void deleteRole(Long roleId) {
        roleRepository.deleteById(roleId);
    }

    public Optional<Role> getRoleByRoleName(String roleUser) {
        return getAllRoles().stream().filter(role -> role.getRoleName().contains(roleUser)).findFirst();
    }
}