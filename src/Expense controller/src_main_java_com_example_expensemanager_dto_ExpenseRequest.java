package com.example.expensemanager.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class ExpenseRequest {
    private String name;
    private String description;
    private BigDecimal amount;
    private String category;
    private LocalDate date;
}