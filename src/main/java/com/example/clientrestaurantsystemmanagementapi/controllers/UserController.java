package com.example.clientrestaurantsystemmanagementapi.controllers;

import com.example.clientrestaurantsystemmanagementapi.entities.RegistrationRequest;
import com.example.clientrestaurantsystemmanagementapi.entities.UserLoginData;
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
                            registrationRequest.getLogin(),
                            hashPassword(registrationRequest.getPassword(), salt),
                            salt
                    ));

            return new ResponseEntity<>("Registration successful", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error during registration: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/login")
    public ResponseEntity<UserLoginData> login(@RequestParam() String login, String password) {

        UserLoginData userLoginData;

        try {
            userLoginData = userLoginDataRepository.findByLogin(login);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        String encryptedPassword = hashPassword(password, userLoginData.getSalt());

        if (!encryptedPassword.equals(userLoginData.getPassword())) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}
