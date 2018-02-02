package com.example.csongor.quizapp;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.share.model.ShareContent;
import com.facebook.share.model.ShareMediaContent;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.widget.ShareDialog;

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
       ViewGroup vg=(ViewGroup)mainActivity.findViewById(R.id.fragment_container);
        vg.setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(vg.getDrawingCache());
        vg.setDrawingCacheEnabled(false);
        SharePhoto sharePhoto = new SharePhoto.Builder()
                .setBitmap(bitmap)
                .build();
        ShareContent shareContent = new ShareMediaContent.Builder()
                .addMedium(sharePhoto)
                .build();
        ShareDialog dialog = new ShareDialog(mainActivity);
        dialog.show(shareContent, ShareDialog.Mode.AUTOMATIC);
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
