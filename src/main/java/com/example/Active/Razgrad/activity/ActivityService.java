package com.example.Active.Razgrad.activity;

import com.example.Active.Razgrad.community.Category;
import com.example.Active.Razgrad.user.Role;
import com.example.Active.Razgrad.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.util.Optional;

@Service
public class ActivityService {

    @Autowired
    ActivityRepository activityRepository;

    @Autowired
    private UserRepository userRepository;


    public String addActivity(Activity activity, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("communityCreator", userRepository.getUserByRole(Role.ROLE_COMMUNITY) );
            model.addAttribute("category",Category.values() );
            return "add-activity";
        }
        activityRepository.save(activity);
        return "redirect:/result";
    }

    public String editActivity(Long id, Model model) {
        Optional<Activity> optionalActivity = activityRepository.findById(id);
        if (optionalActivity.isPresent()) {
            Activity activity = optionalActivity.get();
            model.addAttribute("activity", activity);
            model.addAttribute("communityCreator", userRepository.findAll() );
            model.addAttribute("category", Category.values());
            return "edit-activity";//не е добавена
        }
        return "list-activities";//не е добавена
    }

    public String editActivitySave(Activity activity, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("communityCreator", userRepository.findAll() );
            model.addAttribute("category",Category.values() );
            return "/activity/edit";
        }
        model.addAttribute(activityRepository.save(activity));
        return "redirect:/activity/list";
    }

    public Activity getActivityById(Long id) {
        return activityRepository.findById(id).orElse(null);
    }

}
