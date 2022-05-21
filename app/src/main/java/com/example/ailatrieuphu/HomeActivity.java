package com.example.ailatrieuphu;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView tvQuestion;
    private TextView tvContentQuestion;
    private TextView tvAnswer1,tvAnswer2,tvAnswer3,tvAnswer4;

    private List<Question> mListQuestion;
    private Question mQuestion;
    private int curentQuestion = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragmenthome);

        initUI();
        mListQuestion = getListQuestion();
        if(mListQuestion.isEmpty()){
            return;
        }
        setDataQuestion(mListQuestion.get(curentQuestion));
    }

    private void setDataQuestion(Question question) {
        if(question == null){
            return;
        }

        mQuestion = question;
        tvAnswer1.setBackgroundResource(R.drawable.bg_blue_corner30);
        tvAnswer2.setBackgroundResource(R.drawable.bg_blue_corner30);
        tvAnswer3.setBackgroundResource(R.drawable.bg_blue_corner30);
        tvAnswer4.setBackgroundResource(R.drawable.bg_blue_corner30);

        String titleQuestion = "Question" + question.getNumber();
        tvQuestion.setText(titleQuestion);
        tvContentQuestion.setText(question.getContent());
        tvAnswer1.setText(question.getListAnswer().get(0).getContent());
        tvAnswer2.setText(question.getListAnswer().get(1).getContent());
        tvAnswer3.setText(question.getListAnswer().get(2).getContent());
        tvAnswer4.setText(question.getListAnswer().get(3).getContent());

        tvAnswer1.setOnClickListener(this);
        tvAnswer2.setOnClickListener(this);
        tvAnswer3.setOnClickListener(this);
        tvAnswer4.setOnClickListener(this);
    }

    private void initUI(){
        tvQuestion = findViewById(R.id.tv_question);
        tvContentQuestion = findViewById(R.id.tv_content_question);
        tvAnswer1 = findViewById(R.id.tv_answer1);
        tvAnswer2 = findViewById(R.id.tv_answer2);
        tvAnswer3 = findViewById(R.id.tv_answer3);
        tvAnswer4 = findViewById(R.id.tv_answer4);
    }

    private  List<Question> getListQuestion() {
        List<Question> list=  new ArrayList<>();

        List<Answer> answerList1 = new ArrayList<>();
        answerList1.add(new Answer("147", false));
        answerList1.add(new Answer("148", false));
        answerList1.add(new Answer("149", true));
        answerList1.add(new Answer("150", false));

        List<Answer> answerList2 = new ArrayList<>();
        answerList2.add(new Answer("Việt Nam độc lập đồng minh", true));
        answerList2.add(new Answer("Đảng Cộng sản Việt Nam", false));
        answerList2.add(new Answer("Liên minh Việt Nam",false));
        answerList2.add(new Answer("Đảng Liên minh", false));

        List<Answer> answerList3 = new ArrayList<>();
        answerList3.add(new Answer("159", false));
        answerList3.add(new Answer("359", false));
        answerList3.add(new Answer("559", true));
        answerList3.add(new Answer("759", false));

        List<Answer> answerList4 = new ArrayList<>();
        answerList4.add(new Answer("1918", false));
        answerList4.add(new Answer("1920",true));
        answerList4.add(new Answer("1925",false));
        answerList4.add(new Answer("1930", false));

        list.add(new Question(1,"Việt Nam là thành viên thứ bao nhiêu của Liên hợp quốc?",answerList1));
        list.add(new Question(2,"Mặt trận Việt Minh có tên đầy đủ là gì?",answerList2));
        list.add(new Question(3,"Đường mòn Hồ Chí Minh có số hiệu là?",answerList3));
        list.add(new Question(3,"Bác Hồ tìm ra con đường cứu nước vào năm nào?",answerList4));

        return list;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_answer1:
                tvAnswer1.setBackgroundResource(R.drawable.bg_orange);
                checkAnswer(tvAnswer1,mQuestion,mQuestion.getListAnswer().get(0));
                break;

            case R.id.tv_answer2:
                tvAnswer2.setBackgroundResource(R.drawable.bg_orange);
                checkAnswer(tvAnswer2,mQuestion,mQuestion.getListAnswer().get(1));
                break;

            case R.id.tv_answer3:
                tvAnswer3.setBackgroundResource(R.drawable.bg_orange);
                checkAnswer(tvAnswer3,mQuestion,mQuestion.getListAnswer().get(2));
                break;

            case R.id.tv_answer4:
                tvAnswer4.setBackgroundResource(R.drawable.bg_orange);
                checkAnswer(tvAnswer4,mQuestion,mQuestion.getListAnswer().get(3));
                break;

        }
    }
    private void checkAnswer(TextView textView,Question question,Answer answer) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(answer.isCorrect()){
                    textView.setBackgroundResource(R.drawable.bg_green_corner30);
                    nextQuestion();
                } else {
                    textView.setBackgroundResource(R.drawable.bg_red_corner30);
                    showAnswerCorrect(question);
                    gameOver();
                }
            }

        }, 1000);

    }
    private void gameOver() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                showDialog("Game over");
            }
        }, 1000);
    }

    private void showAnswerCorrect(Question question) {
        if(question == null || question.getListAnswer() == null || question.getListAnswer().isEmpty()){
            return;
        }
        if (question.getListAnswer().get(0).isCorrect()){
            tvAnswer1.setBackgroundResource(R.drawable.bg_green_corner30);

        }else if (question.getListAnswer().get(1).isCorrect()){
            tvAnswer2.setBackgroundResource(R.drawable.bg_green_corner30);

        }else if (question.getListAnswer().get(2).isCorrect()){
            tvAnswer3.setBackgroundResource(R.drawable.bg_green_corner30);

        }else if (question.getListAnswer().get(3).isCorrect()){
            tvAnswer4.setBackgroundResource(R.drawable.bg_green_corner30);
        }
    }

    private void nextQuestion() {
        if(curentQuestion == mListQuestion.size()-1){
            showDialog("You win");
        }else {
            curentQuestion++;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    setDataQuestion(mListQuestion.get(curentQuestion));
                }
            }, 1000);

        }
    }
    private void showDialog( String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message);
        builder.setCancelable(false);

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                curentQuestion = 0;
                setDataQuestion(mListQuestion.get(curentQuestion));
                dialog.dismiss();
            }
        });
        AlertDialog alertDialog =  builder.create();
        alertDialog.show();

    }
}
