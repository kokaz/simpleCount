package me.zirko.ui;

import me.zirko.ExpressionBuilder;
import me.zirko.util.UIUtil;

import javax.swing.*;
import java.awt.*;
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
public class Calculator extends JFrame {
    private ExpressionBuilder mExprBuilder;
    private JPanel mContainer = new JPanel();
    private JLabel mResult = new JLabel("", SwingConstants.RIGHT);

    GridLayout mLayoutManager = new GridLayout(5, 6);

    public Calculator() {
        super("JCalculator");
        init();

        setVisible(true);
    }

    /**
     * <p>This method is used to setup the window like its size or design</p>
     * <p>
     * <p>It should be called after a call to super()</p>
     */
    private void init() {
        setSize(380, 250);
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
        mContainer.setBackground(Color.DARK_GRAY);
        mContainer.setLayout(mLayoutManager);
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
            setIconImage(UIUtil.createImageFromRes("icon.gif", "icon"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
