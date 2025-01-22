package com.test.RegisterLogin.Service;

import com.test.RegisterLogin.Dto.AuthorDTO;
import java.util.List;

public interface AuthorService {
    AuthorDTO createAuthor(AuthorDTO authorDTO);
    AuthorDTO getAuthorById(int id);
    List<AuthorDTO> getAllAuthors();
    AuthorDTO updateAuthor(int id, AuthorDTO authorDTO);
    String deleteAuthor(int id);
    List<AuthorDTO> getAuthorsByEmployeeId(int employeeId);
}
