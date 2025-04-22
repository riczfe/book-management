// File: src/main/java/com/example/bookmanagement/security/JwtAuthFilter.java
package com.example.bookmanagement.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.auth0.jwt.interfaces.DecodedJWT;
import java.io.IOException;
import java.util.Collections;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    // UserDetailsServiceImpl chúng ta sẽ tạo để hỗ trợ load UserDetails nếu cần (Spring Security dùng)

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // Lấy giá trị của header "Authorization"
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            // Tách lấy token (bỏ tiền tố "Bearer ")
            String token = authHeader.substring(7);
            DecodedJWT decoded = JwtTokenUtil.validateToken(token);
            if (decoded != null) {
                // Token hợp lệ
                String username = decoded.getSubject();
                String role = decoded.getClaim("role").asString();

                // Tạo đối tượng Authentication cho SecurityContext
                // GrantedAuthority yêu cầu format "ROLE_<ROLE_NAME>" theo chuẩn của Spring Security
                SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + role);
                Authentication auth = new UsernamePasswordAuthenticationToken(
                        username, // principal (ta có thể đặt là đối tượng UserDetails nếu dùng)
                        null,     // credentials (null vì không cần mật khẩu ở bước xác thực này)
                        Collections.singletonList(authority) // danh sách quyền
                );
                // Đặt đối tượng Authentication vào SecurityContext
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }
        // Tiếp tục chuỗi filter
        filterChain.doFilter(request, response);
    }
}
