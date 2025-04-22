// File: src/main/java/com/example/bookmanagement/controller/AuthController.java
package com.example.bookmanagement.controller;

import com.example.bookmanagement.model.User;
import com.example.bookmanagement.dto.UserDTO;
import com.example.bookmanagement.security.JwtTokenUtil;
import com.example.bookmanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    /** API Đăng ký */
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestParam String username, @RequestParam String password) {
        // Gọi service để đăng ký
        User newUser = userService.registerUser(username, password);
        if (newUser == null) {
            // Username đã tồn tại
            return ResponseEntity.badRequest().body("Username is already existed. Choose different one");
        }
        // Tạo DTO để trả về
        UserDTO userDTO = new UserDTO(newUser.getId(), newUser.getUsername(), newUser.getRole());
        return ResponseEntity.ok(userDTO);
    }

    /** API Đăng nhập */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String username, @RequestParam String password) {
        User user = userService.login(username, password);
        if (user == null) {
            // Sai username hoặc password
            return ResponseEntity.status(401).body("Invalid username or password. Try again");
        }
        // Tạo JWT token cho user
        String token = JwtTokenUtil.generateToken(user.getUsername(), user.getRole().name());
        // Trả về token cho client sử dụng trong các request sau
        return ResponseEntity.ok(token);
    }
}
