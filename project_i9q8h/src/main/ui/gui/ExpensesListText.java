package ui.gui;

import model.Expense;
import model.ExpensesList;
import ui.gui.MenuText;

public class ExpensesListText extends MenuText {
    private ExpensesList list;

    public ExpensesListText(ExpensesList list) {
        super();
        this.list = list;
        text.append(expensesDisplay());
        text.setCaretPosition(text.getDocument().getLength());

    }

    public String expensesDisplay() {
        String expensesText = ("Here is " + list.getOwnerName() + "'s current list of expenses:\n");
        for (Expense expense : list.getList()) {
            expensesText = expensesText + "\n\t=> " + expense.getExpenseName() + ", $" + expense.getExpensePrice();
        }
        double totalCost = list.calculateTotalCost();
        expensesText = expensesText + "\n\n Total cost: $" + String.valueOf(totalCost);
        return expensesText;
    }




}
