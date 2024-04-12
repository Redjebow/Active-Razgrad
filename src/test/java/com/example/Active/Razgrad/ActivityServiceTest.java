package com.example.Active.Razgrad;

import com.example.Active.Razgrad.activity.Activity;
import com.example.Active.Razgrad.activity.ActivityRepository;
import com.example.Active.Razgrad.activity.ActivityService;
import com.example.Active.Razgrad.community.Category;
import com.example.Active.Razgrad.user.Role;
import com.example.Active.Razgrad.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ActivityServiceTest {
    @InjectMocks
    ActivityService activityService;

    @Mock
    BindingResult bindingResult;

    @Mock
    Model model;

    @Mock
    ActivityRepository activityRepository;

    @Mock
    UserRepository userRepository;

    @Test
    void testAddActivityWithError(){
        //Given
        Activity activity = new Activity();
        when(bindingResult.hasErrors()).thenReturn(true);
        //When
        String viewname = activityService.addActivity(activity,bindingResult, model);
        //Then
        assertEquals("add-activity",viewname);
        verify(model).addAttribute("category", Category.values());
        verify(model).addAttribute("communityCreator", userRepository.getUserByRole(Role.ROLE_COMMUNITY));
        verifyNoInteractions(activityRepository);
    }

    @Test
    void testAddActivityWithoutErrors(){
        //Given
        Activity activity = new Activity();
        when(bindingResult.hasErrors()).thenReturn(false);
        //When
        String viewname = activityService.addActivity(activity,bindingResult, model);
        //Then
        assertEquals("result",viewname);
        verify(activityRepository).save(activity);
        verifyNoInteractions(model);
        verifyNoMoreInteractions(activityRepository);
    }

    @Test
    void testEditActivityWhenActivityExists() {
        //Given
        Long id = 1L;
        Activity activity = new Activity();
        when(activityRepository.findById(id)).thenReturn(Optional.of(activity));
        //When
        String result = activityService.editActivity(id, model);
        //Then
        assertEquals("edit-activity", result);
        verify(model).addAttribute("activity", activity);
        verify(model).addAttribute("communityCreator", userRepository.getUserByRole(Role.ROLE_COMMUNITY));
        verify(model).addAttribute("category", Category.values());
    }

    @Test
    void testEditActivityWhenActivityDoesNotExist() {
        //Given
        Long id = 2L;
        when(activityRepository.findById(id)).thenReturn(Optional.empty());
        //When
        String result = activityService.editActivity(id, model);
        //Then
        assertEquals("list-activities", result);
        verify(model, never()).addAttribute(anyString(), any());
    }

    @Test
    void testEditActivitysaveWhenHasErrors(){
        //Given
        Activity activity = new Activity();
        when(bindingResult.hasErrors()).thenReturn(true);
        //When
        String viewname = activityService.editActivitySave(activity,bindingResult, model);
        //Then
        assertEquals("/activity/edit",viewname);
        verify(model).addAttribute("category", Category.values());
        verify(model).addAttribute("communityCreator", userRepository.getUserByRole(Role.ROLE_COMMUNITY));
        verifyNoInteractions(activityRepository);
    }

    @Test
    void testEditActivitySaveWithoutError(){
        //Given
        Activity activity = new Activity();
        when(bindingResult.hasErrors()).thenReturn(false);
        //When
        String viewname = activityService.editActivitySave(activity,bindingResult, model);
        //Then
        assertEquals("redirect:/activity/list",viewname);
        verify(model).addAttribute((activityRepository).save(activity));
    }

    @Test
    void testGetActivityByIdWhenActivityExists() {
        // Given
        Long id = 1L;
        Activity activity = new Activity(); // Create a dummy activity
        when(activityRepository.findById(id)).thenReturn(Optional.of(activity));
        // When
        Activity result = activityService.getActivityById(id);
        // Then
        assertEquals(activity, result);
    }

    @Test
    void testGetActivityByIdWhenActivityDoesNotExist() {
        // Given
        Long id = 2L;
        when(activityRepository.findById(id)).thenReturn(Optional.empty());
        // When
        Activity result = activityService.getActivityById(id);
        // Then
        assertEquals(null, result);
    }
    @Test
    void testFindActivityById_ExistingActivity() {
        // Given
        Long existingId = 1L;
        Activity expectedActivity = new Activity();
        when(activityRepository.findById(existingId)).thenReturn(Optional.of(expectedActivity));

        // when
        Activity actualActivity = activityService.findActivityById(existingId);

        // then
        assertEquals(expectedActivity, actualActivity);
    }

    @Test
    void testFindActivityById_NonExistingActivity() {
        // given
        Long nonExistingId = 2L;
        when(activityRepository.findById(nonExistingId)).thenReturn(Optional.empty());

        // then
        assertThrows(IllegalArgumentException.class, () -> {
            activityService.findActivityById(nonExistingId);
        });
    }
}
