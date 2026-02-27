package bank.model;

import java.math.BigDecimal;

public class Account {
    private final String accountNumber;
    private final String ownerName;
    private BigDecimal balance;

    public Account(String accountNumber, String ownerName, BigDecimal initialBalance) {
        if (initialBalance.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Initial balance cannot be negative.");
        }
        this.accountNumber = accountNumber;
        this.ownerName = ownerName;
        this.balance = initialBalance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public synchronized void deposit(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Deposit amount must be positive.");
        }
        balance = balance.add(amount);
    }

    public synchronized void withdraw(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be positive.");
        }
        if (balance.compareTo(amount) < 0) {
            throw new IllegalArgumentException("Insufficient balance.");
        }
        balance = balance.subtract(amount);
    }

    @Override
    public String toString() {
        return String.format("Account[number=%s, owner=%s, balance=%s]", accountNumber, ownerName, balance);
    }
}
