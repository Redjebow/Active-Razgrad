package com.example.Active.Razgrad.community;

import com.example.Active.Razgrad.category.CategoryRepository;
import com.example.Active.Razgrad.user.User;
import com.example.Active.Razgrad.user.UserDTO;
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
    private CommunityRepository communityRepository;
    private CategoryRepository categoryRepository;

    public CommunityController(CategoryRepository categoryRepository, CommunityRepository communityRepository, CommunityService communityService, CommunityMapper communityMapper) {
        this.communityMapper = communityMapper;
        this.communityService = communityService;
        this.communityRepository = communityRepository;
        this.categoryRepository = categoryRepository;
    }

    @GetMapping("/add")
    public String addCommunityForm(Model model) {
        model.addAttribute("community", new CommunityDTO());
        model.addAttribute("categories", categoryRepository.findAll() );
        return "community-register";
    }

    @PostMapping("/submit")
    public ModelAndView submitUser(@Valid @ModelAttribute CommunityDTO communityDTO, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("community", communityDTO);
            return new ModelAndView("community-register");
        }
        if (!communityDTO.getPassword().equals(communityDTO.getRepeatPassword())) {
            model.addAttribute("PasswordDoNotMatch", "Password Do Not Match");
            model.addAttribute("community", communityDTO);
            return new ModelAndView("community-register");
        }

        Community community = communityMapper.toEntity(communityService.makeCryptedPassword(communityDTO));
        communityService.saveCommunity(community);

        return new ModelAndView("result");
    }

    @GetMapping("/details")
    public String getUserDetails(Model model, Authentication authentication) {
        String username = authentication.getName();
        Community community = communityRepository.getCommunityByUsername(username);

        model.addAttribute("community", community);
        return "community-profile";
    }

    @GetMapping("/access-denied")
    private String accessDenied() {
        return "/accessDenied";
    }
}
