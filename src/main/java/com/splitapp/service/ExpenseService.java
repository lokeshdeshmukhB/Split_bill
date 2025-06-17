package com.splitapp.service;

import com.splitapp.model.Expense;
import com.splitapp.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExpenseService {
    @Autowired
    private ExpenseRepository expenseRepository;

    public List<Expense> getAllExpenses() {
        return expenseRepository.findAll();
    }

    public Optional<Expense> getExpenseById(String id) {
        return expenseRepository.findById(id);
    }

    public Expense addExpense(Expense expense) {
        return expenseRepository.save(expense);
    }

    public Optional<Expense> updateExpense(String id, Expense updatedExpense) {
        return expenseRepository.findById(id).map(expense -> {
            expense.setAmount(updatedExpense.getAmount());
            expense.setDescription(updatedExpense.getDescription());
            expense.setPaidBy(updatedExpense.getPaidBy());
            expense.setParticipants(updatedExpense.getParticipants());
            expense.setSplitType(updatedExpense.getSplitType());
            expense.setSplitDetails(updatedExpense.getSplitDetails());
            return expenseRepository.save(expense);
        });
    }

    public boolean deleteExpense(String id) {
        if (expenseRepository.existsById(id)) {
            expenseRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
