package com.example.Active.Razgrad.user;
import com.example.Active.Razgrad.community.Category;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private UserRepository userRepository;
    private UserMapper userMapper;

    public UserService(BCryptPasswordEncoder bCryptPasswordEncoder, UserRepository userRepository, UserMapper userMapper) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public UserDTO makeCryptedPassword(UserDTO userDTO) {
        String hashsetPassword = bCryptPasswordEncoder.encode(userDTO.getPassword());
        userDTO.setPassword(hashsetPassword);
        return userDTO;
    }

    public void saveUserRoleUser(User user) {
        user.setRole(Role.ROLE_USER);
        userRepository.save(user);
    }
    public void saveUserRoleCommunity(User user) {

        user.setRole(Role.ROLE_COMMUNITY);
        //тези полета ако не ги сетна ми дава че не могат да са Null
        userRepository.save(user);
    }

    public List<User>getAllCommunityRoleUsers(List<User>userList){
        List<User>communityUserList = new ArrayList<>();
        for (User user:userList) {
            if(user.getRole().equals("ROLE_COMMUNITY")){
                communityUserList.add(user);
            }
        }return communityUserList;
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public User findById(Long id) {

        return userRepository.findById(id).orElse(null);
    }

    public void getAllCommunityUsers(Model model){
        List<User>communityRoleUsers = userRepository.getUserByRole(Role.ROLE_COMMUNITY);
        model.addAttribute("communityUsers",communityRoleUsers);
    }

    public String submitUser(UserDTO userDTO, BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()) {
            model.addAttribute("user", userDTO);
            return "user-register";
        }
        if(!userDTO.getPassword().equals(userDTO.getRepeatPassword())){
            model.addAttribute("PasswordDoNotMatch", "Password Do Not Match");
            model.addAttribute("user", userDTO);
            return "user-register";
        }

        User user = userMapper.toEntity(makeCryptedPassword(userDTO));
        saveUserRoleUser(user);

        return "result";
    }

    public String submitCommunity(UserDTO userDTO, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("user", userDTO);
            return "community-register";
        }
        if (!userDTO.getPassword().equals(userDTO.getRepeatPassword())) {
            model.addAttribute("PasswordDoNotMatch", "Password Do Not Match");
            model.addAttribute("user", userDTO);
            model.addAttribute("category", Category.values());
            return "community-register";
        }

        User user = userMapper.toEntity(makeCryptedPassword(userDTO));
        saveUserRoleCommunity(user);

        return "result";
    }
    public boolean cherForExistUserName(UserDTO userDTO) {
        List<User> optionalUser = (List<User>) userRepository.findAll();
        for (User currentUser : optionalUser) {
            if (currentUser.getUsername().equalsIgnoreCase(userDTO.getUsername())) {
                return true;
            }
        }return false;

    }
    public List<User> getAllCommunitiesByCategory(String category) {
        try {
            Category userCategory = Category.valueOf(category.toUpperCase());
            Iterable<User> userIterable = userRepository.findAll();
            List<User> sortedCommunity = new ArrayList<>();
            for (User user : userIterable) {
                if (user.getCategory() != null && user.getCategory().equals(userCategory)) {
                    sortedCommunity.add(user);
                }
            }
            return sortedCommunity;
        } catch (IllegalArgumentException e) {

            return Collections.emptyList(); // Връщаме празен списък в случай на грешка
        }
    }
}
