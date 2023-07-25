package ru.kata.spring.boot_security.demo.repostitories;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WelcomeController {
    @GetMapping()
    public String homePage() {
        return "home_page";
    }
}
