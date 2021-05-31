package com.blueteam.official.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserForm {
    private Long id;

    @NotEmpty(message = "Not empty")
    private String username;

    @NotEmpty(message = "Not empty")
    @Size(min = 6, max = 50, message = "Password must be at least 6 characters")
    private String password;

    @Email(message = "email existed")
    private String email;
    private String address;
    private MultipartFile image;
    private String phoneNumber;
    private Role role;
    public UserForm(Long id, String username, String password, String email, String address, Object o, String phoneNumber, Role role) {
    }
}
