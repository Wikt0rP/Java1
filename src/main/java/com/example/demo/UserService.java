package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Random;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public Boolean unblockUser(User user, LoginRequest unblockRequest) {
        if(user.getPassword().equals(unblockRequest.getPassword())){
            user.setIsBlocked(false);
            userRepository.save(user);

            return true;
        }else{
            return false;
        }
    }

    public void blockUser(User user) {
       user.setIsBlocked(true);
       userRepository.save(user);
    }

    public Boolean generateActivationCode(ActivationRequest activationRequest){
        User user = userRepository.findByUsername(activationRequest.getUsername())
                .orElse(null);

        if(user != null){
            user.setActivationCode(generateCode());
            userRepository.save(user);
            return true;
        }else{
            throw new Error("User not found");

        }

    }
    public static String generateCode(){
        String base = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        char[] chars = new char[6];
        for (int i = 0; i < chars.length; i++) {
            chars[i] = base.charAt(random.nextInt(base.length()));
        }
        return new String(chars);
    }
}
