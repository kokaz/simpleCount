package me.zirko.ui.view.widget;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * {@link me.zirko.ui.CalculatorView} is the view that interacts with the user and send
 * his inputs to the {@link me.zirko.controller.CalculatorController}
 * It initialize the window the user will interact with.
 *
 * It implements {@link java.util.Observer} interface because of the MVC and
 * Observer/Observable design patter between the view and the model {@link me.zirko.model.CalculatorModel}
 *
 * @author Guillaume Fillon
 * @version 1.0
 * @since 1.0
 */
public class CalculatorPanel extends JPanel {
    private Map<String, JButton> mButtons = new LinkedHashMap<>();

    private LayoutManager mLayoutManager;
    private LayoutManager mChildLayoutManager = new GridLayout(5, 7);
    private JLabel mResult = new JLabel("0");
    private JPanel[] mRow = new JPanel[2];
    private LayoutManager mFlowLayout = new FlowLayout(FlowLayout.CENTER, 5, 5);
    private Font mFont = new Font(null, Font.BOLD, 20);
    private Border mPaddingBorder = BorderFactory.createEmptyBorder(10, 10, 10, 10);
    private ActionListener mListener;

    public CalculatorPanel(ActionListener listener) {
        mLayoutManager = new BoxLayout(this, BoxLayout.PAGE_AXIS);
        mListener = listener;
        setBorder(mPaddingBorder);
        setLayout(mLayoutManager);
        init();
    }

    /**
     * This method initialize the UI by placing and styling UI components
     */
    public void init() {
        initComponent();
        mRow[0] = new JPanel();
        mRow[0].setLayout(mFlowLayout);
        mRow[1] = new JPanel();
        mRow[1].setLayout(mChildLayoutManager);


        for (Map.Entry<String, JButton> entry : mButtons.entrySet()) {
            JButton button = entry.getValue();
            button.addActionListener(mListener);
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

    private void initComponent() {
        mButtons.put("inverse", new FunctionButton("1/x"));
        mButtons.put("sqr", new FunctionButton("x²"));
        mButtons.put("sqrt", new FunctionButton("√"));
        mButtons.put("negate", new FunctionButton("+/-"));
        mButtons.put("modulo", new FunctionButton("%"));
        mButtons.put("clear", new FunctionButton("C"));
        mButtons.put("reset", new FunctionButton("AC"));
        mButtons.put("7", new NumberButton("7"));
        mButtons.put("8", new NumberButton("8"));
        mButtons.put("9", new NumberButton("9"));
        mButtons.put("add", new FunctionButton("+"));
        mButtons.put("sin", new FunctionButton("sin"));
        mButtons.put("cos", new FunctionButton("cos"));
        mButtons.put("tan", new FunctionButton("tan"));
        mButtons.put("4", new NumberButton("4"));
        mButtons.put("5", new NumberButton("5"));
        mButtons.put("6", new NumberButton("6"));
        mButtons.put("subtraction", new FunctionButton("-"));
        mButtons.put("sinh", new FunctionButton("sinh"));
        mButtons.put("cosh", new FunctionButton("cosh"));
        mButtons.put("tanh", new FunctionButton("tanh"));
        mButtons.put("1", new NumberButton("1"));
        mButtons.put("2", new NumberButton("2"));
        mButtons.put("3", new NumberButton("3"));
        mButtons.put("multiplication", new FunctionButton("*"));
        mButtons.put("log", new FunctionButton("log"));
        mButtons.put("ln", new FunctionButton("ln"));
        mButtons.put("exp", new FunctionButton("exp"));
        mButtons.put("0", new NumberButton("0"));
        mButtons.put(".", new NumberButton("."));
        mButtons.put("equal", new FunctionButton("="));
        mButtons.put("division", new FunctionButton("/"));
        mButtons.put("pi", new FunctionButton("Pi"));
        mButtons.put("euler", new FunctionButton("e"));
        mButtons.put("pow", new FunctionButton("x^y"));
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
