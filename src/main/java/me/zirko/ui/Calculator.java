package me.zirko.ui;

import me.zirko.ExpressionBuilder;
import me.zirko.ui.widget.CalculatorPanel;
import me.zirko.ui.widget.FunctionButton;
import me.zirko.ui.widget.NumberButton;
import me.zirko.util.UIUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * <p>This class is the controller class that manage the flow between the view and the calculus process done
 * by {@link me.zirko.ExpressionBuilder}</p>
 *
 * @author Guillaume Fillon
 * @version 1.0
 * @since 1.0
 */
public class Calculator extends JFrame implements ActionListener {
    private ExpressionBuilder mExprBuilder;
    private CalculatorPanel mContainer;
    private boolean mFunctionPressed = false;

    public Calculator() {
        super("JCalculator");
        init();
        setVisible(true);
        mExprBuilder = new ExpressionBuilder(mContainer);
    }

    public static void main(String[] args) {
        new Calculator();
    }

    /**
     * <p>This method is used to setup the window like its size or design</p>
     * <p>
     * <p>It should be called after a call to super()</p>
     */
    private void init() {
        setSize(380, 400);
        setResizable(false);
        setIcon();
        setDesign();
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                super.windowClosing(windowEvent);
                System.exit(0);
            }
        });
        mContainer = new CalculatorPanel(this);
        setContentPane(mContainer);
        //TODO centerWindow();
    }

    /**
     * <p>This method center the window in the available screen space : <br/>
     * new position = (Screen size / 2) - (Window size / 2)
     * </p>
     */
    private void centerWindow() {
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dim.width / 2 - getWidth() / 2, dim.height / 2 - getHeight() / 2);
    }

    private void setDesign() {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (ClassNotFoundException |
                InstantiationException |
                IllegalAccessException |
                UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    }

    /**
     * <p>This method set the program icon usually displayed in the task bar and/or title bar in the Window Manager</p>
     */
    private void setIcon() {
        try {
            setIconImage(UIUtils.createImageFromRes("icon.gif", "icon"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        if (o instanceof NumberButton) {
            if (mFunctionPressed) {
                mContainer.clear();
                mFunctionPressed = false;
            }

            mContainer.concatResult(((JButton) o).getText());
        } else if (o instanceof FunctionButton) {
            mFunctionPressed = true;

            String res = mContainer.getResult();
            mExprBuilder.build(((JButton) o).getActionCommand(), Double.valueOf(res));
        }
    }
}
