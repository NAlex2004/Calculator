package alex.alien.myCalc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import alex.alien.myCalc.calculator.BinaryOperationsCalculationTreeBuilder;
import alex.alien.myCalc.calculator.CalculationTreeBuilder;
import alex.alien.myCalc.calculator.Calculator;
import alex.alien.myCalc.calculator.operations.SimpleOperationsFactory;

public class MainActivity extends AppCompatActivity {
    private InputHandler _inputHandler;

    protected void initializeButtons(LinearLayout buttonsLayout) {
        // можно в xml наждой присвоить обработчик.. а пусть будет так..
        int childCount = buttonsLayout.getChildCount();

        for (int i = 0; i < childCount; i++) {
            View v = buttonsLayout.getChildAt(i);
            if (v instanceof LinearLayout) {
                int buttonsCount = ((LinearLayout) v).getChildCount();
                for (int j = 0; j < buttonsCount; j++) {
                    View button = ((LinearLayout) v).getChildAt(j);
                    if (button instanceof Button) {
                        Button b = (Button) button;
                        b.setOnClickListener(_inputHandler);
                        if (b.getText().equals("C")) {
                            b.setOnLongClickListener(_inputHandler);
                        }
                    }
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CalculationTreeBuilder builder = new BinaryOperationsCalculationTreeBuilder(new SimpleOperationsFactory());
        Calculator calculator = new Calculator(builder);
        _inputHandler = new InputHandler(this, calculator);
        LinearLayout layoutButtons = findViewById(R.id.layoutButtons);
        initializeButtons(layoutButtons);
    }
}
