package com.carepay.assignment.domain;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
public class CreateCommentRequest {
    @Length(min = 1, max = 120)
    @NotBlank
    private String comment;
}
