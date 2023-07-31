package ru.kata.spring.boot_security.demo.services;

import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;

public interface UserService {
    void deleteById(Long id);

    void update(User userUpdate, Long id);

    void add(User user);

    List<User> findAllUser();

    User getUserByUsername(String username);
}
