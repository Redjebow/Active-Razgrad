package com.example.Active.Razgrad.activity;

import com.example.Active.Razgrad.community.Category;
import com.example.Active.Razgrad.user.Role;
import com.example.Active.Razgrad.user.User;
import com.example.Active.Razgrad.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class ActivityService {

    @Autowired
    ActivityRepository activityRepository;

    @Autowired
    private UserRepository userRepository;


    public String addActivity(Activity activity, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("communityCreator", userRepository.getUserByRole(Role.ROLE_COMMUNITY));
            model.addAttribute("category", Category.values());
            return "add-activity";
        }
            activityRepository.save(activity);
            return "result";
        }

    public String editActivity(Long id, Model model) {
        Optional<Activity> optionalActivity = activityRepository.findById(id);
        if (optionalActivity.isPresent()) {
            Activity activity = optionalActivity.get();
            model.addAttribute("activity", activity);
            model.addAttribute("communityCreator", userRepository.getUserByRole(Role.ROLE_COMMUNITY) );
            model.addAttribute("category", Category.values());
            return "edit-activity";
        }
        return "list-activities";
    }

    public String editActivitySave(Activity activity, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("communityCreator", userRepository.getUserByRole(Role.ROLE_COMMUNITY) );
            model.addAttribute("category",Category.values() );
            return "/activity/edit";
        }
        model.addAttribute(activityRepository.save(activity));
        return "redirect:/activity/list";
    }

    public Activity getActivityById(Long id) {
        return activityRepository.findById(id).orElse(null);
    }
    public Activity findActivityById(Long id){
        if(activityRepository.findById(id).isPresent()){
            return activityRepository.findById(id).get();
        } else {
            throw new IllegalArgumentException("Activity not found!");
        }
    }
    public List<Activity> getAllActivitiesByCategory(String category) {
        try {
            Category userCategory = Category.valueOf(category.toUpperCase());
            Iterable<Activity> userIterable = activityRepository.findAll();
            List<Activity> sortedActivity = new ArrayList<>();
            for (Activity activity : sortedActivity) {
                if (activity.getCategory() != null && activity.getCategory().equals(userCategory)) {
                    sortedActivity.add(activity);
                }
            }
            return sortedActivity;
        } catch (IllegalArgumentException e) {

            return Collections.emptyList(); // Връщаме празен списък в случай на грешка
        }
    }

}
