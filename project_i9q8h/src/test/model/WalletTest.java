package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WalletTest {
    private Wallet testWallet;
    private static final double FUNDS_FOR_LOAD_FUNDS = 100.00;

    @BeforeEach
    public void runBefore() {
        testWallet = new Wallet("B");
    }

    @Test
    public void testWalletConstructor() {
        assertEquals("B", testWallet.getOwnerName());
        assertEquals(0.00, testWallet.getBalance());
        assertEquals(0.00, testWallet.getTotalAccumulated());
    }

    @Test
    public void testSetWalletOwnerName() {
        assertEquals("B", testWallet.getOwnerName());
        testWallet.setWalletOwnerName("Abigail");
        assertEquals("Abigail", testWallet.getOwnerName());
    }

    @Test
    public void testAddFunds() {
        testWallet.addFunds(100.00);
        assertEquals(100.00, testWallet.getBalance());
    }

    @Test
    public void testAddTotalAccumulatedFunds() {
        testWallet.addTotalAccumulatedFunds(100.0);
        assertEquals(100.00, testWallet.getTotalAccumulated());
        assertEquals(0, testWallet.getBalance());

        testWallet.addTotalAccumulatedFunds(51.50);
        assertEquals(151.50, testWallet.getTotalAccumulated());
        assertEquals(0, testWallet.getBalance());
    }

    @Test
    public void testAddOneCent() {
        testWallet.addFunds(0.01);
        assertEquals(0.01, testWallet.getBalance());
    }

    @Test
    public void testAddFundsMultipleTimes() {
        testWallet.addFunds(30.42);
        testWallet.addFunds(50.00);
        assertEquals(30.42+50.00, testWallet.getBalance());
    }

    @Test
    public void testRefundExpense() {
        loadFunds();
        ExpensesList expList = new ExpensesList("B");
        Expense shirt = new Expense("Shirt", 15.50);
        Expense sunglasses = new Expense("Sunglasses", 12.00);
        expList.addExpense(shirt, testWallet);
        expList.addExpense(sunglasses, testWallet);
        assertEquals(FUNDS_FOR_LOAD_FUNDS-shirt.getExpensePrice()-sunglasses.getExpensePrice(),
                testWallet.getBalance());

        testWallet.refundExpense(shirt);
        assertEquals(FUNDS_FOR_LOAD_FUNDS-sunglasses.getExpensePrice(), testWallet.getBalance());

        testWallet.refundExpense(sunglasses);
        assertEquals(FUNDS_FOR_LOAD_FUNDS, testWallet.getBalance());
    }

    @Test
    public void testRemoveFunds() {
        loadFunds();
        testWallet.removeFunds(23.40);
        assertEquals(FUNDS_FOR_LOAD_FUNDS-23.40, testWallet.getBalance());
    }

    @Test
    public void testRemoveOneCent() {
        loadFunds();
        testWallet.removeFunds(0.01);
        assertEquals(FUNDS_FOR_LOAD_FUNDS-0.01, testWallet.getBalance());
    }

    @Test
    public void testRemoveAllFunds() {
        loadFunds();
        testWallet.removeFunds(100.00);
        assertEquals(0, testWallet.getBalance());
    }

    @Test
    public void testRemoveFundsMultipleTimes() {
        loadFunds();
        testWallet.removeFunds(43.50);
        testWallet.removeFunds(10.00);
        assertEquals(FUNDS_FOR_LOAD_FUNDS-43.50-10.00, testWallet.getBalance());
    }

    @Test
    public void addThenRemoveFunds() {
        loadFunds();
        testWallet.addFunds(54.43);
        testWallet.removeFunds(32.10);
        assertEquals(FUNDS_FOR_LOAD_FUNDS+54.43-32.10, testWallet.getBalance());
    }

    public void loadFunds() {
        testWallet.addFunds(FUNDS_FOR_LOAD_FUNDS);
    }
}
