package com.example.clientrestaurantsystemmanagementapi.controllers;

import com.example.clientrestaurantsystemmanagementapi.models.requests.LoginRequest;
import com.example.clientrestaurantsystemmanagementapi.models.requests.RegistrationRequest;
import com.example.clientrestaurantsystemmanagementapi.models.UserLoginData;
import com.example.clientrestaurantsystemmanagementapi.models.responses.UserResponse;
import com.example.clientrestaurantsystemmanagementapi.repositories.UserLoginDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.example.clientrestaurantsystemmanagementapi.utils.PasswordUtils.generateSalt;
import static com.example.clientrestaurantsystemmanagementapi.utils.PasswordUtils.hashPassword;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class UserController {

    @Autowired
    UserLoginDataRepository userLoginDataRepository;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegistrationRequest registrationRequest) {
        String salt = generateSalt();

        try {
            UserLoginData _userLoginData = userLoginDataRepository
                    .save(new UserLoginData(
                            registrationRequest.getFirstname(),
                            registrationRequest.getLastname(),
                            registrationRequest.getEmail(),
                            hashPassword(registrationRequest.getPassword(), salt),
                            salt,
                            registrationRequest.getRole()
                    ));

            return new ResponseEntity<>("Registration successful", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error during registration: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponse> login(@RequestBody() LoginRequest loginRequest) {

        UserLoginData userLoginData;

        try {
            userLoginData = userLoginDataRepository.findByEmail(loginRequest.getEmail());
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        String encryptedPassword = hashPassword(loginRequest.getPassword(), userLoginData.getSalt());

        if (!encryptedPassword.equals(userLoginData.getPassword())) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        UserResponse userResponse = new UserResponse(
                userLoginData.getUserLoginDataId(),
                userLoginData.getFirstname(),
                userLoginData.getLastname(),
                userLoginData.getEmail(),
                userLoginData.getRole()
        );

        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }
}
