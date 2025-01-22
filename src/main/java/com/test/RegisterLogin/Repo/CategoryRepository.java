


package com.test.RegisterLogin.Repo;

import com.test.RegisterLogin.Entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    List<Category> findByEmployeeId(int employeeId);

    // Custom query to find a category by employee ID and category name
    Optional<Category> findByEmployeeIdAndCategoryName(int employeeId, String categoryName);
    List<Category> findByCategoryName(String categoryName);
    // In CategoryRepository
    List<Category> findByCategoryNameAndEmployeeId(String categoryName, int employeeId);

}
