package me.zirko.ui.widget;

import me.zirko.ui.Calculator;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class CalculatorPanel extends JPanel {
    private static Map<String, JButton> sButtons;

    private LayoutManager mLayoutManager;
    private LayoutManager mChildLayoutManager = new GridLayout(5, 6);
    private JLabel mResult = new JLabel("0");
    private JPanel[] mRow = new JPanel[2];
    private LayoutManager mFlowLayout = new FlowLayout(FlowLayout.CENTER, 1, 1);
    private Font mFont = new Font(null, Font.BOLD, 20);
    private Border mPaddingBorder = BorderFactory.createEmptyBorder(10, 10, 10, 10);
    private Calculator mController;

    static {
        sButtons = new LinkedHashMap<>();
        sButtons.put("inverse", new FunctionButton("1/x"));
        sButtons.put("sqr", new FunctionButton("x^2"));
        sButtons.put("negate", new FunctionButton("+/-"));
        sButtons.put("modulo", new FunctionButton("%"));
        sButtons.put("clear", new FunctionButton("C"));
        sButtons.put("reset", new FunctionButton("AC"));
        sButtons.put("7", new NumberButton("7"));
        sButtons.put("8", new NumberButton("8"));
        sButtons.put("9", new NumberButton("9"));
        sButtons.put("add", new FunctionButton("+"));
        sButtons.put("sin", new FunctionButton("sin"));
        sButtons.put("cos", new FunctionButton("cos"));
        sButtons.put("4", new NumberButton("4"));
        sButtons.put("5", new NumberButton("5"));
        sButtons.put("6", new NumberButton("6"));
        sButtons.put("subtraction", new FunctionButton("-"));
        sButtons.put("tan", new FunctionButton("tan"));
        sButtons.put("log", new FunctionButton("log"));
        sButtons.put("1", new NumberButton("1"));
        sButtons.put("2", new NumberButton("2"));
        sButtons.put("3", new NumberButton("3"));
        sButtons.put("multiplication", new FunctionButton("*"));
        sButtons.put("sqrt", new FunctionButton("sqrt"));
        sButtons.put("exp", new FunctionButton("exp"));
        sButtons.put("0", new NumberButton("0"));
        sButtons.put("point", new NumberButton("."));
        sButtons.put("division", new FunctionButton("/"));
        sButtons.put("equal", new FunctionButton("="));
    }

    public CalculatorPanel(Calculator calculator) {
        mLayoutManager = new BoxLayout(this, BoxLayout.PAGE_AXIS);
        mController = calculator;
        setBorder(mPaddingBorder);
        setLayout(mLayoutManager);
        init();
    }

    /**
     * This method initialize the UI by placing and styling UI components
     */
    public void init() {
        mRow[0] = new JPanel();
        mRow[0].setLayout(mFlowLayout);
        mRow[1] = new JPanel();
        mRow[1].setLayout(mChildLayoutManager);


        for (Map.Entry<String, JButton> entry : sButtons.entrySet()) {
            JButton button = entry.getValue();
            button.addActionListener(mController);
            button.setActionCommand(entry.getKey());
            mRow[1].add(button);
        }

        mResult.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        mResult.setFont(mFont);
        mResult.setPreferredSize(new Dimension(350, 60));
        Border borderColor = BorderFactory.createLineBorder(new Color(0, 0, 0));
        mResult.setBorder(BorderFactory.createCompoundBorder(borderColor, mPaddingBorder));
        mRow[0].add(mResult);
        add(mRow[0]);
        add(mRow[1]);
    }

    public void clear() {
        mResult.setText("0");
    }

    public void concatResult(String res) {
        String r = mResult.getText() + res;

        if (r.charAt(0) == '0' && r.charAt(1) != '.') {
            r = String.valueOf(r.subSequence(1, r.length()));
        }
        mResult.setText(r);
    }

    public String getResult() {
        return mResult.getText();
    }

    public void setResult(String res) {
        mResult.setText(res);
    }
}
