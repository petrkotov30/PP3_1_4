package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.RoleServiceImpl;
import ru.kata.spring.boot_security.demo.services.UserServiceImpl;

import javax.validation.Valid;


@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserServiceImpl userServiceImpl;
    private final RoleServiceImpl roleServiceImpl;

    public AdminController(UserServiceImpl userServiceImpl, @Lazy RoleServiceImpl roleServiceImpl) {
        this.userServiceImpl = userServiceImpl;
        this.roleServiceImpl = roleServiceImpl;
    }

    @GetMapping()
    public String showAllUsers(Model model) {
        model.addAttribute("users", userServiceImpl.showAllUser());
        return "users";
    }

    @GetMapping("/new")
    public String newUser(@ModelAttribute("user") User user, Model model) {
        model.addAttribute("roles", roleServiceImpl.findAll());
        return "new";
    }

    @PostMapping()
    public String createUser(@ModelAttribute("user") @Valid User user, BindingResult result) {
        if (result.hasErrors()) {
            return "new";
        }
        userServiceImpl.add(user);
        return "redirect:/admin";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable Long id) {
        model.addAttribute("user", userServiceImpl.findUser(id));
        model.addAttribute("roles", roleServiceImpl.findAll());
        return "edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("user") @Valid User user, @PathVariable("id") Long id, BindingResult result) {
        if (result.hasErrors()) {
            return "edit";
        }
        userServiceImpl.update(user, id);
        return "redirect:/admin";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        userServiceImpl.deleteById(id);
        return "redirect:/admin";
    }
}