package com.example.bookmanagement.dto;

//LẤY THÔNG TIN TỪ USER MODEL
import com.example.bookmanagement.model.Role;

//Các DTO này sẽ được sử dụng trong tầng Controller
// để trả kết quả cho client thay vì trả trực tiếp đối tượng Entity.
// Điều này giúp:
// 1./ Ẩn các thông tin nhạy cảm (ví dụ password của User).
// 2./ Tránh lộ các liên kết phức tạp hoặc vòng lặp nếu có giữa các entity.
// 3./ Cho phép định dạng dữ liệu đúng yêu cầu của client
// (ví dụ available là boolean dễ hiểu,
// thay vì client phải suy luận từ borrowedBy == null).

public class UserDTO {
    private Long id;
    private String username;
    private Role role;

    public UserDTO() {}

    public UserDTO(Long id, String username, Role role) {
        this.id = id;
        this.username = username;
        this.role = role;
    }

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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}