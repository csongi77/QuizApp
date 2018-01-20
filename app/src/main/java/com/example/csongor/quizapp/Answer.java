package com.example.csongor.quizapp;

/**
 * Created by csongor on 1/20/18.
 */

public class Answer implements AnswerOption {
    //private fields
    private String answerText;
    private boolean rightAnswer;

    // Ctor


    public Answer(String answerText, boolean rightAnswer) {
        this.answerText = answerText;
        this.rightAnswer = rightAnswer;
    }

    /**
     * @return The String that must be shown at answer option in fragment.
     * Exception: If QuestionType is STRING_QUESTION, than this is the right answer.
     */
    @Override
    public String getAnswerText() {
        return answerText;
    }

    /**
     * @return Wheter is this answer option is the right answer
     */
    @Override
    public boolean isRightAnswer() {
        return rightAnswer;
    }
}
