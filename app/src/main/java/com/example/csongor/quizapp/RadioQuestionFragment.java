package com.example.csongor.quizapp;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
 * Fragment for displaying RadioButton type questions
 */

public class RadioQuestionFragment extends Fragment {
    private int mAnswerPoints;
    private QuizQuestion mQuestion;
    private View mRootView;
    private TextView mQuestionText;
    private ImageView mImageView;
    private ArrayList<RadioButton> mRadioButtons = new ArrayList<RadioButton>();
    private boolean mHasAlreadyEvaluated;

    //creating fragment with QuizQuestion argument
    public static RadioQuestionFragment newInstance(QuizQuestion quizQuestion) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("mQuestion", quizQuestion);
        RadioQuestionFragment fragment = new RadioQuestionFragment();
        fragment.setArguments(bundle);
        return fragment;
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
        mAnswerPoints = 0;
        mHasAlreadyEvaluated=false;
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
        mRootView = inflater.inflate(R.layout.radio_question_fragment, container, false);
        readBundle(getArguments());

        // initializing fields
        mQuestionText = mRootView.findViewById(R.id.radio_question_text);
        mImageView = mRootView.findViewById(R.id.radio_question_image_container);
        mRadioButtons.add(mRootView.findViewById(R.id.radio_answer_option_0));
        mRadioButtons.add(mRootView.findViewById(R.id.radio_answer_option_1));
        mRadioButtons.add(mRootView.findViewById(R.id.radio_answer_option_2));
        mRadioButtons.add(mRootView.findViewById(R.id.radio_answer_option_3));

        // displaying texts and images
        mQuestionText.setText(mQuestion.getQuestion());
        Drawable image = getActivity().getDrawable(mQuestion.getImageResourceId());
        for (int i = 0; i < mRadioButtons.size(); i++) {
            mRadioButtons.get(i).setText(mQuestion.getAnswers().get(i).getAnswerText());
        }
        mImageView.setImageDrawable(image);
        // hiding virtual keyboard, if it was active due to StringQuestionFragment - idea from StackOverflow
        InputMethodManager im = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        im.hideSoftInputFromWindow(mRootView.getWindowToken(), 0);
        ScrollView scroll = mRootView.findViewById(R.id.radio_scroll_container);
        scroll.postDelayed(() -> scroll.fullScroll(View.FOCUS_DOWN), 150L);
        return mRootView;
    }


    @Override
    public void onStop() {
        if(!mHasAlreadyEvaluated) {
            // checking right answer. The loop goes through the checked buttons and the answer list.
            // if both is true (buttons[i] and answerOptions.get(i)) than the result is also true.
            // in this, and only this case answer points raises by 1. Because only one right answer is
            // possible, it returns only 1 point for the right answered mQuestion
            for (int i = 0; i < mRadioButtons.size(); i++) {
                if (mRadioButtons.get(i).isChecked() & mQuestion.getAnswers().get(i).isRightAnswer()) {
                    mAnswerPoints++;
                }
            }
            ((MainActivity) getActivity()).addPoints(mAnswerPoints);
            mHasAlreadyEvaluated=true;
        }
        mRootView = null;
        super.onStop();
    }

    // helper method for getting bundle args
    private void readBundle(Bundle bundle) {
        if (bundle != null) {
            mQuestion = (QuizQuestion) bundle.get("mQuestion");
        }
    }
}
