package com.example.demo;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class LoginController {

    @GetMapping("/test")
    public String test() {
        return "test";
    }
    @PostMapping("/login")
    public String login(@RequestBody LoginRequest loginRequest, HttpSession session) {

        System.out.println("Received login request: " + loginRequest.getUsername() + ", " + loginRequest.getPassword());


        String correctLogin = "admin";
        String correctPassword = "admin";
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();

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
        String username = user.getLogin();
        session.setAttribute("loginAttempts", 0);
        return "account unblocked!";
        // w przyszłości dodać generowanie kodu + który trzeba przepisać (niby wysłany na mail'a)


    }
}
