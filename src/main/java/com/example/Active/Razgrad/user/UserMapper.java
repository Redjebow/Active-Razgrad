package com.example.Active.Razgrad.user;

import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public User toEntity(UserDTO userDTO){
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        user.setEmail(userDTO.getEmail());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setTelephone(userDTO.getTelephone());
        user.setRole(userDTO.getRole());
        user.setAddress(userDTO.getAddress());
        user.setBulstat(userDTO.getBulstat());
        user.setWebsite(userDTO.getWebsite());
        user.setDescription(userDTO.getDescription());
        user.setCategory(userDTO.getCategory());
        return user;
    }
}
