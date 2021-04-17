package com.blueteam.official.model;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
@Data
public class UserForm {
    private Long id;
    private String username;
    private String password;
    private String email;
    private String address;
    private MultipartFile image;
    private String phoneNumber;
}
