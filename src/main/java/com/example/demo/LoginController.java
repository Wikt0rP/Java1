package com.example.demo;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class LoginController {

    @Autowired //Można użyć konstruktora? Robi różnice podczas testowania kodu(?)
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @PostMapping("/register")
    public String test(@RequestBody LoginRequest registerRequest) {

        if(userRepository.existsByUsername(registerRequest.getUsername())) {
            return "User already exists";
        }else{
            User user = new User(registerRequest.getUsername(), registerRequest.getPassword());
            userRepository.save(user);
            return "User registered";
        }



    }
    @PostMapping("/login")
    public String login(@RequestBody LoginRequest loginRequest, HttpSession session) {

        Integer attempts = (Integer) session.getAttribute("loginAttempts");
        if(attempts == null) {
            attempts = 0;
        }

        User user = userRepository.findByUsername(loginRequest.getUsername()).orElse(null);
        if(user == null) {
            return "User not found";
        }

        if (user.getPassword().equals(loginRequest.getPassword())  && attempts < 2) {
            session.setAttribute("loginAttempts", 0);
            return "success";
        }else if(attempts < 2){
            attempts++;
            session.setAttribute("loginAttempts", attempts);
            return "failed";
        }else{
            userService.blockUser(user);
            return "blocked";
        }

    }

    @PostMapping("/unblock")
    public String unblock(@RequestBody ActivationRequest activationRequest, HttpSession session) {

        if(userService.generateActivationCode(activationRequest)){
            User user = userRepository.findByUsername(activationRequest.getUsername()).orElse(null);
            if(user!=null){
                session.setAttribute("loginAttempts", 0);
                return "Activation code generated!" + user.getActivationCode();
            }else{
                throw new Error("Activation code not found");
            }


        }
            return "Activation code not generated";

    }
}
