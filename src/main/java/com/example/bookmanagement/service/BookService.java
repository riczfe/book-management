package com.example.bookmanagement.service;

import com.example.bookmanagement.model.Book;
import com.example.bookmanagement.model.User;
import com.example.bookmanagement.repository.BookRepository;
import com.example.bookmanagement.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    private final BookRepository bookRepo;
    private final UserRepository userRepo;

    // Sử dụng constructor injection
    // (Spring tự inject BookRepository, UserRepository vào constructor)

    public BookService(BookRepository bookRepo, UserRepository userRepo) {
        this.bookRepo = bookRepo;
        this.userRepo = userRepo;
    }

    /** Lấy danh sách tất cả sách */
    public List<Book> getAllBooks() {
        return bookRepo.findAll();
    }

    /** Tìm sách theo id, trả về Book hoặc null nếu không tìm thấy */
    public Book getBookById(Long id){
        return bookRepo.findById(id).orElse(null);
    }

    /** Thêm sách mới vào hệ thống */
    public Book createBook(Book book){
        // Khi gọi save với đối tượng mới, JPA sẽ insert vào database
        return bookRepo.save(book);
    }

    /** Cập nhật thông tin một cuốn sách (theo id) */
    public Book updateBook(Long id, Book bookDetails) {
        Optional<Book> optionalBook = bookRepo.findById(id);
        if(optionalBook.isEmpty()) {
            return null;    // Không tìm thấy sách với id cho trước
        }

        Book existingBook = optionalBook.get();
        // Cập nhật các thông tin cho sách hiện có
        existingBook.setTitle(bookDetails.getTitle());
        existingBook.setAuthor(bookDetails.getAuthor());
        existingBook.setDescription(bookDetails.getDescription());
        // Giữ nguyên trạng thái mượn (không thay đổi trường borrowedBy tại đây)
        return bookRepo.save(existingBook);
    }

    /** Xóa một cuốn sách theo id */
    public boolean deleteBook(Long id){
        if (!bookRepo.existsById(id)) {
            return false; // Không có sách với id này để xoá
        }
        bookRepo.deleteById(id);
        return true;
    }

    /** Xử lý mượn sách: gắn user đang mượn vào sách nếu sách còn available */
    public Book borrowBook(Long bookId, User user){
        Optional<Book> optionalBook = bookRepo.findById(bookId);
        if(optionalBook.isEmpty()) {
            return null;    // Sách không tồn tại
        }
        Book book = optionalBook.get();
        if(book.getBorrowedBy() != null) {
            // Sách đã có người mượn rồi
            return null;
        }
        // Gán người mượn và lưu lại
        book.setBorrowedBy(user);
        return bookRepo.save(book);
    }
}
