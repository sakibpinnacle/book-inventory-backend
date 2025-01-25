package com.test.RegisterLogin.EmployeeController;

import com.test.RegisterLogin.Entity.Category;
import com.test.RegisterLogin.Service.CategoryService;
import com.test.RegisterLogin.Util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "Authorization, Content-Type", exposedHeaders = "Authorization",methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
//@CrossOrigin
@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;
    @PostMapping
    public ResponseEntity<Object> createCategory(@RequestBody Category category, @RequestHeader("Authorization") String authorizationHeader) {
        // Extract token from the Authorization header
        String token = authorizationHeader != null && authorizationHeader.startsWith("Bearer ")
                ? authorizationHeader.substring(7)
                : null;

        // Validate the token
        if (token == null || !JwtUtils.validateToken(token)) {
            return ResponseEntity.status(401).body("Invalid or missing token");
        }

        // Extract employeeId from the token
        int employeeIdFromToken = JwtUtils.extractEmployeeId(token);

        // Check if the employeeId in the request matches the one from the token
        if (category.getEmployeeId() != employeeIdFromToken) {
            return ResponseEntity.badRequest().body("Employee ID in request does not match the token.");
        }

        // Create the category
        Category createdCategory = categoryService.createCategory(category);
        return ResponseEntity.ok(createdCategory);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Object> updateCategory(@PathVariable int id, @RequestBody Category category, @RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader != null && authorizationHeader.startsWith("Bearer ")
                ? authorizationHeader.substring(7)
                : null;

        if (token == null || !JwtUtils.validateToken(token)) {
            return ResponseEntity.status(401).body("Invalid or missing token");
        }

        int employeeIdFromToken = JwtUtils.extractEmployeeId(token);

        if (category.getEmployeeId() != employeeIdFromToken) {
            return ResponseEntity.badRequest().body("Employee ID in request does not match the token.");
        }

        try {
            Category updatedCategory = categoryService.updateCategory(id, category);
            return ResponseEntity.ok(updatedCategory);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable int id, @RequestHeader("Authorization") String authorizationHeader) {
        // Extract token from the Authorization header
        String token = authorizationHeader != null && authorizationHeader.startsWith("Bearer ")
                ? authorizationHeader.substring(7)
                : null;

        // Validate the token
        if (token == null || !JwtUtils.validateToken(token)) {
            return ResponseEntity.status(401).body("Invalid or missing token");
        }

        // Extract employeeId from the token
        int employeeIdFromToken = JwtUtils.extractEmployeeId(token);

        // Get the category by its ID
        Category category = categoryService.getCategoryById(id);

        // Check if the category exists and if the employeeId in the category matches the one from the token
        if (category == null) {
            return ResponseEntity.status(404).body("Category not found");
        }

        if (category.getEmployeeId() != employeeIdFromToken) {
            return ResponseEntity.status(403).body("You are not authorized to delete this category");
        }

        // If the employeeId matches, proceed with deletion
        categoryService.deleteCategory(id);
        return ResponseEntity.ok("Category deleted successfully.");
    }


    //get singlw
    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable int id) {
        Category category = categoryService.getCategoryById(id);
        return ResponseEntity.ok(category);
    }



    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<Object> getCategoriesByEmployeeId(@PathVariable int employeeId, @RequestHeader("Authorization") String authorizationHeader) {
        // Extract token from the Authorization header
        String token = authorizationHeader != null && authorizationHeader.startsWith("Bearer ")
                ? authorizationHeader.substring(7)
                : null;

        // Validate the token
        if (token == null || !JwtUtils.validateToken(token)) {
            return ResponseEntity.status(401).body("Invalid or missing token");
        }

        // Extract employeeId from the token
        int employeeIdFromToken = JwtUtils.extractEmployeeId(token);

        // Check if the employeeId in the request matches the one from the token
        if (employeeId != employeeIdFromToken) {
            return ResponseEntity.status(403).body("You are not authorized to access categories for this employee ID.");
        }
        String newToken = JwtUtils.generateToken(employeeIdFromToken, JwtUtils.extractEmployeeName(token));
        // Get categories for the employee
        List<Category> categories = categoryService.getCategoriesByEmployeeId(employeeId);
        return ResponseEntity.ok().header("Authorization", "Bearer " + newToken).body(categories);
    }
}
