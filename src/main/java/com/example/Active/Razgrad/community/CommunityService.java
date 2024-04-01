package com.example.Active.Razgrad.community;

import com.example.Active.Razgrad.community.Community;
import com.example.Active.Razgrad.community.CommunityMapper;
import com.example.Active.Razgrad.community.CommunityRepository;
import com.example.Active.Razgrad.user.UserDTO;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CommunityService {
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private CommunityRepository communityRepository;
    private CommunityMapper communityMapper;

    public CommunityService(BCryptPasswordEncoder bCryptPasswordEncoder, CommunityRepository communityRepository, CommunityMapper communityMapper) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.communityMapper = communityMapper;
        this.communityRepository = communityRepository;
    }

    public CommunityDTO makeCryptedPassword(CommunityDTO communityDTO) {
        String hashsetPassword = bCryptPasswordEncoder.encode(communityDTO.getPassword());
        communityDTO.setPassword(hashsetPassword);
        return communityDTO;
    }

    public void saveCommunity(Community community) {


        communityRepository.save(community);
    }
}

