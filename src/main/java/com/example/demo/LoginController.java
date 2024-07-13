package com.example.demo;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

        System.out.println("Received login request: " + loginRequest.getUsername() + ", " + loginRequest.getPassword());
        String username=loginRequest.getUsername();
        String password=loginRequest.getPassword();
        String correctPassword=loginRequest.getPassword();
        String correctLogin = loginRequest.getUsername();

        if (userRepository.existsByUsername(loginRequest.getUsername())) {
            System.out.println("Username is already in use");
        }else{
            System.out.println("Username is valid");
        }




        Integer attempts = (Integer) session.getAttribute("loginAttempts");

        if (attempts == null) {
            attempts = 0;
        }
        if (username.equals(correctLogin) && password.equals(correctPassword) && attempts < 2) {
            session.setAttribute("loginAttempts", 0);
            return "success";
        }
        else{
            session.setAttribute("loginAttempts", attempts + 1);
            if (attempts >= 2) {
                return "blocked";
            }
            else{
                return "failed";
            }
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
