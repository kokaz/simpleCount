import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CalculatorTest {
    private CalculatorModelTest mModel;

    @Before
    public void setUp() throws Exception {
        mModel = new CalculatorModelTest();
    }

    @After
    public void tearDown() throws Exception {
        mModel.reset();
    }

    @Test
    public void testParseExpression_BadExpression1() {
        mModel.addNumber("99998");
        mModel.setOperator("add", "+", false);

        Assert.assertFalse(mModel.parseExpression());
    }

    @Test
    public void testParseExpression_BadExpression2() {
        mModel.addNumber("99998");
        mModel.setOperator("add", "+", false);
        mModel.addNumber("99998");
        mModel.setOperator("multiplication", "×", false);
        mModel.setOperator("add", "+", false);
        mModel.setOperator("subtraction", "-", false);
        mModel.addNumber("99998");

        Assert.assertFalse(mModel.parseExpression());
    }
    @Test
    public void testParseExpression_BadExpression3() {
        mModel.setOperator("sin ", "sin", true);
        mModel.setOperator("sin ", "sin", true);
        mModel.setOperator("sin ", "sin", true);
        mModel.setOperator("sin ", "sin", true);

        Assert.assertFalse(mModel.parseExpression());
    }
    @Test
    public void testParseExpression_BadExpression4() {
        mModel.setOperator("sin ", "sin", true);
        mModel.setOperator("sin ", "sin", true);
        mModel.setOperator("sin ", "sin", true);
        mModel.setOperator("sin ", "sin", true);
        mModel.setOperator("division", "÷", false);
        mModel.addNumber("0");

        Assert.assertFalse(mModel.parseExpression());
    }

    /**
     * Test the expression : {@code 99998+5.5}
     */
    @Test
    public void testParseExpression_GoodExpression1() {
        mModel.addNumber("99998");
        mModel.setOperator("add", "+", false);
        mModel.addNumber("5.5");

        Assert.assertTrue(mModel.parseExpression());
        Assert.assertTrue("100003.5".equals(mModel.getResult()));
    }

    /**
     * Test the expression : {@code 78+12*12.555/987.45454-9788678.5757+sin(pi)}
     */
    @Test
    public void testParseExpression_GoodExpression2() {
        mModel.addNumber("78");
        mModel.setOperator("add", "+", false);
        mModel.addNumber("12");
        mModel.setOperator("multiplication", "×", false);
        mModel.addNumber("12.555");
        mModel.setOperator("division", "÷", false);
        mModel.addNumber("987.45454");
        mModel.setOperator("subtraction", "-", false);
        mModel.addNumber("9788678.57575373737");
        mModel.setOperator("add", "+", false);
        mModel.setOperator("sin ", "sin", true);
        mModel.addNumber("pi");

        Assert.assertTrue(mModel.parseExpression());
        Assert.assertTrue("-9788600.36837596".equals(mModel.getResult()));
    }

    /**
     * Test the expression : {@code 78+12*12.555/0.000001-pi*978867857575373737}
     */
    @Test
    public void testParseExpression_GoodExpression3() {
        mModel.addNumber("78");
        mModel.setOperator("add", "+", false);
        mModel.addNumber("12");
        mModel.setOperator("multiplication", "×", false);
        mModel.addNumber("12.555");
        mModel.setOperator("division", "÷", false);
        mModel.addNumber("0.000001");
        mModel.setOperator("subtraction", "-", false);
        mModel.addNumber("pi");
        mModel.setOperator("multiplication", "×", false);
        mModel.addNumber("978867857575373737");

        Assert.assertTrue(mModel.parseExpression());
        Assert.assertTrue("-3075204070043313664".equals(mModel.getResult()));
    }

    /**
     * Test the expression : {@code 78^12*12.555/987.45454}
     */
    @Test
    public void testParseExpression_GoodExpression4() {
        mModel.addNumber("78");
        mModel.setOperator("pow", "^", false);
        mModel.addNumber("12");
        mModel.setOperator("multiplication", "×", false);
        mModel.addNumber("12.555");
        mModel.setOperator("division", "÷", false);
        mModel.addNumber("987.45454");

        Assert.assertTrue(mModel.parseExpression());
        Assert.assertTrue("644814564601790464000".equals(mModel.getResult()));
    }
}
