package com.example.demo;

import org.springframework.stereotype.Service;

@Service
public class UserService {
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
}
