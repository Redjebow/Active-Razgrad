package com.example.Active.Razgrad.activity;

import com.example.Active.Razgrad.community.Category;
//import com.example.Active.Razgrad.community.CommunityRepository;
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

    @GetMapping("/add")//достъпна за организации и админ
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
        model.addAttribute("activity", activityRepository.findAll());
        return "list-activities";//не е добавена
    }

    @PostMapping("/delete")//достъпно за организацията и админ
    public String deleteActivity(@RequestParam Long id, Model model) {
        activityRepository.deleteById(id);
        model.addAttribute("activity",activityRepository.findAll());
        return "list-activities";//не е добавена
    }

    @GetMapping("/edit")//достъпно за организацията и админ
    public String editActivity(@RequestParam Long id, Model model) {
        return activityService.editActivity(id, model);
    }

    @PostMapping("/edit")//достъпно за организацията и админ
    public String saveActivity(@Valid @ModelAttribute Activity activity, BindingResult bindingResult, Model model) {
        return activityService.editActivitySave(activity, bindingResult, model);
    }

//    @PostMapping("/like")
//    public String likeActivity(@RequestParam Long id) {
//        Activity activity = activityRepository.findById(id).orElse(null);
//        if (activity != null ) {
//            activity.setLikes(activity.getLikes() + 1);
//            activityRepository.save(activity);
//        }
//        return "redirect:/activity/list";
//    }ако го искаме като функционалност трябва да се изпипа защото така се добавят лайкове до безкрай и трябва да добавим лайкове като полета в Activity


}
