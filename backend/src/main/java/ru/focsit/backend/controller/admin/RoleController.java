package ru.focsit.backend.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.focsit.backend.pojo.Role;
import ru.focsit.backend.repository.RoleRepository;

import java.util.List;

@Controller
@RequestMapping("/roles")
public class RoleController {

    @Autowired
    private RoleRepository roleRepository;

    // Отображение списка ролей
    @GetMapping
    public String getAllRoles(Model model) {
        List<Role> roles = roleRepository.findAll();
        model.addAttribute("roles", roles);
        return "admin/roles/list";
    }

    // Формуляр для создания новой роли
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("role", new Role());
        return "admin/roles/new";
    }

    // Создание новой роли
    @PostMapping
    public String createRole(@ModelAttribute Role role) {
        role.setRoleName("ROLE_" + role.getRoleName());
        roleRepository.save(role);
        return "redirect:/roles";
    }

    // Формуляр для редактирования роли
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Role role = roleRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid role Id:" + id));
        model.addAttribute("role", role);
        return "admin/roles/edit";
    }

    // Обновление роли
    @PostMapping("/update/{id}")
    public String updateRole(@PathVariable Long id, @ModelAttribute Role roleDetails) {
        Role role = roleRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid role Id:" + id));
        role.setRoleName(roleDetails.getRoleName());
        roleRepository.save(role);
        return "redirect:/roles";
    }

    // Удаление роли
    @GetMapping("/delete/{id}")
    public String deleteRole(@PathVariable Long id) {
        Role role = roleRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid role Id:" + id));
        roleRepository.delete(role);
        return "redirect:/roles";
    }
}

