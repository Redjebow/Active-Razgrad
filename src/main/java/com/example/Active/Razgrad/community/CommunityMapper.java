package com.example.Active.Razgrad.community;

import org.springframework.stereotype.Component;

@Component
public class CommunityMapper {
    public Community toEntity(CommunityDTO communityDTO) {
        Community community = new Community();
        community.setUsername(communityDTO.getUsername());
        community.setPassword(communityDTO.getPassword());
        community.setEmail(communityDTO.getEmail());
        community.setFullName(communityDTO.getFullName());
        community.setTelephone(communityDTO.getTelephone());
        community.setAddress(communityDTO.getAddress());
        community.setBulstat(communityDTO.getBulstat());
        community.setWebsite(communityDTO.getWebsite());
        community.setDescription(communityDTO.getDescription());
        community.setCategory(communityDTO.getCategory());
        return community;
    }
}
