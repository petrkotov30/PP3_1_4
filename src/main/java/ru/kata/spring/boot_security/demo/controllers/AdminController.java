package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;

import java.util.List;


@RestController
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;

    public AdminController(UserService userService, @Lazy RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userService.findAllUser();
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> showOne(@PathVariable("id") Long id) {
        return ResponseEntity.ok(userService.findUser(id));
    }

    @GetMapping("/roles")
    public ResponseEntity<List<Role>> getAllRoles() {
        return ResponseEntity.ok(roleService.findAll());
    }

    @PostMapping("/users/new")
    public ResponseEntity<HttpStatus> createUser(User user) {
        userService.add(user);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    @GetMapping("/currentUser")
    public ResponseEntity<User> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok((User) authentication.getPrincipal());
    }

    @PatchMapping("/users/edit")
    public ResponseEntity<HttpStatus> updateUser(User user) {
        userService.update(user);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/users/delete/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable Long id) {
        userService.deleteById(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}