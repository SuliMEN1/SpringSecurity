package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserDetailsServiceImpl;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Controller
public class RegistrationController {
    private final UserDetailsServiceImpl userService;
    private final RoleService roleService;
    @Autowired
    public RegistrationController(UserDetailsServiceImpl userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("allRoles", roleService.getRoleList());
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(@ModelAttribute("user") User user, @RequestParam("role") List<String> role) {
        Collection<Role> roleList = userService.getSetOfRoles(role);
        user.setRoles(roleList);
        userService.add(user);
        return "redirect:/";
    }
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }
}
