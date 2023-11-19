package com.example.clientrestaurantsystemmanagementapi.models.responses;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.UniqueElements;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class UserResponse {
    private UUID userLoginDataId;
    private String firstname;
    private String lastname;
    private String email;
    private String role;
}
