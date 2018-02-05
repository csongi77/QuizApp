package com.example.csongor.quizapp;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.share.model.ShareContent;
import com.facebook.share.model.ShareMediaContent;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.widget.ShareDialog;

/**
 * Created by csongor on 2/2/18.
 * In this state users can restart game or publish their results
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
        Log.e("eval","----------------------------Bitmap created: ");
        vg.setDrawingCacheEnabled(false);
        // Creating simple share intent with action chooser
        String toPut=String.format(mainActivity.getResources().getString(R.string.publish_my_result),mainActivity.getGamePoints(), mainActivity.getMaxPoints(), mainActivity.getPlayerName());
        Intent shareIntent=new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT,toPut);
        mainActivity.startActivity(Intent.createChooser(shareIntent, mainActivity.getResources().getString(R.string.publish)));
        /**
         *   at this moment I could share result via FB because creating a simple implicit Intent
         *  didn't work:
         *  1. The screenshot fileSize was bigger that a single Intent could handle, it threw exception
         *  2. Tried to save it on external_file_storage. I checked and asked for WRITE_EXTERNAL_STORAGE permission.
         *      When I got it, the image was saved to file and I put it into URI.
         *      The Uri was put in Intent, but it couldn't read the file.
         *      However I added .addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
         *  3. At this moment I could achive sharing screenshot only with Facebook API, sorry... :(
         *  4. I need few days/hours to fix this (saving screenshot, save it to file, put file Uri into implicit Intent to publish)
         */

        /*
        //FOR FACEBOOK SHARE
        SharePhoto sharePhoto = new SharePhoto.Builder()
                .setBitmap(bitmap)
                .build();
        ShareContent shareContent = new ShareMediaContent.Builder()
                .addMedium(sharePhoto)
                .build();
        ShareDialog dialog = new ShareDialog(mainActivity);
        dialog.show(shareContent, ShareDialog.Mode.AUTOMATIC);
        */

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
