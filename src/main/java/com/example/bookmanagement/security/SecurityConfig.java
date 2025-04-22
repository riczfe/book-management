// File: src/main/java/com/example/bookmanagement/security/SecurityConfig.java
package com.example.bookmanagement.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {
    @Autowired
    private JwtAuthFilter jwtAuthFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        // Sử dụng BCrypt để mã hoá mật khẩu
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // 1. Tắt bảo vệ CSRF (không dùng cho API stateless)
        http.csrf(csrf -> csrf.disable());
        // 2. Không sử dụng session để lưu trạng thái (vì ta dùng JWT stateless)
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        // 3. Thiết lập quy tắc truy cập cho các URL
        http.authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/auth/**").permitAll() // // Cho phép tất cả truy cập các URL đăng nhập, đăng ký
                .requestMatchers(org.springframework.http.HttpMethod.POST, "/api/books/*/borrow").hasRole("USER")
                .requestMatchers(org.springframework.http.HttpMethod.POST, "/api/books/**").hasRole("ADMIN")
                .requestMatchers(org.springframework.http.HttpMethod.PUT, "/api/books/**").hasRole("ADMIN")
                .requestMatchers(org.springframework.http.HttpMethod.DELETE, "/api/books/**").hasRole("ADMIN")
                .requestMatchers(org.springframework.http.HttpMethod.GET, "/api/books/**").hasRole("ADMIN")
                .requestMatchers("/api/uses/me").authenticated()     // Đăng nhập rồi mới xem được thông tin tài khoản
                .requestMatchers("api/users/**").hasRole("ADMIN")   // Chỉ ADMIN được truy cập các URL quản lý người dùng
                .anyRequest().authenticated()   // Mọi request khác gửi đến đều phải xác thực (authenticated)
        );
        // 4. Thêm filter kiểm tra JWT trước filter mặc định của Spring Security
        http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
