package alex.alien.myCalc.calculator.operations;

public abstract class BaseOperation implements Operation {
    protected int _weight = 0;
    protected String _textName = "";
    protected Operation[] _operands = null;

    // operationWeight minimum value = 0
    public BaseOperation(String operationTextName, int operationWeight) {
        _weight = Math.max(0, operationWeight);
        _textName = operationTextName;
    }

    @Override
    public Operation[] getOperands() {
        return _operands;
    }

    @Override
    public String getName() {
        return _textName;
    }

    @Override
    public int getWeight() {
        return _weight;
    }

}
