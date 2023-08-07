package ru.kata.spring.boot_security.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repostitories.UserRepository;
import ru.kata.spring.boot_security.demo.utill.UserNotFoundException;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, @Lazy PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username + " не найден");
        }
        return user;
    }

    @Override
    public List<User> findAllUser() {
        return userRepository.findAll();
    }



    @Transactional
    @Override
    public void add(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    @Override
    public User findUser(Long id) {
        Optional<User> foundUser = userRepository.findById(id);
        return foundUser.orElseThrow(UserNotFoundException::new);
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    @Transactional
    @Override
    public void update(User userUpdated) {
        User user = userRepository.findUserById(userUpdated.getId());
        userUpdated.setAge(userUpdated.getAge());
        userUpdated.setUsername(userUpdated.getUsername());
        userUpdated.setSurname(userUpdated.getSurname());
        userUpdated.setEmail(userUpdated.getEmail());
        userUpdated.setRoles(userUpdated.getRoles());
        if (userUpdated.getPassword().equals("")) {
            userUpdated.setPassword(user.getPassword());
        } else {
            userUpdated.setPassword(passwordEncoder.encode(userUpdated.getPassword()));
        }
        userRepository.save(userUpdated);
    }
}
