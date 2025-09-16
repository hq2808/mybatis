package com.example.mybatis.service;

import com.example.mybatis.dto.ProfileDTO;
import com.example.mybatis.dto.RoleDTO;
import com.example.mybatis.dto.UserDTO;
import com.example.mybatis.dto.UserWithProfileRoleDTO;
import com.example.mybatis.mapper.UserMapper;
import com.example.mybatis.model.User;
import com.example.mybatis.request.UserFilter;
import com.example.mybatis.response.PageResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserMapper userMapper;

    public UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public List<User> getAll() {
        return userMapper.findAll();
    }

    public UserDTO getById(Long id) {
//        return userMapper.findById(id);
        List<UserWithProfileRoleDTO> rows = userMapper.findById(id);
        if (rows == null || rows.isEmpty()) return null;

        UserWithProfileRoleDTO first = rows.get(0);

        UserDTO user = new UserDTO();
        user.setId(first.getUserId());
        user.setUsername(first.getUsername());
        user.setEmail(first.getEmail());
        user.setAge(first.getAge());
        user.setCreatedAt(first.getCreatedAt());

        // profile
        if (first.getProfileId() != null) {
            ProfileDTO profile = new ProfileDTO();
            profile.setId(first.getProfileId());
            profile.setFullName(first.getProfileFullName());
            profile.setAddress(first.getProfileAddress());
            user.setProfile(profile);
        }

        // roles (gom groupBy)
        List<RoleDTO> roles = rows.stream()
                .filter(r -> r.getRoleId() != null)
                .map(r -> {
                    RoleDTO role = new RoleDTO();
                    role.setId(r.getRoleId());
                    role.setName(r.getRoleName());
                    return role;
                })
                .distinct()
                .collect(Collectors.toList());

        user.setRoles(roles);

        return user;
    }

    public PageResponse<UserDTO> getUsersByPage(UserFilter userFilter) {
        if (userFilter.getPage() < 1) userFilter.setPage(1);   // tránh âm
        if (userFilter.getSize() < 1) userFilter.setSize(10);  // default fallback

        int offset = (userFilter.getPage() - 1) * userFilter.getSize();
        long total = userMapper.countUsers(userFilter);

        Map<String, Object> params = new HashMap<>();
        params.put("size", userFilter.getSize());
        params.put("offset", offset);
        params.put("keySearch", userFilter.getKeySearch());

        // rows phẳng
        List<UserWithProfileRoleDTO> rows = userMapper.getUsersByPage(params);

        Map<Long, List<UserWithProfileRoleDTO>> groupMap = rows.stream()
                .collect(Collectors.groupingBy(UserWithProfileRoleDTO::getUserId));

        List users = groupMap.values().stream()
                .map(list -> {
                    UserWithProfileRoleDTO first = list.get(0);
                    UserDTO user = new UserDTO();
                    user.setId(first.getUserId());
                    user.setUsername(first.getUsername());
                    user.setEmail(first.getEmail());
                    user.setAge(first.getAge());
                    user.setCreatedAt(first.getCreatedAt());

                    if (first.getProfileId() != null) {
                        ProfileDTO profile = new ProfileDTO();
                        profile.setId(first.getProfileId());
                        profile.setFullName(first.getProfileFullName());
                        profile.setAddress(first.getProfileAddress());
                        user.setProfile(profile);
                    }


                    List<RoleDTO> roleDTOS = list.stream()
                            .filter(r -> r.getRoleId() != null)
                            .map(r -> {
                                RoleDTO roleDTO = new RoleDTO();
                                roleDTO.setId(r.getRoleId());
                                roleDTO.setName(r.getRoleName());
                                return roleDTO;
                            }).collect(Collectors.toList());
                    user.setRoles(roleDTOS);
                    return user;
                })
                .collect(Collectors.toList());

        return new PageResponse<>(users, userFilter.getPage(), userFilter.getSize(), total);
    }


    @Transactional
    public void create(User user) {
        user.setCreatedAt(LocalDateTime.now());
        userMapper.insert(user);
//        List<User> users = new ArrayList<>();
//        for (int i = 1; i < 100; i++) {
//            User u = user.clone();
//            u.setUsername(user.getUsername() + i);
//            u.setEmail(user.getUsername() + i + "@gmail.com");
//            users.add(u);
//        }
//        userMapper.insertBatch(users);
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