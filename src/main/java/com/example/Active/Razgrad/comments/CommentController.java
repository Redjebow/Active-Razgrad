package com.example.Active.Razgrad.comments;

import com.example.Active.Razgrad.activity.ActivityRepository;
import com.example.Active.Razgrad.user.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


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

    @GetMapping()
    public String getActivity(@PathVariable Long id, Authentication authentication, Model model){

        return commentService.allCommentsForActivity(id, model, authentication);
    }

    @PostMapping("/add")
    public String addComment(@Valid @ModelAttribute("newComment") Comment newComment,BindingResult bindingResult, Model model){
        return commentService.addComment(newComment, bindingResult, model);
    }

}
