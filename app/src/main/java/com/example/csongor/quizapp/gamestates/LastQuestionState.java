package com.example.csongor.quizapp.gamestates;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.widget.TextView;

import com.example.csongor.quizapp.MainActivity;
import com.example.csongor.quizapp.R;

/**
 * Created by csongor on 2/2/18.
 */

public class LastQuestionState implements GameState {
    // declaring variables
    private static final LastQuestionState LAST_QUESTION_STATE_INSTANCE = new LastQuestionState();
    private static MainActivity sMainActivity;
    private static TextView sLeftButton, sRightButton;
    private static FragmentManager sFragmentManager;
    private static Fragment sFragment;

    static LastQuestionState getInstance(MainActivity activity) {
        // initializing variables
        sMainActivity =activity;
        sLeftButton = sMainActivity.findViewById(R.id.left_button);
        sRightButton = sMainActivity.findViewById(R.id.right_button);
        sFragmentManager = sMainActivity.getSupportFragmentManager();
        return LAST_QUESTION_STATE_INSTANCE;
    }

    private LastQuestionState() {
    }

    /**
     * method for pressing right button
     */
    @Override
    public void doRightButtonAction() {
        // empty since it has no function for it
    }

    /**
     * method for pressing left button
     */
    @Override
    public void doLeftButtonAction() {
        sFragment = sMainActivity.getCurrentFragment();
        sFragmentManager.beginTransaction().detach(sFragment).commitNow();

        // calling evaluation
        sMainActivity.evaluate();

        // setting buttons for next state
        sRightButton.setText(R.string.publish);
        sLeftButton.setText(R.string.reset);
        sRightButton.setClickable(true);
        sRightButton.setEnabled(true);
        sLeftButton.setClickable(true);
        sLeftButton.setEnabled(true);

        // setting next state
        sMainActivity.setGameState(EvaluationState.getInstance(sMainActivity));
    }
}
