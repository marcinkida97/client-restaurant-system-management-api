package com.example.clientrestaurantsystemmanagementapi.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

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
    @Column(name = "login")
    private String login;

    @NonNull
    @Column(name = "password")
    private String password;

    @NonNull
    @Column(name = "salt")
    private String salt;
}
