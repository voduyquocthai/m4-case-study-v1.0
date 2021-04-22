package com.blueteam.official.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Data
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Not empty")
    @Column(unique = true)
    private String username;

    @NotEmpty(message = "Not empty")
    private String password;

    @Column(unique = true)
    private String email;

    private String address;

    private String phoneNumber;

    private String avatarUrl;

    @ManyToOne
    private Role role;

}
