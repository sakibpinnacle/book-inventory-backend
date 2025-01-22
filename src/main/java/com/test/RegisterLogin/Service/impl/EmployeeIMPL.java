package com.test.RegisterLogin.Service.impl;

import com.test.RegisterLogin.Dto.EployeeDTO;
import com.test.RegisterLogin.Dto.LoginDTO;
import com.test.RegisterLogin.Entity.Employee;
import com.test.RegisterLogin.Repo.EmpoyeeRepo;
import com.test.RegisterLogin.Service.EmployeeService;
import com.test.RegisterLogin.Util.JwtUtils;
import com.test.RegisterLogin.Util.PasswordUtils;
import com.test.RegisterLogin.response.LoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmployeeIMPL implements EmployeeService {
    @Autowired
    private EmpoyeeRepo employeeRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public String addEmployee(EployeeDTO employeeDTO) {


        Employee existingEmployee = employeeRepo.findByEmail(employeeDTO.getEmail());
        if (existingEmployee != null) {
            return "An employee with this email already exists.";
        }


        Employee employee = new Employee(
                employeeDTO.getEmployeeid(),
                employeeDTO.getEmployeename(),
                employeeDTO.getEmail(),
                this.passwordEncoder.encode(employeeDTO.getPassword())
        );
        employeeRepo.save(employee);
        return employee.getEmployeename();
    }

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public String forgotPassword(String email) {
        Employee employee = employeeRepo.findByEmail(email);

        if (employee == null) {
            return "No employee found with the provided email.";
        }

        String newPassword = PasswordUtils.generateRandomPassword();
        employee.setPassword(passwordEncoder.encode(newPassword));
        employeeRepo.save(employee);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Your New Password");
        message.setText("Hello " + employee.getEmployeename() + ",\n\nYour new password is: " + newPassword + "\n\nPlease change it after logging in.");
        mailSender.send(message);

        return "A new password has been sent to your email.";
    }
    @Override
    public LoginResponse loginEmployee(LoginDTO loginDTO) {
        Employee employee = employeeRepo.findByEmail(loginDTO.getEmail());
        if (employee != null) {
            boolean isPwdRight = passwordEncoder.matches(loginDTO.getPassword(), employee.getPassword());
            if (isPwdRight) {
                String token = JwtUtils.generateToken(employee.getEmployeeid(), employee.getEmployeename());
                return new LoginResponse("Login successful", true, token);
            } else {
                return new LoginResponse("Invalid password", false);
            }
        } else {
            return new LoginResponse("Email not found", false);
        }
    }

}



