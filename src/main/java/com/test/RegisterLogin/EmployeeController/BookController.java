package com.test.RegisterLogin.EmployeeController;

import com.test.RegisterLogin.Dto.BookDTO;
import com.test.RegisterLogin.Service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/book")
@CrossOrigin
public class BookController {

    @Autowired
    private BookService bookService;

    @PostMapping
    public ResponseEntity<BookDTO> createBook(@RequestBody BookDTO bookDTO) {
        return ResponseEntity.ok(bookService.createBook(bookDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> getBookById(@PathVariable int id) {
        return ResponseEntity.ok(bookService.getBookById(id));
    }

    @GetMapping
    public ResponseEntity<List<BookDTO>> getAllBooks() {
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookDTO> updateBook(@PathVariable int id, @RequestBody BookDTO bookDTO) {
        return ResponseEntity.ok(bookService.updateBook(id, bookDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable int id) {
        return ResponseEntity.ok(bookService.deleteBook(id));
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<BookDTO>> getBooksByEmployeeId(@PathVariable int employeeId) {
        List<BookDTO> books = bookService.getBooksByEmployeeId(employeeId);
        return ResponseEntity.ok(books);
    }
}
