package com.carepay.assignment.web;

import com.carepay.assignment.domain.*;
import com.carepay.assignment.exception.NotFoundException;
import com.carepay.assignment.service.PostService;
import com.carepay.assignment.service.PostServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/posts/{postId}/comments", produces = MediaType.APPLICATION_JSON_VALUE)
public class CommentController {
    private final PostService postService;

    public CommentController(PostServiceImpl postService) {
        this.postService = postService;
    }

    @GetMapping
    ResponseEntity<Page<CommentInfo>> getComments(@PathVariable Long postId, Pageable pageable) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(postService.getPostComments(postId, pageable));
        }catch (NotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    CommentInfo createComment(@PathVariable Long postId, @Valid @RequestBody CreateCommentRequest createCommentRequest) {
        return postService.addPostComment(postId, createCommentRequest);
    }

    @GetMapping("/{id}")
    ResponseEntity<CommentInfo> getSingleComment(@PathVariable Long postId, @PathVariable Long id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(postService.getSingleComment(postId, id));
        }catch (NotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteComment(@PathVariable Long postId, @PathVariable Long id) {
        try {
            postService.deleteComment(postId, id);
            return ResponseEntity.status(HttpStatus.OK).body(null);
        }catch (NotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

}
