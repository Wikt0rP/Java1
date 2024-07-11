package com.example.demo;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class LoginController {

    @GetMapping("/test")
    public String test() {
        return "test";
    }
    @RequestMapping("/login")
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
            attempts=0;
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
