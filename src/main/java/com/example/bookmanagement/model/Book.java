package com.example.bookmanagement.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;    // ID khóa chính cho Book (tự tăng)

    private String title;
    private String author;
    private String description;

    //@ManyToOne và @JoinColumn(name = "borrowed_by"):
    // thiết lập mối quan hệ nhiều - một giữa Book và User.
    // Nhiều cuốn sách có thể được mượn bởi một người dùng. Thuộc tính borrowedBy sẽ lưu đối tượng User mượn sách,
    // và trong bảng "books" sẽ có cột "borrowed_by" lưu user_id tương ứng.
    // Nếu borrowedBy null tức sách chưa được mượn.
    @ManyToOne
    @JoinColumn(name = "borrowed_by")
    // @JsonIgnore trên borrowedBy: để tránh việc khi chuyển Book -> JSON
    // sẽ lôi cả thông tin User (và ngược lại, User có thể chứa danh sách Book
    // nếu ta định nghĩa quan hệ 1-n phía kia). Để đơn giản, ta bỏ qua trường này trong JSON trả về.
    // Thay vào đó, ta sẽ dùng một trường đơn giản trong DTO để thông báo sách đã được mượn hay chưa.
    @JsonIgnore
    private User borrowed_by;   // Người dùng đang mượn sách này (null nếu sách đang rảnh)

    public Book() {}

    public Book(String title, String author, String description) {
        this.title = title;
        this.author = author;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setBorrowed_by(User borrowed_by) {
        this.borrowed_by = borrowed_by;
    }
}
