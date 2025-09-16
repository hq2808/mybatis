CREATE TABLE users (
   id BIGINT AUTO_INCREMENT PRIMARY KEY,
   username VARCHAR(100) NOT NULL,
   email VARCHAR(100),
   age INT,
   created_at DATETIME DEFAULT CURRENT_TIMESTAMP
);

-- Profile table (OneToOne với user)
CREATE TABLE profiles (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    full_name VARCHAR(100),
    address VARCHAR(200),
    FOREIGN KEY (user_id) REFERENCES users(id)
);

-- Role table
CREATE TABLE roles (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL
);

-- User_Role join table (ManyToMany)
CREATE TABLE user_roles (
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    PRIMARY KEY(user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (role_id) REFERENCES roles(id)
);

-- Thêm người dùng
INSERT INTO users (username, email, age) VALUES
                                             ('john_doe', 'john@example.com', 28),
                                             ('jane_smith', 'jane@example.com', 32),
                                             ('admin_user', 'admin@example.com', 35);

-- Thêm profile (OneToOne)
INSERT INTO profiles (user_id, full_name, address) VALUES
                                                       (1, 'John Doe', '123 Main St'),
                                                       (2, 'Jane Smith', '456 Elm St'),
                                                       (3, 'Admin User', '789 Admin Rd');

-- Thêm vai trò (roles)
INSERT INTO roles (name) VALUES
                             ('USER'),
                             ('ADMIN'),
                             ('MODERATOR');

-- Gán vai trò cho người dùng (user_roles)
-- john_doe: USER
-- jane_smith: USER, MODERATOR
-- admin_user: ADMIN

INSERT INTO user_roles (user_id, role_id) VALUES
                                              (1, 1),  -- john_doe -> USER
                                              (2, 1),  -- jane_smith -> USER
                                              (2, 3),  -- jane_smith -> MODERATOR
                                              (3, 2);  -- admin_user -> ADMIN


-- Stored Procedure get users by age
CREATE PROCEDURE get_users_by_age(IN age_param INT)
BEGIN
    SELECT id, username, email, age, created_at
    FROM users
    WHERE age >= age_param
    ORDER BY created_at DESC;
END;