package ru.mikolaych.childscore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private Integer result;
    private int counterMain = 0;
    private int counterPositiveWindow;
    private int counterNegativeWindow;
    private Boolean tableMultiplyStatus = false;
    private int levelInt = 1;
    String numberAskCount;
    String counterPosWrite;
    String counterNegWrite;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        setContentView(R.layout.activity_main);

        TextView numberAsk = findViewById(R.id.numberAsk);
        TextView counterPositive = findViewById(R.id.counterPositive);
        TextView counterNegative = findViewById(R.id.counterNegative);

        if (savedInstanceState != null) {
            numberAskCount = savedInstanceState.getString("numberAskCount", "1");
            counterPosWrite = savedInstanceState.getString("counterPosWrite", "0");
            counterNegWrite = savedInstanceState.getString("counterNegWrite", "0");
            levelInt = savedInstanceState.getInt("levelInt", 1);
            numberAsk.setText(numberAskCount);
            counterPositive.setText(counterPosWrite);
            counterNegative.setText(counterNegWrite);
            levelControl();
            random();
        }
        random();
    }

    public void answer(View view) {
        EditText answerWindow = findViewById(R.id.answerWindow);
        TextView resultWindow = findViewById(R.id.resultWindow);
        TextView counterPositive = findViewById(R.id.counterPositive);
        TextView counterNegative = findViewById(R.id.counterNegative);
        TextView numberAsk = findViewById(R.id.numberAsk);

        String ifNull = answerWindow.getText().toString();
        if (ifNull.length() < 1) {
            resultWindow.setText("Введи число!!!");
        } else {
            String number = answerWindow.getText().toString();
            int numberFinal = Integer.parseInt(number);
            if (numberFinal == result) {
                resultWindow.setText("Правильно!");
                resultWindow.setTextColor(getResources().getColor(R.color.trueText));
                String counterPos = counterPositive.getText().toString();
                int counterPosInt = 1 + Integer.parseInt(counterPos);
                counterPositiveWindow = counterPosInt;
                counterPosWrite = Integer.toString(counterPosInt);
                counterPositive.setText(counterPosWrite);
                counterMain++;
                String numberAskString = numberAsk.getText().toString();
                int numberAskInt = 1 + Integer.parseInt(numberAskString);
                numberAskCount = Integer.toString(numberAskInt);
                numberAsk.setText(numberAskCount);
                answerWindow.setText("");
                answerWindow.setHint("");
                random();
            } else {
                resultWindow.setText("Неправильно!");
                resultWindow.setTextColor(getResources().getColor(R.color.exerciseText));
                String counterNeg = counterNegative.getText().toString();
                int counterNegInt = 1 + Integer.parseInt(counterNeg);
                counterNegativeWindow = counterNegInt;
                counterNegWrite = Integer.toString(counterNegInt);
                counterNegative.setText(counterNegWrite);
                counterMain++;
                String numberAskString = numberAsk.getText().toString();
                int numberAskInt = 1 + Integer.parseInt(numberAskString);
                numberAskCount = Integer.toString(numberAskInt);
                numberAsk.setText(numberAskCount);
                answerWindow.setText("");
                answerWindow.setHint("");
                random();
            }

            if (counterMain == 20) {
                if (((counterPositiveWindow - counterNegativeWindow) >= 18) && levelInt != 4) {
                    levelInt++;
                    levelControl();
                    random();
                    Toast toastLevelUp = Toast.makeText(this, "Ура! Новый уровень!", Toast.LENGTH_LONG);
                    toastLevelUp.setGravity(Gravity.TOP, 0, 0);
                    toastLevelUp.show();
                    counterMain = 1;
                    numberAsk.setText("1");
                    counterPositive.setText("0");
                    counterNegative.setText("0");
                    counterPositiveWindow = 0;
                    counterNegativeWindow = 0;
                } else if (((counterPositiveWindow - counterNegativeWindow) < 18) && levelInt != 0) {

                    levelInt--;
                    levelControl();
                    random();
                    Toast toastLevelDown = Toast.makeText(this, "Плохо! Уровень снижен!", Toast.LENGTH_LONG);
                    toastLevelDown.setGravity(Gravity.TOP, 0, 0);
                    toastLevelDown.show();
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

    @SuppressLint("SetTextI18n")
    public void random() {
        TextView example = findViewById(R.id.exerciseWindow);
        if (!tableMultiplyStatus) {
            int limit1 = 0;
            int limit2 = 0;
            switch (levelInt) {
                case 1:
                    limit1 = 9;
                    limit2 = 1;
                    break;
                case 2:
                    limit1 = 10;
                    limit2 = 10;
                    break;
                case 3:
                    limit1 = 30;
                    limit2 = 20;
                    break;

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
        } else {
            int random3 = new Random().nextInt(9);
            int random4 = new Random().nextInt(9);
            result = random3 * random4;
            String numberThree = Integer.toString(random3);
            String numberFour = Integer.toString(random4);
            example.setText(numberThree + " x " + numberFour);
        }
    }

    public void levelControl(){
        ImageView star1 = findViewById(R.id.star1);
        ImageView star2 = findViewById(R.id.star2);
        ImageView star3 = findViewById(R.id.star3);
        ImageView win = findViewById(R.id.win);
        switch (levelInt) {
            case 0:
                star1.setVisibility(View.GONE);
                star2.setVisibility(View.GONE);
                star3.setVisibility(View.GONE);
                win.setVisibility(View.GONE);
                levelInt = 1;
                Intent wrongActivity = new Intent(this, WrongActivity.class);
                startActivity(wrongActivity);
                break;
            case 1:
                star1.setVisibility(View.VISIBLE);
                star2.setVisibility(View.GONE);
                star3.setVisibility(View.GONE);
                win.setVisibility(View.GONE);
                break;
            case 2:
                star1.setVisibility(View.VISIBLE);
                star2.setVisibility(View.VISIBLE);
                star3.setVisibility(View.GONE);
                win.setVisibility(View.GONE);
                break;
            case 3:
                star1.setVisibility(View.VISIBLE);
                star2.setVisibility(View.VISIBLE);
                star3.setVisibility(View.VISIBLE);
                win.setVisibility(View.GONE);
                break;
            case 4:
                star1.setVisibility(View.GONE);
                star2.setVisibility(View.GONE);
                star3.setVisibility(View.GONE);
                win.setVisibility(View.VISIBLE);
                levelInt = 1;
                Intent winActivity = new Intent(this, WinActivity.class);
                startActivity(winActivity);
                break;
        }

    }

    public void switchRun(View view) {
        boolean checked = ((Switch) view).isChecked();
        if (checked) {
            tableMultiplyStatus = true;
            levelInt = 1;
        } else {
            tableMultiplyStatus = false;
            levelInt = 1;
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("counterPosWrite", counterPosWrite);
        outState.putString("counterNegWrite", counterNegWrite);
        outState.putString("numberAskCount", numberAskCount);
        outState.putInt("levelInt", levelInt);

    }

}