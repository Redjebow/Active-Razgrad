package com.example.Active.Razgrad.user;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

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

    public void saveUser(User user) {

        user.setRole(Role.ROLE_USER);
        user.setAddress("");
        user.setBulstat(0);
        user.setDescription("");
        user.setWebsite("");
        user.setLastName("");
        //тези полета ако не ги сетна ми дава че не могат да са Null
        userRepository.save(user);
    }
}
