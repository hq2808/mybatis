package com.example.mybatis.dto;

import lombok.Data;
import java.util.List;

@Data
public class UserDTO {
    private Long id;
    private String username;
    private String email;
    private Integer age;
    private String createdAt;

    private ProfileDTO profile;
    private List<RoleDTO> roles;
}