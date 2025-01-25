package com.test.RegisterLogin.EmployeeController;

import com.test.RegisterLogin.Dto.AuthorDTO;
import com.test.RegisterLogin.Service.AuthorService;
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
@RequestMapping("api/v1/author")
//@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "Authorization", exposedHeaders = "Authorization")
public class AuthorController {

    @Autowired
    private AuthorService authorService;

    @PostMapping
    public ResponseEntity<Object> createAuthor(@RequestBody AuthorDTO authorDTO, @RequestHeader("Authorization") String authorizationHeader) {
        String token = extractToken(authorizationHeader);
        if (!validateToken(token)) {
            return ResponseEntity.status(401).body(createErrorResponse("Invalid or missing token"));
        }

        int employeeIdFromToken = JwtUtils.extractEmployeeId(token);
        if (authorDTO.getEmployeeId() != employeeIdFromToken) {
            return ResponseEntity.badRequest().body(createErrorResponse("Employee ID in request does not match the token."));
        }

        return ResponseEntity.ok(authorService.createAuthor(authorDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getAuthorById(@PathVariable int id, @RequestHeader("Authorization") String authorizationHeader) {
        String token = extractToken(authorizationHeader);
        if (!validateToken(token)) {
            return ResponseEntity.status(401).body(createErrorResponse("Invalid or missing token"));
        }

        int employeeIdFromToken = JwtUtils.extractEmployeeId(token);
        AuthorDTO authorDTO = authorService.getAuthorById(id);
        if (authorDTO.getEmployeeId() != employeeIdFromToken) {
            return ResponseEntity.status(403).body(createErrorResponse("You are not authorized to access this author."));
        }

        return ResponseEntity.ok(authorDTO);
    }

    @GetMapping
    public ResponseEntity<Object> getAllAuthors(@RequestHeader("Authorization") String authorizationHeader) {
        String token = extractToken(authorizationHeader);
        if (!validateToken(token)) {
            return ResponseEntity.status(401).body(createErrorResponse("Invalid or missing token"));
        }

        int employeeIdFromToken = JwtUtils.extractEmployeeId(token);
        List<AuthorDTO> authors = authorService.getAllAuthors();
        authors.removeIf(author -> author.getEmployeeId() != employeeIdFromToken); // Filter out authors not belonging to the employee

        return ResponseEntity.ok(authors);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateAuthor(@PathVariable int id, @RequestBody AuthorDTO authorDTO, @RequestHeader("Authorization") String authorizationHeader) {
        String token = extractToken(authorizationHeader);
        if (!validateToken(token)) {
            return ResponseEntity.status(401).body(createErrorResponse("Invalid or missing token"));
        }

        int employeeIdFromToken = JwtUtils.extractEmployeeId(token);
        if (authorDTO.getEmployeeId() != employeeIdFromToken) {
            return ResponseEntity.badRequest().body(createErrorResponse("Employee ID in request does not match the token."));
        }

        return ResponseEntity.ok(authorService.updateAuthor(id, authorDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteAuthor(@PathVariable int id, @RequestHeader("Authorization") String authorizationHeader) {
        String token = extractToken(authorizationHeader);
        if (!validateToken(token)) {
            return ResponseEntity.status(401).body(createErrorResponse("Invalid or missing token"));
        }

        int employeeIdFromToken = JwtUtils.extractEmployeeId(token);
        AuthorDTO authorDTO = authorService.getAuthorById(id);
        if (authorDTO.getEmployeeId() != employeeIdFromToken) {
            return ResponseEntity.status(403).body(createErrorResponse("You are not authorized to delete this author."));
        }

        return ResponseEntity.ok(authorService.deleteAuthor(id));
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<Object> getAuthorsByEmployeeId(@PathVariable int employeeId, @RequestHeader("Authorization") String authorizationHeader) {
        String token = extractToken(authorizationHeader);
        if (!validateToken(token)) {
            return ResponseEntity.status(401).body(createErrorResponse("Invalid or missing token"));
        }

        int employeeIdFromToken = JwtUtils.extractEmployeeId(token);
        if (employeeId != employeeIdFromToken) {
            return ResponseEntity.status(403).body(createErrorResponse("You are not authorized to access authors for this employee ID."));
        }

        try {
            List<AuthorDTO> authors = authorService.getAuthorsByEmployeeId(employeeId);
            if (authors.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
//            return ResponseEntity.ok(authors);
            String newToken = JwtUtils.generateToken(employeeIdFromToken, JwtUtils.extractEmployeeName(token));
            return ResponseEntity.ok().header("Authorization", "Bearer " + newToken).body(authors);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(createErrorResponse("An error occurred while retrieving authors."));
        }
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
