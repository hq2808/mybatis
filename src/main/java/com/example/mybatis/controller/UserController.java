package com.example.mybatis.controller;

import com.example.mybatis.dto.UserDTO;
import com.example.mybatis.model.User;
import com.example.mybatis.request.UserFilter;
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
    public UserDTO getById(@PathVariable Long id) {
        return userService.getById(id);
    }

    @PostMapping("/page")
    public PageResponse<UserDTO> getUsersByPage(@RequestBody UserFilter userFilter) {
        return userService.getUsersByPage(userFilter);
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

    @GetMapping("/age/{age}")
    public List<UserDTO> getById(@PathVariable int age) {
        return userService.getUsersByAge(age);
    }
}