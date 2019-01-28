package alex.alien.myCalc.calculator;

import alex.alien.myCalc.calculator.operations.Operation;
import alex.alien.myCalc.calculator.operations.OperationException;

public class Calculator {
    protected CalculationTreeBuilder _builder;

    public Calculator(CalculationTreeBuilder builder) {
        if (builder == null) {
            throw new IllegalArgumentException("Argument cannot be null.");
        }

        _builder = builder;
    }

    public double Calculate(String expression) throws OperationException {
        try {
            Operation calculations = _builder.buildCalculationTree(expression);
            return calculations.getResult();
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
