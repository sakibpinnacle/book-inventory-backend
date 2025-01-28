package com.test.RegisterLogin.Service;

import com.test.RegisterLogin.Dto.*;
import com.test.RegisterLogin.response.LoginResponse;

public interface EmployeeService {

    String addEmployee(EployeeDTO employeeDTO);

    LoginResponse loginEmployee(LoginDTO loginDTO);
//    String sendNewPassword(String email);
//    String updatePassword(String email, String newPassword);
    String sendNewPassword(SendNewPasswordDTO sendNewPasswordDTO);
//    String verifyAndUpdatePassword(UpdatePasswordDTO updatePasswordDTO);
String verifyAndUpdatePassword(VerifyAndUpdatePasswordDTO verifyAndUpdatePasswordDTO); // This verifies and updates the password

}

