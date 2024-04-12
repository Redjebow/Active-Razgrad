package com.example.Active.Razgrad.user;

import com.example.Active.Razgrad.FileUploadUtil;
import com.example.Active.Razgrad.community.Category;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.Collections;
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
        model.addAttribute("user", new UserDTO());
        model.addAttribute("category", Category.values());
        return "community-register";
    }
    @GetMapping("/all-communityUsers")
    public String getAllCommunityRoleUser(Model model){
        userService.getAllCommunityUsers(model);
        return "all-communityUsers";
    }

    @GetMapping("/allCommunities")
    public String getAllCommunities(Model model){
        userService.getAllCommunityUsers(model);
        return "all-communities";
    }

    @GetMapping("/all")
    public String getAllUsers(Model model){
        List<User> users = (List<User>) userRepository.getUserByRole(Role.ROLE_USER);
        Collections.reverse(users);
        model.addAttribute("users",users);
        return "all-users";
    }
    @PostMapping("/submitUser")
    public ModelAndView submitUser(@Valid @ModelAttribute UserDTO userDTO, BindingResult bindingResult, Model model ){
        if(bindingResult.hasErrors()) {
            model.addAttribute("user", userDTO);
            return new ModelAndView("user-register");
        }
        if(userService.cherForExistUserName(userDTO)) {
            model.addAttribute("not_unique_name", "Username already exist");
            model.addAttribute("user", userDTO);
            return new ModelAndView("user-register");
        }
        if(!userDTO.getPassword().equals(userDTO.getRepeatPassword())){
            model.addAttribute("PasswordDoNotMatch", "Password Do Not Match");
            model.addAttribute("user", userDTO);
            return new ModelAndView("user-register");
        }

        return new ModelAndView("result");
    } 
  @PostMapping("/submitCommunity")
public ModelAndView submitCommunity(@Valid @ModelAttribute UserDTO userDTO, BindingResult bindingResult, Model model, @RequestParam("image") MultipartFile multipartFile ) throws IOException {
        if(bindingResult.hasErrors()) {
            model.addAttribute("user", userDTO);
            return new ModelAndView("community-register");
        }
        if(!userDTO.getPassword().equals(userDTO.getRepeatPassword())){
            model.addAttribute("PasswordDoNotMatch", "Password Do Not Match");
            model.addAttribute("user", userDTO);
            model.addAttribute("category", Category.values());
            return new ModelAndView("community-register");
        }

        User user = userMapper.toEntity(userService.makeCryptedPassword(userDTO));
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        user.setPhotos(fileName);
        userService.saveUserRoleCommunity(user);
        String uploadDir = "user-photos/" + user.getId();

        FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
        return new ModelAndView("result");

    @PostMapping("/submitCommunity")
    public String submitCommunity(@Valid @ModelAttribute UserDTO userDTO, BindingResult bindingResult, Model model ){
        return userService.submitCommunity(userDTO,bindingResult,model);
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
        model.addAttribute("category", Category.values());
        return "editUser";
    }

    @PostMapping("/update")
    public ModelAndView updateMeal(@Valid @ModelAttribute User user, BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()){
            model.addAttribute("user", user);
            model.addAttribute("role", Role.values());
            model.addAttribute("category", Category.values());
            return new ModelAndView("editUser");

        }
        userRepository.save(user);
        return new ModelAndView("result");
    }
}