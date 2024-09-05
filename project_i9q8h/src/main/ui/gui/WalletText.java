package ui.gui;

import model.Wallet;
import ui.gui.MenuText;

import static java.lang.Math.abs;

public class WalletText extends MenuText {
    private Wallet wallet;

    public WalletText(Wallet wallet) {
        super();

        this.wallet = wallet;
        text.append(walletDisplay());
        text.setCaretPosition(text.getDocument().getLength());
    }

    public String walletDisplay() {
        String walletText = ("Here is " + wallet.getOwnerName() + "'s current wallet details: \n");
        String totalAccumulation = ("\nTotal money accumulation: $"
                + String.valueOf(wallet.getTotalAccumulated() + "\n"));
        String amount = "";
        if (wallet.getBalance() < 0) {
            amount = "-$" + String.valueOf(abs(wallet.getBalance()));
        } else {
            amount = "$" + String.valueOf(wallet.getBalance());
        }
        String balance = ("Current balance: " + amount);

        walletText = walletText + totalAccumulation + balance;

        return walletText;
    }
}
