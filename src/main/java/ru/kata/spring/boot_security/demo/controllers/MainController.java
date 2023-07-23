package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.UserService;

import java.security.Principal;

@RestController
public class MainController {
    private final UserService userService;

    @Autowired
    public MainController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String homePage() {
        return "hello world";
    }

    @GetMapping("/authenticated")
    public String pageForAuthenticatedUsers(Principal principal) {
        User user = userService.findByUsername(principal.getName());
        return "secured part of web service: " + user.getUsername() + " " + user.getEmail();
    }

    @GetMapping("/only_for_admins")
    public String pageOnlyForAdmins() {
        return "only_for_admins !";
    }

    @GetMapping("/read_profile")
    public String pageReadProfile() {
        return "read profile page";
    }


    @GetMapping("/showUserInfo")
    public String showUSerInfo(Principal principal) {
        User user = userService.findByUsername(principal.getName());
        return user.toString();
    }
}