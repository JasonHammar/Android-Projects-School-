package edu.sjsu.android.mortgagecalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private TextView text;
    private EditText input;
    private TextView seekBarInterest;
    private SeekBar seekBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text = (TextView) findViewById(R.id.result);
        input = (EditText) findViewById(R.id.editText);
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        seekBarInterest = (TextView) findViewById(R.id.seekBarInterest);



        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
            float j = (float) i;
                seekBarInterest.setText(String.valueOf(i));

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    public void onClick(View view){
        switch(view.getId()){// Checks if buttons are checked
            case R.id.button:
                RadioButton fifteenButton =
                        (RadioButton) findViewById(R.id.radioButton);
                RadioButton twentyButton =
                        (RadioButton) findViewById(R.id.radioButton2);
                RadioButton twentyFiveButton =
                        (RadioButton) findViewById(R.id.radioButton3);
                CheckBox taxesAndInsurance =
                        (CheckBox) findViewById(R.id.checkbox);

                if (input.getText().length() == 0) {
                    Toast.makeText(this, "Please enter a valid number",
                            Toast.LENGTH_LONG).show();
                    return;
                }

                float inputValue = Float.parseFloat(input.getText().toString());
                float interest = Float.parseFloat(seekBarInterest.getText().toString());
                float taxAndIns = inputValue / 10;

                // Statements below will help to display the mortgage you owe.
                if (fifteenButton.isChecked()) {
                    if(taxesAndInsurance.isChecked()){
                        text.setText("Your Mortgage: $" + String
                                .valueOf(Calculator.calculateFifteen(inputValue, interest, taxAndIns)));
                    }else{
                        text.setText("Your Mortgage: $" + String
                                .valueOf(Calculator.calculateFifteen(inputValue, interest, 0)));
                    }



                } else if (twentyButton.isChecked()) {
                    if(taxesAndInsurance.isChecked()){
                        text.setText("Your Mortgage: $" + String
                                .valueOf(Calculator.calculateTwenty(inputValue, interest, taxAndIns)));
                    }else{
                        text.setText("Your Mortgage: $" + String
                                .valueOf(Calculator.calculateTwenty(inputValue, interest, 0)));
                    }
                }else if (twentyFiveButton.isChecked()) {
                      if(taxesAndInsurance.isChecked()){
                        text.setText("Your Mortgage: $" + String
                                .valueOf(Calculator.calculateTwentyFive(inputValue, interest, taxAndIns)));
                    }else{
                        text.setText("Your Mortgage: $" + String
                                .valueOf(Calculator.calculateTwentyFive(inputValue, interest, 0)));
                    }

                }

        }
    }
}