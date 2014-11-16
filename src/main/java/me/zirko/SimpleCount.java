package me.zirko;

import me.zirko.controller.CalculatorController;
import me.zirko.model.CalculatorModel;
import me.zirko.ui.CalculatorView;

public class SimpleCount {
    public static void main(String[] args) {
        CalculatorModel model = new CalculatorModel();
        CalculatorController controller = new CalculatorController(model);
        CalculatorView view = new CalculatorView(controller);

        model.addObserver(view);
        view.setVisible(true);
    }
}
