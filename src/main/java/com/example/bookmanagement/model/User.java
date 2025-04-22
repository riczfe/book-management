// File: src/main/java/com/example/bookmanagement/model/User.java
package com.example.bookmanagement.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
@Entity
@Table(name ="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // ID is a primary key for User (tự động tăng)

    @Column(unique = true)
    private String username; // Tên đăng nhập (duy nhất, không được trống)

    //annotation từ thư viện Jackson, dùng để bỏ qua trường password
    // khi đối tượng User được chuyển đổi sang JSON
    // (để không lộ thông tin mật khẩu ra bên ngoài).
    // Mật khẩu sẽ luôn được mã hoá và chỉ sử dụng nội bộ, không bao giờ trả về client.
    @JsonIgnore
    private String password;  // Password must use encoder (ẩn không trả ra JSON)

    @Enumerated(EnumType.STRING)
    private Role role;        // Vai trò của user (USER hoặc ADMIN)

    //Constructor không tham số User() cần cho JPA (để Hibernate tạo đối tượng).
    // Constructor có tham số để tạo nhanh User mới. Các phương thức getter/setter để truy xuất và thay đổi dữ liệu
    // (trong thực tế có thể dùng Lombok để tự động sinh).
    public User() {}

    public User(Long id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    // Getter và Setter => attributes

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
