package alex.alien.myCalc.calculator;

import alex.alien.myCalc.calculator.operations.*;

public class BinaryOperationsCalculationTreeBuilder implements CalculationTreeBuilder {

    class ParsingText {
        public String text;
        public int position;

        public ParsingText(String text, int position) {
            this.text = text;
            this.position = position;
        }
    }

    class Item {
        public String value;
        public boolean isNumber;

        public Item(String value, boolean isNumber) {
            this.value = value;
            this.isNumber = isNumber;
        }
    }

    private OperationsFactory _operationsFactory;

    public BinaryOperationsCalculationTreeBuilder(OperationsFactory operationsFactory) {
        if (operationsFactory == null) {
            throw new IllegalArgumentException("operationsFactory cannot be null.");
        }
        _operationsFactory = operationsFactory;
    }

    private boolean isPartOfNumber(char character) {
        return (Character.isDigit(character) || character == '.');
    }

    protected Item getNextItem(ParsingText text) {
        int length = text.text.length();
        if (text.position >= length) {
            return null;
        }

        StringBuilder builder = new StringBuilder();

        char currentChar = text.text.charAt(text.position);

        // '-' before number is part of number. '-' after - part of another number
        boolean isNumber = isPartOfNumber(currentChar) || currentChar == '-';
        boolean isNextCharSameType = isNumber;

        while (isNextCharSameType == isNumber && text.position < length) {
            builder.append(currentChar);
            text.position++;
            if (text.position >= length) {
                break;
            }
            currentChar = text.text.charAt(text.position);
            // '-' after operation is part of next number
            if (!isNumber && currentChar == '-') {
                break;
            }
            isNextCharSameType = isPartOfNumber(currentChar);
        }

        return new Item(builder.toString(), isNumber);
    }

    protected Operation getSubTree(ParsingText text, Operation initialHeadOperation) throws OperationException, ArithmeticException, NumberFormatException {
        Operation topHeadOperation = initialHeadOperation;
        // for simplicity let's always have initial operation, 0 + 0
        if (topHeadOperation == null) {
            topHeadOperation = _operationsFactory.createOperation("+");
            topHeadOperation.getOperands()[0] = new ConstantOperation(0);
            topHeadOperation.getOperands()[1] = topHeadOperation.getOperands()[0];
        }

        int currentOperationWeight = topHeadOperation.getWeight();
        int previousItemPosition = text.position;

        int length = text.text.length();
        Operation leftOperand = topHeadOperation.getOperands()[0];
        Operation operation = topHeadOperation;

        while (text.position < length) {
            Item item = getNextItem(text);
            if (item == null) {
                break;
            }

            if (item.isNumber) {
                double value = Double.parseDouble(item.value);
                Operation constOperation = new ConstantOperation(value);

                // second operand is negative number, so there was no operation created, just 2 operands (3-2 => 3, -2)
                if (operation == null) {
                    operation = _operationsFactory.createOperation("+");
                }
                // we have only binary operations
                operation.getOperands()[0] = leftOperand;
                operation.getOperands()[1] = constOperation;

                leftOperand = operation;
                topHeadOperation = operation;
                operation = null;

            } else {
                operation = _operationsFactory.createOperation(item.value);

                // same weight operations, go up-right in tree
                if (operation.getWeight() == currentOperationWeight) {
                    operation.getOperands()[0] = leftOperand;
                    topHeadOperation = operation;
                }
                // operation with larger weight (*, /) will be right operand of previous
                // and get it's right operand as own left
                if (operation.getWeight() > currentOperationWeight) {
                    operation.getOperands()[0] = topHeadOperation.getOperands()[1];
                    topHeadOperation.getOperands()[1] = getSubTree(text, operation);

                }

                if (operation.getWeight() < currentOperationWeight) {
                    // operation with smaller weight goes back to topHead of previous branch
                    // so, forget about this operation here and create it there
                    text.position = previousItemPosition;
                    break;
                }
            }

            previousItemPosition = text.position;
        }

        return topHeadOperation;
    }

    @Override
    public Operation buildCalculationTree(String expression) throws OperationException, IllegalArgumentException {
        ParsingText text = new ParsingText(expression, 0);
        Operation result = getSubTree(text, null);
        if (result == null) {
            throw new IllegalArgumentException("Expression is not legal for calculating.");
        }
        return result;
    }
}
