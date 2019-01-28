package alex.alien.myCalc.calculator;

import alex.alien.myCalc.calculator.operations.Operation;
import alex.alien.myCalc.calculator.operations.OperationException;

public interface CalculationTreeBuilder {
    Operation buildCalculationTree(String expression) throws OperationException, ArithmeticException, IllegalArgumentException;
}
