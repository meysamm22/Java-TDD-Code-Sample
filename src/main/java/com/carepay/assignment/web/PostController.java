package com.carepay.assignment.web;

import javax.validation.Valid;

import com.carepay.assignment.domain.*;
import com.carepay.assignment.exception.NotFoundException;
import com.carepay.assignment.service.PostService;
import com.carepay.assignment.service.PostServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController
@RequestMapping(path = "/posts", produces = MediaType.APPLICATION_JSON_VALUE)
public class PostController {
    private final PostService postService;

    public PostController(PostServiceImpl postService) {
        this.postService = postService;
    }

    @GetMapping
    Page<PostInfo> getPosts(Pageable pageable) {
        return postService.getPosts(pageable);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    PostDetails createPost(@Valid @RequestBody CreatePostRequest createPostRequest) {
        return postService.createPost(createPostRequest);
    }

    @GetMapping("{id}")
    ResponseEntity<PostDetails> getPostDetails(@PathVariable("id") final Long id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(postService.getPostDetails(id));
        }catch (NotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<?> deletePost(@PathVariable("id") final Long id) {
        try {
            postService.deletePost(id);
            return ResponseEntity.status(HttpStatus.OK).body("");
        }catch (NotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }





}
