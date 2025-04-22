// File: src/main/java/com/example/bookmanagement/repository/BookRepository.java
package com.example.bookmanagement.repository;

import com.example.bookmanagement.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

/** Repository quản lý thao tác với bảng Book */
public interface BookRepository extends JpaRepository<Book, Long> {
    // JpaRepository đã cung cấp các method cơ bản như findAll, findById, save, delete...
}