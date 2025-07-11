package com.example.expensemanager.repository;

import com.example.expensemanager.model.Expense;
import com.example.expensemanager.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    Page<Expense> findByUserAndDateBetween(
        User user,
        LocalDate startDate,
        LocalDate endDate,
        Pageable pageable
    );
}