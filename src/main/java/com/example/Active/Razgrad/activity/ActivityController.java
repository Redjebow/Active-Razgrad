package com.example.Active.Razgrad.activity;

import com.example.Active.Razgrad.community.Category;
import com.example.Active.Razgrad.user.Role;
import com.example.Active.Razgrad.user.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/activity")
public class ActivityController {
    @Autowired
    private ActivityRepository activityRepository;
    @Autowired
    private ActivityService activityService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/add")
    public String addActivity(Model model){
        model.addAttribute("activity", new Activity());
        model.addAttribute("communityCreator", userRepository.getUserByRole(Role.ROLE_COMMUNITY) );
        model.addAttribute("category", Category.values());
        return "add-activity";
    }

    @PostMapping("/save")
    public String submitActivity(@ModelAttribute @Valid Activity activity, BindingResult bindingResult, Model model){
        return activityService.addActivity(activity, bindingResult,model);
    }

    @GetMapping("/list")
    public String listActivities(Model model) {
        model.addAttribute("categories", Category.values());
        model.addAttribute("activity", activityRepository.findAll());
        return "list-activities";
    }
    @GetMapping("/sortedList")
    public String sortedListActivities(@RequestParam("category") String category, Model model) {
        model.addAttribute("categories", Category.values());
        model.addAttribute("activity", activityService.getAllActivitiesByCategory(category));
        return "sorted-activities";
    }

    @PostMapping("/delete")
    public String deleteActivity(@RequestParam Long id, Model model) {
        activityRepository.deleteById(id);
        model.addAttribute("activity",activityRepository.findAll());
        return "list-activities";
    }

    @GetMapping("/edit")
    public String editActivity(@RequestParam Long id, Model model) {
        return activityService.editActivity(id, model);
    }

    @PostMapping("/edit")
    public String saveActivity(@Valid @ModelAttribute Activity activity, BindingResult bindingResult, Model model) {
        return activityService.editActivitySave(activity, bindingResult, model);
    }

}
