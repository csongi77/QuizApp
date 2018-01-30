package com.example.csongor.quizapp;

import java.io.Serializable;

/**
 * Created by csongor on 1/20/18.
 */

public class Answer implements Serializable {
    //private fields
    private String answerText;
    private boolean rightAnswer;

    /**
     * Ctor
     * @param answerText String that must be shown. In case STRING_QUESTION this is the right answer
     * @param rightAnswer true if this is the right answer, else false
     */
    public Answer(String answerText, boolean rightAnswer) {
        this.answerText = answerText;
        this.rightAnswer = rightAnswer;
    }

    /**
     * @return The String that must be shown at answer option in fragment.
     * Exception: If QuestionType is STRING_QUESTION, than this is the right answer.
     */
    public String getAnswerText() {
        return answerText;
    }

    /**
     * @return Wheter is this answer option is the right answer
     */
    public boolean isRightAnswer() {
        return rightAnswer;
    }
}
