package com.example.expensemanager.service;

import com.example.expensemanager.dto.*;
import com.example.expensemanager.model.Comment;
import com.example.expensemanager.model.Expense;
import com.example.expensemanager.model.User;
import com.example.expensemanager.repository.CommentRepository;
import com.example.expensemanager.repository.ExpenseRepository;
import com.example.expensemanager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private UserRepository userRepository;

    public ExpenseResponse createExpense(ExpenseRequest request, String username) {
        User user = userRepository.findByUsername(username).orElseThrow();
        Expense expense = Expense.builder()
                .name(request.getName())
                .description(request.getDescription())
                .amount(request.getAmount())
                .category(request.getCategory())
                .date(request.getDate())
                .user(user)
                .build();
        expense = expenseRepository.save(expense);
        return mapToResponse(expense);
    }

    public Page<ExpenseResponse> getExpensesForMonth(int year, int month, String username, int page, int size, String sortBy, String direction) {
        User user = userRepository.findByUsername(username).orElseThrow();
        YearMonth ym = YearMonth.of(year, month);
        LocalDate start = ym.atDay(1);
        LocalDate end = ym.atEndOfMonth();
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(direction), sortBy));
        Page<Expense> expenses = expenseRepository.findByUserAndDateBetween(user, start, end, pageable);
        return expenses.map(this::mapToResponse);
    }

    public Optional<ExpenseResponse> getExpense(Long id, String username) {
        Expense expense = expenseRepository.findById(id).orElse(null);
        if (expense == null || !expense.getUser().getUsername().equals(username)) return Optional.empty();
        return Optional.of(mapToResponse(expense));
    }

    public Optional<ExpenseResponse> updateExpense(Long id, ExpenseRequest request, String username) {
        Expense expense = expenseRepository.findById(id).orElse(null);
        if (expense == null || !expense.getUser().getUsername().equals(username)) return Optional.empty();
        expense.setName(request.getName());
        expense.setDescription(request.getDescription());
        expense.setAmount(request.getAmount());
        expense.setCategory(request.getCategory());
        expense.setDate(request.getDate());
        expense = expenseRepository.save(expense);
        return Optional.of(mapToResponse(expense));
    }

    public boolean deleteExpense(Long id, String username) {
        Expense expense = expenseRepository.findById(id).orElse(null);
        if (expense == null || !expense.getUser().getUsername().equals(username)) return false;
        expenseRepository.delete(expense);
        return true;
    }

    public Optional<CommentResponse> addComment(Long expenseId, CommentRequest request, String username) {
        Expense expense = expenseRepository.findById(expenseId).orElse(null);
        User user = userRepository.findByUsername(username).orElse(null);
        if (expense == null || user == null) return Optional.empty();
        Comment comment = Comment.builder().text(request.getText()).expense(expense).user(user).build();
        comment = commentRepository.save(comment);
        return Optional.of(mapToCommentResponse(comment));
    }

    // Mapping helpers
    public ExpenseResponse mapToResponse(Expense expense) {
        ExpenseResponse dto = new ExpenseResponse();
        dto.setId(expense.getId());
        dto.setName(expense.getName());
        dto.setDescription(expense.getDescription());
        dto.setAmount(expense.getAmount());
        dto.setCategory(expense.getCategory());
        dto.setDate(expense.getDate());
        if (expense.getComments() != null) {
            dto.setComments(expense.getComments().stream()
                    .map(this::mapToCommentResponse)
                    .collect(Collectors.toList()));
        }
        return dto;
    }

    public CommentResponse mapToCommentResponse(Comment comment) {
        CommentResponse dto = new CommentResponse();
        dto.setId(comment.getId());
        dto.setText(comment.getText());
        dto.setUsername(comment.getUser().getUsername());
        return dto;
    }
}