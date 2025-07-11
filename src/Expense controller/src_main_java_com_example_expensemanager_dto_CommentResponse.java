package com.example.expensemanager.dto;

import lombok.Data;

@Data
public class CommentResponse {
    private Long id;
    private String text;
    private String username;
}