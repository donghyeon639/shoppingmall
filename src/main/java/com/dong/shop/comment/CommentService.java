package com.dong.shop.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;


    public void saveComment(String content, String username,Long parent) {
        Comment comment = new Comment();
        comment.setContent(content);
        comment.setUsername(username);
        comment.setParentId(parent);
        commentRepository.save(comment);
    }
    public void listComment(Model model){
        List<Comment> result = commentRepository.findAll();
        model.addAttribute("comments",result);
    }
}
