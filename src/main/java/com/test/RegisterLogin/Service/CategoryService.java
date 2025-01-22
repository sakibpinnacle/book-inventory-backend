package com.test.RegisterLogin.Service;

import com.test.RegisterLogin.Entity.Category;

import java.util.List;

public interface CategoryService {
    Category createCategory(Category category);

    Category updateCategory(int categoryId, Category category);

    void deleteCategory(int categoryId);

    Category getCategoryById(int categoryId);

    List<Category> getCategoriesByEmployeeId(int employeeId);

}
