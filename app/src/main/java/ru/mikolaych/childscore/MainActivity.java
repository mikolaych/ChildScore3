package ru.mikolaych.childscore;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.FragmentTransaction;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
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
    private int numberOfExercise = 20;
    private int numberOfError = 3;
    private String numberAskCount;
    private String counterPosWrite;
    private String counterNegWrite;
    private CountDownTimer timer;
    private MediaPlayer backgroundMusic;
    private Boolean timerOn = true;
    private Boolean music = true;
    private FrameLayout frameLayout;
    private Boolean startFrame = true;
    OptionsFragment optionsFragment = new OptionsFragment();



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
        countDown(levelInt);
    }

                 //Опции(фрагмент)
    public void startFrame(View view) {
            ImageView optionsButton = findViewById(R.id.optionsButton);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (startFrame) {
            ft.replace(R.id.frame_layout, optionsFragment);
            ft.commit();
            startFrame = false;
            ft.addToBackStack(null);
        } else {
            ft.remove(optionsFragment);
            ft.commit();
            startFrame = true;
        }
    }

                    //Закрыть фрагмент
    public void closeFrame(View view) {
        Button closeFragmentButton = findViewById(R.id.closeFragmentButton);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.remove(optionsFragment);
        ft.commit();
        startFrame = true;
    }


    //Вкл/выкл счетчика
    public void countDownOnOff(View view){
        Switch countdownOnOff = findViewById(R.id.stopCount);
        if (countdownOnOff.isChecked()){
            timerOn = true;
            countDown(levelInt);
            timer.start();

        } else{
            timerOn = false;
            timer.cancel();
            countDown(0);
        }
    }

                            //СЧЕТЧИК
    public void countDown(int level) {
        TextView countDown = findViewById(R.id.countDown);
        int time = 0;
        switch (level){
            case 0: time = 90000000;
            case 1: time = 90000;
            break;
            case 6:
            case 2: time = 120000;
            break;
            case 3: time = 180000;
            break;

        }
        if (timerOn) {
            timer = new CountDownTimer(time, 1000) {
                @Override
                public void onTick(long l) {
                    countDown.setText("Осталось: " + l / 1000 + " сек");
                }

                @Override
                public void onFinish() {
                    countDown.setText("Время вышло!");
                    levelInt--;
                    levelControl();
                }
            }
                    .start();
        } else countDown.setText("Остановлен");

    }

       //Нажатие на кнопку Старт
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
                resultWindow.setTextColor(getResources().getColor(R.color.falseseText));
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

                     //Изменение уровня
            if (counterMain == numberOfExercise && counterNegativeWindow < numberOfError) {
                levelInt++;
                levelControl();
                random();
                resetParameters();
                Toast toastLevelUp = Toast.makeText(this, "Ура! Новый уровень!", Toast.LENGTH_LONG);
                toastLevelUp.setGravity(Gravity.TOP, 0, 0);
                toastLevelUp.show();

            } else if (counterNegativeWindow >= numberOfError) {
                levelInt--;
                levelControl();
                random();
                resetParameters();
                Toast toastLevelDown = Toast.makeText(this, "Плохо! Уровень снижен!", Toast.LENGTH_LONG);
                toastLevelDown.setGravity(Gravity.TOP, 0, 0);
                toastLevelDown.show();
            }
        }
    }

                //Сброс параметров
    public void resetParameters() {
        TextView counterPositive = findViewById(R.id.counterPositive);
        TextView counterNegative = findViewById(R.id.counterNegative);
        TextView numberAsk = findViewById(R.id.numberAsk);
        counterMain = 1;
        numberAsk.setText("1");
        counterPositive.setText("0");
        counterNegative.setText("0");
        counterPositiveWindow = 0;
        counterNegativeWindow = 0;

    }

                 //Генератор случайных чисел
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

                         //Контроль уровней
    public void levelControl() {
        ImageView star1 = findViewById(R.id.star1);
        ImageView star2 = findViewById(R.id.star2);
        ImageView star3 = findViewById(R.id.star3);
        switch (levelInt) {
            case 0:
            case 5:
                star1.setVisibility(View.GONE);
                star2.setVisibility(View.GONE);
                star3.setVisibility(View.GONE);
                Intent wrongActivity = new Intent(this, WrongActivity.class);
                startActivity(wrongActivity);
                this.finish();
                break;
            case 1:
                star1.setVisibility(View.VISIBLE);
                star2.setVisibility(View.GONE);
                star3.setVisibility(View.GONE);
                if (timerOn){
                timer.cancel();
                countDown(levelInt);
                }
                break;
            case 2:
                star1.setVisibility(View.VISIBLE);
                star2.setVisibility(View.VISIBLE);
                star3.setVisibility(View.GONE);
                if (timerOn){
                    timer.cancel();
                    countDown(levelInt);
                    }
                break;
            case 3:
                star1.setVisibility(View.VISIBLE);
                star2.setVisibility(View.VISIBLE);
                star3.setVisibility(View.VISIBLE);
                if (timerOn){
                    timer.cancel();
                    countDown(levelInt);
                   }
                break;
            case 4:
            case 7:
                star1.setVisibility(View.GONE);
                star2.setVisibility(View.GONE);
                star3.setVisibility(View.GONE);
                Intent winActivity = new Intent(this, WinActivity.class);
                startActivity(winActivity);
                this.finish();
                break;
            case 6:
                star1.setVisibility(View.GONE);
                star2.setVisibility(View.GONE);
                star3.setVisibility(View.GONE);
                if (timerOn){
                    timer.cancel();
                    countDown(6);
                }
                break;

        }

    }

                    //Переключатель умножения
    public void switchRun(View view) {
        Switch multiply = findViewById(R.id.tableMultiply);
        if (multiply.isChecked()) {
            tableMultiplyStatus = true;
            levelInt = 6;
            numberOfExercise = 40;
            random();
            levelControl();
            resetParameters();
        } else {
            tableMultiplyStatus = false;
            levelInt = 1;
            numberOfExercise = 20;
            random();
            levelControl();
            resetParameters();
        }
    }

                    //Сохранение данных
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("counterPosWrite", counterPosWrite);
        outState.putString("counterNegWrite", counterNegWrite);
        outState.putString("numberAskCount", numberAskCount);
        outState.putInt("levelInt", levelInt);

    }

                        //Фоновая музыка
    public void music(View view) {
        CheckBox sound = findViewById(R.id.sound);
        if (sound.isChecked()){
            music = true;
        } else {
            music = false;
            backgroundMusic.stop();
        }
        playMusic();

    }

    public void playMusic() {
        backgroundMusic = MediaPlayer.create(this, R.raw.music);
        if (music) {
            backgroundMusic.start();
            backgroundMusic.setLooping(true);
        }
    }

                            //Справка

    public void openSupport(View view) {
        ImageView supportButton = findViewById(R.id.supportButton);
        Intent support = new Intent(this, SupportActivity.class);
        startActivity(support);
    }

                            //Меню
   /* @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.add(0, 0, 0, "Справка");
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==0){
            Intent support = new Intent(this, SupportActivity.class);
            startActivity(support);
            finish();

        }
        return super.onOptionsItemSelected(item);
    }*/

                            //OnStartMethod

    @Override
    protected void onStart() {
        super.onStart();
        playMusic();
        timer.cancel();
        countDown(levelInt);
    }

                            //OnPause

    @Override
    protected void onPause() {
        super.onPause();
        backgroundMusic.stop();
        timer.cancel();
    }


}