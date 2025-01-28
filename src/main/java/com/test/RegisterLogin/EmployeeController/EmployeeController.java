package com.test.RegisterLogin.EmployeeController;

import com.test.RegisterLogin.Dto.*;
import com.test.RegisterLogin.response.LoginResponse;
import com.test.RegisterLogin.Service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("api/v1/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping(path ="/save")
    public String savaEmployee(@RequestBody EployeeDTO employeeDTO) {
        String id = employeeService.addEmployee(employeeDTO);
        return id;
    }


    @PostMapping(path = "/login")
    public ResponseEntity<?> loginEmployee(@RequestBody LoginDTO loginDTO) {
        LoginResponse loginResponse = employeeService.loginEmployee(loginDTO);
        return ResponseEntity.ok(loginResponse);
    }


    @PostMapping(path = "/send-new-password")
    public ResponseEntity<String> sendNewPassword(@RequestBody SendNewPasswordDTO sendNewPasswordDTO) {
        System.out.println("Request received to send new password for email: " + sendNewPasswordDTO.getEmail());
        String response = employeeService.sendNewPassword(sendNewPasswordDTO);
        return ResponseEntity.ok(response);
    }
    @PostMapping(path = "/verify-and-update-password")
    public ResponseEntity<String> verifyAndUpdatePassword(@RequestBody VerifyAndUpdatePasswordDTO verifyAndUpdatePasswordDTO) {
        String response = employeeService.verifyAndUpdatePassword(verifyAndUpdatePasswordDTO);
        return ResponseEntity.ok(response);
    }


}

