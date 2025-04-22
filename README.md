# 📌 Kế Hoạch SCRUM & Sprint (2 tuần triển khai dự án)

## 🗓 **SCRUM SPRINT TIMELINE**

### 📅 Tuần 1 (22/04 - 28/04): Tập trung hoàn thiện Backend core

| Thứ | Ngày  | Nội dung thực hiện                                                                    |
|-----|-------|---------------------------------------------------------------------------------------|
| Hai | 22/04 | **(Hoàn thành)**: Setup Project, Entity, Repo cơ bản, Planning, Security cơ bản ✅    |
| Ba  | 23/04 | JWT Authentication Filter, JwtService, UserDetailsService                             |
| Tư  | 24/04 | Hoàn thiện SecurityConfig + JWT login, register API                                   |
| Năm | 25/04 | Service Layer cho User (register, login, CRUD user)                                   |
| Sáu | 26/04 | Service Layer cho Book (CRUD sách, mượn sách)                                         |
| Bảy | 27/04 | REST Controllers (AuthController hoàn chỉnh + BookController cơ bản)                  |
| CN  | 28/04 | Nghỉ hoặc review code tuần 1                                                          |

---

### 📅 Tuần 2 (29/04 - 05/05): Hoàn thiện REST APIs + Test & Review

| Thứ | Ngày  | Nội dung thực hiện                                                                    |
|-----|-------|---------------------------------------------------------------------------------------|
| Hai | 29/04 | Hoàn thiện BookController (CRUD, borrow sách) + Phân quyền endpoint                   |
| Ba  | 30/04 | UserController (thông tin user, Admin quản lý user), bổ sung DTO                      |
| Tư  | 01/05 | Chức năng mở rộng (Tìm kiếm sách + phân trang)                                        |
| Năm | 02/05 | Testing API bằng Postman (test kỹ tất cả endpoint CRUD)                               |
| Sáu | 03/05 | Xử lý ngoại lệ (Global Exception Handler), hoàn thiện chú thích code                  |
| Bảy | 04/05 | Chuẩn bị slide tổng kết, review toàn bộ code                                          |
| CN  | 05/05 | Dự phòng & kiểm tra, fix lỗi cuối cùng trước khi nộp                                 |

---

## ✅ **Checklist theo dõi tiến độ**
- Mỗi ngày kiểm tra lại xem đã hoàn thành công việc chưa.
- Nếu hoàn thành sớm thì bắt đầu task kế tiếp.
