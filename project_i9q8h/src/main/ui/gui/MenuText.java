package ui.gui;

import javax.swing.*;
import java.awt.*;

public class MenuText extends JPanel {
    protected JTextArea text;

    public MenuText() {
        super(new GridBagLayout());

        text = new JTextArea();
        text.setEditable(false);

        GridBagConstraints c = new GridBagConstraints();
        c.gridwidth = GridBagConstraints.REMAINDER;

        c.fill = GridBagConstraints.HORIZONTAL;

        add(text, c);

        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1.0;
        c.weighty = 1.0;

    }

    public JTextArea getText() {
        return text;
    }

}
