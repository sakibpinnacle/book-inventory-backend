package com.test.RegisterLogin.Dto;

public class UpdatePasswordDTO {
        private String email;
        private String newPassword;

        public UpdatePasswordDTO() {}

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getNewPassword() {
            return newPassword;
        }

        public void setNewPassword(String newPassword) {
            this.newPassword = newPassword;
        }
    }



