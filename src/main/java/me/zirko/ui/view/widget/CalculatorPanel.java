package me.zirko.ui.view.widget;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * {@link me.zirko.ui.CalculatorView} is the view that interacts with the user and send
 * his inputs to the {@link me.zirko.controller.CalculatorController}
 * It initialize the window the user will interact with.
 * <p>
 * It implements {@link java.util.Observer} interface because of the MVC and
 * Observer/Observable design patter between the view and the model {@link me.zirko.model.CalculatorModel}
 *
 * @author Guillaume Fillon
 * @version 1.0
 * @since 1.0
 */
public class CalculatorPanel extends JPanel {
    private List<JButton> mButtons = new ArrayList<>();
    private LayoutManager mLayoutManager;
    private LayoutManager mChildLayoutManager = new GridLayout(5, 7);
    private JLabel mResult = new JLabel("");
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


        for (JButton button : mButtons) {
            mRow[1].add(button);
        }

        mResult.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        mResult.setFont(mFont);
        mResult.setPreferredSize(new Dimension(420, 60));
        Border borderColor = BorderFactory.createLineBorder(new Color(0, 0, 0));
        mResult.setBorder(BorderFactory.createCompoundBorder(borderColor, mPaddingBorder));
        mRow[0].add(mResult);
        add(mRow[0]);
        add(mRow[1]);
    }

    private void initComponent() {
        mButtons.add(new FunctionButton("1÷x", "1÷", true, mListener));
        mButtons.add(new FunctionButton("x²", "^2", true, mListener));
        mButtons.add(new FunctionButton("√", "√ ", true, mListener));
        mButtons.add(new FunctionButton("±", "-", true, mListener));
        mButtons.add(new FunctionButton("mod", "mod ", true, mListener));
        mButtons.add(new FunctionButton("C", "clear", mListener));
        mButtons.add(new FunctionButton("AC", "reset", mListener));
        mButtons.add(new NumberButton("7", "7", mListener));
        mButtons.add(new NumberButton("8", "8", mListener));
        mButtons.add(new NumberButton("9", "9", mListener));
        mButtons.add(new FunctionButton("+", "add", mListener));
        mButtons.add(new FunctionButton("sin", "sin ", true, mListener));
        mButtons.add(new FunctionButton("cos", "cos ", true, mListener));
        mButtons.add(new FunctionButton("tan", "tan ", true, mListener));
        mButtons.add(new NumberButton("4", "4", mListener));
        mButtons.add(new NumberButton("5", "5", mListener));
        mButtons.add(new NumberButton("6", "6", mListener));
        mButtons.add(new FunctionButton("-", "subtraction", mListener));
        mButtons.add(new FunctionButton("sinh", "sinh ", true, mListener));
        mButtons.add(new FunctionButton("cosh", "cosh ", true, mListener));
        mButtons.add(new FunctionButton("tanh", "tanh ", true, mListener));
        mButtons.add(new NumberButton("1", "1", mListener));
        mButtons.add(new NumberButton("2", "2", mListener));
        mButtons.add(new NumberButton("3", "3", mListener));
        mButtons.add(new FunctionButton("×", "multiplication", mListener));
        mButtons.add(new FunctionButton("log", "log ", true, mListener));
        mButtons.add(new FunctionButton("ln", "ln ", true, mListener));
        mButtons.add(new FunctionButton("exp", "exp ", true, mListener));
        mButtons.add(new NumberButton("0", "0", mListener));
        mButtons.add(new NumberButton(".", ".", mListener));
        mButtons.add(new FunctionButton("=", "equal", mListener));
        mButtons.add(new FunctionButton("÷", "division", mListener));
        mButtons.add(new NumberButton("ℼ", "pi", mListener));
        mButtons.add(new NumberButton("ℯ", "euler", mListener));
        mButtons.add(new FunctionButton("x^y", "^", true, mListener));
    }

    public void setResult(String res) {
        mResult.setText(res);
    }
}
