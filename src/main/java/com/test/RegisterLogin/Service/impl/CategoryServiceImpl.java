package com.test.RegisterLogin.Service.impl;

import com.test.RegisterLogin.Entity.Category;
import com.test.RegisterLogin.Repo.CategoryRepository;
import com.test.RegisterLogin.Service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Category createCategory(Category category) {
        if (category.getCategoryName() == null || category.getCategoryName().trim().isEmpty()) {
            throw new IllegalArgumentException("author name cannot be empty.");
        }
        // Check if category with the same name already exists for the given employee
        Optional<Category> existingCategory = categoryRepository.findByEmployeeIdAndCategoryName(category.getEmployeeId(), category.getCategoryName());
        if (existingCategory.isPresent()) {
            throw new RuntimeException("Category with name " + category.getCategoryName() + " already exists for this employee.");
        }
        return categoryRepository.save(category);
    }

    @Override
    public Category updateCategory(int categoryId, Category category) {
        if (category.getCategoryName() == null || category.getCategoryName().trim().isEmpty()) {
            throw new IllegalArgumentException("author name cannot be empty.");
        }
        // Check if the category exists
        Optional<Category> existingCategory = categoryRepository.findById(categoryId);
        if (existingCategory.isEmpty()) {
            throw new RuntimeException("Category not found with ID: " + categoryId);
        }

        // Check if another category with the same name exists for the same employee
        Optional<Category> duplicateCategory = categoryRepository.findByEmployeeIdAndCategoryName(
                category.getEmployeeId(),
                category.getCategoryName()
        );
        if (duplicateCategory.isPresent() && duplicateCategory.get().getCategoryId() != categoryId) {
            throw new RuntimeException("Category with name " + category.getCategoryName() + " already exists for this employee.");
        }

        // Update the category
        Category updatedCategory = existingCategory.get();
        updatedCategory.setCategoryName(category.getCategoryName());
        updatedCategory.setDescription(category.getDescription());
        return categoryRepository.save(updatedCategory);
    }

    @Override
    public void deleteCategory(int categoryId) {
        categoryRepository.deleteById(categoryId);
    }

    @Override
    public Category getCategoryById(int categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found with ID: " + categoryId));
    }

    @Override
    public List<Category> getCategoriesByEmployeeId(int employeeId) {
        return categoryRepository.findByEmployeeId(employeeId);
    }
}
