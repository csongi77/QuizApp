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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

/**
 * Created by csongor on 1/22/18.
 * Fragment for displaying single line EditText type questions
 */

public class StringQuestionFragment extends Fragment {

    private int mAnswerPoints;
    private QuizQuestion mQuestion;
    private String mPlayerAnswer;
    private View mRootView;
    private TextView mQuestionText;
    private ImageView mImageView;
    private EditText mAnswerText;
    private boolean mHasAlreadyEvaluated;

    //creating fragment with QuizQuestion argument
    public static StringQuestionFragment newInstance(QuizQuestion quizQuestion) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("mQuestion", quizQuestion);
        StringQuestionFragment fragment = new StringQuestionFragment();
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
        //initializing the variables
        mHasAlreadyEvaluated=false;
        mAnswerPoints = 0;
        mPlayerAnswer = "";
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
        mRootView = inflater.inflate(R.layout.string_question_fragment, container, false);
        mQuestionText = mRootView.findViewById(R.id.string_question_text);
        mImageView = mRootView.findViewById(R.id.string_question_image_container);
        mAnswerText = mRootView.findViewById(R.id.player_string_answer);
        readBundle(getArguments());

        // displaying the image and mQuestion
        mQuestionText.setText(mQuestion.getQuestion());
        Drawable image = getActivity().getDrawable(mQuestion.getImageResourceId());
        mImageView.setImageDrawable(image);
        InputMethodManager im = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        im.hideSoftInputFromWindow(mRootView.getWindowToken(), 0);

        // scrolling down to make the editText field visible
        ScrollView scroll = mRootView.findViewById(R.id.string_scroll_container);
        scroll.postDelayed(() -> scroll.fullScroll(View.FOCUS_DOWN), 150L);
        return mRootView;
    }


    /**
     * Called when the Fragment is no longer started.  This is generally
     * tied to {@link Activity#onStop() Activity.onStop} of the containing
     * Activity's lifecycle.
     */
    @Override
    public void onStop() {
        if(!mHasAlreadyEvaluated) {
            mPlayerAnswer = (mAnswerText.getEditableText().toString().toLowerCase());
            mRootView = null;
            /**
             * Checking the answer. If the string answer is the same then the player gets 1 point
             */
            if (mPlayerAnswer.equals(mQuestion.getAnswers().get(0).getAnswerText().toLowerCase())) {
                mAnswerPoints = 1;
                ((MainActivity) getActivity()).addPoints(mAnswerPoints);
            }
            mHasAlreadyEvaluated=true;
        }
        super.onStop();
    }

    // helper method for getting bundle args
    private void readBundle(Bundle bundle) {
        if (bundle != null) {
            mQuestion = (QuizQuestion) bundle.get("mQuestion");
        }
    }
}
