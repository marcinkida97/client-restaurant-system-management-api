package com.example.clientrestaurantsystemmanagementapi.repositories;

import com.example.clientrestaurantsystemmanagementapi.models.UserLoginData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserLoginDataRepository extends JpaRepository<UserLoginData, UUID> {
    UserLoginData findByEmail(String email);
}
