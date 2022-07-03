package ru.mikolaych.childscore;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private Integer result;
    private String answerNumber;
    private int counterPositive;
    private int counterNegative;
    private int counterMain = 0;
    private int level;
    private int counterPositiveWindow;
    private int counterNegativeWindow;
    private Boolean tableMultiplyStatus = false;

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
        TextView level = findViewById(R.id.level);
        String ifNull = answerWindow.getText().toString();
        if (ifNull.length() < 1){
            resultWindow.setText("Введи число!!!");
        } else {
            String number = answerWindow.getText().toString();
            int numberFinal = Integer.parseInt(number);
            if (numberFinal == result) {
                resultWindow.setText("Правильно!");
                resultWindow.setTextColor(getResources().getColor(R.color.negativeText));
                String counterPos = counterPositive.getText().toString();
                int counterPosInt = 1 + Integer.parseInt(counterPos);
                counterPositiveWindow = counterPosInt;
                String counterPosWrite = Integer.toString(counterPosInt);
                counterPositive.setText(counterPosWrite);
                counterMain++;
                String numberAskString = numberAsk.getText().toString();
                int numberAskInt = 1 + Integer.parseInt(numberAskString);
                String numberAskCount = Integer.toString(numberAskInt);
                numberAsk.setText(numberAskCount);
                answerWindow.setText("");
                answerWindow.setHint("");
                random(view);
            } else if (numberFinal != result) {
                resultWindow.setText("Неправильно!");
                resultWindow.setTextColor(getResources().getColor(R.color.exerciseText));
                String counterNeg = counterNegative.getText().toString();
                int counterNegInt = 1 + Integer.parseInt(counterNeg);
                counterNegativeWindow = counterNegInt;
                String counterPosWrite = Integer.toString(counterNegInt);
                counterNegative.setText(counterPosWrite);
                counterMain++;
                String numberAskString = numberAsk.getText().toString();
                int numberAskInt = 1 + Integer.parseInt(numberAskString);
                String numberAskCount = Integer.toString(numberAskInt);
                numberAsk.setText(numberAskCount);
                answerWindow.setText("");
                answerWindow.setHint("");
                random(view);
            }

            if (counterMain == 20) {
                if ((counterPositiveWindow - counterNegativeWindow) >= 18) {
                    String levelRead = level.getText().toString();
                    int levelInt = 1 + Integer.parseInt(levelRead);
                    Toast toastLevelUp = Toast.makeText(this, "Ура! Новый уровень!", Toast.LENGTH_LONG);
                    toastLevelUp.setGravity(Gravity.TOP, 0, 0);
                    toastLevelUp.show();
                    String levelWrite = Integer.toString(levelInt);
                    level.setText(levelWrite);
                    counterMain = 1;
                    numberAsk.setText("1");
                    counterPositive.setText("0");
                    counterNegative.setText("0");
                    counterPositiveWindow = 0;
                    counterNegativeWindow = 0;
                } else {
                    String levelRead = level.getText().toString();
                    int levelInt = Integer.parseInt(levelRead) - 1;
                    Toast toastLevelDown = Toast.makeText(this, "Плохо! Уровень снижен!", Toast.LENGTH_LONG);
                    toastLevelDown.setGravity(Gravity.TOP, 0, 0);
                    toastLevelDown.show();
                    String levelWrite = Integer.toString(levelInt);
                    level.setText(levelWrite);
                    counterMain = 1;
                    numberAsk.setText("1");
                    counterPositive.setText("0");
                    counterNegative.setText("0");
                    counterPositiveWindow = 0;
                    counterNegativeWindow = 0;
                }
            }
        }
    }

    public void random(View view) {
        TextView level = findViewById(R.id.level);
        TextView example = findViewById(R.id.exerciseWindow);
        TextView resultWindow = findViewById(R.id.resultWindow);
        String lev = level.getText().toString();
        if (tableMultiplyStatus == false) {
            int limit1 = 0;
            int limit2 = 0;
            switch (lev) {
                case "0":
                    Toast toastWrong = Toast.makeText(this, "Позор! Ты проиграл!", Toast.LENGTH_LONG);
                    toastWrong.setGravity(Gravity.TOP, 0, 0);
                    toastWrong.show();
                    level.setText("1");
                case "1":
                    limit1 = 9;
                    limit2 = 1;
                    break;
                case "2":
                    limit1 = 10;
                    limit2 = 10;
                    break;
                case "3":
                    limit1 = 30;
                    limit2 = 20;
                    break;
                case "4":
                    Toast toastWin = Toast.makeText(this, "Поздравляем! Ты Выиграл!", Toast.LENGTH_LONG);
                    toastWin.setGravity(Gravity.TOP, 0, 0);
                    toastWin.show();
                    level.setText("1");
                    limit1 = 9;
                    limit2 = 1;
            }
            int randomPlusMinus = new Random().nextInt(100);
            int random1 = new Random().nextInt(limit1) + limit2;
            int random2 = new Random().nextInt(limit1) + limit2;
            int flag = 0;
            if ((randomPlusMinus % 2) == 0) {
                result = random1 + random2;
                flag = 1;
            } else {
                if (random1 >= random2) {
                    result = random1 - random2;
                    flag = 2;
                } else {
                    result = random2 - random1;
                    flag = 3;
                }
            }
            String numberOne = Integer.toString(random1);
            String numberTwo = Integer.toString(random2);
            switch (flag) {
                case 1:
                    example.setText(numberOne + " + " + numberTwo);
                    break;
                case 2:
                    example.setText(numberOne + " - " + numberTwo);
                    break;
                case 3:
                    example.setText(numberTwo + " - " + numberOne);
                    break;
            }
        } else{
                int random3 = new Random().nextInt(9);
                int random4 = new Random().nextInt(9);
                result = random3 * random4;
                String numberThree = Integer.toString(random3);
                String numberFour = Integer.toString(random4);
                example.setText(numberThree + " x " + numberFour);
        }
    }

    public void switchRun(View view) {
        boolean checked = ((Switch)view).isChecked();
        if(checked){
            tableMultiplyStatus = true;
        } else {
            tableMultiplyStatus = false;
        }
    }
}