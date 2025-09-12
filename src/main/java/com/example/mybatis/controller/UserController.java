package com.example.mybatis.controller;

import com.example.mybatis.model.User;
import com.example.mybatis.response.PageResponse;
import com.example.mybatis.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getAll() {
        return userService.getAll();
    }

    @GetMapping("/{id}")
    public User getById(@PathVariable Long id) {
        return userService.getById(id);
    }

    @GetMapping("/page")
    public PageResponse<User> getUsersByPage(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return userService.getUsersByPage(page, size);
    }

    @PostMapping
    public String create(@RequestBody User user) {
        userService.create(user);
        return "User created!";
    }

    @PutMapping("/{id}")
    public String update(@PathVariable Long id, @RequestBody User user) {
        user.setId(id);
        userService.update(user);
        return "User updated!";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        userService.delete(id);
        return "User deleted!";
    }
}