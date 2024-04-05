package com.example.Active.Razgrad.user;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {
    public UserService userService;
    public UserMapper userMapper;
    public UserController(UserService userService, UserMapper userMapper){

        this.userMapper = userMapper;
        this.userService = userService;
    }

    @Autowired
    UserRepository userRepository;

    @GetMapping("/addRoleUser")
    public String addUserUserRole(Model model){
        model.addAttribute("user", new UserDTO());
        return "user-register";
    }
    @GetMapping("/addRoleCommunity")
    public String addUserCommunityRole(Model model){
        model.addAttribute("user", new User());
        return "community-register";
    }
    @GetMapping("/all")
    public String getAllCommunityRoleUser(Model model){
        List<User>communityRoleUsers = userRepository.getUserByRole(Role.ROLE_COMMUNITY);
        model.addAttribute("communityUsers",communityRoleUsers);
        return "all-communityUsers";
    }
    @PostMapping("/submitUser")
    public ModelAndView submitUser(@Valid @ModelAttribute UserDTO userDTO, BindingResult bindingResult, Model model ){
        if(bindingResult.hasErrors()) {
            model.addAttribute("user", userDTO);
            return new ModelAndView("user-register");
        }
        if(!userDTO.getPassword().equals(userDTO.getRepeatPassword())){
            model.addAttribute("PasswordDoNotMatch", "Password Do Not Match");
            model.addAttribute("user", userDTO);
            return new ModelAndView("user-register");
        }

        User user = userMapper.toEntity(userService.makeCryptedPassword(userDTO));
        userService.saveUserRoleUser(user);

        return new ModelAndView("result");
    } @PostMapping("/submitCommunity")
    public ModelAndView submitCommunity(@Valid @ModelAttribute User user, BindingResult bindingResult, Model model ){
        if(bindingResult.hasErrors()) {
            model.addAttribute("user", user);
            return new ModelAndView("community-register");
        }

        userService.saveUserRoleUser(user);

        return new ModelAndView("result");
    }

    @GetMapping("/details")
    public String getUserDetails(Model model, Authentication authentication) {
        String username = authentication.getName();
        User user = userRepository.getUserByUsername(username);

        model.addAttribute("user", user);
        return "user-profile";
    }

    @GetMapping("/access-denied")
    private String accessDenied() {
        return "/accessDenied";
    }
    @GetMapping("/{id}/delete")
    public ModelAndView deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return new ModelAndView("result");
    }
    @GetMapping("/{id}/edit")
    public String editUser(@PathVariable Long id, Model model) {
        User user = userService.findById(id);
        model.addAttribute("user", user);
        model.addAttribute("role", Role.values());
        return "editUser";
    }

    @PostMapping("/update")
    public ModelAndView updateMeal(@Valid @ModelAttribute User user, BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()){
            model.addAttribute("user", user);
            model.addAttribute("role", Role.values());
            return new ModelAndView("editUser");

        }
        userRepository.save(user);
        return new ModelAndView("result");
    }
}