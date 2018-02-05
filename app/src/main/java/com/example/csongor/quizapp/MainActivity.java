package com.example.csongor.quizapp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.LruCache;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    // variable declarations
    private static final int MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE=42;
    private int gamePoints;
    private int maxPoints;
    private GameState gameState;
    private android.support.v4.app.FragmentManager fragmentManager;
    private TextView rightButton, leftButton;
    private List<QuizQuestion> questions;
    private Iterator<QuizQuestion> questionIterator;
    private Fragment currentFragment;
    private String playerName;
    private AlertDialog.Builder alertDialogBuilder;
    private AlertDialog alertDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // set up default game state
        gameState=InGameState.getInstance(this);
        // loading question list and the iterator for it
        questions = getQuizQuestions();
        questionIterator = questions.iterator();
        // getting the maximum available point values for the game
        maxPoints=questions.size();
        //setting up currentFragment manager
        fragmentManager = MainActivity.this.getSupportFragmentManager();
        currentFragment = new WelcomeFragment();
        // show welcome screen
        fragmentManager.beginTransaction().add(R.id.fragment_container, currentFragment).commit();
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
            gameState.doRightButtonAction();
        });
        leftButton.setOnClickListener(v -> {
            gameState.doLeftButtonAction();
        });
    }


    /**
     * Taking care of finishing the game as appropriate.
     * If user presses the back button, it will get an alert to ensure that the game will be ended
     */
    @Override
    public void onBackPressed() {
        // alert dialog declaration if it's not end game state...
        if (gameState.getClass()!=EvaluationState.class) {
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


    public Iterator<QuizQuestion> getQuestionIterator() {
        return questionIterator;
    }

    // After finishing game the activity displays the result via Toast (as it was required) and evaluation currentFragment
    public void evaluate() {
        Toast.makeText(this, getResources().getString(R.string.your_points) + this.gamePoints, Toast.LENGTH_SHORT).show();
        currentFragment = EvaluationFragment.newInstance(gamePoints, maxPoints, playerName);
        fragmentManager.beginTransaction().replace(R.id.fragment_container, currentFragment).commitNow();
    }


    /**
     *
     * Getters and setters for private fields
     */

    public int getMaxPoints() {
        return maxPoints;
    }

    public int getGamePoints() {
        return gamePoints;
    }

    public Fragment getCurrentFragment() {
        return currentFragment;
    }

    public void setCurrentFragment(Fragment currentFragment) {
        this.currentFragment = currentFragment;
    }


    // set player name
    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    // set current GameState
    public void setGameState(GameState gameState) {
        this.gameState = gameState;
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
