package com.example.Active.Razgrad;

import com.example.Active.Razgrad.activity.Activity;
import com.example.Active.Razgrad.activity.ActivityRepository;
import com.example.Active.Razgrad.comments.Comment;
import com.example.Active.Razgrad.comments.CommentRepository;
import com.example.Active.Razgrad.comments.CommentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CommentServiceTest {
    @Mock
    private CommentRepository commentRepository;

    @Mock
    private ActivityRepository activityRepository;

    @Mock
    private Model model;

    @InjectMocks
    private CommentService commentService;

    @Test
    void testListAllCommentsWhenActivityExists() {
        // Given
        Long id = 1L;
        Activity activity = new Activity();
        List<Comment> commentsList = new ArrayList<>();
        activity.setCommentsList(commentsList);
        when(activityRepository.findById(id)).thenReturn(Optional.of(activity));
        // When
        String result = commentService.listAllComments(id, model);
        // then
        assertEquals("add-comment", result);
        verify(model).addAttribute(eq("newComment"), any(Comment.class));
        verify(model).addAttribute(eq("selectActivity"), same(activity));
        verify(model).addAttribute(eq("commentsList"), same(commentsList));
    }

    @Test
    void testListAllCommentsWhenActivityNoExist(){
        //Given
        Long id = 2L;
        when(activityRepository.findById(id)).thenReturn(Optional.empty());
        //when
        String result = commentService.listAllComments(id, model);
        //Then
        assertEquals("redirect:/activity/list",result);
        verify(model, never()).addAttribute(anyString(), any());
    }
    @Test
    void testAddCommentWhenNoErrors() {
        //Given
        Comment comment = new Comment();
        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(false);

        //when
        String result = commentService.addComment(comment, bindingResult, model);

        //Then
        assertEquals("redirect:/activity/list", result);
        verify(commentRepository).save(comment);
    }

}
