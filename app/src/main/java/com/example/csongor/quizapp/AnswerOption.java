package com.example.csongor.quizapp;

/**
 * Created by csongor on 1/20/18.
 */

public interface AnswerOption {
    /**
     * @return The String that must be shown at answer option in fragment.
     * Exception: If QuestionType is STRING_QUESTION, than this is the right answer.
     *
     */
    public String getAnswerText();

    /**
     * @return Wheter is this answer option is the right answer
     */
    public boolean isRightAnswer();
}
