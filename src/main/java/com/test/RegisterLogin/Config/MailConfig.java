package com.test.RegisterLogin.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class MailConfig {

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");  // Set your SMTP host
        mailSender.setPort(587);  // Set the port

        mailSender.setUsername("sssakibss880@gmail.com");  // Your email address
        mailSender.setPassword("mqrgjywshtnigpjy");  // Your email password

        mailSender.setJavaMailProperties(new Properties() {{
            put("mail.smtp.auth", "true");
            put("mail.smtp.starttls.enable", "true");
            put("mail.smtp.ssl.protocols", "TLSv1.2");
        }});

        return mailSender;
    }
}
