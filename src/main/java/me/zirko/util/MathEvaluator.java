package me.zirko.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class MathEvaluator {
    private static MathEvaluator sInstance;
    private static String sFunctions = "log|ln|exp|√|sinh |cosh |tanh |sin |cos |tan ";
    private static String sHighPriorityOp = "([×÷^]|mod )";
    private static String sLowPriorityOp = "[+-]";
    private static String sOperations = "(" + sHighPriorityOp + "|" + sLowPriorityOp + ")";
    private static String sOperators = "(" + sOperations + "|" + sFunctions + ")";

    private MathEvaluator() {
    }

    /**
     * Return the instance of the class {@link MathEvaluator}
     *
     * @return {@link MathEvaluator} instance
     */
    public static MathEvaluator getInstance() {
        if (sInstance == null) {
            sInstance = new MathEvaluator();
        }
        return sInstance;
    }

    /**
     * Resolve the expression passed as parameter
     * <p>
     * Call the correct method to solve the expression with the scientific functions
     * high and low priority operations
     *
     * @param expression A math expression
     * @return The result of the operation as a {@link Double}
     * @throws IllegalArgumentException
     */
    public Double parse(String expression) throws IllegalArgumentException {
        Double res = 0.0;
        List<String> listExp;

        String[] exp = expression.split("(?<=op)|(?=op)".replace("op", sOperators));

        listExp = new LinkedList<>(Arrays.asList(exp));

        try {
            resolveScientific(listExp);
            resolveOperations(listExp);
        } catch (Exception e) {
            throw new IllegalArgumentException("Expression is not valid");
        }
        if (listExp.get(0) == null) {
            throw new IllegalArgumentException("Expression is not valid");
        }

        return Double.valueOf(listExp.get(0));
    }

    /**
     * Resolve scientific functions contained in the list
     * <p>
     * We compute recursively all the scientific function we can find
     * in the list and replace node by node the result
     *
     * @param listExp List of the elements in the math expression
     * @throws Exception
     */
    private void resolveScientific(List<String> listExp) throws Exception {
        for (int i = 0; i < listExp.size(); i++) {
            if (listExp.get(i).matches(sFunctions)) {
                resolveScientific(listExp.subList(i + 1, listExp.size()));

                String trimStr = listExp.get(i).trim();
                String res = (String) ReflectionUtils.tryInvoke(this, MathUtils.getLiteral(trimStr), listExp.get(i + 1));
                listExp.set(i, res);
                listExp.set(i + 1, "");
                listExp.removeAll(Collections.singleton(""));
            }
        }
    }

    /**
     * Resolve operations by order of priority
     * <p>
     * First of all, we compute all the priority operations, then we compute the lower priority operations
     *
     * @param listExp List of the elements in the math expression
     * @throws Exception If the expression is valid or not
     */
    private void resolveOperations(List<String> listExp) throws Exception {
        for (int i = 0; i < listExp.size(); i++) {
            if (listExp.get(i).matches(sHighPriorityOp)) {
                String trimStr = listExp.get(i).trim();

                String res = (String) ReflectionUtils.tryInvoke(
                        this, MathUtils.getLiteral(trimStr), listExp.get(i - 1), listExp.get(i + 1));
                listExp.set(i, res);
                listExp.set(i - 1, "");
                listExp.set(i + 1, "");
                listExp.removeAll(Collections.singleton(""));
                i = 0;
            }
        }
        for (int i = 0; i < listExp.size(); i++) {
            if (listExp.get(i).matches(sLowPriorityOp)) {
                String res;

                if (i - 1 < 0) {
                    res = (String) ReflectionUtils.tryInvoke(
                            this, MathUtils.getLiteral(listExp.get(i)), "0", listExp.get(i + 1));
                } else {
                    res = (String) ReflectionUtils.tryInvoke(
                            this, MathUtils.getLiteral(listExp.get(i)), listExp.get(i - 1), listExp.get(i + 1));
                    listExp.set(i - 1, "");
                }
                listExp.set(i, res);
                listExp.set(i + 1, "");
                listExp.removeAll(Collections.singleton(""));
                i = 0;
            }
        }
    }

    /**
     * Pow one number by the other
     *
     * @param a First operand
     * @param b Second operand
     * @return Result of the operation as {@link String}
     */
    public String pow(String a, String b) {
        return String.valueOf(Math.pow(Double.parseDouble(a), Double.parseDouble(b)));
    }

    public String sqrt(String operand) {
        return String.valueOf(Math.sqrt(Double.parseDouble(operand)));
    }

    /**
     * Add two numbers
     *
     * @param a First operand
     * @param b Second operand
     * @return Result of the operation as {@link String}
     */
    public String add(String a, String b) {
        return String.valueOf(Double.parseDouble(a) + Double.parseDouble(b));
    }

    /**
     * Subtract two numbers
     *
     * @param a First operand
     * @param b Second operand
     * @return Result of the operation as {@link String}
     */
    public String subtraction(String a, String b) {
        return String.valueOf(Double.parseDouble(a) - Double.parseDouble(b));
    }

    /**
     * Divide two numbers
     *
     * @param a First operand
     * @param b Second operand
     * @return Result of the operation as {@link String}
     */
    public String division(String a, String b) {
        return String.valueOf(Double.parseDouble(a) / Double.parseDouble(b));
    }

    /**
     * Modulo of two numbers
     *
     * @param a First operand
     * @param b Second operand
     * @return Result of the operation as {@link String}
     */
    public String modulo(String a, String b) {
        return String.valueOf(Double.parseDouble(a) % Double.parseDouble(b));
    }

    /**
     * Multiply two numbers
     *
     * @param a First operand
     * @param b Second operand
     * @return Result of the operation as {@link String}
     */
    public String multiplication(String a, String b) {
        return String.valueOf(Double.parseDouble(a) * Double.parseDouble(b));
    }

    /**
     * Returns the trigonometric sine of an angle.  Special cases:
     * <ul><li>If the argument is NaN or an infinity, then the
     * result is NaN.
     * <li>If the argument is zero, then the result is a zero with the
     * same sign as the argument.</ul>
     * <p>
     * <p>The computed result must be within 1 ulp of the exact result.
     * Results must be semi-monotonic.
     *
     * @param operand an angle, in radians.
     * @return the sine of the argument.
     */
    public String sin(String operand) {
        return String.valueOf(Math.sin(Math.toRadians(Double.parseDouble(operand))));
    }

    /**
     * Returns the trigonometric cosine of an angle. Special cases:
     * <ul><li>If the argument is NaN or an infinity, then the
     * result is NaN.</ul>
     * <p>
     * <p>The computed result must be within 1 ulp of the exact result.
     * Results must be semi-monotonic.
     *
     * @param operand an angle, in radians.
     * @return the cosine of the argument.
     */
    public String cos(String operand) {
        return String.valueOf(Math.cos(Math.toRadians(Double.parseDouble(operand))));
    }

    /**
     * Returns the trigonometric tangent of an angle.  Special cases:
     * <ul><li>If the argument is NaN or an infinity, then the result
     * is NaN.
     * <li>If the argument is zero, then the result is a zero with the
     * same sign as the argument.</ul>
     * <p>
     * <p>The computed result must be within 1 ulp of the exact result.
     * Results must be semi-monotonic.
     *
     * @param operand an angle, in radians.
     * @return the tangent of the argument.
     */
    public String tan(String operand) {
        return String.valueOf(Math.tan(Math.toRadians(Double.parseDouble(operand))));
    }

    /**
     * Returns the hyperbolic sine of a {@code double} value.
     * The hyperbolic sine of <i>x</i> is defined to be
     * (<i>e<sup>x</sup>&nbsp;-&nbsp;e<sup>-x</sup></i>)/2
     * where <i>e</i> is {@linkplain Math#E Euler's number}.
     * <p>
     * <p>Special cases:
     * <ul>
     * <li>If the argument is NaN, then the result is NaN.
     * <li>If the argument is infinite, then the result is an infinity
     * with the same sign as the argument.
     * <li>If the argument is zero, then the result is a zero with the
     * same sign as the argument.
     * </ul>
     * <p>
     * <p>The computed result must be within 2.5 ulps of the exact result.
     *
     * @param operand The number whose hyperbolic sine is to be returned.
     * @return The hyperbolic sine of {@code x}.
     */
    public String sinh(String operand) {
        return String.valueOf(Math.sinh(Double.parseDouble(operand)));
    }

    /**
     * Returns the hyperbolic cosine of a {@code double} value.
     * The hyperbolic cosine of <i>x</i> is defined to be
     * (<i>e<sup>x</sup>&nbsp;+&nbsp;e<sup>-x</sup></i>)/2
     * where <i>e</i> is {@linkplain Math#E Euler's number}.
     * <p>
     * <p>Special cases:
     * <ul>
     * <li>If the argument is NaN, then the result is NaN.
     * <li>If the argument is infinite, then the result is positive
     * infinity.
     * <li>If the argument is zero, then the result is {@code 1.0}.
     * </ul>
     * <p>
     * <p>The computed result must be within 2.5 ulps of the exact result.
     *
     * @param operand The number whose hyperbolic cosine is to be returned.
     * @return The hyperbolic cosine of {@code x}.
     */
    public String cosh(String operand) {
        return String.valueOf(Math.cosh(Double.parseDouble(operand)));
    }

    /**
     * Returns the hyperbolic tangent of a {@code double} value.
     * The hyperbolic tangent of <i>x</i> is defined to be
     * (<i>e<sup>x</sup>&nbsp;-&nbsp;e<sup>-x</sup></i>)/(<i>e<sup>x</sup>&nbsp;+&nbsp;e<sup>-x</sup></i>),
     * in other words, {@linkplain Math#sinh
     * sinh(<i>x</i>)}/{@linkplain Math#cosh cosh(<i>x</i>)}.  Note
     * that the absolute value of the exact tanh is always less than
     * 1.
     * <p>
     * <p>Special cases:
     * <ul>
     * <li>If the argument is NaN, then the result is NaN.
     * <li>If the argument is zero, then the result is a zero with the
     * same sign as the argument.
     * <li>If the argument is positive infinity, then the result is
     * {@code +1.0}.
     * <li>If the argument is negative infinity, then the result is
     * {@code -1.0}.
     * </ul>
     * <p>
     * <p>The computed result must be within 2.5 ulps of the exact result.
     * The result of {@code tanh} for any finite input must have
     * an absolute value less than or equal to 1.  Note that once the
     * exact result of tanh is within 1/2 of an ulp of the limit value
     * of &plusmn;1, correctly signed &plusmn;{@code 1.0} should
     * be returned.
     *
     * @param operand The number whose hyperbolic tangent is to be returned.
     * @return The hyperbolic tangent of {@code x}.
     */
    public String tanh(String operand) {
        return String.valueOf(Math.tanh(Double.parseDouble(operand)));
    }

    /**
     * Returns the natural logarithm (base <i>e</i>) of a {@code double}
     * value.  Special cases:
     * <ul><li>If the argument is NaN or less than zero, then the result
     * is NaN.
     * <li>If the argument is positive infinity, then the result is
     * positive infinity.
     * <li>If the argument is positive zero or negative zero, then the
     * result is negative infinity.</ul>
     * <p>
     * <p>The computed result must be within 1 ulp of the exact result.
     * Results must be semi-monotonic.
     *
     * @param operand a value
     * @return the value ln&nbsp;{@code a}, the natural logarithm of
     * {@code a}.
     */
    public String ln(String operand) {
        return String.valueOf(Math.log(Double.parseDouble(operand)));
    }

    /**
     * Returns the base 10 logarithm of a {@code double} value.
     * Special cases:
     * <p>
     * <ul><li>If the argument is NaN or less than zero, then the result
     * is NaN.
     * <li>If the argument is positive infinity, then the result is
     * positive infinity.
     * <li>If the argument is positive zero or negative zero, then the
     * result is negative infinity.
     * <li> If the argument is equal to 10<sup><i>n</i></sup> for
     * integer <i>n</i>, then the result is <i>n</i>.
     * </ul>
     * <p>
     * <p>The computed result must be within 1 ulp of the exact result.
     * Results must be semi-monotonic.
     *
     * @param operand a value
     * @return the base 10 logarithm of  {@code a}.
     */
    public String log(String operand) {
        return String.valueOf(Math.log10(Double.parseDouble(operand)));
    }

    /**
     * Returns Euler's number <i>e</i> raised to the power of a
     * {@code double} value.  Special cases:
     * <ul><li>If the argument is NaN, the result is NaN.
     * <li>If the argument is positive infinity, then the result is
     * positive infinity.
     * <li>If the argument is negative infinity, then the result is
     * positive zero.</ul>
     * <p>
     * <p>The computed result must be within 1 ulp of the exact result.
     * Results must be semi-monotonic.
     *
     * @param operand the exponent to raise <i>e</i> to.
     * @return the value <i>e</i><sup>{@code a}</sup>,
     * where <i>e</i> is the base of the natural logarithms.
     */
    public String exp(String operand) {
        return String.valueOf(Math.exp(Double.parseDouble(operand)));
    }
}
