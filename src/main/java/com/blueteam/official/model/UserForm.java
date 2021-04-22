package com.blueteam.official.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserForm {
    private Long id;
    private String username;
    private String password;
    private String email;
    private String address;
    private MultipartFile image;
    private String phoneNumber;
    private Role role;
    public UserForm(Long id, String username, String password, String email, String address, Object o, String phoneNumber, Role role) {
    }
}
