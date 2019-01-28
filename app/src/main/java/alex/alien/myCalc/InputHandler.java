package alex.alien.myCalc;

import android.app.Activity;
import android.graphics.Color;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Arrays;

import alex.alien.myCalc.calculator.Calculator;

public class InputHandler implements View.OnLongClickListener, View.OnClickListener {

    private String[] commandNames;
    private Activity _activity;
    private Calculator _calculator;
    private int defaultTextColor;

    public InputHandler(Activity activity, Calculator calculator) {
        super();
        _activity = activity;
        _calculator = calculator;
        commandNames = new String[] { "C", "=", "+", "-", "*", "/" };
        Arrays.sort(commandNames);

        EditText editText = _activity.findViewById(R.id.editText);
        defaultTextColor = editText.getCurrentTextColor();
    }

    private boolean isCommand(String characters) {
        return Arrays.binarySearch(commandNames, characters) >= 0;
    }

    private boolean hasLastNumberComma(Editable text) {
        int length = text.length();
        boolean result = false;
        int i = length - 1;

        while (i >= 0 && isCommand(Character.toString(text.charAt(i)))) {
            i--;
        }

        while (i >= 0 && !isCommand(Character.toString(text.charAt(i)))) {
            if (text.charAt(i) == '.') {
                result = true;
                break;
            }
            i--;
        }

        return result;
    }

    protected void processInput(Button pressedButton, EditText editText) {
        Editable presentText = editText.getText();
        int length = presentText.length();
        String buttonText = pressedButton.getText().toString();
        char lastChar = presentText.charAt(length - 1);
        boolean isCommandButton = isCommand(buttonText);
        boolean isComma = buttonText.equals(".");
        boolean lastIsCommand = isCommand(Character.toString(lastChar));
        boolean hasComma = false;

        editText.setTextColor(defaultTextColor);

        // command button
        if (isCommandButton) {
            switch (buttonText) {
                case "C":
                    presentText.delete(length - 1, length);
                    if (length == 1) {
                        presentText.append('0');
                    }
                    return;
                case "=":
                    if (lastChar == '.') {
                        return;
                    }

                    try {
                        double result = _calculator.Calculate(presentText.toString());
                        long roundResult = Math.round(result);
                        presentText.clear();
                        if (roundResult == result) {
                            presentText.append(String.valueOf(roundResult));
                        } else {
//                            presentText.append(NumberFormat.getInstance(Locale.ROOT).format(result));
                            presentText.append(String.valueOf(result));
                        }
                    }
                    catch (Exception e) {
                        editText.setTextColor(Color.RED);
                    }

                    return;
                case "-":
                    if (lastChar == '+' || lastChar == '-') {
                        presentText.delete(length - 1, length);
                    }
                    if (lastChar == '.') {
                        return;
                    }

                    break;
                default:
                    if ((length == 1 && lastChar == '0')
                            || lastChar == '.') {
                        return;
                    }

                    if (length == 1 && lastIsCommand) {
                        return;
                    }

                    while (lastIsCommand && length > 1) {
                        presentText.delete(length - 1, length);
                        length--;
                        lastChar = presentText.charAt(length - 1);
                        lastIsCommand = isCommand(Character.toString(lastChar));
                    }
            }
        }

        if (presentText.toString().equals("0") && !isComma) {
            presentText.clear();
        }

        hasComma = hasLastNumberComma(presentText);

        if (isComma && (hasComma || lastIsCommand)) {
            return;
        }

        presentText.append(buttonText);
    }

    @Override
    public void onClick(View view) {
        Button button = (Button)view;
        EditText editText = _activity.findViewById(R.id.editText);
        processInput(button, editText);
    }

    @Override
    public boolean onLongClick(View view) {
        EditText editText = _activity.findViewById(R.id.editText);
        editText.setText("0");
        return true;
    }
}
