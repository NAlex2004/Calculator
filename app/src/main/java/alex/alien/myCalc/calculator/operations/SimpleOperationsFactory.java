package alex.alien.myCalc.calculator.operations;

import java.util.HashMap;
import java.util.Map;

public class SimpleOperationsFactory implements OperationsFactory {
    protected HashMap<String, Class> _operationClasses = new HashMap<String, Class>();

    public SimpleOperationsFactory() {
        // shall use ConstantOperation explicitly
        // Class c = ConstantOperation.class;
        _operationClasses.put("+", SumOperation.class);
        _operationClasses.put("*", MultiplyOperation.class);
        _operationClasses.put("/", DivideOperation.class);
        _operationClasses.put("E", ExponentOperation.class);
    }

    @Override
    public Map<String, Class> getKnownOperations() {
        return _operationClasses;
    }

    @Override
    public Operation createOperation(String operationName) throws OperationException {
        Class type = _operationClasses.get(operationName);
        if (type == null) {
            throw new OperationUnknownException("Operation '" + operationName + "' is unknown.");
        }

        Operation instance = null;

        try {
            instance = (Operation)type.newInstance();
        } catch (Exception e) {
            OperationException exception = new OperationException("Cannot create operation '" + operationName + "' instance.");
            exception.initCause(e);
            throw exception;
        }

        return instance;
    }
}
