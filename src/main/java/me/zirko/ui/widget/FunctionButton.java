package me.zirko.ui.widget;

import me.zirko.ui.Calculator;

import javax.swing.*;
import java.awt.*;

public class FunctionButton extends JButton {

    public FunctionButton(String text, Calculator listener) {
        super(text);
        setPreferredSize(new Dimension(50, 50));
        addActionListener(listener);
    }

    public FunctionButton(String text) {
        super(text);
        setPreferredSize(new Dimension(50, 50));
    }
}
