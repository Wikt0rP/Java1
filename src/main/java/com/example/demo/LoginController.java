package com.example.demo;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.SecureRandom;

@RestController
@CrossOrigin(origins = "http://localhost:63342")
@RequestMapping("/api")
public class LoginController {

    @Autowired //Można użyć konstruktora? Robi różnice podczas testowania kodu(?)
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private HttpServletRequest request;


    @PostMapping("/register")
    public String test(@RequestBody LoginRequest registerRequest) {

        if(userRepository.existsByUsername(registerRequest.getUsername())) {
            return "User already exists";
        }else{
            User user = new User(registerRequest.getUsername(), passwordEncoder.encode(registerRequest.getPassword()));

            if(userService.registerUser(user)){
                return "User registered successfully \n" +
                        "Username: "+ user.getUsername() +
                        "Activation code -->" + user.getActivationCode();
            }else {
                return "User can not be registered";
            }
        }
    }


    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest, HttpSession session) {

        Integer attempts = (Integer) session.getAttribute("loginAttempts");
        if(attempts == null) {
            attempts = 0;
        }

        User user = userRepository.findByUsername(loginRequest.getUsername()).orElse(null);
        if(user == null) {
            attempts++;
            session.setAttribute("loginAttempts", attempts);
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }

        if (passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())  && attempts < 2 && !user.getIsBlocked()) {
            session.setAttribute("loginAttempts", 0);
            return new ResponseEntity<>("success", HttpStatus.OK);
        }else if(attempts < 2){
            attempts++;
            session.setAttribute("loginAttempts", attempts);
            return new ResponseEntity<>("failed", HttpStatus.BAD_REQUEST);
        }else{
            userService.blockUser(user);
            session.invalidate();
            return new ResponseEntity<>("blocked", HttpStatus.TOO_MANY_REQUESTS);

        }
    }

    @PostMapping("/unblock")
    public String unblock(@RequestBody ActivationCodeRequest activationCodeRequest, HttpSession session) {

        if(userService.generateActivationCode(activationCodeRequest)){
            User user = userRepository.findByUsername(activationCodeRequest.getUsername()).orElse(null);
            if(user!=null){
                session.setAttribute("loginAttempts", 0);
                return "Activation code generated! --->" + user.getActivationCode();
            }else{
                throw new Error("Activation code not found");
            }
        }

            return "Activation code not generated";
    }

    @PostMapping("/activate")
    public String activate(@RequestBody ActivateAccountRequest activateAccountRequest){

       if(userService.activateUser(activateAccountRequest)){
           return "activated";
       }else{
           return "account could not be activated";
       }
    }
}
