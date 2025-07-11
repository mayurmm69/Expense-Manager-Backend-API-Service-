package com.example.expensemanager.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
public class ExpenseResponse {
    private Long id;
    private String name;
    private String description;
    private BigDecimal amount;
    private String category;
    private LocalDate date;
    private List<CommentResponse> comments;
}