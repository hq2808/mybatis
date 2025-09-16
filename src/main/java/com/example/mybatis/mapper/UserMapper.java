package com.example.mybatis.mapper;

import com.example.mybatis.dto.UserDTO;
import com.example.mybatis.dto.UserWithProfileRoleDTO;
import com.example.mybatis.model.User;
import com.example.mybatis.request.UserFilter;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserMapper {
//    User findById(Long id);
    List<UserWithProfileRoleDTO> findById(@Param("id") Long id);

    List<User> findAll();
    int insert(User user);
    int insertBatch(List<User> users);
    int update(User user);
    int deleteById(Long id);

    List<UserWithProfileRoleDTO> getUsersByPage(Map<String, Object> params);

    long countUsers(UserFilter userFilter);

    List<UserDTO> callGetUsersByAge(@Param("ageParam") int age);

    void callCountUsersByAge(Map<String, Object> params);
}