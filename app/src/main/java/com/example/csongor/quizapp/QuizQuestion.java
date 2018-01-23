package com.example.csongor.quizapp;

import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by csongor on 1/20/18.
 *
 */

public interface QuizQuestion extends Serializable{
    public static final int STRING_QUESTION=0;
    public static final int RADIO_QUESTION=1;
    public static final int CHECKBOX_QUESTION=2;

    /**
     * @return returns the Question Type.
     * The AnswerOption List will determine the QuizQuestionType:
     *                     1: The list has only 1 and only 1 AnswerOption -> STRING_QUESTION
     *                     2: The list has 4 AnswerOption with 1 rightAnswer -> RADIO_QUESTION
     *                     3: The list has 4 AnswerOption with multiple rightAnswer -> CHECKBOX_QUESTION
     */
    public int getQuestionType();

    /**
     *
     * @return the maximum points (for instance CheckBox questions can worth 2-4 points)
     */
    public int getMaxPoints();
}
