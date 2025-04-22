// File: src/main/java/com/example/bookmanagement/repository/UserRepository.java
package com.example.bookmanagement.repository;

import com.example.bookmanagement.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

/** Repository cho bảng User */
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username); // Tìm user theo username
    boolean existByUsername(String username);   // Kiểm tra tồn tại user theo username (dùng để validate đăng ký)
}

