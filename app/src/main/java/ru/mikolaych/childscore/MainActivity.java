package ru.mikolaych.childscore;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private Integer result;
    private String answerNumber;
    private int counterPositive;
    private int counterNegative;
    private int counterMain = 0;
    private Boolean flagAnswer = true;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView example = findViewById(R.id.exerciseWindow);
        int random1 = new Random().nextInt(9);
        int random2 = new Random().nextInt(9);
        String numberOne = Integer.toString(random1);
        String numberTwo = Integer.toString(random2);
        result = random1 + random2;
        example.setText(numberOne + " + " + numberTwo);

    }

    public void answer(View view) {

        EditText answerWindow = findViewById(R.id.answerWindow);
        TextView resultWindow = findViewById(R.id.resultWindow);
        TextView counterPositive = findViewById(R.id.counterPositive);
        TextView counterNegative = findViewById(R.id.counterNegative);
        TextView numberAsk = findViewById(R.id.numberAsk);
        String number = answerWindow.getText().toString();
        int numberFinal = Integer.parseInt(number);
        if (numberFinal == result ) {
            resultWindow.setText("Правильно!");
            String counterPos = counterPositive.getText().toString();
            int counterPosInt = 1 + Integer.parseInt(counterPos);
            String counterPosWrite = Integer.toString(counterPosInt);
            counterPositive.setText(counterPosWrite);
            counterMain++;
            String numberAskString = numberAsk.getText().toString();
            int numberAskInt = 1 + Integer.parseInt(numberAskString);
            String numberAskCount = Integer.toString(numberAskInt);
            numberAsk.setText(numberAskCount);
            answerWindow.setText("");
            random(view);
        } else if (numberFinal != result ) {
            resultWindow.setText("Неправильно!");
            String counterNeg = counterNegative.getText().toString();
            int counterNegInt = 1 + Integer.parseInt(counterNeg);
            String counterPosWrite = Integer.toString(counterNegInt);
            counterNegative.setText(counterPosWrite);
            counterMain++;
            String numberAskString = numberAsk.getText().toString();
            int numberAskInt = 1 + Integer.parseInt(numberAskString);
            String numberAskCount = Integer.toString(numberAskInt);
            numberAsk.setText(numberAskCount);
            answerWindow.setText("");
            random(view);
        }

    }





    public void random(View view) {
        TextView example = findViewById(R.id.exerciseWindow);
        int random1 = new Random().nextInt(9);
        int random2 = new Random().nextInt(9);
        String numberOne = Integer.toString(random1);
        String numberTwo = Integer.toString(random2);
        result = random1 + random2;
        example.setText(numberOne + " + " + numberTwo);
    }


}