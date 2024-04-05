package com.example.Active.Razgrad.user;

//import com.example.Active.Razgrad.community.Community;
//import com.example.Active.Razgrad.community.CommunityRepository;
//import com.example.Active.Razgrad.community.MyCommunityDetails;
import com.example.Active.Razgrad.user.MyUserDetails;
import com.example.Active.Razgrad.user.User;
import com.example.Active.Razgrad.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private
    UserRepository userRepository;

//    @Autowired
//    private CommunityRepository communityRepository;

    @Override
    public UserDetails loadUserByUsername(String userNameOrEmail)
            throws UsernameNotFoundException {
        User userByUsername = userRepository.getUserByUsername(userNameOrEmail);
        User userByEmail = userRepository.getUserByEmail(userNameOrEmail);

        if (userByUsername != null) {
            return new MyUserDetails(userByUsername);
        } else if (userByEmail != null) {
            return new MyUserDetails(userByEmail);
        }
        throw new UsernameNotFoundException("Could not find user");
    }

//    @Override
//    public UserDetails loadUserByUsername(String userNameOrEmail) throws UsernameNotFoundException {
//        User user = userRepository.getUserByUsername(userNameOrEmail);
//        if (user != null) {
//            return new MyUserDetails(user);
//        }
//
//        Community community = communityRepository.getCommunityByUsername(userNameOrEmail);
//        if (community != null) {
//            return new MyCommunityDetails(community);
//        }
//
//        throw new UsernameNotFoundException("Could not find user or community with the provided username or email");
//    }
//
}
