package com.example.csongor.quizapp.gamestates;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.widget.TextView;

import com.example.csongor.quizapp.MainActivity;
import com.example.csongor.quizapp.R;

/**
 * Created by csongor on 2/2/18.
 * In this state users can restart game or publish their results
 */

public class EvaluationState implements GameState {
    private static final EvaluationState EVALUATION_STATE_INSTANCE = new EvaluationState();
    //declaring variables
    private static MainActivity sMainActivity;
    private static TextView sLeftButton, sRightButton;
    private static FragmentManager sFragmentManager;
    private static Fragment sFragment;

    static EvaluationState getInstance(MainActivity activity) {
        // setting up variables
        sMainActivity = activity;
        sLeftButton = sMainActivity.findViewById(R.id.left_button);
        sRightButton = sMainActivity.findViewById(R.id.right_button);
        sFragmentManager = sMainActivity.getSupportFragmentManager();
        return EVALUATION_STATE_INSTANCE;
    }

    private EvaluationState() {
    }

    /**
     * method for pressing right button
     */
    @Override
    public void doRightButtonAction() {

        // Creating simple share intent with action chooser
        String mToPut = String.format(sMainActivity.getResources().getString(R.string.publish_my_result), sMainActivity.getGamePoints(), sMainActivity.getMaxPoints(), sMainActivity.getPlayerName());
        String mSubject = sMainActivity.getResources().getString(R.string.send_subject);
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, mSubject);
        shareIntent.putExtra(Intent.EXTRA_TEXT, mToPut);
        sMainActivity.startActivity(Intent.createChooser(shareIntent, sMainActivity.getResources().getString(R.string.publish)));
    }

    /**
     * method for pressing left button
     */
    @Override
    public void doLeftButtonAction() {
        // restarting the game - method I took was described on StackOverflow
        Context context = sMainActivity;
        Intent mStartActivity = new Intent(context, MainActivity.class);
        int mPendingIntentId = 123456;
        PendingIntent mPendingIntent = PendingIntent.getActivity(context, mPendingIntentId, mStartActivity, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager mgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 100, mPendingIntent);
        System.exit(0);
    }
}
