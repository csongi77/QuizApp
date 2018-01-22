package com.example.csongor.quizapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //game states
    public static final int IN_GAME_STATE = 0;
    public static final int UNDO_STATE = 1;
    public static final int UNDO_STACK_IS_EMPTY_STATE = 2;
    public static final int REDO_STATE = 3;
    public static final int LAST_QUESTION_STATE = 4;
    public static final int EVALUATION_STATE = 5;

    private int gameState;
    private int gamePoints;
    private android.support.v4.app.FragmentManager fragmentManager;
    private android.support.v4.app.FragmentTransaction transaction;
    private TextView rightButton;
    private List<QuizQuestion> questions;
    private Iterator<QuizQuestion> questionIterator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        questions = getQuizQuestions();
        questionIterator = questions.iterator();


    }

    @Override
    protected void onStart() {
        super.onStart();
        rightButton = (TextView) findViewById(R.id.right_button);
        rightButton.setOnClickListener(v -> {Log.e("Main/ onStart","------------CLICKED------------");doit();});
    }

    private void doit() {

        if (questionIterator.hasNext()) {
            fragmentManager = MainActivity.this.getSupportFragmentManager();
            transaction = fragmentManager.beginTransaction();
            Fragment fragment;
            Question question;
            question = (Question)questionIterator.next();
            Log.e("mainActivity: ", "the question type is: " + question.getQuestionType());
            if (question.getQuestionType() == QuizQuestion.STRING_QUESTION) {
                fragment = StringQuestionFragment.newInstance(question);
                Log.e("mainActivity: ", "stringQuestion fragment has instantinated: ");
            } else if (question.getQuestionType() == QuizQuestion.RADIO_QUESTION) {
                fragment = RadioQuestionFragment.newInstance(question);
                Log.e("mainActivity: ", "radioQuestion fragment has instantinated: ");
            } else if (question.getQuestionType() == QuizQuestion.CHECKBOX_QUESTION) {
                fragment = CheckboxQuestionFragment.newInstance(question);
                Log.e("mainActivity: ", "checkBox fragment has instantinated: ");
            } else {
                fragment = new Fragment();
            }


            // Replace whatever is in the fragment_container view with this fragment,
            // and add the transaction to the back stack
            transaction.replace(R.id.fragment_container, fragment);
            //transaction.addToBackStack(null);
            transaction.commit();
        } else {
            MainActivity.this.evaluate();
        }
    }

    // After finishing game the activity displays the result
    private void evaluate() {
        Toast.makeText(this, "Your points: " + this.gamePoints, Toast.LENGTH_SHORT).show();
    }

    //get current gameState
    public int getGameState() {
        return gameState;
    }

    //set new gameState
    public void setGameState(int gameState) {
        this.gameState = gameState;
    }


    /**
     * @param value to add this amount to game points
     */
    public void addPoints(int value) {
        this.gamePoints += value;
    }


    /**
     * dummy List with quiz questions. Later it will delegated into quiz question list factory
     */
    private ArrayList<QuizQuestion> getQuizQuestions() {
        //creating quiz question list
        ArrayList<QuizQuestion> questionList = new ArrayList<QuizQuestion>();

        // first question (String question)
        AnswerOption answerOption_1_1 = new Answer("Edward Whymper", true);
        ArrayList<AnswerOption> answerOptions_1 = new ArrayList<AnswerOption>();
        answerOptions_1.add(answerOption_1_1);
        QuizQuestion question_1 = new Question(R.drawable.matterhorn, "Who climbed first the Matterhorn?", answerOptions_1);


        // first.A question (String question)
        AnswerOption answerOption_1a_1 = new Answer("Reinhold Messner", true);
        ArrayList<AnswerOption> answerOptions_1a = new ArrayList<AnswerOption>();
        answerOptions_1a.add(answerOption_1a_1);
        QuizQuestion question_1a = new Question(R.drawable.eight_thousanders, "Who climbed first the Mount Everest without support oxygen?", answerOptions_1a);

        // second question (Radio question)
        AnswerOption answerOption_2_1 = new Answer("El Capitan", true);
        AnswerOption answerOption_2_2 = new Answer("El Jefe", false);
        AnswerOption answerOption_2_3 = new Answer("The Captain", false);
        AnswerOption answerOption_2_4 = new Answer("The Boss", false);
        ArrayList<AnswerOption> answerOptions_2 = new ArrayList<AnswerOption>();
        answerOptions_2.add(answerOption_2_1);
        answerOptions_2.add(answerOption_2_2);
        answerOptions_2.add(answerOption_2_3);
        answerOptions_2.add(answerOption_2_4);
        QuizQuestion question_2 = new Question(R.drawable.elcap, "What is the name of Yosemite's favourite granite Monolith?", answerOptions_2);


        //third question
        AnswerOption answerOption_3_1 = new Answer("Tenzing Norgay", true);
        AnswerOption answerOption_3_2 = new Answer("Hermann Buhl", false);
        AnswerOption answerOption_3_3 = new Answer("Sir Edmund Hillary", true);
        AnswerOption answerOption_3_4 = new Answer("Reinhold Messner", false);
        ArrayList<AnswerOption> answerOptions_3 = new ArrayList<AnswerOption>();
        answerOptions_3.add(answerOption_3_1);
        answerOptions_3.add(answerOption_3_2);
        answerOptions_3.add(answerOption_3_3);
        answerOptions_3.add(answerOption_3_4);
        QuizQuestion question_3 = new Question(R.drawable.everest, "Who climbed first time the Mount Everest?", answerOptions_3);

        //third question_A
        AnswerOption answerOption_3a_1 = new Answer("Reinhold Messner", false);
        AnswerOption answerOption_3a_2 = new Answer("Tenzing Norgay", true);
        AnswerOption answerOption_3a_3 = new Answer("Sir Edmund Hillary", true);
        AnswerOption answerOption_3a_4 = new Answer("Reinhold Messner", false);
        ArrayList<AnswerOption> answerOptions_3a = new ArrayList<AnswerOption>();
        answerOptions_3a.add(answerOption_3a_1);
        answerOptions_3a.add(answerOption_3a_2);
        answerOptions_3a.add(answerOption_3a_3);
        answerOptions_3a.add(answerOption_3a_4);
        QuizQuestion question_3a = new Question(R.drawable.eight_thousanders, "Who climbed first time the Mount Everest?", answerOptions_3a);

        //fourth question
        AnswerOption answerOption_4_1 = new Answer("Reinhold Messner", true);
        ArrayList<AnswerOption> answerOptions_4 = new ArrayList<AnswerOption>();
        answerOptions_1.add(answerOption_4_1);
        QuizQuestion question_4 = new Question(R.drawable.eight_thousanders, "Who climbed first the Mount Everest without additional oxygen?", answerOptions_4);

        //adding questions to list
        questionList.add(question_1);
        questionList.add(question_1a);
        questionList.add(question_3);
        //questionList.add(question_3a);
        questionList.add(question_2);
       // questionList.add(question_4);

        for (int i = 0; i < questionList.size(); i++) {
            Log.e("main", "question no." + i + ". type: " + questionList.get(i).getQuestionType());
        }
        //returning the list of questions
        return questionList;
    }
}
