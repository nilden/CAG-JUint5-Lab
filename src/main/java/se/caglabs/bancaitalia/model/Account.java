package se.caglabs.bancaitalia.model;

import lombok.*;

@Data
@AllArgsConstructor
public class Account {
    private String accountOwnerName;
    private long accountNumber;
    private int pinCode;
    private long balance;
    private int failedAttempts;
}