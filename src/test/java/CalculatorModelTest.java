import me.zirko.model.CalculatorModel;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class CalculatorModelTest extends CalculatorModel {
    public String getResult() {
        String retValue = "";
        Method getContextMethod;
        try {
            getContextMethod = CalculatorModel.class.getDeclaredMethod("getSimplifiedResult");

            if (getContextMethod != null) {
                getContextMethod.setAccessible(true);
                retValue = (String) getContextMethod.invoke(this);
            }
            return retValue;
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return retValue;
    }
}
