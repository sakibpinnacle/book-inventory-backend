package com.test.RegisterLogin.Repo;

import com.test.RegisterLogin.Entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthorRepo extends JpaRepository<Author, Integer> {

    List<Author> findByEmployeeEmployeeid(int employeeId);

    boolean existsByAuthorNameAndEmployeeEmployeeid(String authorName, int employeeId);

    List<Author> findByAuthorNameAndEmployeeEmployeeid(String authorName, int employeeId);
//    List<Author> findByAuthorNameAndEmployeeEmployeeid(String authorName, int employeeId);

}
