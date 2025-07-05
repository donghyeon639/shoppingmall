package com.dong.shop.comment;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // private으로 변경

    private String username;
    private String content;  // @Column(length = 1000) 제거
    private Long parentId;
}
