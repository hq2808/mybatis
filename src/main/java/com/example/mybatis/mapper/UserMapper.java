package com.example.mybatis.mapper;

import com.example.mybatis.model.User;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface UserMapper {
    User findById(Long id);
    List<User> findAll();
    int insert(User user);
    int insertBatch(List<User> users);
    int update(User user);
    int deleteById(Long id);

    List<User> getUsersByPage(int size, int offset);

    long countUsers();
}