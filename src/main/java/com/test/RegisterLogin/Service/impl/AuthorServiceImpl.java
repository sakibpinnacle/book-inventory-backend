package com.test.RegisterLogin.Service.impl;

import com.test.RegisterLogin.Dto.AuthorDTO;
import com.test.RegisterLogin.Entity.Author;
import com.test.RegisterLogin.Entity.Employee;
import com.test.RegisterLogin.Repo.AuthorRepo;
import com.test.RegisterLogin.Repo.EmpoyeeRepo;
import com.test.RegisterLogin.Service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthorServiceImpl implements AuthorService {

    @Autowired
    private AuthorRepo authorRepo;

    @Autowired
    private EmpoyeeRepo employeeRepo;
    @Override
    public AuthorDTO createAuthor(AuthorDTO authorDTO) {

        if (authorDTO.getAuthorName() == null || authorDTO.getAuthorName().trim().isEmpty()) {
            throw new IllegalArgumentException("author name cannot be empty.");
        }
        if (authorRepo.existsByAuthorNameAndEmployeeEmployeeid(authorDTO.getAuthorName(), authorDTO.getEmployeeId())) {
            throw new IllegalArgumentException("Author with name already exists.");
        }

        Employee employee = employeeRepo.findById(authorDTO.getEmployeeId()).orElseThrow(() ->
                new IllegalArgumentException("Employee not found with ID: " + authorDTO.getEmployeeId())
        );

        Author author = new Author(
                authorDTO.getAuthorName(),
                authorDTO.getDescription(),
                employee,
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        Author savedAuthor = authorRepo.save(author);
        return mapToDTO(savedAuthor);
    }

    @Override
    public AuthorDTO getAuthorById(int id) {
        Author author = authorRepo.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Author not found with ID: " + id)
        );
        return mapToDTO(author);
    }

    @Override
    public List<AuthorDTO> getAllAuthors() {
        return authorRepo.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public AuthorDTO updateAuthor(int id, AuthorDTO authorDTO) {
        if (authorDTO.getAuthorName() == null || authorDTO.getAuthorName().trim().isEmpty()) {
            throw new IllegalArgumentException("author name cannot be empty.");
        }
        // Fetch the existing author by ID
        Author author = authorRepo.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Author not found with ID: " + id)
        );

        // Check if another author with the same name exists for the same employee
        boolean isDuplicate = authorRepo.existsByAuthorNameAndEmployeeEmployeeid(authorDTO.getAuthorName(), authorDTO.getEmployeeId())
                && !author.getAuthorName().equals(authorDTO.getAuthorName());

        if (isDuplicate) {
            throw new IllegalArgumentException("Author with name already exists.");
        }

        // Fetch the employee for the update
        Employee employee = employeeRepo.findById(authorDTO.getEmployeeId()).orElseThrow(() ->
                new IllegalArgumentException("Employee not found with ID: " + authorDTO.getEmployeeId())
        );

        // Update the author fields
        author.setAuthorName(authorDTO.getAuthorName());
        author.setDescription(authorDTO.getDescription());
        author.setEmployee(employee);
        author.setUpdatedAt(LocalDateTime.now());

        // Save the updated author
        Author updatedAuthor = authorRepo.save(author);

        return mapToDTO(updatedAuthor);
    }


    @Override
    public String deleteAuthor(int id) {
        Author author = authorRepo.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Author not found with ID: " + id)
        );

        authorRepo.delete(author);
        return "Author deleted successfully.";
    }

    @Autowired
    private AuthorRepo authorRepository;


    @Override
    public List<AuthorDTO> getAuthorsByEmployeeId(int employeeId) {
        List<Author> authors = authorRepo.findByEmployeeEmployeeid(employeeId);
        return authors.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    private AuthorDTO mapToDTO(Author author) {
        return new AuthorDTO(
                author.getAuthorId(),
                author.getAuthorName(),
                author.getDescription(),
                author.getEmployee().getEmployeeid(),
                author.getCreatedAt(),
                author.getUpdatedAt()
        );
    }
}
