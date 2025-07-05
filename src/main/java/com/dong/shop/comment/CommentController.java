package com.dong.shop.comment;

import com.dong.shop.Member.CustomUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class CommentController {
    private final CommentRepository commentRepository;
    private final CommentService commentService;

    @PostMapping("/comment")
    String postComment(@RequestParam String content,@RequestParam Long parent,Authentication auth) {
        System.out.println("Received content: " + content);
        System.out.println("Received parent: " + parent);
        CustomUser user = (CustomUser) auth.getPrincipal();
        String username = user.getUsername();
        System.out.println("Received parent: " + username);
        commentService.saveComment(content,user.getUsername(),parent);
        return "redirect:/detail/" + parent;
    }
}
