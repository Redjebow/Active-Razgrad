package com.example.Active.Razgrad;

import com.example.Active.Razgrad.user.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    UserService userService;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Mock
    private UserRepository userRepository;

    @Mock
    Model model;

    @Mock
    private UserMapper userMapper;

    @Mock
    private BindingResult bindingResult;

    @Test
    public void testMakeCryptedPassword() {
        // Given
        UserDTO userDTO = new UserDTO();
        userDTO.setPassword("password");
        when(bCryptPasswordEncoder.encode(anyString())).thenReturn("hashedPassword");

        // when
        UserDTO result = userService.makeCryptedPassword(userDTO);

        // then
        assertEquals("hashedPassword", result.getPassword());
    }

    @Test
    public void testSaveUserRoleUser() {
        // Given
        User user = new User();

        //when
        userService.saveUserRoleUser(user);

        // then
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void testSaveUserRoleCommunity(){
        //Given
        User user = mock(User.class);

        //when
        userService.saveUserRoleCommunity(user);

        //then
        verify(user).setRole(Role.ROLE_COMMUNITY);
        verify(userRepository).save(user);
    }

    @Test
    public void testDeleteUser() {
        // Given
        Long userId = 1L;
        doNothing().when(userRepository).deleteById(userId);

        // When
        userService.deleteUser(userId);

        // Then
        verify(userRepository, times(1)).deleteById(userId);
    }

    @Test
    public void testFindById() {
        // Given
        Long userId = 1L;
        User user = new User();
        user.setId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // When
        User foundUser = userService.findById(userId);

        // Then
        assertEquals(user, foundUser);
    }

    @Test
    public void testGetAllCommunityUsers() {
        // Given
        List<User> communityUsers = new ArrayList<>();
        when(userRepository.getUserByRole(Role.ROLE_COMMUNITY)).thenReturn(communityUsers);

        // When
        userService.getAllCommunityUsers(model);

        // Then
        verify(userRepository).getUserByRole(Role.ROLE_COMMUNITY);
        verify(model).addAttribute("communityUsers", communityUsers);
    }

    @Test
    public void testSubmitUserWithError() {
        // Given
        UserDTO userDTO = new UserDTO();
        userDTO.setPassword("password");
        userDTO.setRepeatPassword("differentPassword");
        when(bindingResult.hasErrors()).thenReturn(true);

        // When
        String viewName = userService.submitUser(userDTO, bindingResult, model);

        // Then
        verify(model).addAttribute("user", userDTO);
        assertEquals("user-register", viewName);
    }

    @Test
    public void testSubmitUserWithoutError() {
        // Given
        UserDTO userDTO = new UserDTO();
        userDTO.setPassword("password");
        userDTO.setRepeatPassword("password");
        when(bindingResult.hasErrors()).thenReturn(false);

        // When
        String viewName = userService.submitUser(userDTO, bindingResult, model);

        // Then
        verify(model, never()).addAttribute(eq("user"), any(UserDTO.class));
        assertEquals("result", viewName);
    }

    @Test
    public void testSubmitCommunityWithError(){
        // Given
        UserDTO userDTO = new UserDTO();
        userDTO.setPassword("password");
        userDTO.setRepeatPassword("differentPassword");
        when(bindingResult.hasErrors()).thenReturn(true);

        // When
        String viewName = userService.submitCommunity(userDTO, bindingResult, model);

        // Then
        verify(model).addAttribute("user", userDTO);
        assertEquals("community-register", viewName);

    }

//    @Test
//    public void testSubmitCommunityWithoutError() {
//        // Given
//        UserDTO userDTO = new UserDTO();
//        userDTO.setPassword("password");
//        userDTO.setRepeatPassword("password");
//        when(bindingResult.hasErrors()).thenReturn(false);
//
//        // When
//        String viewName = userService.submitCommunity(userDTO, bindingResult, model);
//
//        // Then
//        verify(model, never()).addAttribute(eq("user"), any(UserDTO.class));
//        assertEquals("result", viewName);
//    }
//    @Test
//    public void testGetAllCommunityRoleUsers() {
//        // Given
//        User communityUser1 = mock(User.class);
//        communityUser1.setRole(Role.ROLE_COMMUNITY);
////        when(communityUser1.getRole()).thenReturn(Role.ROLE_COMMUNITY);
//
//        User user2 = mock(User.class);
//        user2.setRole(Role.ROLE_USER);
////        when(user2.getRole()).thenReturn(Role.ROLE_USER);
//
//        List<User> userList = new ArrayList<>();
//        userList.add(communityUser1);
//        userList.add(user2);
//
//        // When
//        List<User> communityUsers = userService.getAllCommunityRoleUsers(userList);
//
//        // Then
//        assertEquals(1, communityUsers.size());
//    }


}
