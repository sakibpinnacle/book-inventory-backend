package com.test.RegisterLogin.Service.impl;

import com.test.RegisterLogin.Dto.*;
import com.test.RegisterLogin.Entity.Employee;
import com.test.RegisterLogin.Repo.EmpoyeeRepo;
import com.test.RegisterLogin.Service.EmployeeService;
import com.test.RegisterLogin.Util.JwtUtils;
import com.test.RegisterLogin.Util.PasswordUtils;
import com.test.RegisterLogin.response.LoginResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

@Service
public class EmployeeIMPL implements EmployeeService {
    @Autowired
    private EmpoyeeRepo employeeRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Autowired
    private JavaMailSender mailSender;


    @Override
    public String addEmployee(EployeeDTO employeeDTO) {


        Employee existingEmployee = employeeRepo.findByEmail(employeeDTO.getEmail());
        if (existingEmployee != null) {
        System.out.println("hii");
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


    private String generatedPassword; // Store the generated password
    private String email1; // Store
    private Map<String, String> emailPasswordMap = new HashMap<>();


    @Override
    public String sendNewPassword(SendNewPasswordDTO sendNewPasswordDTO) {
        String email = sendNewPasswordDTO.getEmail();
        Employee employee = employeeRepo.findByEmail(email);
        if (employee == null) {
            return "Email not found.";
        }

        generatedPassword = generateRandomPassword(); // Generate password
        emailPasswordMap.put(email, generatedPassword);
        email1 = email;
        System.out.println(generatedPassword);
        // Sending email
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Your New Password");
        message.setText("Your new password is: " + generatedPassword);
        mailSender.send(message);
        System.out.println(generatedPassword);

        return "New password sent to your email.";
    }
    @Override
    public String verifyAndUpdatePassword(VerifyAndUpdatePasswordDTO verifyAndUpdatePasswordDTO) {
        String email = verifyAndUpdatePasswordDTO.getEmail();
        String enteredPassword = verifyAndUpdatePasswordDTO.getEnteredPassword();

        // Retrieve the employee
        Employee employee = employeeRepo.findByEmail(email);
        if (employee == null) {
            return "Email not found.";
        }

        // Check if the email exists in the map
        if (emailPasswordMap.containsKey(email)) {
            String storedGeneratedPassword = emailPasswordMap.get(email);

            // Compare the entered password with the stored generated password
            boolean isMatch = enteredPassword.equals(storedGeneratedPassword);
            if (!isMatch) {
                return "Entered password does not match the generated password.";
            }

            // If the entered password is valid, update the password in the database
            employee.setPassword(passwordEncoder.encode(enteredPassword));
            employeeRepo.save(employee);
            emailPasswordMap.remove(email);

            String token = JwtUtils.generateToken(employee.getEmployeeid(), employee.getEmployeename());
            return "Login successful" + token;
        } else {
            return "Email not found in the generated password map.";
        }
    }










    private String generateRandomPassword() {
        int length = 8; // Define your desired password length
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(characters.charAt(random.nextInt(characters.length())));
        }
        return sb.toString();
    }
    //dfs
    //dsfsdfsd
    //fddsfsdf/
    //fdsfs
    //dfssfjd
    //ffwsf
    //rwfwef
    //fwfwf
}



