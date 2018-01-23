package com.example.csongor.quizapp;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

/**
 * Created by csongor on 1/23/18.
 */

public class EvaluationFragment extends Fragment {
    private   int answerPoints;
    private  int maxPoints;
    private View rootView;
    private TextView titleText, evaluationText;
    private ImageView imageView;
    private String playerName;


    //creating fragment with QuizQuestion argument
    public static EvaluationFragment newInstance(int actualPoints, int maxPoints, String playerName) {
        Bundle bundle = new Bundle();
        bundle.putString("player",playerName);
        bundle.putInt("maxPoints",maxPoints);
        bundle.putInt("actualPoints", actualPoints);
        EvaluationFragment fragment =  new EvaluationFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    private void readBundle(Bundle bundle) {
        if (bundle != null) {
            this.answerPoints=bundle.getInt("actualPoints");
            this.maxPoints=bundle.getInt("maxPoints");
            this.playerName=bundle.getString("player");
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
        rootView=inflater.inflate(R.layout.evaluation_fragment,container,false);
        // setting up views
        titleText=rootView.findViewById(R.id.evaluation_title_text);
        evaluationText=rootView.findViewById(R.id.evaluation_text);
        imageView=rootView.findViewById(R.id.evaluation_image_container);
        // loading bundle arguments
        readBundle(getArguments());
        ScrollView scroll=(ScrollView)rootView.findViewById(R.id.evaluation_container);
        scroll.postDelayed(() -> scroll.fullScroll(View.FOCUS_DOWN),150L);
        titleText.setText(playerName+", "+(answerPoints/(double)maxPoints)*100+"%, congrats");
        imageView.setImageDrawable(getActivity().getDrawable(R.drawable.eight_thousanders));
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
}
