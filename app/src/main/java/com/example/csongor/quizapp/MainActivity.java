package com.example.csongor.quizapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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

    // variable fields
    private int gameState;
    private int gamePoints;
    private int maxPoints;
    private android.support.v4.app.FragmentManager fragmentManager;
    private android.support.v4.app.FragmentTransaction transaction;
    private TextView rightButton, leftButton;
    private List<QuizQuestion> questions;
    private Iterator<QuizQuestion> questionIterator;
    Fragment fragment;
    private String playerName;
    AlertDialog.Builder alertDialogBuilder;
    AlertDialog alertDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // set up default game state
        gameState = IN_GAME_STATE;
        // loading question list and the iterator for it
        questions = getQuizQuestions();
        questionIterator = questions.iterator();
        // getting the maximum available point values for the game
        for (QuizQuestion question : questions
                ) {
            maxPoints += question.getMaxPoints();
        }
        //setting up fragment manager
        fragmentManager = MainActivity.this.getSupportFragmentManager();
        fragment = new WelcomeFragment();
        // show welcome screen
        fragmentManager.beginTransaction().add(R.id.fragment_container, fragment).commit();
    }

    @Override
    protected void onStart() {
        super.onStart();
        // assigning buttons. Later it's easier to use these variable names
        rightButton = (TextView) findViewById(R.id.right_button);
        leftButton = (TextView) findViewById(R.id.left_button);
        leftButton.setEnabled(false);
        // setting up listeners to buttons
        rightButton.setOnClickListener(v -> {
            Log.e("Main/ onStart", "------------CLICKED------------");
            doQuestionFragment();
        });
    }


    /**
     * Take care of finishing the game as appropriate.
     * If user presses the back button, it will get an alert to ensure that the game will be ended
     */
    @Override
    public void onBackPressed() {
        // alert dialog declaration
        alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialog = alertDialogBuilder.setTitle(R.string.exit_header)
                .setMessage(R.string.exit_alert)
                .setPositiveButton(R.string.ok, (v, i) -> {
                    MainActivity.super.onBackPressed();
                })
                .setNegativeButton(R.string.cancel, (v, i) -> {
                    alertDialog.hide();
                })
                .create();
        alertDialog.show();
    }


    // method for creating fragments for the questions
    private void doQuestionFragment() {
        if (this.gameState != LAST_QUESTION_STATE) {
            transaction = fragmentManager.beginTransaction();
            Question question;
            //get the next question
            question = (Question) questionIterator.next();
            Log.e("mainActivity: ", "the question type is: " + question.getQuestionType());
            // depending the question type the appropriate fragment will be used.
            // development suggestion: make this using Chain of Responsibility pattern to avoid multiple if statements
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
            // Replace whatever is in the fragment_container view with this fragment
            transaction.replace(R.id.fragment_container, fragment);
            // Add the transaction to the back stack if you want to step back to previous question.
            // This is depending on game type. If later you want to develop undo option, this would be
            // the entry point for it
            //transaction.addToBackStack(null);
            transaction.commit();

        }
        // change state if there are no more questions
        if (!questionIterator.hasNext()) {
            setGameState(LAST_QUESTION_STATE);
            // enabling evaluation button and disabling the next question button
            rightButton.setClickable(false);
            rightButton.setEnabled(false);
            leftButton.setClickable(true);
            leftButton.setEnabled(true);
            leftButton.setOnClickListener(v -> {
                fragmentManager.beginTransaction().detach(fragment).commitNow();
                evaluate();
            });
        }
    }

    // After finishing game the activity displays the result
    private void evaluate() {
        Toast.makeText(this, "Your points: " + this.gamePoints, Toast.LENGTH_SHORT).show();
        Log.d("evaluate", "maxpoints: " + maxPoints + ", playerpoints: " + gamePoints + " -------->");
        fragment = EvaluationFragment.newInstance(gamePoints, maxPoints, playerName);
        fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commitNow();
    }

    //get current gameState
    public int getGameState() {
        return gameState;
    }

    //set new gameState
    public void setGameState(int gameState) {
        this.gameState = gameState;
    }

    // get player name
    public String getPlayerName() {
        return playerName;
    }

    // set player name
    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    // return maximum available points for evaluation
    public int getMaxPoints() {
        return maxPoints;
    }

    /**
     * @param value to add this amount to game points
     */
    public void addPoints(int value) {
        this.gamePoints += value;
    }


    /**
     * dummy List with quiz questions. Later it will delegated into quiz question list factory
     * Maybe a Content provider could do this.
     */
    private List<QuizQuestion> getQuizQuestions() {
        //creating quiz question list
        List<QuizQuestion> questionList = new ArrayList<QuizQuestion>();

        // first question (String question)
        AnswerOption answerOption_1_1 = new Answer("Edward Whymper", true);
        List<AnswerOption> answerOptions_1 = new ArrayList<AnswerOption>();
        answerOptions_1.add(answerOption_1_1);
        QuizQuestion question_1 = new Question(R.drawable.matterhorn, "Who climbed first the Matterhorn?", answerOptions_1);


        // first.A question (String question)
        AnswerOption answerOption_1a_1 = new Answer("Reinhold Messner", true);
        List<AnswerOption> answerOptions_1a = new ArrayList<AnswerOption>();
        answerOptions_1a.add(answerOption_1a_1);
        QuizQuestion question_1a = new Question(R.drawable.eight_thousanders, "Who climbed first the Mount Everest without support oxygen?", answerOptions_1a);

        // second question (Radio question)
        AnswerOption answerOption_2_1 = new Answer("El Capitan", true);
        AnswerOption answerOption_2_2 = new Answer("El Jefe", false);
        AnswerOption answerOption_2_3 = new Answer("The Captain", false);
        AnswerOption answerOption_2_4 = new Answer("The Boss", false);
        List<AnswerOption> answerOptions_2 = new ArrayList<AnswerOption>();
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
        List<AnswerOption> answerOptions_3 = new ArrayList<AnswerOption>();
        answerOptions_3.add(answerOption_3_1);
        answerOptions_3.add(answerOption_3_2);
        answerOptions_3.add(answerOption_3_3);
        answerOptions_3.add(answerOption_3_4);
        QuizQuestion question_3 = new Question(R.drawable.everest, "Who climbed first time the Mount Everest?", answerOptions_3);

        //fourth question
        AnswerOption answerOption_3a_1 = new Answer("Andes", false);
        AnswerOption answerOption_3a_2 = new Answer("Himalayas", true);
        AnswerOption answerOption_3a_3 = new Answer("Karakoram", true);
        AnswerOption answerOption_3a_4 = new Answer("North American Cordillera", false);
        List<AnswerOption> answerOptions_3a = new ArrayList<AnswerOption>();
        answerOptions_3a.add(answerOption_3a_1);
        answerOptions_3a.add(answerOption_3a_2);
        answerOptions_3a.add(answerOption_3a_3);
        answerOptions_3a.add(answerOption_3a_4);
        QuizQuestion question_3a = new Question(R.drawable.eight_thousanders, "Where areas can you find the Eight Thousanders (summits greater than 8000 meters peek)?", answerOptions_3a);


        //adding questions to list
        questionList.add(question_1);
        //questionList.add(question_1a);
        questionList.add(question_2);
        questionList.add(question_3);
        questionList.add(question_3a);

        // questionList.add(question_4);

        for (int i = 0; i < questionList.size(); i++) {
            Log.e("main", "question no." + i + ". type: " + questionList.get(i).getQuestionType());
        }
        //returning the list of questions
        return questionList;
    }
}
