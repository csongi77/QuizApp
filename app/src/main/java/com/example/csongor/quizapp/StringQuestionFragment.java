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
 */

public class StringQuestionFragment extends Fragment {

  private   int answerPoints;
    private QuizQuestion question;
    private String playerAnswer;
    private View rootView;
    private TextView questionText;
    private ImageView imageView;
    private EditText answerText;

    //creating fragment with QuizQuestion argument
    public static StringQuestionFragment newInstance(QuizQuestion quizQuestion) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("question",quizQuestion);
        StringQuestionFragment fragment =  new StringQuestionFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    private void readBundle(Bundle bundle) {
        if (bundle != null) {
            question=(QuizQuestion) bundle.get("question");
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
        //initializing the variables
        answerPoints=0;
        playerAnswer="";
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
        rootView=inflater.inflate(R.layout.string_question_fragment,container,false);
        questionText=rootView.findViewById(R.id.string_question_text);
        imageView=rootView.findViewById(R.id.string_question_image_container);
        answerText=rootView.findViewById(R.id.player_string_answer);
        readBundle(getArguments());
        // displaying the image and question
        questionText.setText(question.getQuestion());
        Drawable image= getActivity().getDrawable(question.getImageResourceId());
        imageView.setImageDrawable(image);
        InputMethodManager im=(InputMethodManager)getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        im.hideSoftInputFromWindow(rootView.getWindowToken(),0);
        // scrolling down to make the editText field visible
        ScrollView scroll= rootView.findViewById(R.id.string_scroll_container);
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
        playerAnswer=((EditText)getActivity().findViewById(R.id.player_string_answer)).getEditableText().toString().toLowerCase();
        rootView=null;
        /**
         * Checking the answer. If the string answer is the same then the player gets 1 point
         */
        if(playerAnswer.equals(question.getAnswers().get(0).getAnswerText().toLowerCase())){
            answerPoints=1;
            ((MainActivity)getActivity()).addPoints(answerPoints);
        }else{
            answerPoints=0;
        }
    }
}
