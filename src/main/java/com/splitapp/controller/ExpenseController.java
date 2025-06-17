package com.splitapp.controller;

import com.splitapp.model.Expense;
import com.splitapp.service.ExpenseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/expenses")
public class ExpenseController {
    @Autowired
    private ExpenseService expenseService;

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllExpenses() {
        List<Expense> expenses = expenseService.getAllExpenses();
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("data", expenses);
        response.put("message", "Expenses fetched successfully");
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> addExpense(@Valid @RequestBody Expense expense) {
        Expense saved = expenseService.addExpense(expense);
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("data", saved);
        response.put("message", "Expense added successfully");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateExpense(@PathVariable String id, @Valid @RequestBody Expense expense) {
        Optional<Expense> updated = expenseService.updateExpense(id, expense);
        Map<String, Object> response = new HashMap<>();
        if (updated.isPresent()) {
            response.put("success", true);
            response.put("data", updated.get());
            response.put("message", "Expense updated successfully");
            return ResponseEntity.ok(response);
        } else {
            response.put("success", false);
            response.put("message", "Expense not found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteExpense(@PathVariable String id) {
        boolean deleted = expenseService.deleteExpense(id);
        Map<String, Object> response = new HashMap<>();
        if (deleted) {
            response.put("success", true);
            response.put("message", "Expense deleted successfully");
            return ResponseEntity.ok(response);
        } else {
            response.put("success", false);
            response.put("message", "Expense not found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }
}
