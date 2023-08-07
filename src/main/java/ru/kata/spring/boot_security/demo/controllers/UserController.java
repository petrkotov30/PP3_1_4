package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kata.spring.boot_security.demo.models.User;

@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping("/currentUser")
    public ResponseEntity<User> showUserInfo() {
        System.out.println("test");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok((User) authentication.getPrincipal());
    }
}
