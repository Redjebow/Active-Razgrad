package com.example.Active.Razgrad.community;

import com.example.Active.Razgrad.community.Community;
import com.example.Active.Razgrad.community.CommunityMapper;
import com.example.Active.Razgrad.community.CommunityRepository;
import com.example.Active.Razgrad.user.UserDTO;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

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

    public String addCommunity(CommunityDTO communityDTO, BindingResult bindingResult, Model model){

        if (bindingResult.hasErrors()) {
            model.addAttribute("community", communityDTO);

            model.addAttribute("category",Category.values());
            return "community-register";
        }

        if (!communityDTO.getPassword().equals(communityDTO.getRepeatPassword())) {
            model.addAttribute("PasswordDoNotMatch", "Password Do Not Match");
            model.addAttribute("community", communityDTO);

            model.addAttribute("category",Category.values());
            return "community-register";
        }

        Community community = communityMapper.toEntity(makeCryptedPassword(communityDTO));
        saveCommunity(community);

        return "redirect:/index";

    }
}

