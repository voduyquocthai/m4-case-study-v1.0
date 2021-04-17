package com.blueteam.official.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@Data
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Not empty")
    @Column(unique = true)
    private String username;

    @NotEmpty(message = "Not empty")
    @Size(min = 6, max = 50, message = "Password must be at least 6 characters")
    private String password;

    @Email
    private String email;

    private String address;

    private String phoneNumber;

    private String avatarUrl;

    @ManyToOne
    private Role role;
}
