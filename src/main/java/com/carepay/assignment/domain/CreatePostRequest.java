package com.carepay.assignment.domain;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
public class CreatePostRequest {
    @Length(min = 1, max = 128)
    private String title;

    @NotBlank
    private String content;
}
