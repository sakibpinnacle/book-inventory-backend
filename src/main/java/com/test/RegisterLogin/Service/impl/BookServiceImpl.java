package com.test.RegisterLogin.Service.impl;

import com.test.RegisterLogin.Dto.BookDTO;
import com.test.RegisterLogin.Entity.Author;
import com.test.RegisterLogin.Entity.Book;
import com.test.RegisterLogin.Entity.Category;
import com.test.RegisterLogin.Entity.Employee;
import com.test.RegisterLogin.Repo.AuthorRepo;
import com.test.RegisterLogin.Repo.BookRepo;
import com.test.RegisterLogin.Repo.CategoryRepository;
import com.test.RegisterLogin.Repo.EmpoyeeRepo;
import com.test.RegisterLogin.Service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepo bookRepo;

    @Autowired
    private EmpoyeeRepo employeeRepo;

    @Autowired
    private AuthorRepo authorRepo;

    @Autowired
    private CategoryRepository categoryRepo;




    @Override
    public BookDTO createBook(BookDTO bookDTO) {
        // Fetch the employee by ID
        Employee employee = employeeRepo.findById(bookDTO.getEmployeeId()).orElseThrow(() ->
                new IllegalArgumentException("Employee not found with ID: " + bookDTO.getEmployeeId())
        );

        Author author = null;

        // Fetch authors by name and employee ID
        List<Author> authors = authorRepo.findByAuthorNameAndEmployeeEmployeeid(bookDTO.getAuthorName(), bookDTO.getEmployeeId());

        // Handle author logic
        if (authors.isEmpty() && !bookDTO.getAuthorName().isEmpty()) {
            // Create a new author if not found
            LocalDateTime now = LocalDateTime.now();
            author = new Author(bookDTO.getAuthorName(), "", employee, now, now); // Use empty string for description
            authorRepo.save(author);
        } else if (!authors.isEmpty()) {
            // Select the first author if multiple are found
            author = authors.get(0);
        }

        // Fetch the category by name
        Category category = null;
        if (!bookDTO.getCategoryName().isEmpty()) {
            List<Category> categories = categoryRepo.findByCategoryNameAndEmployeeId(bookDTO.getCategoryName(), bookDTO.getEmployeeId());

            if (categories.isEmpty()) {
                // Create a new category if not found
                LocalDateTime now = LocalDateTime.now();
                category = new Category(bookDTO.getCategoryName(), "", bookDTO.getEmployeeId());
                categoryRepo.save(category);
            } else {
                // Select the first category if multiple are found
                category = categories.get(0);
            }
        }

        // Check for duplicate ISBN for the employee
        if (bookRepo.existsByIsbnNoAndEmployee(bookDTO.getIsbnNo(), employee)) {
            throw new IllegalArgumentException("A book with the same ISBN already exists for this employee.");
        }

        // Create and save the new book
        Book book = new Book();
        book.setBookName(bookDTO.getBookName());
        book.setDescription(bookDTO.getDescription());
        book.setEmployee(employee);
        book.setAuthor(author); // This can be null if no author is provided
        book.setCategory(category); // This can be null if no category is provided
        book.setIsbnNo(bookDTO.getIsbnNo());
        book.setPrice(bookDTO.getPrice());
        book.setQuantity(bookDTO.getQuantity());
        book.setRating(bookDTO.getRating());
        book.setCreatedAt(LocalDateTime.now());
        book.setUpdatedAt(LocalDateTime.now());

        Book savedBook = bookRepo.save(book);
        return mapToDTO(savedBook);
    }



    @Override
    public BookDTO getBookById(int id) {
        Book book = bookRepo.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Book not found with ID: " + id)
        );
        return mapToDTO(book);
    }

    @Override
    public List<BookDTO> getAllBooks() {
        return bookRepo.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
    }



















    @Override
//    @Override
    public BookDTO updateBook(int id, BookDTO bookDTO) {
        Book book = bookRepo.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Book not found with ID: " + id)
        );



        // Update basic fields
        book.setBookName(bookDTO.getBookName());
        book.setDescription(bookDTO.getDescription());
        book.setIsbnNo(bookDTO.getIsbnNo());
        book.setRating(bookDTO.getRating());
        book.setPrice(bookDTO.getPrice());
        book.setQuantity(bookDTO.getQuantity());
        book.setUpdatedAt(LocalDateTime.now());

        // Handle category logic
        if (bookDTO.getCategoryName() != null) {
            if (!bookDTO.getCategoryName().isEmpty()) {
                List<Category> categories = categoryRepo.findByCategoryNameAndEmployeeId(bookDTO.getCategoryName(), book.getEmployee().getEmployeeid());
                if (categories.isEmpty()) {
                    // Create a new category if not found
                    Category newCategory = new Category(bookDTO.getCategoryName(), "", book.getEmployee().getEmployeeid());
                    categoryRepo.save(newCategory);
                    book.setCategory(newCategory);
                } else {
                    book.setCategory(categories.get(0));
                }
            } else {
                // Nullify the category if the provided name is empty
                book.setCategory(null);
            }
        }

        // Handle author logic
        if (bookDTO.getAuthorName() != null) {
            if (!bookDTO.getAuthorName().isEmpty()) {
                List<Author> authors = authorRepo.findByAuthorNameAndEmployeeEmployeeid(bookDTO.getAuthorName(), book.getEmployee().getEmployeeid());
                if (authors.isEmpty()) {
                    // Create a new author if not found
                    Author newAuthor = new Author(bookDTO.getAuthorName(), "", book.getEmployee(), LocalDateTime.now(), LocalDateTime.now());
                    authorRepo.save(newAuthor);
                    book.setAuthor(newAuthor);
                } else {
                    book.setAuthor(authors.get(0));
                }
            } else {
                // Nullify the author if the provided name is empty
                book.setAuthor(null);
            }
        }

        Book updatedBook = bookRepo.save(book);
        return mapToDTO(updatedBook);
    }

//    private BookDTO mapToDTO(Book book) {
//        return new BookDTO(
//                book.getBookId(),
//                book.getBookName(),
//                book.getDescription(),
//                book.getEmployee().getEmployeeid(),
//                book.getAuthor() != null ? book.getAuthor().getAuthorName() : null,
//                book.getCategory() != null ? book.getCategory().getCategoryName() : null,
//                book.getIsbnNo(),
//                book.getRating(),
//                book.getPrice(),
//                book.getQuantity()
//        );
//    }





























    @Override
    public String deleteBook(int id) {
        Book book = bookRepo.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Book not found with ID: " + id)
        );

        bookRepo.delete(book);
        return "Book deleted successfully.";
    }

    @Override
    public List<BookDTO> getBooksByEmployeeId(int employeeId) {
        List<Book> books = bookRepo.findByEmployeeEmployeeid(employeeId);
        return books.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    private BookDTO mapToDTO(Book book) {
        return new BookDTO(
                book.getBookId(),
                book.getBookName(),
                book.getDescription(),
                book.getEmployee().getEmployeeid(),
                book.getAuthor() != null ? book.getAuthor().getAuthorName() : null,
                book.getCategory() != null ? book.getCategory().getCategoryName() : null,
                book.getIsbnNo(),
                book.getRating(),
                book.getPrice(), // New field
                book.getQuantity()// New field
//                book.getUpdatedAt()
//                book.getCreatedAt(),
        );
    }
}
