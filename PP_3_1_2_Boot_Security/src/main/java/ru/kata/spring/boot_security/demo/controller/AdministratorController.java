package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserDetailsServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdministratorController {
    public final UserDetailsServiceImpl userService;
    private final RoleService roleService;

    @Autowired
    public AdministratorController(UserDetailsServiceImpl userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }
    @GetMapping()
    public String getAllUsers(Model model) {
        model.addAttribute("allUsers", userService.listUser());
        return "admin";
    }
    @GetMapping("/delete/{id}")
    public  String deleteUser(@PathVariable("id") Long id) {
        userService.delete(id);
        return "redirect:/admin";
    }
    @GetMapping("/update/{id}")
    public String updateUserForm(@PathVariable("id") Long id, Model model) {
        model.addAttribute("update", userService.get(id));
        model.addAttribute("allRole", roleService.getRoleList());
        return "update";
    }

    @PostMapping("/update")
    public String updateUser(@ModelAttribute("update") User user, @RequestParam("roleList") List<String> role) {
        user.setRoles(userService.getSetOfRoles(role));
        userService.update(user);
        return "redirect:/admin";
    }

}
