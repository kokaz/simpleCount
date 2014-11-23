package me.zirko.controller;

import me.zirko.model.CalculatorModel;
import me.zirko.ui.view.widget.FunctionButton;
import me.zirko.ui.view.widget.NumberButton;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * {@link me.zirko.controller.CalculatorController} is the controller that bridge the view
 * {@link me.zirko.ui.CalculatorView} and the model {@link me.zirko.model.CalculatorModel}
 * <p>
 * The class handle the view events and call the appropriate method of {@link me.zirko.model.CalculatorModel}
 *
 * @author Guillaume Fillon
 * @version 1.0
 * @since 1.0
 */
public class CalculatorController implements ActionListener {
    private CalculatorModel mModel;

    /**
     * Construct the controller with a {@link me.zirko.model.CalculatorModel}
     *
     * @param model The model the controller should interact with
     */
    public CalculatorController(CalculatorModel model) {
        if (model == null) {
            throw new IllegalArgumentException("The model should not be null!");
        }
        this.mModel = model;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        if (o instanceof NumberButton) {
            mModel.addNumber(((JButton) o).getActionCommand());
        } else if (o instanceof FunctionButton) {
            FunctionButton fBt = (FunctionButton) o;

            if ("equal".equals(fBt.getActionCommand())) {
                mModel.parseExpression();
            } else if ("clear".equals(fBt.getActionCommand())) {
                mModel.clear();
            } else if ("reset".equals(fBt.getActionCommand())) {
                mModel.reset();
            } else {
                mModel.setOperator(fBt.getActionCommand(), fBt.getText(), fBt.isScientific());
            }
        }
    }
}
