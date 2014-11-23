package me.zirko.model;

import me.zirko.util.MathEvaluator;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Observable;

/**
 * {@link CalculatorModel} is the model that interacts with the user data
 * sent by {@link me.zirko.controller.CalculatorController}.
 * <p>
 * The class compute all the data user (operations, numbers, etc...)
 * When the model is completely update, it notify all the {@link java.util.Observer} with
 * {@link java.util.Observable#notifyObservers(Object)} with {@link CalculatorModel#mResult}
 * <p>
 * It implements {@link java.util.Observable} interface because of the MVC and
 * Observer/Observable design patter between the model and the view {@link me.zirko.ui.CalculatorView}
 *
 * @author Guillaume Fillon
 * @version 1.0
 * @since 1.0
 */
public class CalculatorModel extends Observable {
    private Double mResult = 0.0;
    private String mExpr = "";
    private boolean isResultNaN = false;
    private boolean isDecimal = false;

    @Override
    public void notifyObservers(Object arg) {
        String argStr = (String) arg;

        while (argStr.matches("^0[0-9]+\\.?.*")) {
            argStr = argStr.substring(1, argStr.length());
        }

        setChanged();

        super.notifyObservers(argStr);
    }

    public void setOperator(String actionCommand, String label, boolean isScientific) {
        if (isScientific) {
            mExpr += actionCommand;
        } else {
            mExpr += label;
        }

        notifyObservers(mExpr);
    }

    /**
     * This method concatenate a number the user clicked on
     *
     * @param number A number
     */
    public void addNumber(String number) {
        if ("pi".equals(number)) {
            number = String.valueOf(Math.PI);
        } else if ("euler".equals(number)) {
            number = String.valueOf(Math.E);
        }

        if (isDecimal && ".".equals(number)) {
            return;
        } else if (!isDecimal && ".".equals(number)) {
            isDecimal = true;
        }

        mExpr += number;

        notifyObservers(mExpr);
    }

    /**
     * @return The result of the simplification of a Double
     * @see {@link CalculatorModel#getSimplifiedDouble(Double)}
     */
    private String getSimplifiedResult() {
        return getSimplifiedDouble(mResult);
    }

    /**
     * This method simplifies {@link CalculatorModel#mResult} from its excessive precision
     *
     * @param a Any double
     * @return The result of the simplification of a Double (could be inf, -inf or NaN)
     */
    private String getSimplifiedDouble(Double a) {
        if (a == Double.POSITIVE_INFINITY) {
            return "inf";
        } else if (a == Double.NEGATIVE_INFINITY) {
            return "-inf";
        } else if (isResultNaN) {
            isResultNaN = true;
            return "NaN";
        }

        BigDecimal bd = new BigDecimal(a);
        DecimalFormat df = new DecimalFormat();

        df.setMaximumFractionDigits(8);
        df.setMinimumFractionDigits(0);
        df.setGroupingUsed(false);

        return df.format(bd);
    }

    public void parseExpression() {
        try {
            mResult = MathEvaluator.getInstance().parse(mExpr);
            mExpr = getSimplifiedResult();

            notifyObservers(mExpr);
        } catch (IllegalArgumentException e) {
            notifyObservers(e.getMessage());
            mExpr = "";
            mResult = 0.0;
            isDecimal = false;
        }
    }

    public void clear() {
        if (mExpr.length() > 0) {
            mExpr = mExpr.substring(0, mExpr.length() - 1);
        }

        notifyObservers(mExpr);
    }

    public void reset() {
        mExpr = "";
        mResult = 0.0;
        isDecimal = false;

        notifyObservers(mExpr);
    }
}
