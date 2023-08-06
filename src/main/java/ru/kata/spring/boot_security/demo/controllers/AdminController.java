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
import ru.kata.spring.boot_security.demo.utill.UserNotCreatedException;
import ru.kata.spring.boot_security.demo.utill.UserNotFoundException;
import ru.kata.spring.boot_security.demo.utill.UserResponseError;

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
        System.out.println(getAllRoles());
        userService.update(user);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/users/delete/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable Long id) {
        userService.deleteById(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
//
//    @ExceptionHandler
//    private ResponseEntity<UserResponseError> handleException(UserNotFoundException e) {
//        UserResponseError response = new UserResponseError(
//                "User with this id was not found!");
//        // В HTTP ответа будет (response) и статус в заголовке (404)
//        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND); // 404 not found
//    }
//
//    @ExceptionHandler
//    private ResponseEntity<UserResponseError> handleException(UserNotCreatedException e) {
//        UserResponseError response = new UserResponseError(
//                e.getMessage());
//        // В HTTP ответа будет (response) и статус в заголовке (500)
//        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST); // 500
//    }
}


//        if (bindingResult.hasErrors()) {
//            StringBuilder message = new StringBuilder();
//            List<FieldError> listError = bindingResult.getFieldErrors();
//            for (FieldError fieldError : listError) {
//                message.append(fieldError.getField()).append(" - ")
//                        .append(fieldError.getDefaultMessage());
//            }
//            throw new UserNotCreatedException(message.toString());
//        }