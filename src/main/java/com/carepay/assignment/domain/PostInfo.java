package com.carepay.assignment.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor @NoArgsConstructor @Data
public class PostInfo implements Serializable {
    private Long id;
    private String title;

}
