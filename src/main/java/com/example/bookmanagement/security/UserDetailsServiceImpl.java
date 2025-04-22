package com.example.bookmanagement.security;

import com.example.bookmanagement.model.User;
import com.example.bookmanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
import java.util.Collections;
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepo;

    /** Load đối tượng UserDetails (thông tin bảo mật) từ username */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Tìm user trong DB
        User user = userRepo.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("User not found")
        );
        // Tạo đối tượng UserDetails dựa trên thông tin user
        // UserDetails yêu cầu cung cấp: username, password (mã hoá), danh sách quyền
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                Collections.singleton(new SimpleGrantedAuthority("Role " + user.getRole().name()))
        );
    }
}
