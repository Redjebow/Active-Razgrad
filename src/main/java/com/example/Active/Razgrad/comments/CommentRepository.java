package com.example.Active.Razgrad.comments;

import com.example.Active.Razgrad.activity.Activity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CommentRepository extends CrudRepository<Comment, Long> {
    List<Comment> findByActivity(Activity activity);
}
