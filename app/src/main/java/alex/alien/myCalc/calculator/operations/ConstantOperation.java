package alex.alien.myCalc.calculator.operations;

public class ConstantOperation extends BaseOperation {
    protected double _value;

    @Override
    public double getResult() {
        return _value;
    }

    @Override
    public Operation[] getOperands() {
        return new Operation[0];
    }

    public ConstantOperation(double value) {
        super("", 0);
        _value = value;
    }

}
