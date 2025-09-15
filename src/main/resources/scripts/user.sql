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