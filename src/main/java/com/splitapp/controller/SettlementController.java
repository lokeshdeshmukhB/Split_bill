package com.splitapp.controller;

import com.splitapp.service.SettlementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("")
public class SettlementController {
    @Autowired
    private SettlementService settlementService;

    @GetMapping("/settlements")
    public ResponseEntity<Map<String, Object>> getSettlements() {
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("data", settlementService.getSettlements());
        response.put("message", "Settlement summary fetched successfully");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/balances")
    public ResponseEntity<Map<String, Object>> getBalances() {
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("data", settlementService.calculateBalances());
        response.put("message", "Balances fetched successfully");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/people")
    public ResponseEntity<Map<String, Object>> getPeople() {
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("data", settlementService.getAllPeople());
        response.put("message", "People fetched successfully");
        return ResponseEntity.ok(response);
    }
}
