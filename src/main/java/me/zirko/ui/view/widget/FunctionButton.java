package me.zirko.ui.view.widget;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class FunctionButton extends JButton {

    private boolean isScientific;

    public FunctionButton(String text, boolean isScientific) {
        super(text);
        this.isScientific = isScientific;

        setPreferredSize(new Dimension(50, 50));
    }

    public FunctionButton(String text, String actionCommand, ActionListener listener) {
        this(text);
        setActionCommand(actionCommand);
        addActionListener(listener);
    }

    public FunctionButton(String text, String actionCommand, boolean isScientific, ActionListener listener) {
        this(text, actionCommand, listener);
        this.isScientific = isScientific;
    }

    public FunctionButton(String text) {
        this(text, false);
    }

    public boolean isScientific() {
        return isScientific;
    }
}
