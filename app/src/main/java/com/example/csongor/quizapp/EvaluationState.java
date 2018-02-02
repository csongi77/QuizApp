package com.example.csongor.quizapp;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.widget.TextView;

/**
 * Created by csongor on 2/2/18.
 */

class EvaluationState implements GameState {
    private static final EvaluationState ourInstance = new EvaluationState();
    //declaring variables
    private static MainActivity mainActivity;
    private static TextView leftButton, rightButton;
    private static FragmentManager fragmentManager;
    private static Fragment fragment;

    static EvaluationState getInstance(MainActivity activity) {
        // setting up variables
        mainActivity=activity;
        leftButton= mainActivity.findViewById(R.id.left_button);
        rightButton= mainActivity.findViewById(R.id.right_button);
        fragmentManager=mainActivity.getSupportFragmentManager();
        return ourInstance;
    }

    private EvaluationState() {
    }

    /**
     * method for pressing right button
     */
    @Override
    public void doRightButtonAction() {
        // TODO publish on facebook
    }

    /**
     * method for pressing left button
     */
    @Override
    public void doLeftButtonAction() {
        // restarting the game
        Context context=(Context)mainActivity;
        Intent mStartActivity = new Intent(context, MainActivity.class);
        int mPendingIntentId = 123456;
        PendingIntent mPendingIntent = PendingIntent.getActivity(context, mPendingIntentId,    mStartActivity, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager mgr = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 100, mPendingIntent);
        System.exit(0);
    }
}
