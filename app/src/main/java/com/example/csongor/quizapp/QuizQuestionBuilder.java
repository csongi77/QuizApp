package com.example.csongor.quizapp;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by csongor on 1/31/18.
 * This is the builder class for creating QuizQuestions easily
 */

public class QuizQuestionBuilder {

    private static int imageResourceId;
    private static String questionText;
    private static List<Answer> answers;
    private static QuizQuestionBuilder instance = new QuizQuestionBuilder();

    private QuizQuestionBuilder() {
    }

    /**
     *
     * @return QuizQuestionBuilder instance
     * this method initializes a new answer ArrayList
     */
    public static QuizQuestionBuilder getInstance() {
        answers = new ArrayList<Answer>();
        return instance;
    }

    /**
     *
     * @param resourceId the image resource ID (int)
     * @return QuizQuestionBuilder instance
     * IMPORTANT: only one ID is allowed, otherwise the previously added ID will be
     * overwritten!
     */
    public static QuizQuestionBuilder addImageResourceId(int resourceId) {
        imageResourceId = resourceId;
        return instance;
    }

    /**
     *
     * @param text the String that has to be displayed
     * @return QuizQuestionBuilder instance
     * IMPORTANT: only one question is allowed, otherwise the previously added question will be
     * overwritten!
     */
    public static QuizQuestionBuilder addQuestionText(String text) {
        questionText = text;
        return instance;
    }

    /**
     *
     * @param answerText - the answer option text that will be displayed in case of checkBox or radioButton
     *                   quizQuestion. If the quizQuestion has only one Answer then this text will be
     *                   compared to players' answer.
     *
     * @param isRightAnswer - boolean for evaluation. This will be compared to players' answer.
     *                      If there is one and only one answer in the list, the answer _must_be_true!_
     * @return QuizQuestionBuilder instance <br>
     * IMPORTANT: Only one or four answers must be added due to gameType:<br>
     *                   1 answer with 1 right answer option-> getQuestionType=STRING_QUESTION<br>
     *                   4 answer with 1 right answer option -> getQuestionType=RADIO_QUESTION<br>
     *                   4 answer with 2-4 right answer option -> getQuestionType=CHECKBOX_QUESTION<br>
     */
    public static QuizQuestionBuilder addAnswer(String answerText, boolean isRightAnswer) {
        Answer answerToAdd = new Answer(answerText, isRightAnswer);
        /* because the game handles only maximum 4 questions (checkbox or radioButton), it must
        * checked to avoid throving exceptions
        */
        if (answers.size() < 5) {
            answers.add(answerToAdd);
        }
        return instance;
    }

    /**
     *
     * @return new QuizQuestion. QuestionType is dependent to Answers:
     *                   1 answer with 1 right answer option-> getQuestionType=STRING_QUESTION
     *                   4 answer with 1 right answer option -> getQuestionType=RADIO_QUESTION
     *                   4 answer with 2-4 right answer option -> getQuestionType=CHECKBOX_QUESTION
     */
    public static QuizQuestion build() {
        QuizQuestion toBuild = new QuizQuestion(imageResourceId, questionText, answers);
        return toBuild;
    }
}
