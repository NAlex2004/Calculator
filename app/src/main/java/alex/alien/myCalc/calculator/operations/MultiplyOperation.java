package alex.alien.myCalc.calculator.operations;

public class MultiplyOperation extends BaseOperation {
    public MultiplyOperation() {
        super("*", 1);
        _operands = new Operation[2];
    }

    @Override
    public double getResult() throws OperationException {
        if (_operands[0] == null || _operands[1] == null) {
            throw new OperationException("Operands cannot be null");
        }
        double left = _operands[0].getResult();
        double right = _operands[1].getResult();
        return left * right;
    }
}
