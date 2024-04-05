package com.example.Active.Razgrad.community;

import com.example.Active.Razgrad.user.User;
import org.springframework.stereotype.Component;

@Component
public class CommunityMapper {
    public User toEntity(CommunityDTO communityDTO) {
        User community = new User();
        community.setUsername(communityDTO.getUsername());
        community.setPassword(communityDTO.getPassword());
        community.setEmail(communityDTO.getEmail());
        community.setFirstName(communityDTO.getFullName());
        community.setTelephone(communityDTO.getTelephone());
        community.setAddress(communityDTO.getAddress());
        community.setBulstat(communityDTO.getBulstat());
        community.setWebsite(communityDTO.getWebsite());
        community.setDescription(communityDTO.getDescription());
        community.setCategory(communityDTO.getCategory());
        community.setRole(communityDTO.getRole());
        return community;
    }
}
