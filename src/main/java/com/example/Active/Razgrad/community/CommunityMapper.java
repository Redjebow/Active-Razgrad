package com.example.Active.Razgrad.community;

import com.example.Active.Razgrad.category.Category;
import jakarta.persistence.ManyToMany;
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
        community.setAdress(communityDTO.getAdress());
        community.setBulstat(communityDTO.getBulstat());
        community.setWebsite(communityDTO.getWebsite());
        community.setDescription(communityDTO.getDescription());
        community.setCategory(community.getCategory());
        return community;
    }
}
