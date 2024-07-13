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
        Optional<User> user = Optional.of(new User());
        user = userRepository.findByUsername(loginRequest.getUsername());

        if (user.isPresent() && user.get().getPassword().equals(loginRequest.getPassword()) && attempts < 2) {
            session.setAttribute("loginAttempts", 0);
            return "success";
        }else if(user.isPresent() && attempts < 2){
            attempts++;
            session.setAttribute("loginAttempts", attempts);
            return "failed";
        }else{
            return "blocked";
        }

    }

    @PostMapping("/unblock")
    public String unblock(@RequestBody User user, HttpSession session) {
        String username = user.getUsername();
        session.setAttribute("loginAttempts", 0);
        return "account unblocked!";
        // w przyszłości dodać generowanie kodu + który trzeba przepisać (niby wysłany na mail'a)


    }
}
