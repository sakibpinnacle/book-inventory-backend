//package com.test.RegisterLogin.Repo;
//
////import com.example.bookmanagement.entity.Book;
//import com.test.RegisterLogin.Entity.Book;
//import org.springframework.data.jpa.repository.JpaRepository;
//
//import java.util.List;
//import java.util.Optional;
//
//public interface BookRepo extends JpaRepository<Book, Long> {
//    Optional<Book> findByIsbnNoAndEmployeeId(String isbnNo, Long employeeId);
//
//    List<Book> findByEmployeeId(Long employeeId);
//}
package com.test.RegisterLogin.Repo;

import com.test.RegisterLogin.Entity.Author;
import com.test.RegisterLogin.Entity.Book;
import com.test.RegisterLogin.Entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepo extends JpaRepository<Book, Integer> {

    boolean existsByIsbnNoAndEmployee(String isbnNo, Employee employee);

    List<Book> findByEmployeeEmployeeid(int employee);


}
