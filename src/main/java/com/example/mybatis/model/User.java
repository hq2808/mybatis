package com.example.mybatis.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class User implements Cloneable {
    private Long id;
    private String username;
    private String email;
    private Integer age;
    private LocalDateTime createdAt;

    // OneToOne
    private Profile profile;

    // ManyToMany
    private List<Role> roles;

    @Override
    public User clone() {
        try {
            User clone = (User) super.clone();
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}