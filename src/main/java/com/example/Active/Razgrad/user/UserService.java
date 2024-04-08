package com.example.Active.Razgrad.user;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
}
