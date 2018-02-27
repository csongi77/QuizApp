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
    private View rootView;
    private TextView questionText;
    private ImageView imageView;
    private ArrayList<CheckBox> checkBoxes = new ArrayList<CheckBox>();

    //creating fragment with QuizQuestion argument
    public static CheckboxQuestionFragment newInstance(QuizQuestion quizQuestion) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(BUNDLE_QUESTION, quizQuestion);
        CheckboxQuestionFragment fragment = new CheckboxQuestionFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    private void readBundle(Bundle bundle) {
        if (bundle != null) {
            mQuestion = (QuizQuestion) bundle.get(BUNDLE_QUESTION);
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
        rootView = inflater.inflate(R.layout.checkbox_question_fragment, container, false);
        // setting up the variables
        questionText = rootView.findViewById(R.id.checkbox_question_text);
        imageView = rootView.findViewById(R.id.checkbox_question_image_container);
        // adding the checkbox variables into arraylist for the evaluation
        checkBoxes.add(rootView.findViewById(R.id.checkbox_answer_option_0));
        checkBoxes.add(rootView.findViewById(R.id.checkbox_answer_option_1));
        checkBoxes.add(rootView.findViewById(R.id.checkbox_answer_option_2));
        checkBoxes.add(rootView.findViewById(R.id.checkbox_answer_option_3));
        readBundle(getArguments());
        questionText.setText(mQuestion.getQuestion());
        Drawable image = getActivity().getDrawable(mQuestion.getImageResourceId());
        // displaying the questions
        for (int i = 0; i < checkBoxes.size(); i++) {
            checkBoxes.get(i).setText(mQuestion.getAnswers().get(i).getAnswerText());
        }
        imageView.setImageDrawable(image);
        rootView.clearFocus();
        // hiding virtual keyboard, if it was active due to StringQuestionFragment
        InputMethodManager im=(InputMethodManager)getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        im.hideSoftInputFromWindow(rootView.getWindowToken(),0);
       ScrollView scroll= rootView.findViewById(R.id.checkbox_scroll_container);
       scroll.postDelayed(() -> scroll.fullScroll(View.FOCUS_DOWN),150L);
        return rootView;
    }

    @Override
    public void onStop() {
        /** Checking right answer. The loop goes through the checked buttons and the answer list.
         * if both is true (checkBoxes.get(i) and answerOptions.get(i)) than the result is also true.
         * in this, and only this case answer points raises by 1. If the answerOption is false but it
         * was checked the answer points will be decreased to avoid getting points by selecting all
         * possible answer options.
         *  For example if player checks 2 correct and 1 wrong answer they get only 1 point
         * If they checks 2 incorrect and 1 right answer they got 0 points, etc.
         */
        for (int i = 0; i < checkBoxes.size(); i++) {
            if (checkBoxes.get(i).isChecked() & mQuestion.getAnswers().get(i).isRightAnswer()) {
                mAnswerPoints++;
            } else if (checkBoxes.get(i).isChecked() && !(mQuestion.getAnswers().get(i).isRightAnswer())) {
                mAnswerPoints--;
            }
        }
        if (mAnswerPoints == mQuestion.getMaxPoints())
            ((MainActivity) getActivity()).addPoints(1);
        super.onStop();
    }
}
