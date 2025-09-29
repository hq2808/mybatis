package com.example.mybatis;

import com.example.mybatis.mapper.UserMapper;
import com.example.mybatis.model.User;
import com.example.mybatis.service.UserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)   // dùng MockitoExtension, KHÔNG dùng SpringExtension
public class UserServiceTest {
    @Mock
    private UserMapper userMapper;   // ✅ sẽ được mock

    @InjectMocks
    private UserService userService; // ✅ mock userMapper sẽ được inject

    private static User user;

    @BeforeAll
    static void beforeAll() {
        user = new User();
        user.setUsername("admin");
        user.setEmail("admin@gmail.com");
        user.setAge(18);
        user.setCreatedAt(LocalDateTime.now());
    }

    @Test
    void testGetById_found() {
        when(userMapper.findUserById(1L)).thenReturn(user);

        User result = userService.getUserById(1L);

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getUsername()).isEqualTo("admin");
        Assertions.assertThat(result.getEmail()).isEqualTo("admin@gmail.com");
    }

    @Test
    void testGetById_notFound() {
        when(userMapper.findUserById(99L)).thenReturn(null);

        User result = userService.getUserById(99L);

        Assertions.assertThat(result).isNull();
    }
}
