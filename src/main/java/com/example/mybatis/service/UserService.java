package com.example.mybatis.service;

import com.example.mybatis.mapper.UserMapper;
import com.example.mybatis.model.User;
import com.example.mybatis.request.UserFilter;
import com.example.mybatis.response.PageResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private final UserMapper userMapper;

    public UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public List<User> getAll() {
        return userMapper.findAll();
    }

    public User getById(Long id) {
        return userMapper.findById(id);
    }

    public PageResponse<User> getUsersByPage(UserFilter userFilter) {
        if (userFilter.getPage() < 1) userFilter.setPage(1);   // tránh âm
        if (userFilter.getSize() < 1) userFilter.setSize(10);  // default fallback

        int offset = (userFilter.getPage() - 1) * userFilter.getSize();
        long total = userMapper.countUsers(userFilter);
        List<User> users = userMapper.getUsersByPage(userFilter, offset);
        return new PageResponse<>(users, userFilter.getPage(), userFilter.getSize(), total);
    }


    @Transactional
    public void create(User user) {
        user.setCreatedAt(LocalDateTime.now());
//        userMapper.insert(user);
        List<User> users = new ArrayList<>();
        for (int i = 1; i < 100; i++) {
            User u = user.clone();
            u.setUsername(user.getUsername() + i);
            u.setEmail(user.getUsername() + i + "@gmail.com");
            users.add(u);
        }
        userMapper.insertBatch(users);
    }

    @Transactional
    public void update(User user) {
        userMapper.update(user);
    }

    @Transactional
    public void delete(Long id) {
        userMapper.deleteById(id);
    }
}