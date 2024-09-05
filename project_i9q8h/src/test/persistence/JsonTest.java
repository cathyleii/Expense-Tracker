package persistence;

import model.Expense;
import model.ExpensesList;
import model.Wallet;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {

    protected void checkWallet(String name, double totalAccumulated, double balance, Wallet wallet) {
        assertEquals(name, wallet.getOwnerName());
        assertEquals(totalAccumulated, wallet.getTotalAccumulated());
        assertEquals(balance, wallet.getBalance());
    }

    protected void checkExpense(String name, double price, Expense expense) {
        assertEquals(name, expense.getExpenseName());
        assertEquals(price, expense.getExpensePrice());

    }

    protected void checkList(String name, int numItems, ExpensesList el) {
        assertEquals(name, el.getOwnerName());
        assertEquals(numItems, el.getList().size());
    }
}
