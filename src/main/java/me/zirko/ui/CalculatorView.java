package me.zirko.ui;

import me.zirko.controller.CalculatorController;
import me.zirko.ui.view.widget.CalculatorPanel;
import me.zirko.util.UIUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Observable;
import java.util.Observer;

/**
 * <p>
 * {@link me.zirko.ui.CalculatorView} is the view that interacts with the user and send
 * his inputs to the {@link me.zirko.controller.CalculatorController}
 * <p>
 * It initialize the window the user will interact with.
 * This window contains a {@link me.zirko.ui.view.widget.CalculatorPanel} that populate the window with UI elements
 * <p>
 * It implements {@link java.util.Observer} interface because of the MVC and
 * Observer/Observable design patter between the view and the model {@link me.zirko.model.CalculatorModel}
 *
 * @author Guillaume Fillon
 * @version 1.0
 * @see me.zirko.controller.CalculatorController
 * @see me.zirko.model.CalculatorModel
 * @see me.zirko.ui.view.widget.CalculatorPanel
 * @since 1.0
 */
public class CalculatorView extends JFrame implements Observer {
    private final CalculatorController mController;
    private CalculatorPanel mContainer;

    public CalculatorView(CalculatorController controller) {
        super();
        mController = controller;
        init();
    }

    /**
     * This method is used to setup the window like its size or design
     * <p>
     * It should be called after a call to super()
     */
    private void init() {
        if (!isRootPaneCheckingEnabled()) {
            throw new IllegalStateException("JFrame is not constructed. "
                    + "You must call this method after the call of JFrame constructor.");
        }
        setTitle("Simple Count v1.0");
        setSize(450, 400);
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
        mContainer = new CalculatorPanel(mController);
        setContentPane(mContainer);
        //TODO centerWindow();
    }

    /**
     * This method center the window in the available screen space : <br/>
     * new position = (Screen size / 2) - (Window size / 2)
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
     * This method set the program icon usually displayed in the task bar and/or title bar in the Window Manager
     */
    private void setIcon() {
        try {
            setIconImage(UIUtils.createImageFromRes("icon.gif", "icon"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        mContainer.setResult((String) arg);
    }
}
