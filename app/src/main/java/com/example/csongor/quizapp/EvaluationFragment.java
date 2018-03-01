package com.example.csongor.quizapp;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

/**
 * Created by csongor on 1/23/18.
 * Game evaluation fragment
 */

public class EvaluationFragment extends Fragment {
    // defining vatiables
    private static final String BUNDLE_PLAYER = "BUNDLE_PLAYER";
    private static final String BUNDLE_MAX_POINTS = "BUNDLE_MAX_POINTS";
    private static final String BUNDLE_ACTUAL_POINTS = "BUNDLE_ACTUAL_POINTS";
    private int mAnswerPoints;
    private int mMaxPoints;
    private View mRootView;
    private TextView mTitleText, mEvaluationText;
    private ImageView mImageView;
    private String mPlayerName;


    //creating fragment with QuizQuestion argument
    public static EvaluationFragment newInstance(int actualPoints, int maxPoints, String playerName) {
        Bundle bundle = new Bundle();
        bundle.putString(BUNDLE_PLAYER, playerName);
        bundle.putInt(BUNDLE_MAX_POINTS, maxPoints);
        bundle.putInt(BUNDLE_ACTUAL_POINTS, actualPoints);
        EvaluationFragment fragment = new EvaluationFragment();
        fragment.setArguments(bundle);
        return fragment;
    }


    /**
     * Called to have the fragment instantiate its user interface view.
     * This is optional, and non-graphical fragments can return null (which
     * is the default implementation).  This will be called between
     * {@link #onCreate(Bundle)} and {@link #onActivityCreated(Bundle)}.
     * <p>
     * <p>If you return a View from here, you will later be called in
     * {@link #onDestroyView} when the view is being released.
     *
     * @param inflater           The LayoutInflater object that can be used to inflate
     *                           any views in the fragment,
     * @param container          If non-null, this is the parent view that the fragment's
     *                           UI should be attached to.  The fragment should not add the view itself,
     *                           but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     *                           from a previous saved state as given here.
     * @return Return the View for the fragment's UI, or null.
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.evaluation_fragment, container, false);
        // setting up views
        mTitleText = mRootView.findViewById(R.id.evaluation_title_text);
        mEvaluationText = mRootView.findViewById(R.id.evaluation_text);
        mImageView = mRootView.findViewById(R.id.evaluation_image_container);
        // loading bundle arguments
        readBundle(getArguments());
        // scrolling down to remain the evaluation text visible
        ScrollView scroll = mRootView.findViewById(R.id.evaluation_scroll_container);
        scroll.postDelayed(() -> scroll.fullScroll(View.FOCUS_DOWN), 150L);
        // evaluating game
        evaluateGame();
        return mRootView;
    }


    /**
     * Called when the Fragment is no longer resumed.  This is generally
     * tied to {@link Activity#onPause() Activity.onPause} of the containing
     * Activity's lifecycle.
     */
    @Override
    public void onPause() {
        super.onPause();

    }

    private void evaluateGame() {
        // formatting result
        String resultToDisplay = String.format("%.2f", (mAnswerPoints / (double) mMaxPoints) * 100);
        String titleToDisplay = String.format(getResources().getString(R.string.evaluation_title), mPlayerName, mAnswerPoints, mMaxPoints,resultToDisplay);
        mTitleText.setText(titleToDisplay);
        mImageView.setImageDrawable(getActivity().getDrawable(R.drawable.eight_thousanders));
        double result = mAnswerPoints / (double) mMaxPoints * 100;
        // displaying result dependent message and background image
        if (result < 25) {
            mEvaluationText.setText(R.string.try_again);
            // own picture
            mImageView.setImageDrawable(getActivity().getDrawable(R.drawable.rookie2));
        } else if (result < 81) {
            mEvaluationText.setText(R.string.not_bad);
            // By Sonia Sevilla - Own work, CC0, https://commons.wikimedia.org/w/index.php?curid=30938215
            mImageView.setImageDrawable(getActivity().getDrawable(R.drawable.rock_climber));
        } else {
            mEvaluationText.setText(R.string.awesome);
            // By Mountaineer - Own work, CC BY 3.0, https://commons.wikimedia.org/w/index.php?curid=5730546
            mImageView.setImageDrawable(getActivity().getDrawable(R.drawable.mountaineer));
        }
    }

    // helper method for retrieving bundle args
    private void readBundle(Bundle bundle) {
        if (bundle != null) {
            this.mAnswerPoints = bundle.getInt(BUNDLE_ACTUAL_POINTS);
            this.mMaxPoints = bundle.getInt(BUNDLE_MAX_POINTS);
            this.mPlayerName = bundle.getString(BUNDLE_PLAYER);
        }
    }

}
