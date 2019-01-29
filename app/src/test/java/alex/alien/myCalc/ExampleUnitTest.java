package alex.alien.myCalc;

import org.junit.Test;

import alex.alien.myCalc.calculator.BinaryOperationsCalculationTreeBuilder;
import alex.alien.myCalc.calculator.CalculationTreeBuilder;
import alex.alien.myCalc.calculator.Calculator;
import alex.alien.myCalc.calculator.operations.MathHelper;
import alex.alien.myCalc.calculator.operations.OperationException;
import alex.alien.myCalc.calculator.operations.SimpleOperationsFactory;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    @Test
    public void calculationThrowsOnBadInput() {
        CalculationTreeBuilder builder = new BinaryOperationsCalculationTreeBuilder(new SimpleOperationsFactory());
        Calculator calculator = new Calculator(builder);
        String expression = "-";
        try {
            calculator.Calculate(expression);
        }
        catch (OperationException | IllegalArgumentException e) {
            assertTrue(true);
        }

        expression = "1/0";
        try {
            calculator.Calculate(expression);
        }
        catch (OperationException | IllegalArgumentException e) {
            assertTrue(true);
        }
    }

    @Test
    public void calculationResultIsCorrect() {
        CalculationTreeBuilder builder = new BinaryOperationsCalculationTreeBuilder(new SimpleOperationsFactory());
        Calculator calculator = new Calculator(builder);
        String expression = "2+2*3+1+4/2";

        try {
            double result = calculator.Calculate(expression);
            assertEquals(11.0, result, 0);

            expression = "-5-4";
            result = calculator.Calculate(expression);
            assertEquals(-9.0, result, 0);

            expression = "-15/-3.0*5.5+14.89/4+24.578*457/3-5-4-2.2+1E3";
            result = calculator.Calculate(expression);
            assertEquals(MathHelper.round(4764.071166666667, 10), result, 0);
        }
        catch (OperationException | IllegalArgumentException e) {
            fail();
        }
    }

}