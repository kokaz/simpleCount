package me.zirko;

import me.zirko.ui.CalculatorView;
import me.zirko.controller.CalculatorController;
import me.zirko.model.CalculatorModel;

public class SimpleCount {
    public static void main(String[] args) {
        CalculatorModel model = new CalculatorModel();
        CalculatorController controller = new CalculatorController(model);
        CalculatorView view = new CalculatorView(controller);

        model.addObserver(view);
        view.setVisible(true);
    }
}
