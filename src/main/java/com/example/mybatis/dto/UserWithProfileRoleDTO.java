package com.example.mybatis.dto;

import lombok.Data;

@Data
public class UserWithProfileRoleDTO {
    private Long userId;
    private String username;
    private String email;
    private Integer age;
    private String createdAt;

    private Long profileId;
    private String profileFullName;
    private String profileAddress;

    private Long roleId;
    private String roleName;
}