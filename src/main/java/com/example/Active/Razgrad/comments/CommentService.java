package com.example.Active.Razgrad.comments;


import com.example.Active.Razgrad.activity.Activity;
import com.example.Active.Razgrad.activity.ActivityRepository;
import com.example.Active.Razgrad.activity.ActivityService;
import com.example.Active.Razgrad.user.User;
import com.example.Active.Razgrad.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.util.Optional;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private ActivityRepository activityRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ActivityService activityService;

    public String allCommentsForActivity(Long id, Model model, Authentication authentication) {
        User user = userRepository.findByUsername(authentication.getName());
        Comment comment = new Comment();
        comment.setUser(user);
        comment.setActivity(activityService.findActivityById(id));
        model.addAttribute("newComment", comment);
        model.addAttribute("selectedActivity", activityService.findActivityById(id));
        model.addAttribute("commentList", commentRepository.findByActivity(comment.getActivity()));

        return "activity";

    }
    public String addComment(Comment comment, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            Optional<Activity> optionalActivity = activityRepository.findById(comment.getActivity().getId());
            if (optionalActivity.isPresent()) {
                Activity activity = optionalActivity.get();
                model.addAttribute("selectActivity", activity);
                model.addAttribute("commentsList", commentRepository.findByActivity(comment.getActivity()));
                model.addAttribute("newComment", comment);
                return "activity";
            }
            return "redirect:/activity/list";
        }
        commentRepository.save(comment);
        return "redirect:/activities/all";
    }
}
