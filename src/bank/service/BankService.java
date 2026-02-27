package bank.service;

import bank.model.Account;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class BankService {
    private final Map<String, Account> accounts = new HashMap<>();

    public void createAccount(String accountNumber, String ownerName, BigDecimal initialBalance) {
        if (accounts.containsKey(accountNumber)) {
            throw new IllegalArgumentException("Account with number " + accountNumber + " already exists.");
        }
        Account account = new Account(accountNumber, ownerName, initialBalance);
        accounts.put(accountNumber, account);
    }

    public Optional<Account> getAccount(String accountNumber) {
        return Optional.ofNullable(accounts.get(accountNumber));
    }

    public void deposit(String accountNumber, BigDecimal amount) {
        Account account = getAccount(accountNumber)
                .orElseThrow(() -> new IllegalArgumentException("Account not found."));
        account.deposit(amount);
    }

    public void withdraw(String accountNumber, BigDecimal amount) {
        Account account = getAccount(accountNumber)
                .orElseThrow(() -> new IllegalArgumentException("Account not found."));
        account.withdraw(amount);
    }

    public void transfer(String fromAccountNumber, String toAccountNumber, BigDecimal amount) {
        if (fromAccountNumber.equals(toAccountNumber)) {
            throw new IllegalArgumentException("Source and destination accounts must be different.");
        }

        Account fromAccount = getAccount(fromAccountNumber)
                .orElseThrow(() -> new IllegalArgumentException("Source account not found."));
        Account toAccount = getAccount(toAccountNumber)
                .orElseThrow(() -> new IllegalArgumentException("Destination account not found."));

        // To avoid deadlock in concurrent scenarios, always lock in a consistent order
        Account firstLock = fromAccountNumber.compareTo(toAccountNumber) < 0 ? fromAccount : toAccount;
        Account secondLock = fromAccountNumber.compareTo(toAccountNumber) < 0 ? toAccount : fromAccount;

        synchronized (firstLock) {
            synchronized (secondLock) {
                fromAccount.withdraw(amount);
                toAccount.deposit(amount);
            }
        }
    }
}
