package com.test.RegisterLogin.Dto;

import com.test.RegisterLogin.Entity.Employee;
import jakarta.validation.constraints.NotEmpty;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class BookDTO {
    @NotEmpty(message = "Book name is required.")
    private int bookId;
    private String bookName;
    private String description;
    private int employeeId;
    private String authorName;
    private String categoryName;
    @NotEmpty
    private String isbnNo;
    private Integer rating;
    private BigDecimal price; // New field
    private int quantity; // New field
    private LocalDateTime createdAt;

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }



    public BookDTO() {
    }
    public BookDTO(int bookId, String bookName, String description, int employeeId, String authorName, String categoryName, String isbnNo, int rating, BigDecimal price, int quantity, LocalDateTime createdAt) {
        this.bookId = bookId;
        this.bookName = bookName;
        this.description = description;
        this.employeeId = employeeId;
        this.authorName = authorName;
        this.categoryName = categoryName;
        this.isbnNo = isbnNo;
        this.rating = rating;
        this.price = price;
        this.quantity = quantity;
        this.createdAt = createdAt;
    }

    // Getters and Setters
    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getIsbnNo() {
        return isbnNo;
    }

    public void setIsbnNo(String isbnNo) {
        this.isbnNo = isbnNo;
    }




}
