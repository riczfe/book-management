// File: src/main/java/com/example/bookmanagement/security/SecurityConfig.java
package com.example.bookmanagement.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    @Autowired
    private JwtAuthFilter jwtAuthFilter; // Filter kiểm tra JWT sẽ định nghĩa bên dưới

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // 1. Disable CSRF vì ta không dùng form login (REST API stateless)
        http.csrf(csrf -> csrf.disable());
        // 2. Set session thành Stateless (không tạo session lưu trạng thái đăng nhập)
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        // 3. Cấu hình các quy tắc truy cập
        http.authorizeHttpRequests(auth -> auth
                // Các endpoint mở không cần auth
                .requestMatchers("/api/auth/**").permitAll()  // /api/auth/* ai cũng truy cập (đăng ký, đăng nhập)
                // Các endpoint cho chức năng sách
                .requestMatchers("/api/books/**").authenticated()
                // * Tất cả request tới /api/books đều yêu cầu đăng nhập (authenticated).
                // Sẽ phân quyền chi tiết trong controller hoặc có thể tách ra như dưới nếu cần:
                // .requestMatchers(HttpMethod.POST, "/api/books").hasRole("ADMIN")
                // .requestMatchers(HttpMethod.PUT, "/api/books/*").hasRole("ADMIN")
                // .requestMatchers(HttpMethod.DELETE, "/api/books/*").hasRole("ADMIN")
                // .requestMatchers(HttpMethod.POST, "/api/books/*/borrow").hasRole("USER")
                // (Phần trên có thể linh hoạt tùy bài toán, ở đây làm đơn giản: sách tạo/sửa/xóa do Admin, mượn sách do User).
                // Các endpoint người dùng
                .requestMatchers("/api/users/**").hasRole("ADMIN")  // Chỉ ADMIN được quản lý user (xem danh sách, xóa user)
                .requestMatchers("/api/users/me").authenticated()   // Ai đăng nhập rồi đều xem đc thông tin chính mình
                // Các request còn lại
                .anyRequest().authenticated()  // Mặc định tất cả request khác đều cần phải đăng nhập
        );
        // 4. Thêm filter JWT vào trước filter kiểm tra đăng nhập mặc định
        http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // Sử dụng BCrypt mạnh để mã hóa mật khẩu
        return new BCryptPasswordEncoder();
    }
}
