package com.example.Active.Razgrad.comments;


import com.example.Active.Razgrad.activity.Activity;
import com.example.Active.Razgrad.activity.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    public String listAllComments(Long id, Model model) {
        Optional<Activity> optionalActivity = activityRepository.findById(id);
        if (optionalActivity.isPresent()) {
            Activity activity = optionalActivity.get();
            model.addAttribute("newComment", new Comment());
            model.addAttribute("selectActivity", activity);
            model.addAttribute("commentsList", activity.getCommentsList());
            return "add-comment";
        }
        return "redirect:/activity/list";
    }
    public String addComment(Comment comment, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            Optional<Activity> optionalActivity = activityRepository.findById(comment.getActivity().getId());
            if (optionalActivity.isPresent()) {
                Activity activity = optionalActivity.get();
                model.addAttribute("selectActivity", activity);
                model.addAttribute("commentsList", activity.getCommentsList());
                model.addAttribute("newComment", comment);
                return "add-comment";
            }
            return "redirect:/activity/list";
        }
        commentRepository.save(comment);
        return "redirect:/activity/list";
    }
}
