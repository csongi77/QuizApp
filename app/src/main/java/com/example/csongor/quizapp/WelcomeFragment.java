package com.example.csongor.quizapp;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

public class WelcomeFragment extends Fragment {
    // field for player's name
    private String playerName;
    TextView welcomeText;
    ImageView imageView;
    EditText playerNameText;
    View rootView;


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
        rootView=inflater.inflate(R.layout.string_question_fragment,container,false);
        // assigning variable namest to views
        welcomeText=rootView.findViewById(R.id.string_question_text);
        imageView=rootView.findViewById(R.id.string_question_image_container);
        playerNameText=rootView.findViewById(R.id.player_string_answer);
        // setting up appropriate text
        playerNameText.setHint(R.string.name_hint);
        welcomeText.setText(R.string.welcome_title);
        Drawable image= getActivity().getDrawable(R.drawable.p1020310);
        imageView.setImageDrawable(image);
        ScrollView scroll=(ScrollView)rootView.findViewById(R.id.string_scroll_container);
        scroll.postDelayed(() -> scroll.fullScroll(View.FOCUS_DOWN),150L);
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
        // setting player name
        playerName=playerNameText.getText().toString();
        rootView=null;
        ((MainActivity)getActivity()).setPlayerName(playerName);
    }
}
