package me.zirko.ui.view.widget;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class NumberButton extends JButton {

    public NumberButton(String text, String actionCommand, ActionListener listener) {
        this(text);
        setActionCommand(actionCommand);
        addActionListener(listener);
    }

    public NumberButton(String text) {
        super(text);
        setPreferredSize(new Dimension(50, 50));
    }
}
