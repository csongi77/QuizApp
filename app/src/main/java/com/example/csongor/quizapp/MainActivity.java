package com.example.csongor.quizapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.example.csongor.quizapp.fragments.EvaluationFragment;
import com.example.csongor.quizapp.fragments.WelcomeFragment;
import com.example.csongor.quizapp.gamestates.EvaluationState;
import com.example.csongor.quizapp.gamestates.GameState;
import com.example.csongor.quizapp.gamestates.InGameState;
import com.example.csongor.quizapp.questionhelper.QuizQuestion;
import com.example.csongor.quizapp.questionhelper.QuizQuestionBuilder;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private int mGamePoints;
    private int mMaxPoints;
    private GameState mGameState;
    private android.support.v4.app.FragmentManager mFragmentManager;
    private TextView mRightButton, mLeftButton;
    private List<QuizQuestion> mQuestions;
    private Iterator<QuizQuestion> mQuestionIterator;
    private Fragment mCurrentFragment;
    private String mPlayerName;
    private AlertDialog.Builder mAlertDialogBuilder;
    private AlertDialog mAlertDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // assigning buttons. Later it's easier to use these variable names
        mRightButton = findViewById(R.id.right_button);
        mLeftButton = findViewById(R.id.left_button);

        // set up default game state
        if (mGameState == null) {
            mGameState = InGameState.getInstance(this);
            mLeftButton.setEnabled(false);
        }
        // loading question list and the iterator for it
        mQuestions = getQuizQuestions();
        mQuestionIterator = mQuestions.iterator();
        // getting the maximum available point values for the game
        mMaxPoints = mQuestions.size();
        //setting up mCurrentFragment manager
        mFragmentManager = MainActivity.this.getSupportFragmentManager();
        mCurrentFragment = new WelcomeFragment();
        // show welcome screen
        mFragmentManager.beginTransaction().add(R.id.fragment_container, mCurrentFragment).commit();
    }

    @Override
    protected void onStart() {
        super.onStart();

        // setting up listeners to buttons
        mRightButton.setOnClickListener(v -> {
            mGameState.doRightButtonAction();
        });
        mLeftButton.setOnClickListener(v -> {
            mGameState.doLeftButtonAction();
        });
    }


    /**
     * Taking care of finishing the game as appropriate.
     * If user presses the back button, they will get an alert to ensure that the game will be ended
     */
    @Override
    public void onBackPressed() {
        // alert dialog declaration if it's not end game state...
        if (mGameState.getClass() != EvaluationState.class) {
            mAlertDialogBuilder = new AlertDialog.Builder(this);
            mAlertDialog = mAlertDialogBuilder.setTitle(R.string.exit_header)
                    .setMessage(R.string.exit_alert)
                    .setPositiveButton(R.string.ok, (v, i) -> {
                        MainActivity.super.onBackPressed();
                    })
                    .setNegativeButton(R.string.cancel, (v, i) -> {
                        mAlertDialog.hide();
                    })
                    .create();
            mAlertDialog.show();
        } else {
            super.onBackPressed();
        }
    }

    /**
     * Getters and setters for private fields
     */
    public Iterator<QuizQuestion> getQuestionIterator() {
        return mQuestionIterator;
    }

    public int getMaxPoints() {
        return mMaxPoints;
    }

    public int getGamePoints() {
        return mGamePoints;
    }

    public Fragment getCurrentFragment() {
        return mCurrentFragment;
    }

    public void setCurrentFragment(Fragment currentFragment) {
        this.mCurrentFragment = currentFragment;
    }

    public String getPlayerName() {
        return mPlayerName;
    }

    // set player name
    public void setPlayerName(String playerName) {
        this.mPlayerName = playerName;
    }

    // set current GameState
    public void setGameState(GameState gameState) {
        this.mGameState = gameState;
    }

    /**
     * @param value to add this amount to game points
     */
    public void addPoints(int value) {
        this.mGamePoints += value;
    }


    // After finishing game the activity displays the result via Toast (as it was required) and evaluation mCurrentFragment
    public void evaluate() {
        Toast.makeText(this, getResources().getString(R.string.your_points) + this.mGamePoints, Toast.LENGTH_SHORT).show();
        mCurrentFragment = EvaluationFragment.newInstance(mGamePoints, mMaxPoints, mPlayerName);
        mFragmentManager.beginTransaction().
                replace(R.id.fragment_container, mCurrentFragment).
                commitNow();
    }

    /**
     * dummy List with quiz mQuestions. Later it can implemented to get mQuestions from an external
     * DB via REST...
     */
    private List<QuizQuestion> getQuizQuestions() {
        //creating quiz question list
        List<QuizQuestion> questionList = new ArrayList<QuizQuestion>();

        // first question (String question)
        // image By Photo: chil, on Camptocamp.orgDerivative work:Zacharie Grossen - Camptocamp.org, CC BY-SA 3.0, https://commons.wikimedia.org/w/index.php?curid=16791896
        QuizQuestion question_1 = QuizQuestionBuilder.getsInstance()
                .addImageResourceId(R.drawable.matterhorn)
                .addQuestionText("Who climbed first the Matterhorn?")
                .addAnswer("Edward Whymper", true)
                .build();

        // second question (Radio question)
        // image By Mike Murphy, CC BY-SA 3.0, https://commons.wikimedia.org/w/index.php?curid=295635
        QuizQuestion question_2 = QuizQuestionBuilder.getsInstance()
                .addImageResourceId(R.drawable.elcap)
                .addQuestionText("What is the name of Yosemite's favourite granite Monolith?")
                .addAnswer("El Capitan", true)
                .addAnswer("El Jefe", false)
                .addAnswer("The Captain", false)
                .addAnswer("The Boss", false)
                .build();

        //third question
        //By Uwe Gille (talk · contribs) - Own work, CC BY-SA 3.0, https://commons.wikimedia.org/w/index.php?curid=121222
        QuizQuestion question_3 = QuizQuestionBuilder.getsInstance()
                .addImageResourceId(R.drawable.everest)
                .addQuestionText("Who climbed first time the Mount Everest?")
                .addAnswer("Tenzing Norgay", true)
                .addAnswer("Hermann Buhl", false)
                .addAnswer("Sir Edmund Hillary", true)
                .addAnswer("Reinhold Messner", false)
                .build();

        //fourth question
        // By Guilhem Vellut from Paris - Glacier, CC BY-SA 2.0, https://commons.wikimedia.org/w/index.php?curid=4685304
        QuizQuestion question_4 = QuizQuestionBuilder.getsInstance()
                .addImageResourceId(R.drawable.eight_thousanders)
                .addQuestionText("Where areas can you find the Eight Thousanders (summits greater than 8000 meters peek)?")
                .addAnswer("Andes", false)
                .addAnswer("Himalayas", true)
                .addAnswer("Karakoram", true)
                .addAnswer("North American Cordillera", false)
                .build();

        // fifth question (Radio question)
        //By Tahsin Anwar Ali - Own work, CC BY-SA 3.0, https://commons.wikimedia.org/w/index.php?curid=40616492
        QuizQuestion question_5 = QuizQuestionBuilder.getsInstance()
                .addImageResourceId(R.drawable.nanga_parbat)
                .addQuestionText("The first ascent of Nanga Parbat in 1953 is achieved in solo! Who did it?")
                .addAnswer("Heinrich Harrer", false)
                .addAnswer("Pete Schoening", false)
                .addAnswer("Hermann Buhl", true)
                .addAnswer("Kurt Diemberger", false)
                .build();

        //adding mQuestions to list
        questionList.add(question_1);
        questionList.add(question_2);
        questionList.add(question_3);
        questionList.add(question_4);
        questionList.add(question_5);

        //returning the list of mQuestions
        return questionList;
    }
}
