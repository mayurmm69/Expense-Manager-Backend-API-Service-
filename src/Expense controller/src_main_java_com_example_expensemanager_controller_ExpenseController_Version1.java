package com.example.expensemanager.controller;

import com.example.expensemanager.dto.*;
import com.example.expensemanager.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    @PostMapping
    public ResponseEntity<ExpenseResponse> createExpense(
            @RequestBody ExpenseRequest request,
            @AuthenticationPrincipal(expression = "username") String username
    ) {
        return ResponseEntity.ok(expenseService.createExpense(request, username));
    }

    @GetMapping
    public ResponseEntity<Page<ExpenseResponse>> getExpensesForMonth(
            @RequestParam int year,
            @RequestParam int month,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "date") String sortBy,
            @RequestParam(defaultValue = "DESC") String direction,
            @AuthenticationPrincipal(expression = "username") String username
    ) {
        return ResponseEntity.ok(expenseService.getExpensesForMonth(year, month, username, page, size, sortBy, direction));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExpenseResponse> getExpense(
            @PathVariable Long id,
            @AuthenticationPrincipal(expression = "username") String username
    ) {
        return expenseService.getExpense(id, username)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExpenseResponse> updateExpense(
            @PathVariable Long id,
            @RequestBody ExpenseRequest request,
            @AuthenticationPrincipal(expression = "username") String username
    ) {
        return expenseService.updateExpense(id, request, username)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteExpense(
            @PathVariable Long id,
            @AuthenticationPrincipal(expression = "username") String username
    ) {
        boolean deleted = expenseService.deleteExpense(id, username);
        if (deleted) return ResponseEntity.ok().build();
        else return ResponseEntity.notFound().build();
    }

    @PostMapping("/{expenseId}/comments")
    public ResponseEntity<CommentResponse> addComment(
            @PathVariable Long expenseId,
            @RequestBody CommentRequest request,
            @AuthenticationPrincipal(expression = "username") String username
    ) {
        return expenseService.addComment(expenseId, request, username)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}