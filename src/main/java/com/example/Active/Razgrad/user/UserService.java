package com.example.Active.Razgrad.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private UserRepository userRepository;
    public UserService(BCryptPasswordEncoder bCryptPasswordEncoder, UserRepository userRepository){
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userRepository = userRepository;
    }
    public User makeCryotedPassword (User user){
        String hashsetPassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(hashsetPassword);
        return user;
    }
    public void saveUser(User user){

        user.setRole("ROLE_USER");
        userRepository.save(user);
    }
}
