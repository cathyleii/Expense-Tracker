package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ExpensesListTest {
    private ExpensesList testExpensesList;
    private Expense apples;
    private Expense giftCard;
    private Expense shirt;
    private Wallet wallet;
    private static final double INIT_WALLET_FUNDS = 100.0;

    @BeforeEach
    public void runBefore() {
        testExpensesList = new ExpensesList("B");
        apples = new Expense("Apples", 4.32);
        giftCard = new Expense("Gift Card", 50.0);
        shirt = new Expense("Shirt", 15.45);
        wallet = new Wallet("B");
        wallet.addFunds(INIT_WALLET_FUNDS);
    }

    @Test
    public void testExpensesListConstructor() {
        assertEquals("B", testExpensesList.getOwnerName());
        assertEquals(0.00, testExpensesList.getTotalCost());
        assertEquals(0, testExpensesList.getList().size());
    }

    @Test
    public void testListContainsExpenseTrue() {
        loadExpenses();
        assertTrue(testExpensesList.listContainsExpense("Apples", 4.32));

        testExpensesList.addExpense(shirt, wallet);
        assertTrue(testExpensesList.listContainsExpense("Shirt", 15.45));
    }

    @Test
    public void testListContainsExpenseFalse() {
        loadExpenses();
        assertFalse(testExpensesList.listContainsExpense("Shirt", 15.45));
        assertFalse(testExpensesList.listContainsExpense("Apples", 4.31));
        assertFalse(testExpensesList.listContainsExpense("Bananas", 4.32));
    }

    @Test
    public void testAddExpense() {
        testExpensesList.addExpense(apples, wallet);
        assertEquals(1, testExpensesList.getList().size());
        assertEquals(INIT_WALLET_FUNDS-apples.getExpensePrice(), wallet.getBalance());

        testExpensesList.addExpense(giftCard, wallet);
        assertEquals(2, testExpensesList.getList().size());
        assertEquals(INIT_WALLET_FUNDS-apples.getExpensePrice()- giftCard.getExpensePrice(), wallet.getBalance());
    }

    @Test
    public void testRemoveExpense() {
        loadExpenses();
        assertEquals(2, testExpensesList.getList().size());
        assertEquals(INIT_WALLET_FUNDS-apples.getExpensePrice()- giftCard.getExpensePrice(),
                wallet.getBalance());
        assertEquals(apples, testExpensesList.getList().get(0));
        assertEquals(giftCard, testExpensesList.getList().get(1));

        testExpensesList.removeExpense(apples, wallet);
        assertEquals(1, testExpensesList.getList().size());
        assertEquals(giftCard, testExpensesList.getList().get(0));
        assertEquals(INIT_WALLET_FUNDS-giftCard.getExpensePrice(), wallet.getBalance(), 0.000001);

        testExpensesList.removeExpense(giftCard, wallet);
        assertEquals(0, testExpensesList.getList().size());
        assertEquals(INIT_WALLET_FUNDS, wallet.getBalance());
    }

    @Test
    public void testSetListOwnerName() {
        assertEquals("B", testExpensesList.getOwnerName());
        testExpensesList.setListOwnerName("Jane");
        assertEquals("Jane", testExpensesList.getOwnerName());
    }

    @Test
    public void testCalculateTotalCost() {
        loadExpenses();
        assertEquals(apples.getExpensePrice()+ giftCard.getExpensePrice(),
                testExpensesList.calculateTotalCost());

        testExpensesList.addExpense(shirt, wallet);
        assertEquals(apples.getExpensePrice()+ giftCard.getExpensePrice()+shirt.getExpensePrice(),
                testExpensesList.calculateTotalCost());

        testExpensesList.removeExpense(apples, wallet);
        assertEquals(giftCard.getExpensePrice()+shirt.getExpensePrice(),
                testExpensesList.calculateTotalCost());
    }

    @Test
    public void testCalculateTotalCostEmptyList() {
        assertEquals(0, testExpensesList.calculateTotalCost());
    }

    @Test
    public void testSetTotalCost() {
        testExpensesList.setTotalCost(12.32);
        assertEquals(12.32, testExpensesList.getTotalCost());

        testExpensesList.setTotalCost(0.00);
        assertEquals(0.00, testExpensesList.getTotalCost());
    }

    public void loadExpenses() {
        testExpensesList.addExpense(apples, wallet);
        testExpensesList.addExpense(giftCard, wallet);
    }


}
