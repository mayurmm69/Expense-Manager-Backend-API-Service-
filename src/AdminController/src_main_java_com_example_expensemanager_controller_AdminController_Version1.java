package com.example.expensemanager.controller;

import com.example.expensemanager.dto.ExpenseResponse;
import com.example.expensemanager.model.User;
import com.example.expensemanager.repository.ExpenseRepository;
import com.example.expensemanager.repository.UserRepository;
import com.example.expensemanager.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ExpenseRepository expenseRepository;
    @Autowired
    private ExpenseService expenseService;

    // List all users
    @GetMapping("/users")
    public ResponseEntity<List<String>> listUsers() {
        List<String> usernames = userRepository.findAll()
                .stream()
                .map(User::getUsername)
                .collect(Collectors.toList());
        return ResponseEntity.ok(usernames);
    }

    // List all expenses (paginated and sorted)
    @GetMapping("/expenses")
    public ResponseEntity<Page<ExpenseResponse>> listAllExpenses(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "date") String sortBy,
            @RequestParam(defaultValue = "DESC") String direction
    ) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(direction), sortBy));
        Page<ExpenseResponse> expenses = expenseRepository.findAll(pageRequest)
                .map(expenseService::mapToResponse);
        return ResponseEntity.ok(expenses);
    }

    // Optionally: Delete a user by username
    @DeleteMapping("/users/{username}")
    public ResponseEntity<?> deleteUser(@PathVariable String username) {
        User user = userRepository.findByUsername(username).orElse(null);
        if (user == null) return ResponseEntity.notFound().build();
        userRepository.delete(user);
        return ResponseEntity.ok().build();
    }
}