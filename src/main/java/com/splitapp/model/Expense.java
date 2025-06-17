package com.splitapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "expenses")
public class Expense {
    @Id
    private String id;

    @Min(value = 0, message = "Amount must be positive")
    @NotNull(message = "Amount is required")
    private Double amount;

    @NotBlank(message = "Description is required")
    private String description;

    @NotBlank(message = "Paid by is required")
    private String paidBy;

    @NotNull(message = "Participants are required")
    private List<String> participants;

    // Split type: EQUAL, PERCENTAGE, EXACT
    @NotBlank(message = "Split type is required")
    private String splitType;

    // For PERCENTAGE or EXACT splits, map participant to share
    private Map<String, Double> splitDetails;
}
