package com.route.calculator;

import static java.lang.Math.sqrt;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.text.Editable;
import android.widget.TextView;
import java.lang.Math;

import java.util.function.DoubleToIntFunction;


public class calculator extends AppCompatActivity {

    TextView resultScreen;
    int i = 0; // var to iterate on the loop
    String values[] = new String[1000];
    double result = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);
        resultScreen = findViewById(R.id.screen);

    }

    public void clickedNumber(View view) {
        //to reset every this to zero in the start of any new operation
        Button clickedNumber = ((Button) view);
        double temp;
//        int cursorPosition = resultScreen.getSelectionStart();
        String currentText = resultScreen.getText().toString();
        if (view instanceof Button) {
            if (currentText.length() >= 0) {
//                Editable updatedText = resultScreen.getEditableText();
//                updatedText.insert(cursorPosition, clickedNumber.getText().toString());
//                resultScreen.setText(updatedText.toString());
//                resultScreen.setSelection(cursorPosition + 1); // Set the cursor position after the inserted number
                resultScreen.append(clickedNumber.getText().toString());
                //handle the negative like (4/-4) or (4*-4)
                if (i > 2) {
                    if (values[i - 1].equals("-") && values[i - 2].equals("/")) {//5/-
                        temp = Double.valueOf(clickedNumber.getText().toString());
                        temp *= -1;
                        values[i - 1] = String.valueOf(temp);
                    } else if (values[i - 1].equals("-") && values[i - 2].equals("×")) {
                        temp = Double.valueOf(clickedNumber.getText().toString());
                        temp *= -1;
                        values[i - 1] = String.valueOf(temp);
                    }
                }
                if (i > 0) {
                    // if i have a case like his 55 how to save them like{"55",.....} and not like { "5" ,"5"....}
                    if (values[i - 1].equals("+") || values[i - 1].equals("-") || values[i - 1].equals("/") || values[i - 1].equals("×")) {
                        values[i] = clickedNumber.getText().toString();
                        i++;
                    } else {
                        values[i - 1] += clickedNumber.getText().toString();

                    }
                } else {
                    values[i] = clickedNumber.getText().toString();
                    i++;
                }

            }
        }

    }

    public void clickedOperation(View view) {       //5

        Button clickedOperation = ((Button) view);
        String copyclickedOperator = clickedOperation.getText().toString();
        if (clickedOperation instanceof Button) {
            if (copyclickedOperator.equals("-")) {
                if (values[i - 1].equals("-")) {
                    values[i - 1] = "+";
                    resultScreen.append(clickedOperation.getText().toString());
                } else if (values[i - 1].equals("+")) {
                    values[i - 1] = "-";
                    resultScreen.append(clickedOperation.getText().toString());
                } else {
                    values[i] = clickedOperation.getText().toString();
                    resultScreen.append(clickedOperation.getText().toString());
                    i++;
                }
            } else {
                values[i] = clickedOperation.getText().toString();
                resultScreen.append(clickedOperation.getText().toString());
                i++;
            }
        }
    }

    public void clickedDeleteAll(View view) {
        Button clickedButton = ((Button) view);
        if (clickedButton instanceof Button) {
            resultScreen.setText("");
            values = new String[values.length]; //to retrun all the elements of an array to null
            result = 0;
            i = 0;
        }
    }

    public void clickedDeleteLetter(View view) {
//        int cursorPosition = resultScreen.getSelectionStart();
        if (view instanceof Button) {
            String currentText = resultScreen.getText().toString();
            if (currentText.length() > 0) {
                String updatedText = currentText.substring(0, currentText.length() - 1);
                resultScreen.setText(updatedText);
//                Editable editableText = resultScreen.getEditableText();
//                editableText.delete(cursorPosition - 1, cursorPosition);
                //make sure to delete the last number from the array to
                //check if the last thing is an operator
                if( values[i-1].equals("+") || values[i-1].equals("-") || values[i-1].equals("×") ||values[i-1].equals("/")){
                    values[i-1]= "";
                    i--;
                }
                else {
                    String temp = values[i-1];
                    values[i - 1] = temp.substring(0,temp.length() -1 );
                }
            }
        }
    }

    public void clickedEqual(View view) {
        Button equal = ((Button) view);
        if (equal instanceof Button) {
            calculate(values);
            resultScreen.setText(String.valueOf(result));
            values = new String[values.length]; //to retrun all the elements of an array to null
            values[0] = String.valueOf(result);
            i = 1;
        }
    }

    public void clickedSquare(View view) {
        double temp = Double.valueOf(values[i-1]);
        temp *= temp;
        values[i-1] = String.valueOf(temp);
        resultScreen.append("²");
    }

    public void clickedRoot(View view) {
        double temp = Double.valueOf(values[i-1]);
        resultScreen.append("^(1/2)");
        temp = sqrt(temp);
        values[i-1] = String.valueOf(temp);

    }

    public double calculate(String values[]) {
        int j = 0;
        double RHS = 0, LHS = 0;
        String operator = "";
        result = Double.valueOf(values[j]); //first element
        for (j = 1; j <= i; j++) {
            if (j % 2 == 0) {
                RHS = Double.valueOf(values[j]);
                if (operator.equals("+")) {
                    result += RHS;
                } else if (operator.equals("×")) {
                    result *= RHS;
                } else if (operator.equals("/")) {
                    result /= RHS;
                } else if (operator.equals("-")) {
                    result -= RHS;
                }
            } else {
                operator = values[j];
            }
        }

        return result;
    }

}