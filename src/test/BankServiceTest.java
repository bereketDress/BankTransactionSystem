package test;

import bank.model.Account;
import bank.service.BankService;

import java.math.BigDecimal;
import java.util.Optional;

public class BankServiceTest {

    public static void main(String[] args) {
        testCreateAccount();
        testDeposit();
        testWithdraw();
        testTransfer();
        System.out.println("All tests passed!");
    }

    private static void testCreateAccount() {
        BankService service = new BankService();
        service.createAccount("123", "Alice", new BigDecimal("100.00"));
        Optional<Account> account = service.getAccount("123");
        assert account.isPresent();
        assert account.get().getBalance().equals(new BigDecimal("100.00"));
        assert account.get().getOwnerName().equals("Alice");
    }

    private static void testDeposit() {
        BankService service = new BankService();
        service.createAccount("123", "Alice", new BigDecimal("100.00"));
        service.deposit("123", new BigDecimal("50.00"));
        assert service.getAccount("123").get().getBalance().equals(new BigDecimal("150.00"));
    }

    private static void testWithdraw() {
        BankService service = new BankService();
        service.createAccount("123", "Alice", new BigDecimal("100.00"));
        service.withdraw("123", new BigDecimal("40.00"));
        assert service.getAccount("123").get().getBalance().equals(new BigDecimal("60.00"));
    }

    private static void testTransfer() {
        BankService service = new BankService();
        service.createAccount("123", "Alice", new BigDecimal("100.00"));
        service.createAccount("456", "Bob", new BigDecimal("50.00"));
        service.transfer("123", "456", new BigDecimal("30.00"));
        assert service.getAccount("123").get().getBalance().equals(new BigDecimal("70.00"));
        assert service.getAccount("456").get().getBalance().equals(new BigDecimal("80.00"));
    }
}
