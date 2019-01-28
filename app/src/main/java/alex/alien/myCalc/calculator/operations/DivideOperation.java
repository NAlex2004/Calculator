package alex.alien.myCalc.calculator.operations;

public class DivideOperation extends BaseOperation {
    public DivideOperation() {
        super("/", 1);
        _operands = new Operation[2];
    }

    @Override
    public double getResult() throws OperationException {
        if (_operands[0] == null || _operands[1] == null) {
            throw new OperationException("Operands cannot be null");
        }
        double left = _operands[0].getResult();
        double right = _operands[1].getResult();
        if (right == 0.0) {
            throw new OperationException("Division by zero.");
        }
        return left / right;
    }
}
