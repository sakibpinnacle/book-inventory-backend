package com.test.RegisterLogin.EmployeeController;

import com.test.RegisterLogin.Dto.EployeeDTO;
import com.test.RegisterLogin.Dto.ForgotPasswordRequest;
import com.test.RegisterLogin.Dto.LoginDTO;
import com.test.RegisterLogin.Service.ForgotPasswordService;
import com.test.RegisterLogin.response.LoginResponse;
import com.test.RegisterLogin.Service.EmployeeService;
import jakarta.validation.Valid;
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



    @Autowired
    private ForgotPasswordService forgotPasswordService;

    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestBody @Valid ForgotPasswordRequest request) {
        // Handle forgot password logic here
        forgotPasswordService.sendEmail(request.getEmail());
        return ResponseEntity.ok("A new password has been sent to your email.");
    }






}

