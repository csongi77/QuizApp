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
 */

public class EvaluationFragment extends Fragment {
    // defining vatiables
    private static final String PLAYER = "player";
    private static final String MAX_POINTS = "maxPoints";
    private static final String ACTUAL_POINTS = "actualPoints";
    private int answerPoints;
    private int maxPoints;
    private View rootView;
    private TextView titleText, evaluationText;
    private ImageView imageView;
    private String playerName;


    //creating fragment with QuizQuestion argument
    public static EvaluationFragment newInstance(int actualPoints, int maxPoints, String playerName) {
        Bundle bundle = new Bundle();
        bundle.putString(PLAYER, playerName);
        bundle.putInt(MAX_POINTS, maxPoints);
        bundle.putInt(ACTUAL_POINTS, actualPoints);
        EvaluationFragment fragment = new EvaluationFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    private void readBundle(Bundle bundle) {
        if (bundle != null) {
            this.answerPoints = bundle.getInt(ACTUAL_POINTS);
            this.maxPoints = bundle.getInt(MAX_POINTS);
            this.playerName = bundle.getString(PLAYER);
        }
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
        rootView = inflater.inflate(R.layout.evaluation_fragment, container, false);
        // setting up views
        titleText = rootView.findViewById(R.id.evaluation_title_text);
        evaluationText = rootView.findViewById(R.id.evaluation_text);
        imageView = rootView.findViewById(R.id.evaluation_image_container);
        // loading bundle arguments
        readBundle(getArguments());
        // scrolling down to remain the evaluation text visible
        ScrollView scroll = (ScrollView) rootView.findViewById(R.id.evaluation_scroll_container);
        scroll.postDelayed(() -> scroll.fullScroll(View.FOCUS_DOWN), 150L);
        // evaluating game
        evaluateGame();
        return rootView;
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
        String resultToDisplay = String.format("%.2f", (answerPoints / (double) maxPoints) * 100);
        String titleToDisplay = String.format(getResources().getString(R.string.evaluation_title),playerName,answerPoints,maxPoints,resultToDisplay);
        titleText.setText(titleToDisplay);
        imageView.setImageDrawable(getActivity().getDrawable(R.drawable.eight_thousanders));
        double result = answerPoints / (double) maxPoints * 100;
        // displaying result dependent message and background image
        if (result < 25) {
            evaluationText.setText(R.string.try_again);
            // own picture
            imageView.setImageDrawable(getActivity().getDrawable(R.drawable.rookie));
        } else if (result < 81) {
            evaluationText.setText(R.string.not_bad);
            // By Sonia Sevilla - Own work, CC0, https://commons.wikimedia.org/w/index.php?curid=30938215
            imageView.setImageDrawable(getActivity().getDrawable(R.drawable.rock_climber));
        } else {
            evaluationText.setText(R.string.awesome);
            // By Mountaineer - Own work, CC BY 3.0, https://commons.wikimedia.org/w/index.php?curid=5730546
            imageView.setImageDrawable(getActivity().getDrawable(R.drawable.mountaineer));
        }
    }
}
