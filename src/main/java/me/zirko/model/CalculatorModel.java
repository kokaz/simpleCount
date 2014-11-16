package me.zirko.model;

import me.zirko.util.ReflectionUtils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/**
 * {@link me.zirko.model.CalculatorModel} is the model that interacts with the user data 
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
    private String mOperand = "0";
    private String mOperator = "";
    private List<String> mSpecialOperators = new ArrayList<>();
    private String mOldOperator = "";
    private boolean isResultNaN = false;
    private boolean needOperandUpdate = true;
    private boolean isDecimal = false;

    public CalculatorModel() {
        mSpecialOperators.add("equal");
        mSpecialOperators.add("sin");
        mSpecialOperators.add("sinh");
        mSpecialOperators.add("cos");
        mSpecialOperators.add("cosh");
        mSpecialOperators.add("tan");
        mSpecialOperators.add("tanh");
        mSpecialOperators.add("exp");
        mSpecialOperators.add("log");
        mSpecialOperators.add("ln");
        mSpecialOperators.add("sqr");
        mSpecialOperators.add("sqrt");
        mSpecialOperators.add("pi");
        mSpecialOperators.add("euler");
        mSpecialOperators.add("inverse");
        mSpecialOperators.add("negate");
        mSpecialOperators.add("reset");
        mSpecialOperators.add("clear");
    }

    @Override
    public void notifyObservers(Object arg) {
        String argStr = (String) arg;

        if (argStr.matches("^0[0-9]+")) {
            argStr = argStr.substring(1, argStr.length());
        }

        super.notifyObservers(argStr);
    }

    /**
     * This method use {@link me.zirko.util.ReflectionUtils} to use the appropriate method
     * for calculating the last operation thanks to the {@link CalculatorModel#mOperator}
     * which set the {@link CalculatorModel#mResult} with the good result
     */
    private void computeData() {
        if ("".equals(mOperator)) {
            mResult = Double.parseDouble(mOperand);
        } else {
            try {
                ReflectionUtils.tryInvoke(this, mOperator, Double.parseDouble(mOperand));
            } catch (NumberFormatException e) {
                ReflectionUtils.tryInvoke(this, mOperator, mResult);
            }
        }

        if (!mSpecialOperators.contains(mOperator) && needOperandUpdate) {
            mOperand = "";
            isDecimal = false;
        } else {
            needOperandUpdate = true;
        }

        notifyObservers(getSimplifiedResult());
    }

    /**
     * Compute an addition of {@link CalculatorModel#mResult} and operand
     * <p>
     * Act like {@link CalculatorModel#mResult} + operand
     *
     * @param operand Actual operand
     */
    public void add(Double operand) {
        mResult += operand;
        setChanged();
    }

    /**
     * Compute a subtraction of {@link CalculatorModel#mResult} and operand
     * <p>
     * Act like {@link CalculatorModel#mResult} - operand
     *
     * @param operand Actual operand
     */
    public void subtraction(Double operand) {
        mResult -= operand;
        setChanged();
    }

    /**
     * Compute a product of {@link CalculatorModel#mResult} and operand
     * <p>
     * Act like {@link CalculatorModel#mResult} * operand
     *
     * @param operand Actual operand
     */
    public void multiplication(Double operand) {
        mResult *= operand;
        setChanged();
    }

    /**
     * Compute a division of {@link CalculatorModel#mResult} and operand
     * <p>
     * Act like {@link CalculatorModel#mResult} / operand
     *
     * @param operand Actual operand
     */
    public void division(Double operand) {
        mResult /= operand;
        setChanged();
    }

    /**
     * Compute a modulo of {@link CalculatorModel#mResult} and operand
     * <p>
     * Act like {@link CalculatorModel#mResult} % operand
     *
     * @param operand Actual operand
     */
    public void modulo(Double operand) {
        if (operand == 0.0) {
            isResultNaN = true;
        }
        mResult %= operand;
        setChanged();
    }

    /**
     * Compute exponential of operand
     * <p>
     * Act like e^operand
     *
     * @param operand Actual operand
     */
    public void exp(Double operand) {
        mResult = Math.exp(operand);
        setChanged();
    }

    /**
     * Compute a {@link java.lang.Math#sin} operand (converted to Radians)
     *
     * @param operand Actual operand
     */
    public void sin(Double operand) {
        mResult = Math.sin(Math.toRadians(operand));
        setChanged();
    }

    /**
     * Compute a {@link java.lang.Math#sinh} operand (converted to Radians)
     *
     * @param operand Actual operand
     */
    public void sinh(Double operand) {
        mResult = Math.sinh(Math.toRadians(operand));
        setChanged();
    }

    /**
     * Compute a {@link java.lang.Math#cos} operand (converted to Radians)
     *
     * @param operand Actual operand
     */
    public void cos(Double operand) {
        mResult = Math.cos(Math.toRadians(operand));
        setChanged();
    }

    /**
     * Compute a {@link java.lang.Math#cosh} operand (converted to Radians)
     *
     * @param operand Actual operand
     */
    public void cosh(Double operand) {
        mResult = Math.cosh(Math.toRadians(operand));
        setChanged();
    }

    /**
     * Compute a {@link java.lang.Math#tan} operand (converted to Radians)
     *
     * @param operand Actual operand
     */
    public void tan(Double operand) {
        mResult = Math.tan(Math.toRadians(operand));
        setChanged();
    }

    /**
     * Compute a {@link java.lang.Math#tanh} of operand (converted to Radians)
     *
     * @param operand Actual operand
     */
    public void tanh(Double operand) {
        mResult = Math.tanh(Math.toRadians(operand));
        setChanged();
    }

    /**
     * Compute a {@link java.lang.Math#log10} of operand
     *
     * @param operand Actual operand
     */
    public void log(Double operand) {
        mResult = Math.log10(operand);
        setChanged();
    }

    /**
     * Compute a {@link java.lang.Math#log} of operand
     *
     * @param operand Actual operand
     */
    public void ln(Double operand) {
        mResult = Math.log(operand);
        setChanged();
    }

    /**
     * Compute a {@link java.lang.Math#pow} of 2 on {@link CalculatorModel#mResult}
     *
     * @param operand Actual operand
     */
    public void sqr(Double operand) {
        mResult = Math.pow(operand, 2);
        setChanged();
    }

    /**
     * Compute a {@link java.lang.Math#sqrt} on operand
     *
     * @param operand Actual operand
     */
    public void sqrt(Double operand) {
        if (operand < 0.0) {
            isResultNaN = true;
        }
        mResult = Math.sqrt(operand);
        setChanged();
    }

    /**
     * Compute a {@link java.lang.Math#pow} of operand on {@link CalculatorModel#mResult}
     *
     * @param operand Actual operand
     */
    public void pow(Double operand) {
        mResult = Math.pow(mResult, operand);
        setChanged();
    }

    /**
     * Inverse the actual operand act like 1/x where x is the operand
     *
     * @param operand Actual operand
     */
    public void inverse(Double operand) {
        mResult = 1 / operand;
        setChanged();
    }

    /**
     * Change the sign of the actual operand
     *
     * @param operand Actual operand
     */
    public void negate(@SuppressWarnings("unused") Double operand) {
        if (mOperand.charAt(0) == '-') {
            mOperand = mOperand.substring(1);
        } else {
            mOperand = "-" + mOperand;
        }
        mOperator = mOldOperator;
        needOperandUpdate = false;

        setChanged();
        notifyObservers(mOperand);
    }

    /**
     * Set the operand to {@link java.lang.Math#PI}
     */
    public void pi() {
        mResult = Math.PI;
        mOperand = String.valueOf(Math.PI);
        setChanged();
    }

    /**
     * Set the operand to {@link java.lang.Math#E}
     */
    public void euler() {
        mOperand = String.valueOf(Math.E);
        mResult = Math.E;
        setChanged();
    }

    /**
     * @param a unused
     * @see CalculatorModel#pi()
     */
    public void pi(@SuppressWarnings("unused") Double a) {
        pi();
    }

    /**
     * @param a unused
     * @see CalculatorModel#euler()
     */
    public void euler(@SuppressWarnings("unused") Double a) {
        euler();
    }

    /**
     * @param a unused
     * @see CalculatorModel#reset()
     */
    public void reset(@SuppressWarnings("unused") Double a) {
        reset();
    }

    /**
     * Reset the model like it was newly instantiated and notify views about his update
     */
    public void reset() {
        mResult = 0.0;
        mOperand = "0";
        mOperator = "";
        isResultNaN = false;
        isDecimal = false;

        setChanged();
        notifyObservers(getSimplifiedResult());
    }

    /**
     * @param a unused
     * @see CalculatorModel#clear()
     */
    public void clear(@SuppressWarnings("unused") Double a) {
        clear();
    }

    /**
     * This method clear the actual display with "0"
     * <p>
     * Update {@link CalculatorModel#mOperator} with {@link CalculatorModel#mOldOperator}
     */
    public void clear() {
        mOperator = mOldOperator;
        isDecimal = false;

        setChanged();
        notifyObservers("0");
    }

    /**
     * This method check the operator whether we must compute it now or later
     * <p>
     * Set {@link CalculatorModel#mOperator} and {@link CalculatorModel#mOldOperator}
     * to the right value
     *
     * @param operator A math operator for calcul
     */
    public void setOperator(String operator) {
        if (mSpecialOperators.contains(operator)) {
            if (!"equal".equals(operator)) {
                mOldOperator = mOperator;
                mOperator = operator;
            }
            computeData();
        } else {
            computeData();
            mOldOperator = mOperator;
            mOperator = operator;
        }

        if (!mSpecialOperators.contains(operator)) {
            mOperand = "";
            isDecimal = false;
        }
    }

    /**
     * This method concatenate a number the user clicked on
     *
     * @param operand A number
     */
    public void setOperand(String operand) {
        if (isDecimal && ".".equals(operand)) {
            return ;
        } else if (!isDecimal && ".".equals(operand)) {
            isDecimal = true;
        }
        mOperand += operand;

        setChanged();
        notifyObservers(mOperand);
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
}
