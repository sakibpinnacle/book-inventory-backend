package com.test.RegisterLogin.Dto;

public class VerifyAndUpdatePasswordDTO {
    private String email;
    private String enteredPassword;

    public VerifyAndUpdatePasswordDTO() {}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEnteredPassword() {
        return enteredPassword;
    }

    public void setEnteredPassword(String enteredPassword) {
        this.enteredPassword = enteredPassword;
    }
}
