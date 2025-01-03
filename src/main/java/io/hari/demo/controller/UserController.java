package io.hari.demo.controller;

import io.hari.demo.entity.User;
import io.hari.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public User createUser(@RequestBody User user) {
        System.out.println("Creating user: " + user);
        return userService.create(user);
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id) {
        System.out.println("Fetching user with ID: " + id);
        return userService.getUserById(id);
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User user) {
        System.out.println("Updating user with ID: " + id);
        return userService.updateUser(id, user);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        System.out.println("Deleting user with ID: " + id);
        userService.deleteUser(id);
    }

    @GetMapping
    public List<User> getAllUsers() {
        System.out.println("Fetching all users");
        return userService.getAllUsers();
    }
}
