package alex.alien.myCalc.calculator;

import alex.alien.myCalc.calculator.operations.MathHelper;
import alex.alien.myCalc.calculator.operations.Operation;
import alex.alien.myCalc.calculator.operations.OperationException;

public class Calculator {
    protected CalculationTreeBuilder _builder;
    protected int _decimalPlacesCount;

    public Calculator(CalculationTreeBuilder builder) {
        this(builder, 10);
    }

    public Calculator(CalculationTreeBuilder builder, int decimalPlacesCount) {
        if (builder == null) {
            throw new IllegalArgumentException("Argument cannot be null.");
        }
        _decimalPlacesCount = decimalPlacesCount;

        _builder = builder;
    }

    public double Calculate(String expression) throws OperationException {
        try {
            Operation calculations = _builder.buildCalculationTree(expression);

            double result = calculations.getResult();
            if (_decimalPlacesCount >= 0) {
                result = MathHelper.round(result, _decimalPlacesCount);
            }
            return result;
        }
        catch (OperationException e) {
            throw e;
        }
        catch (Exception e) {
            OperationException exception = new OperationException(e.getMessage());
            exception.initCause(e);
            throw exception;
        }

    }
}
