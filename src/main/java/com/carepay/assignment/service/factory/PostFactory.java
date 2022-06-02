package com.carepay.assignment.service.factory;

import com.carepay.assignment.domain.CreatePostRequest;
import com.carepay.assignment.domain.Post;
import org.springframework.stereotype.Service;

@Service
public class PostFactory {
    public Post make(CreatePostRequest createPostRequest){
        return new Post(createPostRequest.getTitle(), createPostRequest.getContent());
    }
}
