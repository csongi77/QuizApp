package com.example.csongor.quizapp;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.widget.TextView;
import android.support.v4.app.FragmentTransaction;

/**
 * Created by csongor on 2/2/18.
 */

class InGameState implements GameState {
    // declaring variables
    private static final InGameState ourInstance = new InGameState();
    private static MainActivity mainActivity;
    private static TextView leftButton, rightButton;
    private static FragmentManager fragmentManager;
    private static FragmentTransaction transaction;
    private static Fragment fragment;

    static InGameState getInstance(MainActivity activity) {
        // setting up variables
        mainActivity=activity;
        leftButton= mainActivity.findViewById(R.id.left_button);
        rightButton= mainActivity.findViewById(R.id.right_button);
        fragmentManager=mainActivity.getSupportFragmentManager();
        return ourInstance;
    }

    private InGameState() {
    }

    /**
     * method for pressing right button
     */
    @Override
    public void doRightButtonAction() {
            transaction = fragmentManager.beginTransaction();
            QuizQuestion question;
            //get the next question
            question = mainActivity.getQuestionIterator().next();
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
            // Replace whatever is in the fragment_container view with this
            mainActivity.setCurrentFragment(fragment);
            transaction.replace(R.id.fragment_container, mainActivity.getCurrentFragment());
            transaction.commit();

        // change state if there are no more questions, evaluation will be available
        if (!mainActivity.getQuestionIterator().hasNext()) {
            // enabling evaluation button and disabling the next question button
            rightButton.setClickable(false);
            rightButton.setEnabled(false);
            leftButton.setClickable(true);
            leftButton.setEnabled(true);
            mainActivity.setGameState(LastQuestionState.getInstance(mainActivity));
        }
    }

    /**
     * method for pressing left button
     */
    @Override
    public void doLeftButtonAction() {

    }
}
