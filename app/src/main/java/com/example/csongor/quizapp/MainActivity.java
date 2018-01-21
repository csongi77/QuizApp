package com.example.csongor.quizapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }


    /**
     *  dummy List with quiz questions. Later it will delegated into quiz question list factory
     */
    private Iterator<QuizQuestion> getQuizQuestionIterator(){
        //creating quiz question list
        List<QuizQuestion> questionList=new ArrayList<QuizQuestion>();

        // first question (String question)
        AnswerOption answerOption_1_1=new Answer("Edward Whymper",true);
        List<AnswerOption> answerOptions_1=new ArrayList<AnswerOption>();
        answerOptions_1.add(answerOption_1_1);
        QuizQuestion question_1=new Question(R.drawable.matterhorn,"Who climbed first the Matterhorn?",answerOptions_1);

        // second question (Radio question)
        AnswerOption answerOption_2_1=new Answer("El Capitan", true);
        AnswerOption answerOption_2_2=new Answer("El Jefe", false);
        AnswerOption answerOption_2_3=new Answer("The Captain", false);
        AnswerOption answerOption_2_4=new Answer("The Boss", false);
        List<AnswerOption> answerOptions_2=new ArrayList<AnswerOption>();
        answerOptions_2.add(answerOption_2_1);
        answerOptions_2.add(answerOption_2_2);
        answerOptions_2.add(answerOption_2_3);
        answerOptions_2.add(answerOption_2_4);
        QuizQuestion question_2=new Question(R.drawable.elcap,"What is the name of Yosemite's favourite granite Monolith?", answerOptions_2);

        //third question
        AnswerOption answerOption_3_1=new Answer("Tenzing Norgay", true);
        AnswerOption answerOption_3_2=new Answer("Hermann Buhl", false);
        AnswerOption answerOption_3_3=new Answer("Sir Edmund Hillary", true);
        AnswerOption answerOption_3_4=new Answer("Reinhold Messner", false);
        List<AnswerOption> answerOptions_3=new ArrayList<AnswerOption>();
        answerOptions_2.add(answerOption_3_1);
        answerOptions_2.add(answerOption_3_2);
        answerOptions_2.add(answerOption_3_3);
        answerOptions_2.add(answerOption_3_4);
        QuizQuestion question_3=new Question(R.drawable.everest,"Who climbed first time the Mount Everest?", answerOptions_3);

        //fourth question
        AnswerOption answerOption_4_1=new Answer("Reinhold Messner",true);
        List<AnswerOption> answerOptions_4=new ArrayList<AnswerOption>();
        answerOptions_1.add(answerOption_1_1);
        QuizQuestion question_4=new Question(R.drawable.eight_thousanders,"Who climbed first the Mount Everest without additional oxygen?",answerOptions_1);

        //adding questions to list
        questionList.add(question_1);
        questionList.add(question_2);
        questionList.add(question_3);
        questionList.add(question_4);

        //returning the iterator of questions
        return  questionList.iterator();
    }
}
