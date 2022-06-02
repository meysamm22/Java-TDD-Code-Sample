package com.carepay.assignment.service;

import javax.validation.Valid;

import com.carepay.assignment.domain.*;
import com.carepay.assignment.exception.NotFoundException;
import com.carepay.assignment.repository.PostRepository;
import com.carepay.assignment.service.factory.PostFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository repository;
    @Autowired
    private PostFactory postFactory;

    @Override
    public PostDetails createPost(@Valid CreatePostRequest createPostRequest) {
        Post post = postFactory.make(createPostRequest);
        return makePostDetails(repository.save(post));
    }

    @Override
    public Page<PostInfo> getPosts(Pageable pageable) {
        Page<Post> posts = repository.findAll(pageable);
        List<PostInfo> postInfos = posts.stream().map(post -> new PostInfo(post.getId(), post.getTitle())).collect(Collectors.toList());
        return new PageImpl<>(postInfos);
    }

    @Override
    public PostDetails getPostDetails(Long id) {
        Post post = getPost(id);
        return makePostDetails(post);
    }

    private Post getPost(Long id) {
        return repository.findById(id).orElseThrow(NotFoundException::new);
    }


    @Override
    public void deletePost(Long id) {
        Post post = getPost(id);
        repository.delete(post);
    }

    @Override
    public Page<CommentInfo> getPostComments(Long postId, Pageable pageable) {
        Post post = getPost(postId);
        List<CommentInfo> commentInfos = post.getComments().stream().map(this::makeCommentInfo).collect(Collectors.toList());
        return new PageImpl<>(commentInfos);
    }

    @Override
    public CommentInfo addPostComment(Long postId, @Valid CreateCommentRequest createCommentRequest) {
        Post post = getPost(postId);
        post.addComment(createCommentRequest);
        repository.save(post);
        return makeCommentInfo(
                post.getComments().stream().skip((long) post.getComments().size() - 1).findFirst().get()
        );
    }

    @Override
    public CommentInfo getSingleComment(Long postId, Long commentId) {
        Post post = getPost(postId);
        return makeCommentInfo(
                findDesireComment(commentId, post)
        );


    }

    @Override
    public void deleteComment(Long postId, Long commentId) {
        Post post = getPost(postId);
        post.getComments().remove(findDesireComment(commentId, post));
        repository.save(post);
    }

    private Comment findDesireComment(Long commentId, Post post) {
        return post.getComments().stream().filter(comment -> comment.getId().equals(commentId)).findFirst().orElseThrow(NotFoundException::new);
    }

    private CommentInfo makeCommentInfo(Comment comment) {
        return new CommentInfo(comment.getId(), comment.getComment());
    }


    private PostDetails makePostDetails(Post post) {
        return new PostDetails(post.getId(), post.getTitle(), post.getContent());
    }

}
