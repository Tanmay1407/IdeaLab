package com.lnct.ac.in.idealab.quiz;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import com.lnct.ac.in.idealab.R;

import java.util.ArrayList;
import java.util.List;

public class QuestionActivity extends AppCompatActivity {
    RadioGroup radioGroup;
    TextView lblQuestion;
    RadioButton optionA;
    RadioButton optionB;
    RadioButton optionC;
    RadioButton optionD;
    Button confirm;
    String rightAnswer;
    String Answer;
    List<Question> questions;
    int score;
    TextView countDown;
    private long resendTime = 30;
    CountDownTimer countDownTimer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        confirm = findViewById(R.id.confirm);
        lblQuestion = findViewById(R.id.lblPergunta);
        optionA = findViewById(R.id.opcaoA);
        optionB = findViewById(R.id.opcaoB);
        optionC = findViewById(R.id.opcaoC);
        optionD = findViewById(R.id.opcaoD);
        countDown = findViewById(R.id.tvCountDown);
        score = 0;
        radioGroup = findViewById(R.id.radioGroup);

        questions = new ArrayList<Question>(){
            {
                add(new Question("Which one is NOT a part of four main components of android ?",
                        "D",
                        "Broadcast Receiver",
                        "Activities",
                        "Content Provider",
                        "Drawer"
                ));

                add(new Question("Which one is NOT a part of four main components of android ?",
                        "D",
                        "Broadcast Receiver",
                        "Activities",
                        "Content Provider",
                        "Drawer"
                ));

                add(new Question("Which one is NOT a part of four main components of android ?",
                        "D",
                        "Broadcast Receiver",
                        "Activities",
                        "Content Provider",
                        "Drawer"
                ));

                add(new Question("Which one is NOT a part of four main components of android ?",
                        "D",
                        "Broadcast Receiver",
                        "Activities",
                        "Content Provider",
                        "Drawer"
                ));
            }
        };
        startCountDownTimer();
        loadQuestion();


    }


    @Override
    protected void onRestart(){
        super.onRestart();
        loadQuestion();
    }


    private void loadQuestion(){
        countDownTimer.start();
        if(questions.size() > 0) {
            Question q = questions.remove(0);
            lblQuestion.setText(q.getQuestion());
            List<String> answers = q.getAnswers();

            optionA.setText(answers.get(0));
            optionB.setText(answers.get(1));
            optionC.setText(answers.get(2));
            optionD.setText(answers.get(3));

            rightAnswer = q.getRightAnswer();
        } else {
            countDownTimer.cancel();
            Intent intent = new Intent(this, ShowScoreActivity.class);
            intent.putExtra("score", score);
            finish();
            startActivity(intent);

        }
    }

    public void loadAnswer(View v) {
        countDownTimer.cancel();
        int op = radioGroup.getCheckedRadioButtonId();

        switch (op){
            case R.id.opcaoA:
                Answer="A";
                break;

            case R.id.opcaoB:
                Answer="B";
                break;

            case R.id.opcaoC:
                Answer="C";
                break;

            case R.id.opcaoD:
                Answer="D";
                break;

            default:
                Answer="X";


        }

        radioGroup.clearCheck();

        this.startActivity(isRightOrWrong(Answer));

    }

    private Intent isRightOrWrong(String Answer){
        Intent screen;
        if(Answer.equals(rightAnswer)) {
            this.score += 1;
            screen = new Intent(this, RightActivity.class);

        }else {
            screen = new Intent(this, WrongActivity.class);
        }

        return screen;
    }


    private void startCountDownTimer(){


        countDownTimer = new  CountDownTimer(resendTime * 1000,1000){
            @Override
            public void onTick(long l) {
                countDown.setText(String.valueOf (l / 1000));
            }

            @Override
            public void onFinish() {
                Toast.makeText(QuestionActivity.this, "Times UP!", Toast.LENGTH_SHORT).show();
                confirm.callOnClick();
            }
        };
    }
}
