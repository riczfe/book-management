// File: src/main/java/com/example/bookmanagement/service/UserService.java
package com.example.bookmanagement.service;

import com.example.bookmanagement.model.User;
import com.example.bookmanagement.model.Role;
import com.example.bookmanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;

    /** Đăng ký người dùng mới (mặc định role USER) */
    public User registerUser(String username, String rawPassword) {
        if (userRepo.existByUsername(username)) {
            return null; // Username đã tồn tại, trả về null để báo lỗi
        }
        // Mã hóa mật khẩu trước khi lưu
        String encodedPassword = passwordEncoder.encode(rawPassword);
        // Tạo user mới với role USER
        User newUser = new User(username, encodedPassword, Role.USER);
        return userRepo.save(newUser);
    }
    /** Xác thực đăng nhập: nếu username/password đúng thì trả về User, sai thì trả về null */

}