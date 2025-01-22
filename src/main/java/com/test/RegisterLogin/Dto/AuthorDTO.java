package com.test.RegisterLogin.Dto;

import java.time.LocalDateTime;

public class AuthorDTO {

    private int authorId;
    private String authorName;
    private String description;
    private int employeeId;

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public AuthorDTO(int authorId, String authorName, String description, int employeeId, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.authorId = authorId;
        this.authorName = authorName;
        this.description = description;
        this.employeeId = employeeId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public AuthorDTO() {}


}
