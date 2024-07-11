package com.example.demo;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:63342")
public class LoginController {

    @GetMapping("/test")
    public String test() {
        return "test";
    }
    @PostMapping("/login")
    public String login(@RequestBody User user, HttpSession session) {

        String correctLogin = "admin";
        String correctPassword = "admin";
        String username = user.getLogin();
        String password = user.getPassword();

        Integer attempts = (Integer) session.getAttribute("loginAttempts");

        if (attempts == null) {
            attempts = 0;
        }
        if (username.equals(correctLogin) && password.equals(correctPassword)) {
            session.setAttribute("loginAttempts", 0);
            return "success";
        }
        else{
            attempts++;
            session.setAttribute("loginAttempts", attempts + 1);
            if (attempts >= 2) {
                return "blocked";
            }
            else{
                return "failed";
            }
        }






    }
}
