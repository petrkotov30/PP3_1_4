package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.UserService;

@Controller
public class WelcomeController {
    final UserService userService;

    public WelcomeController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String getHomePage() {
        if (userService.findAllUser().isEmpty()) {
            addFirstUser();
        }
        return "index";
    }

    @GetMapping("/admin")
    public String printAdminPage (ModelMap model, Authentication authentication){
        model.addAttribute("nawUser",(User)authentication.getPrincipal());
        return "admin/aUsers";
    }

    @GetMapping("/user")
    public String printUserPage (ModelMap model, Authentication authentication){
        model.addAttribute("nawUser",(User)authentication.getPrincipal());
        return "admin/aUsers";
    }

    public void addFirstUser(){
        User user = new User();
        user.setUsername("admin");
        user.setSurname("admin");
        user.setAge(25);
        user.setEmail("admin@mail.ru");
        user.setPassword("admin");
        user.getRoles().add(new Role( "ROLE_ADMIN"));
        userService.add(user);
    }
}
