package com.test.RegisterLogin.Service;

import com.test.RegisterLogin.Dto.EployeeDTO;
import com.test.RegisterLogin.Dto.LoginDTO;
import com.test.RegisterLogin.response.LoginResponse;

public interface EmployeeService {

    String addEmployee(EployeeDTO employeeDTO);

    LoginResponse loginEmployee(LoginDTO loginDTO);

    String forgotPassword(String email);

}
