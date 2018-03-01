package com.example.csongor.quizapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.widget.TextView;
import android.support.v4.app.FragmentTransaction;

/**
 * Created by csongor on 2/2/18.
 */

class InGameState implements GameState {
    // declaring variables
    private static final InGameState IN_GAME_STATE_INSTANCE = new InGameState();
    private static MainActivity sMainActivity;
    private static TextView sLeftButton, sRightButton;
    private static FragmentManager sFragmentManager;
    private static FragmentTransaction sTransaction;
    private static Fragment sFragment;

    static InGameState getInstance(MainActivity activity) {
        // setting up variables
        sMainActivity =activity;
        sLeftButton = sMainActivity.findViewById(R.id.left_button);
        sRightButton = sMainActivity.findViewById(R.id.right_button);
        sFragmentManager = sMainActivity.getSupportFragmentManager();
        return IN_GAME_STATE_INSTANCE;
    }

    private InGameState() {
    }

    /**
     * method for pressing right button
     */
    @Override
    public void doRightButtonAction() {
            sTransaction = sFragmentManager.beginTransaction();
            QuizQuestion question;

            //get the next question
            question = sMainActivity.getQuestionIterator().next();

            // depending the question type the appropriate sFragment will be used.
            // next step: make this using Chain of Responsibility pattern to avoid multiple if statements
            if (question.getQuestionType() == QuizQuestion.STRING_QUESTION) {
                sFragment = StringQuestionFragment.newInstance(question);
            } else if (question.getQuestionType() == QuizQuestion.RADIO_QUESTION) {
                sFragment = RadioQuestionFragment.newInstance(question);
            } else if (question.getQuestionType() == QuizQuestion.CHECKBOX_QUESTION) {
                sFragment = CheckboxQuestionFragment.newInstance(question);
            } else {
                sFragment = new Fragment();
            }
            // Replace whatever is in the fragment_container view with this
            sMainActivity.setCurrentFragment(sFragment);
            sTransaction.replace(R.id.fragment_container, sMainActivity.getCurrentFragment());
            sTransaction.commit();

        // change state if there are no more questions, evaluation will be available
        if (!sMainActivity.getQuestionIterator().hasNext()) {
            // enabling evaluation button and disabling the next question button
            sRightButton.setClickable(false);
            sRightButton.setEnabled(false);
            sLeftButton.setClickable(true);
            sLeftButton.setEnabled(true);

            // setting next state
            sMainActivity.setGameState(LastQuestionState.getInstance(sMainActivity));
        }
    }

    /**
     * method for pressing left button
     */
    @Override
    public void doLeftButtonAction() {
        // empty since it's inactive in this state
    }
}
