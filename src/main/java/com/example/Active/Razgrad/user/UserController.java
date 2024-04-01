package com.example.Active.Razgrad.user;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/users")
public class UserController {
    public UserService userService;
    public UserController(UserService userService){
        this.userService = userService;
    }

    @Autowired
    UserRepository userRepository;

    @GetMapping("/add")
    public String addUserForm(Model model){
        model.addAttribute("user", new User());
        return "register";
    }
    @PostMapping("/submit")
    public ModelAndView submitUser(@Valid @ModelAttribute User user, BindingResult bindingResult, Model model ){
        if(bindingResult.hasErrors()) {
            model.addAttribute("user", user);
            return new ModelAndView("register");
        }
        userService.saveUser(userService.makeCryotedPassword(user));

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
}
