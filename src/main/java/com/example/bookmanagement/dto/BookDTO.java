package com.example.bookmanagement.dto;

public class BookDTO {
    private Long id;
    private String title;
    private String author;
    private String description;
    // trạng thái sách: true nếu chưa bị mượn, false nếu đang có người mượn
    private boolean available;

    public BookDTO() {}

    public BookDTO(Long id, String title, String author, String description, boolean available) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.description = description;
        this.available = available;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}
