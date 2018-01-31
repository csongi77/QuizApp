package com.example.csongor.quizapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //game states
    public static final int IN_GAME_STATE = 0;
    public static final int LAST_QUESTION_STATE = 1;
    public static final int EVALUATION_STATE = 2;

    // variable declarations
    private int gameState;
    private int gamePoints;
    private int maxPoints;
    private android.support.v4.app.FragmentManager fragmentManager;
    private android.support.v4.app.FragmentTransaction transaction;
    private TextView rightButton, leftButton;
    private List<QuizQuestion> questions;
    private Iterator<QuizQuestion> questionIterator;
    private Fragment fragment;
    private String playerName;
    private AlertDialog.Builder alertDialogBuilder;
    private AlertDialog alertDialog;


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
        maxPoints=questions.size();
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
        rightButton = findViewById(R.id.right_button);
        leftButton = findViewById(R.id.left_button);
        leftButton.setEnabled(false);
        // setting up listeners to buttons
        rightButton.setOnClickListener(v -> {
            doQuestionFragment();
        });
    }


    /**
     * Taking care of finishing the game as appropriate.
     * If user presses the back button, it will get an alert to ensure that the game will be ended
     */
    @Override
    public void onBackPressed() {
        // alert dialog declaration if it's not end game state...
        if (gameState != EVALUATION_STATE) {
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
        } else {
            super.onBackPressed();
        }
    }


    // method for creating fragments for the questions
    private void doQuestionFragment() {
        if (this.gameState != LAST_QUESTION_STATE) {
            transaction = fragmentManager.beginTransaction();
            QuizQuestion question;
            //get the next question
            question = questionIterator.next();
            // depending the question type the appropriate fragment will be used.
            // development suggestion: make this using Chain of Responsibility pattern to avoid multiple if statements
            if (question.getQuestionType() == QuizQuestion.STRING_QUESTION) {
                fragment = StringQuestionFragment.newInstance(question);
            } else if (question.getQuestionType() == QuizQuestion.RADIO_QUESTION) {
                fragment = RadioQuestionFragment.newInstance(question);
            } else if (question.getQuestionType() == QuizQuestion.CHECKBOX_QUESTION) {
                fragment = CheckboxQuestionFragment.newInstance(question);
            } else {
                fragment = new Fragment();
            }
            // Replace whatever is in the fragment_container view with this fragment
            transaction.replace(R.id.fragment_container, fragment);
            transaction.commit();
        }
        // change state if there are no more questions, evaluation will be available
        if (!questionIterator.hasNext()) {
            setGameState(LAST_QUESTION_STATE);
            // enabling evaluation button and disabling the next question button
            rightButton.setClickable(false);
            rightButton.setEnabled(false);
            leftButton.setClickable(true);
            leftButton.setEnabled(true);
            // setting up clickListener for the left button
            leftButton.setOnClickListener(v -> {
                fragmentManager.beginTransaction().detach(fragment).commitNow();
                evaluate();
                setGameState(EVALUATION_STATE);
            });
        }
    }

    // After finishing game the activity displays the result via Toast (as it was required) and evaluation fragment
    private void evaluate() {
        Toast.makeText(this, getResources().getString(R.string.your_points) + this.gamePoints, Toast.LENGTH_SHORT).show();
        fragment = EvaluationFragment.newInstance(gamePoints, maxPoints, playerName);
        fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commitNow();
    }

    //set new gameState
    public void setGameState(int gameState) {
        this.gameState = gameState;
    }

    // set player name
    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }


    /**
     * @param value to add this amount to game points
     */
    public void addPoints(int value) {
        this.gamePoints += value;
    }


    /**
     * dummy List with quiz questions. Later it can implemented to get questions from an external
     * DB via REST...
     */
    private List<QuizQuestion> getQuizQuestions() {
        //creating quiz question list
        List<QuizQuestion> questionList = new ArrayList<QuizQuestion>();

        // first question (String question)
        // image By Photo: chil, on Camptocamp.orgDerivative work:Zacharie Grossen - Camptocamp.org, CC BY-SA 3.0, https://commons.wikimedia.org/w/index.php?curid=16791896
        QuizQuestion question_1=QuizQuestionBuilder.getInstance()
                .addImageResourceId(R.drawable.matterhorn)
                .addQuestionText("Who climbed first the Matterhorn?")
                .addAnswer("Edward Whymper",true)
                .build();

        // second question (Radio question)
        // image By Mike Murphy, CC BY-SA 3.0, https://commons.wikimedia.org/w/index.php?curid=295635
        QuizQuestion question_2=QuizQuestionBuilder.getInstance()
                .addImageResourceId(R.drawable.elcap)
                .addQuestionText("What is the name of Yosemite's favourite granite Monolith?")
                .addAnswer("El Capitan", true)
                .addAnswer("El Jefe", false)
                .addAnswer("The Captain", false)
                .addAnswer("The Boss", false)
                .build();

        //third question
        //By Uwe Gille (talk · contribs) - Own work, CC BY-SA 3.0, https://commons.wikimedia.org/w/index.php?curid=121222
        QuizQuestion question_3=QuizQuestionBuilder.getInstance()
                .addImageResourceId(R.drawable.everest)
                .addQuestionText("Who climbed first time the Mount Everest?")
                .addAnswer("Tenzing Norgay", true)
                .addAnswer("Hermann Buhl", false)
                .addAnswer("Sir Edmund Hillary", true)
                .addAnswer("Reinhold Messner", false)
                .build();

        //fourth question
        // By Guilhem Vellut from Paris - Glacier, CC BY-SA 2.0, https://commons.wikimedia.org/w/index.php?curid=4685304
        QuizQuestion question_4=QuizQuestionBuilder.getInstance()
                .addImageResourceId(R.drawable.eight_thousanders)
                .addQuestionText("Where areas can you find the Eight Thousanders (summits greater than 8000 meters peek)?")
                .addAnswer("Andes", false)
                .addAnswer("Himalayas", true)
                .addAnswer("Karakoram", true)
                .addAnswer("North American Cordillera", false)
                .build();

        // fifth question (Radio question)
        //By Tahsin Anwar Ali - Own work, CC BY-SA 3.0, https://commons.wikimedia.org/w/index.php?curid=40616492
        QuizQuestion question_5=QuizQuestionBuilder.getInstance()
                .addImageResourceId(R.drawable.nanga_parbat)
                .addQuestionText("The first ascent of Nanga Parbat in 1953 is achieved in solo! Who did it?")
                .addAnswer("Heinrich Harrer", false)
                .addAnswer("Pete Schoening", false)
                .addAnswer("Hermann Buhl", true)
                .addAnswer("Kurt Diemberger", false)
                .build();

        //adding questions to list
        questionList.add(question_1);
        questionList.add(question_2);
        questionList.add(question_3);
        questionList.add(question_4);
        questionList.add(question_5);

        //returning the list of questions
        return questionList;
    }
}
