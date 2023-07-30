package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.RoleServiceImpl;
import ru.kata.spring.boot_security.demo.services.UserService;
import ru.kata.spring.boot_security.demo.services.UserServiceImpl;

import javax.validation.Valid;
import java.security.Principal;


@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final RoleServiceImpl roleService;

    public AdminController(UserServiceImpl userServiceImpl, @Lazy RoleServiceImpl roleServiceImpl) {
        this.userService = userServiceImpl;
        this.roleService = roleServiceImpl;
    }

    @GetMapping()
    public String showAllUsers(Model model, Principal principal) {
        model.addAttribute("users", userService.findAllUser());
        model.addAttribute("admin", userService.getUserByUsername(principal.getName()));
        model.addAttribute("roles", roleService.findAll());
        return "users";
    }

    @GetMapping("/info")
    public String showAdminInfo(Model model, Principal principal) {
        model.addAttribute("adminUser", userService.getUserByUsername(principal.getName()));
        return "adminUser";
    }


    @GetMapping("/new")
    public String newUser(@ModelAttribute("user") User user, Model model, Principal principal) {
        model.addAttribute("admin", userService.getUserByUsername(principal.getName()));
        model.addAttribute("roles", roleService.findAll());
        return "new";
    }

    @PostMapping()
    public String createUser(@ModelAttribute("user") @Valid User user, BindingResult result) {
        if (result.hasErrors()) {
            return "new";
        }
        userService.add(user);
        return "redirect:/admin";
    }

//    @GetMapping("/{id}/edit")
//    public String edit(Model model, @PathVariable Long id) {
//        model.addAttribute("user", userService.findUser(id));
//        model.addAttribute("roles", roleService.findAll());
//        return "edit";
//    }

    @GetMapping(value = "/{id}/update")
    public String update(Model model, @PathVariable("id") Integer id) {
        model.addAttribute("user", userService.findUser(id));
        return "update";
    }

    @PatchMapping("/{id}")
    public String updateUser(@ModelAttribute("user") User user, @PathVariable("id") Long id, BindingResult result) {
        if (result.hasErrors()) {
            return "edit";
        }
        userService.update(user, id);
        return "redirect:/admin";
    }

    @DeleteMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        userService.deleteById(id);
        return "redirect:/admin";
    }
}