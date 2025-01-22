package com.test.RegisterLogin.EmployeeController;

import com.test.RegisterLogin.Dto.AuthorDTO;
import com.test.RegisterLogin.Entity.Author;
import com.test.RegisterLogin.Service.AuthorService;
import com.test.RegisterLogin.Service.impl.AuthorServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
//
//@RestController
//@RequestMapping("api/v1/author")
//public class AuthorController {
//
//    @Autowired
//    private AuthorService authorService;
//
//    @PostMapping
//    public ResponseEntity<AuthorDTO> createAuthor(@RequestBody AuthorDTO authorDTO) {
//        return ResponseEntity.ok(authorService.createAuthor(authorDTO));
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<AuthorDTO> getAuthorById(@PathVariable int id) {
//        return ResponseEntity.ok(authorService.getAuthorById(id));
//    }
//
//    @GetMapping
//    public ResponseEntity<List<AuthorDTO>> getAllAuthors() {
//        return ResponseEntity.ok(authorService.getAllAuthors());
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<AuthorDTO> updateAuthor(@PathVariable int id, @RequestBody AuthorDTO authorDTO) {
//        return ResponseEntity.ok(authorService.updateAuthor(id, authorDTO));
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<String> deleteAuthor(@PathVariable int id) {
//        return ResponseEntity.ok(authorService.deleteAuthor(id));
//    }
////    @Autowired
//    private AuthorServiceImpl authorServiceImpl;
//
//
//    //@Autowired
////    private AuthorService authorService;
//
//    @GetMapping("/employee/{employeeId}")
//    public ResponseEntity<?> getAuthorsByEmployeeId(@PathVariable int employeeId) {
//        try {
//            List<Author> authors = authorServiceImpl.getAuthorsByEmployeeId(employeeId);
//            if (authors.isEmpty()) {
//                return ResponseEntity.noContent().build();
//            }
//            return ResponseEntity.ok(authors);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body("Error: " + e.getMessage());
//        }
//    }
//}





@RestController
@RequestMapping("api/v1/author")
@CrossOrigin
public class AuthorController {

    @Autowired
    private AuthorService authorService;
    @Autowired
    private AuthorServiceImpl authorServiceImpl;

    @PostMapping
    public ResponseEntity<AuthorDTO> createAuthor(@RequestBody AuthorDTO authorDTO) {
        return ResponseEntity.ok(authorService.createAuthor(authorDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthorDTO> getAuthorById(@PathVariable int id) {
        return ResponseEntity.ok(authorService.getAuthorById(id));
    }

    @GetMapping
    public ResponseEntity<List<AuthorDTO>> getAllAuthors() {
        return ResponseEntity.ok(authorService.getAllAuthors());
    }

    @PutMapping("/{id}")
    public ResponseEntity<AuthorDTO> updateAuthor(@PathVariable int id, @RequestBody AuthorDTO authorDTO) {
        return ResponseEntity.ok(authorService.updateAuthor(id, authorDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAuthor(@PathVariable int id) {
        return ResponseEntity.ok(authorService.deleteAuthor(id));
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<AuthorDTO>> getAuthorsByEmployeeId(@PathVariable int employeeId) {
        try {
            List<AuthorDTO> authors = authorService.getAuthorsByEmployeeId(employeeId);
            if (authors.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(authors);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }




    @RestControllerAdvice
    public class GlobalExceptionHandler {

        @ExceptionHandler(IllegalArgumentException.class)
        public ResponseEntity<Map<String, String>> handleIllegalArgumentException(IllegalArgumentException ex) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", ex.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

}
