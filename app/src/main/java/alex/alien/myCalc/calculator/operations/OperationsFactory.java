package alex.alien.myCalc.calculator.operations;

import java.util.Map;

public interface OperationsFactory {
    Map<String, Class> getKnownOperations();
    Operation createOperation(String operationName) throws OperationException;
}
