package com.carepay.assignment.service;

import javax.validation.Valid;

import com.carepay.assignment.domain.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostService {
    PostDetails createPost(@Valid CreatePostRequest createPostRequest);

    Page<PostInfo> getPosts(final Pageable pageable);

    PostDetails getPostDetails(Long id);

    void deletePost(Long id);

    Page<CommentInfo> getPostComments(Long postId, final Pageable pageable);
    CommentInfo addPostComment(Long postId, @Valid CreateCommentRequest createCommentRequest);
    CommentInfo getSingleComment(Long postId, Long commentId);
    void deleteComment(Long postId, Long commentId);

}
