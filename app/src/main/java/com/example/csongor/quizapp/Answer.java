package com.example.csongor.quizapp;

import java.io.Serializable;

/**
 * Created by csongor on 1/20/18.
 */

public class Answer implements Serializable {
    //private fields
    private String mAnswerText;
    private boolean mRightAnswer;

    /**
     * Ctor
     * @param mAnswerText String that must be shown. In case STRING_QUESTION this is the right answer
     * @param mRightAnswer true if this is the right answer, else false
     */
    public Answer(String mAnswerText, boolean mRightAnswer) {
        this.mAnswerText = mAnswerText;
        this.mRightAnswer = mRightAnswer;
    }

    /**
     * @return The String that must be shown at answer option in fragment.
     * Exception: If QuestionType is STRING_QUESTION, than this is the right answer.
     */
    public String getAnswerText() {
        return mAnswerText;
    }

    /**
     * @return Whether is this answer option is the right answer
     */
    public boolean isRightAnswer() {
        return mRightAnswer;
    }
}
