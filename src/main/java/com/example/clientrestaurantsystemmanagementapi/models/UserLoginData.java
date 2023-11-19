package com.example.clientrestaurantsystemmanagementapi.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.UniqueElements;

import java.util.UUID;

@Entity
@Table(name="user-login-data")
@Getter
@RequiredArgsConstructor
@NoArgsConstructor
public class UserLoginData {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID userLoginDataId;

    @NonNull
    @Column(name = "firstname")
    private String firstname;

    @NonNull
    @Column(name = "lastname")
    private String lastname;

    @NonNull
    @Column(name = "email", unique = true)
    @Email
    private String email;

    @NonNull
    @Column(name = "password")
    private String password;

    @NonNull
    @Column(name = "salt")
    private String salt;

    @NonNull
    @Column(name = "role")
    private String role;
}
