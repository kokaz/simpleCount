package me.zirko;

import me.zirko.ui.widget.CalculatorPanel;
import me.zirko.util.ReflectionUtils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * ExpressionBuilder is a simple math expression API that interface with your CalculatorPanel
 * It handles Int and Double for simple operation and scientific functions like {@link #sin(Double)},
 * or {@link #cos(Double)}
 *
 * Here is an example of use which do "( 5 + 10 ) / 5"
 * <pre>
 *     ExpressionBuilder expBuilder = new ExpressionBuilder(myView);
 *     expBuilder.build("add", 5);
 *     expBuilder.build("divide", 10);
 *     myRes = expBuilder.build("equal", 5);
 *     System.out.println(myRes); // Will print 3
 * </pre>
 *
 * @author Guillaume Fillon
 * @version 1.0
 * @since 1.0
 */
public class ExpressionBuilder {
    public CalculatorPanel mView;
    public Double mRes = null;
    public String mOldOperation = "";
    public String mOperation = "";

    public ExpressionBuilder(CalculatorPanel view) {
        this.mView = view;
    }

    public Double build(String actionCommand, Double operand) {
        ReflectionUtils.tryInvoke(this, actionCommand, operand);
        mOldOperation = actionCommand;
        mOperation = "";
        return mRes;
    }

    public void equal(Double operand) {
        if ("".equals(mOldOperation)) {
            mOldOperation = "";
            mOperation = "";
            mRes = operand;
            mView.setResult(getSimplifiedResult());
            return;
        }
        ReflectionUtils.tryInvoke(this, mOldOperation, operand);
        resetOperationAndSetResult();
    }

    public void add(Double operand) {
        if (mRes == null) {
            mRes = operand;
        } else {
            mRes += operand;
        }
    }

    public void subtraction(Double operand) {
        if (mRes == null) {
            mRes = operand;
        } else {
            mRes -= operand;
        }
    }

    public void multiplication(Double operand) {
        if (mRes == null) {
            mRes = operand;
        } else {
            mRes *= operand;
        }
    }

    public void division(Double operand) {
        if (operand == 0.0) {
            throw new ArithmeticException("Division by 0");
        }
        if (mRes == null) {
            mRes = operand;
        } else {
            mRes /= operand;
        }
    }

    public void modulo(Double operand) {
        if (operand == 0.0) {
            throw new ArithmeticException("Division by 0");
        }
        if (mRes == null) {
            mRes = operand;
        } else {
            mRes %= operand;
        }
    }

    public void negate(Double operand) {
        mView.setResult(getSimplifiedDouble(-operand));
    }

    public void log(Double operand) {
        mRes = Math.log(operand);
        resetOperationAndSetResult();
    }

    public void sqrt(Double operand) {
        if (operand < 0.0) {
            throw new ArithmeticException("Square root with signed number");
        }
        mRes = Math.sqrt(operand);
        resetOperationAndSetResult();
    }

    public void cos(Double operand) {
        mRes = Math.cos(Math.toRadians(operand));
        resetOperationAndSetResult();
    }

    public void sin(Double operand) {
        mRes = Math.sin(Math.toRadians(operand));
        resetOperationAndSetResult();
    }

    public void tan(Double operand) {
        mRes = Math.tan(Math.toRadians(operand));
        resetOperationAndSetResult();
    }

    public void reset(Double operand) {
        mRes = 0.0;
        mOldOperation = "";
        mOperation = "";
        mView.clear();
    }

    public void clear(Double operand) {
        mView.clear();
    }

    private void resetOperationAndSetResult() {
        mOldOperation = "";
        mOperation = "";
        mView.setResult(getSimplifiedResult());
        mRes = null;
    }

    private String getSimplifiedResult() {
        return getSimplifiedDouble(mRes);
    }

    private String getSimplifiedDouble(Double a) {
        BigDecimal bd = new BigDecimal(a);
        DecimalFormat df = new DecimalFormat();

        df.setMaximumFractionDigits(8);
        df.setMinimumFractionDigits(0);
        df.setGroupingUsed(false);

        return df.format(bd);
    }
}
