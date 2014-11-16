package me.zirko.ui.view.widget;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class FunctionButton extends JButton {

    public FunctionButton(String text, ActionListener listener) {
        super(text);
        setPreferredSize(new Dimension(50, 50));
        addActionListener(listener);
    }

    public FunctionButton(String text) {
        super(text);
        setPreferredSize(new Dimension(50, 50));
    }
}
