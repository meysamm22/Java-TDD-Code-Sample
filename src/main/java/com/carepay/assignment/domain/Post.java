package com.carepay.assignment.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity @Getter @NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private String content;

    @OneToMany(
            mappedBy = "post",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private List<Comment> comments;

    public Post(String title, String content){
        this.title = title;
        this.content = content;
    }

    public void addComment(CreateCommentRequest createCommentRequest){
        Comment comment = new Comment(createCommentRequest.getComment());
        if (this.comments == null){
            this.comments = new ArrayList<>();
        }
        comment.setPost(this);
        this.comments.add(comment);
    }
}
