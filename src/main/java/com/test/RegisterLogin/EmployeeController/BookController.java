package com.test.RegisterLogin.EmployeeController;

import com.test.RegisterLogin.Dto.BookDTO;
import com.test.RegisterLogin.Service.BookService;
import com.test.RegisterLogin.Util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@CrossOrigin
@RestController
@RequestMapping("api/v1/book")
//@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "Authorization", exposedHeaders = "Authorization")
public class BookController {

    @Autowired
    private BookService bookService;

    @PostMapping
    public ResponseEntity<Object> createBook(@RequestBody BookDTO bookDTO, @RequestHeader("Authorization") String authorizationHeader) {
        String token = extractToken(authorizationHeader);
        if (!validateToken(token)) {
            return ResponseEntity.status(401).body(createErrorResponse("Invalid or missing token"));
        }

        int employeeIdFromToken = JwtUtils.extractEmployeeId(token);
        if (bookDTO.getEmployeeId() != employeeIdFromToken) {
            return ResponseEntity.badRequest().body(createErrorResponse("Employee ID in request does not match the token."));
        }

        return ResponseEntity.ok(bookService.createBook(bookDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getBookById(@PathVariable int id, @RequestHeader("Authorization") String authorizationHeader) {
        String token = extractToken(authorizationHeader);
        if (!validateToken(token)) {
            return ResponseEntity.status(401).body(createErrorResponse("Invalid or missing token"));
        }

        int employeeIdFromToken = JwtUtils.extractEmployeeId(token);
        BookDTO bookDTO = bookService.getBookById(id);
        if (bookDTO.getEmployeeId() != employeeIdFromToken) {
            return ResponseEntity.status(403).body(createErrorResponse("You are not authorized to access this book."));
        }

        return ResponseEntity.ok(bookDTO);
    }

    @GetMapping
    public ResponseEntity<Object> getAllBooks(@RequestHeader("Authorization") String authorizationHeader) {
        String token = extractToken(authorizationHeader);
        if (!validateToken(token)) {
            return ResponseEntity.status(401).body(createErrorResponse("Invalid or missing token"));
        }

        int employeeIdFromToken = JwtUtils.extractEmployeeId(token);
        List<BookDTO> books = bookService.getAllBooks();
        books.removeIf(book -> book.getEmployeeId() != employeeIdFromToken); // Filter out books not belonging to the employee

        return ResponseEntity.ok(books);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateBook(@PathVariable int id, @RequestBody BookDTO bookDTO, @RequestHeader("Authorization") String authorizationHeader) {
        String token = extractToken(authorizationHeader);
        if (!validateToken(token)) {
            return ResponseEntity.status(401).body(createErrorResponse("Invalid or missing token"));
        }

        int employeeIdFromToken = JwtUtils.extractEmployeeId(token);
        if (bookDTO.getEmployeeId() != employeeIdFromToken) {
            return ResponseEntity.badRequest().body(createErrorResponse("Employee ID in request does not match the token."));
        }

        return ResponseEntity.ok(bookService.updateBook(id, bookDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteBook(@PathVariable int id, @RequestHeader("Authorization") String authorizationHeader) {
        String token = extractToken(authorizationHeader);
        if (!validateToken(token)) {
            return ResponseEntity.status(401).body(createErrorResponse("Invalid or missing token"));
        }

        int employeeIdFromToken = JwtUtils.extractEmployeeId(token);
        BookDTO bookDTO = bookService.getBookById(id);
        if (bookDTO.getEmployeeId() != employeeIdFromToken) {
            return ResponseEntity.status(403).body(createErrorResponse("You are not authorized to delete this book."));
        }

        return ResponseEntity.ok(bookService.deleteBook(id));
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<Object> getBooksByEmployeeId(@PathVariable int employeeId, @RequestHeader("Authorization") String authorizationHeader) {
        String token = extractToken(authorizationHeader);
        if (!validateToken(token)) {
            return ResponseEntity.status(401).body(createErrorResponse("Invalid or missing token"));
        }

        int employeeIdFromToken = JwtUtils.extractEmployeeId(token);
        if (employeeId != employeeIdFromToken) {
            return ResponseEntity.status(403).body(createErrorResponse("You are not authorized to access books for this employee ID."));
        }

        List<BookDTO> books = bookService.getBooksByEmployeeId(employeeId);
        if (books.isEmpty()) {
            return ResponseEntity.noContent().build();
        }


        String newToken = JwtUtils.generateToken(employeeIdFromToken, JwtUtils.extractEmployeeName(token));

        return ResponseEntity.ok().header("Authorization", "Bearer " + newToken).body(books);
    }

    // Helper methods to extract and validate token
    private String extractToken(String authorizationHeader) {
        return authorizationHeader != null && authorizationHeader.startsWith("Bearer ")
                ? authorizationHeader.substring(7)
                : null;
    }

    private boolean validateToken(String token) {
        return token != null && JwtUtils.validateToken(token);
    }

    private Map<String, String> createErrorResponse(String message) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", message);
        return errorResponse;
    }
}
