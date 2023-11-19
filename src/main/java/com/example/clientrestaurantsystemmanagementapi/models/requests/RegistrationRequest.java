package com.example.clientrestaurantsystemmanagementapi.models.requests;

import lombok.Getter;

@Getter
public class RegistrationRequest {

    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private String role;
}
