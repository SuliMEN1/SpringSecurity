package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserDetailsServiceImpl;

import java.util.List;


@Controller
@RequestMapping("/user")
public class UserController {

    public final UserDetailsServiceImpl userService;
    private final RoleService roleService;

    @Autowired
    public UserController(UserDetailsServiceImpl userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping()
    public String findUser(Model model) {
        model.addAttribute("user", userService.loadUserByUsername(userService.getCurrentUsername()));
        return "show";
    }


    @GetMapping("/updateUser/{id}")
    public String updateUserForm(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userService.get(id));
        return "updateUser";
    }

    @PostMapping("/updateUser")
    public String updateUser(@ModelAttribute("user") User user) {
        userService.updateUser(user);
        return "redirect:/logout";
    }
}
