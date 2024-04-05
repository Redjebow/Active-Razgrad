package com.example.Active.Razgrad.community;

import com.example.Active.Razgrad.user.User;
import com.example.Active.Razgrad.user.UserRepository;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/communities")
public class CommunityController {
    private CommunityMapper communityMapper;
    private CommunityService communityService;
    private UserRepository userRepository;

    public CommunityController(CommunityService communityService, CommunityMapper communityMapper, UserRepository userRepository) {
        this.communityMapper = communityMapper;
        this.communityService = communityService;
        this.userRepository = userRepository;
    }

    @GetMapping("/add")
    public String addCommunityForm(Model model) {
        model.addAttribute("community", new CommunityDTO());
        model.addAttribute("category", Category.values() );
        return "community-register";
    }

    @PostMapping("/submit")
    public String submitCommunity(@Valid @ModelAttribute CommunityDTO communityDTO, BindingResult bindingResult, Model model) {
        return communityService.addCommunity(communityDTO,bindingResult,model);
    }

    @GetMapping("/details")
    public String getCommunityDetails(Model model, Authentication authentication) {
        String username = authentication.getName();
        User community = userRepository.getUserByUsername(username);

        model.addAttribute("community", community);
        return "community-profile";
    }

    @GetMapping("/access-denied")
    private String accessDenied() {
        return "/accessDenied";
    }


}
