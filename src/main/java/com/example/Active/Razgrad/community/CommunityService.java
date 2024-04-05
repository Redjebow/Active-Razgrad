package com.example.Active.Razgrad.community;

import com.example.Active.Razgrad.user.Role;
import com.example.Active.Razgrad.user.User;
import com.example.Active.Razgrad.user.UserDTO;
import com.example.Active.Razgrad.user.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

@Service
public class CommunityService {
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private CommunityMapper communityMapper;

    private UserRepository userRepository;


    public CommunityService(BCryptPasswordEncoder bCryptPasswordEncoder, CommunityMapper communityMapper, UserRepository userRepository) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.communityMapper = communityMapper;
        this.userRepository = userRepository;
    }

    public CommunityDTO makeCryptedPassword(CommunityDTO communityDTO) {
        String hashsetPassword = bCryptPasswordEncoder.encode(communityDTO.getPassword());
        communityDTO.setPassword(hashsetPassword);
        return communityDTO;
    }

    public void saveCommunity(User community) {
        community.setRole(Role.ROLE_COMMUNITY);
        userRepository.save(community);
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

        User community = communityMapper.toEntity(makeCryptedPassword(communityDTO));
        saveCommunity(community);

        return "redirect:/index";

    }
}

