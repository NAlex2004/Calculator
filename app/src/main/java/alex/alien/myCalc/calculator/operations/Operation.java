package alex.alien.myCalc.calculator.operations;

public interface Operation {
    String getName();
    int getWeight();
    double getResult() throws OperationException;
    Operation[] getOperands();
}
