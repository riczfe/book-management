package com.example.bookmanagement.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import java.util.Date;

/** Lớp tiện ích để tạo và xác thực JWT token */
public class JwtTokenUtil {
    // Khóa bí mật để ký JWT (cần giữ kín, có thể cấu hình bên ngoài thay vì hard-code)
    private static final String JWT_SECRET = "admin";
    // Thời gian sống của token (ví dụ 24h)
    private static final long JWT_EXPIRATION_MS = 86400000; // 24 * 60 * 60 * 1000 (ms)

    // Thuật toán ký HMAC sử dụng secret ở trên
    private static final Algorithm SIGN_ALGORITHM = Algorithm.HMAC256(JWT_SECRET);

    /** Tạo JWT chứa thông tin người dùng */
    public static String generateToken(String username, String role) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + JWT_EXPIRATION_MS);
        // Tạo token JWT với các thông tin cần thiết
        return JWT.create()
                .withSubject(username)              // Đặt chủ thể token là username
                .withClaim("role", role)     // Thêm thông tin vai trò (claim tùy chỉnh)
                .withIssuedAt(now)                 // Thời điểm phát hành
                .withExpiresAt(expiryDate)         // Thời điểm hết hạn
                .sign(SIGN_ALGORITHM);            // Ký token với thuật toán đã chọn
    }
    /** Kiểm tra và giải mã token JWT.
     *  @return đối tượng DecodedJWT nếu token hợp lệ, hoặc null nếu không hợp lệ.*/
    public static DecodedJWT validateToken(String token) {
        try {
            // Kiểm tra token với secret và thuật toán. Nếu không exception nghĩa là token hợp lệ
            return JWT.require(SIGN_ALGORITHM).build().verify(token);
        }
        catch (Exception ex){
            // Bắt mọi lỗi (token hết hạn, token sai, ...)
            return null;
        }
    }
}
