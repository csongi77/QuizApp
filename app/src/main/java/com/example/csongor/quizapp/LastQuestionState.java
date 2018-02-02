package com.example.csongor.quizapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.widget.TextView;

/**
 * Created by csongor on 2/2/18.
 */

class LastQuestionState implements GameState {
    // declaring variables
    private static final LastQuestionState ourInstance = new LastQuestionState();
    private static MainActivity mainActivity;
    private static TextView leftButton, rightButton;
    private static FragmentManager fragmentManager;
    private static Fragment fragment;

    static LastQuestionState getInstance(MainActivity activity) {
        // setting up variables
        mainActivity=activity;
        leftButton= mainActivity.findViewById(R.id.left_button);
        rightButton= mainActivity.findViewById(R.id.right_button);
        fragmentManager=mainActivity.getSupportFragmentManager();
        return ourInstance;
    }

    private LastQuestionState() {
    }

    /**
     * method for pressing right button
     */
    @Override
    public void doRightButtonAction() {

    }

    /**
     * method for pressing left button
     */
    @Override
    public void doLeftButtonAction() {
        fragment=mainActivity.getCurrentFragment();
        fragmentManager.beginTransaction().detach(fragment).commitNow();
        mainActivity.evaluate();
        rightButton.setText(R.string.publish);
        leftButton.setText(R.string.reset);
        rightButton.setClickable(true);
        rightButton.setEnabled(true);
        leftButton.setClickable(true);
        leftButton.setEnabled(true);
        mainActivity.setGameState(EvaluationState.getInstance(mainActivity));
    }
}
