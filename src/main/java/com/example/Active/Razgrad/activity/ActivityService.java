package com.example.Active.Razgrad.activity;

import com.example.Active.Razgrad.category.CategoryRepository;
import com.example.Active.Razgrad.community.CommunityRepository;
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
    private CommunityRepository communityRepository;
    @Autowired
    private CategoryRepository categoryRepository;


    public String addActivity(Activity activity, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("community", communityRepository.findAll() );
            model.addAttribute("category",categoryRepository.findAll() );
            return "add-activity";
        }
        activityRepository.save(activity);
        return "redirect:/activity/list";
    }

    public String editActivity(Long id, Model model) {
        Optional<Activity> optionalActivity = activityRepository.findById(id);
        if (optionalActivity.isPresent()) {
            Activity activity = optionalActivity.get();
            model.addAttribute("activity", activity);
            model.addAttribute("community", communityRepository.findAll() );
            model.addAttribute("category",categoryRepository.findAll() );
            return "edit-activity";//не е добавена
        }
        return "list-activities";//не е добавена
    }

    public String editActivitySave(Activity activity, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("community", communityRepository.findAll() );
            model.addAttribute("category",categoryRepository.findAll() );
            return "/activity/edit";
        }
        model.addAttribute(activityRepository.save(activity));
        return "redirect:/activity/list";
    }

    public Activity getActivityById(Long id) {
        return activityRepository.findById(id).orElse(null);
    }

}
