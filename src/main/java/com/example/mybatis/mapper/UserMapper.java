package com.example.mybatis.mapper;

import com.example.mybatis.model.User;
import com.example.mybatis.request.UserFilter;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {
    User findById(Long id);
    List<User> findAll();
    int insert(User user);
    int insertBatch(List<User> users);
    int update(User user);
    int deleteById(Long id);

    List<User> getUsersByPage(@Param("filter") UserFilter filter,
                              @Param("offset") int offset);

    long countUsers(UserFilter userFilter);
}