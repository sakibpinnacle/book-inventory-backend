package com.test.RegisterLogin.Service;

import org.springframework.stereotype.Service;

@Service
public class ForgotPasswordService {

    public void sendEmail(String email) {
        // Implement the logic for sending the email
        System.out.println("Sending email to: " + email);
    }
}
