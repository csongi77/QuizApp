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
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by csongor on 1/22/18.
 * Fragment for displaying CheckBox type questions
 */

public class CheckboxQuestionFragment extends Fragment {
    private static final String BUNDLE_QUESTION ="BUNDLE_QUESTION";
    private int mAnswerPoints;
    private QuizQuestion mQuestion;
    private View mRootView;
    private TextView mQuestionText;
    private ImageView mImageView;
    private ArrayList<CheckBox> mCheckBoxes = new ArrayList<CheckBox>();

    //creating fragment with QuizQuestion argument
    public static CheckboxQuestionFragment newInstance(QuizQuestion quizQuestion) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(BUNDLE_QUESTION, quizQuestion);
        CheckboxQuestionFragment fragment = new CheckboxQuestionFragment();
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
        mRootView = inflater.inflate(R.layout.checkbox_question_fragment, container, false);
        // setting up the variables
        mQuestionText = mRootView.findViewById(R.id.checkbox_question_text);
        mImageView = mRootView.findViewById(R.id.checkbox_question_image_container);
        // adding the checkbox variables into arraylist for the evaluation
        mCheckBoxes.add(mRootView.findViewById(R.id.checkbox_answer_option_0));
        mCheckBoxes.add(mRootView.findViewById(R.id.checkbox_answer_option_1));
        mCheckBoxes.add(mRootView.findViewById(R.id.checkbox_answer_option_2));
        mCheckBoxes.add(mRootView.findViewById(R.id.checkbox_answer_option_3));
        // reading arguments
        readBundle(getArguments());
        mQuestionText.setText(mQuestion.getQuestion());
        Drawable image = getActivity().getDrawable(mQuestion.getImageResourceId());
        // displaying the questions
        for (int i = 0; i < mCheckBoxes.size(); i++) {
            mCheckBoxes.get(i).setText(mQuestion.getAnswers().get(i).getAnswerText());
        }
        mImageView.setImageDrawable(image);
        mRootView.clearFocus();

        // hiding virtual keyboard, if it was active due to StringQuestionFragment
        InputMethodManager im=(InputMethodManager)getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        im.hideSoftInputFromWindow(mRootView.getWindowToken(),0);
       ScrollView scroll= mRootView.findViewById(R.id.checkbox_scroll_container);
       scroll.postDelayed(() -> scroll.fullScroll(View.FOCUS_DOWN),150L);
        return mRootView;
    }

    @Override
    public void onStop() {
        /** Checking right answer. The loop goes through the checked buttons and the answer list.
         * If both are true (mCheckBoxes.get(i) and answerOptions.get(i)) than the result is also true.
         * If player chosen all good answers, they gets the point
         */
        for (int i = 0; i < mCheckBoxes.size(); i++) {
            if (mCheckBoxes.get(i).isChecked() & mQuestion.getAnswers().get(i).isRightAnswer()) {
                mAnswerPoints++;
            }/* else if (mCheckBoxes.get(i).isChecked() && !(mQuestion.getAnswers().get(i).isRightAnswer())) {
                mAnswerPoints--;
            }*/
        }
        if (mAnswerPoints == mQuestion.getRightAnswerNumber())
            ((MainActivity) getActivity()).addPoints(1);
        super.onStop();
    }

    // helper method for getting Bundle argumennts
    private void readBundle(Bundle bundle) {
        if (bundle != null) {
            mQuestion = (QuizQuestion) bundle.get(BUNDLE_QUESTION);
        }
    }
}
