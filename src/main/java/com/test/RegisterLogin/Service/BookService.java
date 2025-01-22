package com.test.RegisterLogin.Service;

import com.test.RegisterLogin.Dto.BookDTO;

import java.util.List;

public interface BookService {
    BookDTO createBook(BookDTO bookDTO);
    BookDTO getBookById(int id);
    List<BookDTO> getAllBooks();
    BookDTO updateBook(int id, BookDTO bookDTO);
    String deleteBook(int id);
    List<BookDTO> getBooksByEmployeeId(int employeeId);
}
