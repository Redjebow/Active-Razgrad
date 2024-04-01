package com.example.Active.Razgrad.comments;

import com.example.Active.Razgrad.activity.ActivityRepository;
import com.example.Active.Razgrad.user.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/comments")
public class CommentController {
    @Autowired
    CommentRepository commentRepository;

    @Autowired
    ActivityRepository activityRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    private CommentService commentService;

    @GetMapping
    public String listAllComments(Long id, Model model) {
        return commentService.listAllComments(id, model);
    }

    @PostMapping("/add")
    public String addComment(@Valid @ModelAttribute Comment comment, BindingResult bindingResult, Model model) {
        return commentService.addComment(comment, bindingResult, model);
    }

}
