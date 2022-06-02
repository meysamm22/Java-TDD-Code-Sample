package com.carepay.assignment.domain;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class PostDetails extends PostInfo {
    private String content;

    public PostDetails(Long id, String title,String content){
        super.setId(id);
        super.setTitle(title);
        this.content = content;
    }
}
