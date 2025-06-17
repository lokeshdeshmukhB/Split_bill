package com.splitapp.service;

import com.splitapp.model.Expense;
import com.splitapp.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SettlementService {
    @Autowired
    private ExpenseRepository expenseRepository;

    // Returns a map of person -> net balance (positive: owed, negative: owes)
    public Map<String, Double> calculateBalances() {
        List<Expense> expenses = expenseRepository.findAll();
        Map<String, Double> balances = new HashMap<>();
        for (Expense expense : expenses) {
            double total = expense.getAmount();
            List<String> participants = expense.getParticipants();
            String paidBy = expense.getPaidBy();
            String splitType = expense.getSplitType();
            Map<String, Double> splitDetails = expense.getSplitDetails();
            int n = participants.size();
            if (n == 0) continue;
            if (splitType.equalsIgnoreCase("EQUAL")) {
                double share = Math.round((total / n) * 100.0) / 100.0;
                for (String person : participants) {
                    balances.put(person, balances.getOrDefault(person, 0.0) - share);
                }
                balances.put(paidBy, balances.getOrDefault(paidBy, 0.0) + total);
            } else if (splitType.equalsIgnoreCase("PERCENTAGE") && splitDetails != null) {
                for (String person : participants) {
                    double percent = splitDetails.getOrDefault(person, 0.0);
                    double share = Math.round((total * percent / 100.0) * 100.0) / 100.0;
                    balances.put(person, balances.getOrDefault(person, 0.0) - share);
                }
                balances.put(paidBy, balances.getOrDefault(paidBy, 0.0) + total);
            } else if (splitType.equalsIgnoreCase("EXACT") && splitDetails != null) {
                for (String person : participants) {
                    double share = splitDetails.getOrDefault(person, 0.0);
                    balances.put(person, balances.getOrDefault(person, 0.0) - share);
                }
                balances.put(paidBy, balances.getOrDefault(paidBy, 0.0) + total);
            }
        }
        // Round balances to 2 decimal places
        for (String person : balances.keySet()) {
            balances.put(person, Math.round(balances.get(person) * 100.0) / 100.0);
        }
        return balances;
    }

    // Returns a list of settlements: who pays whom and how much (minimized transactions)
    public List<Map<String, Object>> getSettlements() {
        Map<String, Double> balances = calculateBalances();
        List<Map.Entry<String, Double>> creditors = new ArrayList<>();
        List<Map.Entry<String, Double>> debtors = new ArrayList<>();
        for (Map.Entry<String, Double> entry : balances.entrySet()) {
            if (entry.getValue() > 0.01) creditors.add(entry);
            else if (entry.getValue() < -0.01) debtors.add(entry);
        }
        creditors.sort((a, b) -> Double.compare(b.getValue(), a.getValue()));
        debtors.sort((a, b) -> Double.compare(a.getValue(), b.getValue()));
        List<Map<String, Object>> settlements = new ArrayList<>();
        int i = 0, j = 0;
        while (i < debtors.size() && j < creditors.size()) {
            String debtor = debtors.get(i).getKey();
            double debt = -debtors.get(i).getValue();
            String creditor = creditors.get(j).getKey();
            double credit = creditors.get(j).getValue();
            double amount = Math.min(debt, credit);
            Map<String, Object> settlement = new HashMap<>();
            settlement.put("from", debtor);
            settlement.put("to", creditor);
            settlement.put("amount", Math.round(amount * 100.0) / 100.0);
            settlements.add(settlement);
            debtors.get(i).setValue(debtors.get(i).getValue() + amount);
            creditors.get(j).setValue(creditors.get(j).getValue() - amount);
            if (Math.abs(debtors.get(i).getValue()) < 0.01) i++;
            if (Math.abs(creditors.get(j).getValue()) < 0.01) j++;
        }
        return settlements;
    }

    // Returns a set of all people involved in expenses
    public Set<String> getAllPeople() {
        List<Expense> expenses = expenseRepository.findAll();
        Set<String> people = new HashSet<>();
        for (Expense expense : expenses) {
            people.add(expense.getPaidBy());
            people.addAll(expense.getParticipants());
        }
        return people;
    }
}
