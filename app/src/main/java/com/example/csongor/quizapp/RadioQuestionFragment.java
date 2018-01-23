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
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by csongor on 1/22/18.
 */

public class RadioQuestionFragment extends Fragment {
    private int answerPoints;
    private QuizQuestion question;
    private View rootView;
    private TextView questionText;
    private ImageView imageView;
    private Boolean[] playerAnswers={false,false,false,false};
    private ArrayList<RadioButton> radioButtons = new ArrayList<RadioButton>();

    //creating fragment with QuizQuestion argument
    public static RadioQuestionFragment newInstance(QuizQuestion quizQuestion) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("question", quizQuestion);
        RadioQuestionFragment fragment = new RadioQuestionFragment();
        fragment.setArguments(bundle);
        Log.e("RadioQuestionFragment", " fragment instantinated ---->");
        return fragment;
    }

    private void readBundle(Bundle bundle) {
        if (bundle != null) {
            question = (Question) bundle.get("question");
        }
    }

    /**
     * Called when a fragment is first attached to its context.
     * {@link #onCreate(Bundle)} will be called after this.
     *
     * @param context
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        answerPoints = 0;

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
        rootView = inflater.inflate(R.layout.radio_question_fragment, container, false);
        readBundle(getArguments());
        questionText = rootView.findViewById(R.id.radio_question_text);
        imageView = rootView.findViewById(R.id.radio_question_image_container);
        radioButtons.add((RadioButton) rootView.findViewById(R.id.radio_answer_option_0));
        radioButtons.add((RadioButton) rootView.findViewById(R.id.radio_answer_option_1));
        radioButtons.add((RadioButton) rootView.findViewById(R.id.radio_answer_option_2));
        radioButtons.add((RadioButton) rootView.findViewById(R.id.radio_answer_option_3));


        questionText.setText(((Question) question).getQuestion());
        Drawable image = getActivity().getDrawable(((Question) question).getImageResourceId());
        for (int i=0; i<radioButtons.size(); i++){
            radioButtons.get(i).setText(((Question) question).getAnswerOptions().get(i).getAnswerText());
        }
        imageView.setImageDrawable(image);
        Log.e("RadioQuestionFragment", " fragment onCreateView executed ---->");
        InputMethodManager im=(InputMethodManager)getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        im.hideSoftInputFromWindow(rootView.getWindowToken(),0);
        ScrollView scroll=(ScrollView)rootView.findViewById(R.id.radio_question_container);
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
        // checking right answer. The loop goes through the checked buttons and the answer list.
        // if both is true (buttons[i] and answerOptions.get(i)) than the result is also true.
        // in this, and only this case answer points raises by 1. Because only one right answer is
        // possible, it returns only 1 point for the right answered question
        for (int i = 0; i < radioButtons.size(); i++) {
            if (radioButtons.get(i).isChecked() & ((Question) question).getAnswerOptions().get(i).isRightAnswer()) {
                answerPoints++;
            }
        }
        ((MainActivity) getActivity()).addPoints(answerPoints);
        rootView = null;
        Log.e("RadioQFragment", " fragment onPause executed ---->");
    }



}
