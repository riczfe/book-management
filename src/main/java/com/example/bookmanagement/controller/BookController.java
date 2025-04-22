// File: src/main/java/com/example/bookmanagement/controller/BookController.java
package com.example.bookmanagement.controller;

import com.example.bookmanagement.model.Book;
import com.example.bookmanagement.model.User;
import com.example.bookmanagement.dto.BookDTO;
import com.example.bookmanagement.service.BookService;
import com.example.bookmanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/books")
public class BookController {
    @Autowired
    private BookService bookService;
    @Autowired
    private UserService userService; // để lấy User khi mượn sách

    /** Lấy danh sách tất cả sách (dành cho Admin và User đã đăng nhập) */
    @GetMapping
    public ResponseEntity<?> listBooks() {
        List<Book> books = bookService.getAllBooks();
        // Chuyển đổi mỗi Book -> BookDTO
        List<BookDTO> result = books.stream().map(book -> {
            boolean available = (book.getBorrowedBy() == null);
            return new BookDTO(book.getId(), book.getTitle(), book.getAuthor(), book.getDescription(), available);
        }).collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }

    /** Lấy thông tin chi tiết một cuốn sách */
    @GetMapping("/{id}")
    public ResponseEntity<?> getBookDetails(@PathVariable Long id) {
        Book book = bookService.getBookById(id);
        if (book == null) {
            return ResponseEntity.status(404).body("Book not found");
        }
        boolean available = (book.getBorrowedBy() == null);
        BookDTO dto = new BookDTO(book.getId(), book.getTitle(), book.getAuthor(), book.getDescription(), available);
        return ResponseEntity.ok(dto);
    }

    /** Thêm sách mới (Chỉ ADMIN) */
    @PostMapping
    public ResponseEntity<?> createBook(@RequestBody Book book) {
        // Lưu ý: yêu cầu Admin mới được gọi API này (đã cấu hình Security ở SecurityConfig)
        Book created = bookService.createBook(book);
        // Trả về sách vừa tạo (có id)
        BookDTO dto = new BookDTO(created.getId(), created.getTitle(), created.getAuthor(), created.getDescription(), true);
        return ResponseEntity.ok(dto);
    }

    /** Cập nhật thông tin sách (Chỉ ADMIN) */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateBook(@PathVariable Long id, @RequestBody Book bookDetails) {
        Book updated = bookService.updateBook(id, bookDetails);
        if (updated == null) {
            return ResponseEntity.status(404).body("Book not found or cannot update");
        }
        boolean available = (updated.getBorrowedBy() == null);
        BookDTO dto = new BookDTO(updated.getId(), updated.getTitle(), updated.getAuthor(), updated.getDescription(), available);
        return ResponseEntity.ok(dto);
    }

    /** Xóa sách (Chỉ ADMIN) */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable Long id) {
        boolean deleted = bookService.deleteBook(id);
        if (!deleted) {
            return ResponseEntity.status(404).body("Book not found");
        }
        return ResponseEntity.ok("Book deleted successfully");
    }

    /** Mượn sách (Chỉ USER) */
    @PostMapping("/{id}/borrow")
    public ResponseEntity<?> borrowBook(@PathVariable Long id, Authentication authentication) {
        // Authentication chứa thông tin user hiện tại (do filter JWT đã điền vào)
        String username = authentication.getName(); // username hiện tại
        User user = userService.getUserByUsername(username);
        if (user == null) {
            // Trường hợp hiếm: user không tồn tại (có thể đã bị xóa)
            return ResponseEntity.status(401).body("User not found or not authorized");
        }
        Book borrowed = bookService.borrowBook(id, user);
        if (borrowed == null) {
            return ResponseEntity.badRequest().body("Book is not available or does not exist");
        }
        return ResponseEntity.ok("Book borrowed successfully");
    }
}
