package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ExpenseTest {
    private Expense oneCentExpense;
    private Expense testExpense;

    @BeforeEach
    public void runBefore() {
        oneCentExpense = new Expense("A", 0.01);
        testExpense = new Expense("Bananas", 4.39);
    }

    @Test
    public void testExpenseConstructor() {
        assertEquals("A", oneCentExpense.getExpenseName());
        assertEquals(0.01, oneCentExpense.getExpensePrice());
    }

    @Test
    public void testSetExpensePrice() {
        assertEquals(4.39, testExpense.getExpensePrice());
        testExpense.setExpensePrice(7.99);
        assertEquals(7.99, testExpense.getExpensePrice());

    }

    @Test
    public void testSetExpenseName() {
        assertEquals("Bananas", testExpense.getExpenseName());
        testExpense.setExpenseName("Apples");
        assertEquals("Apples", testExpense.getExpenseName());
    }
}