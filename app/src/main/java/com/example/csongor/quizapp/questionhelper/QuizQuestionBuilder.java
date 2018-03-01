package com.example.csongor.quizapp.questionhelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by csongor on 1/31/18.
 * This is the builder class for creating QuizQuestions easily
 */

public class QuizQuestionBuilder {

    private static int sImageResourceId;
    private static String sQuestionText;
    private static List<Answer> sAnswers;
    private static QuizQuestionBuilder sInstance = new QuizQuestionBuilder();

    private QuizQuestionBuilder() {
    }

    /**
     *
     * @return QuizQuestionBuilder sInstance
     * this method initializes a new answer ArrayList
     */
    public static QuizQuestionBuilder getsInstance() {
        sAnswers = new ArrayList<Answer>();
        return sInstance;
    }

    /**
     *
     * @param resourceId the image resource ID (int)
     * @return QuizQuestionBuilder sInstance
     * IMPORTANT: only one ID is allowed, otherwise the previously added ID will be
     * overwritten!
     */
    public static QuizQuestionBuilder addImageResourceId(int resourceId) {
        sImageResourceId = resourceId;
        return sInstance;
    }

    /**
     *
     * @param text the String that has to be displayed
     * @return QuizQuestionBuilder sInstance
     * IMPORTANT: only one question is allowed, otherwise the previously added question will be
     * overwritten!
     */
    public static QuizQuestionBuilder addQuestionText(String text) {
        sQuestionText = text;
        return sInstance;
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
     * IMPORTANT: Only one or four sAnswers must be added due to gameType:<br>
     *                   1 answer with 1 right answer option-> getQuestionType returns STRING_QUESTION<br>
     *                   4 answer with 1 right answer option -> getQuestionType returns RADIO_QUESTION<br>
     *                   4 answer with 2-4 right answer option -> getQuestionType returns CHECKBOX_QUESTION<br>
     */
    public static QuizQuestionBuilder addAnswer(String answerText, boolean isRightAnswer) {
        Answer answerToAdd = new Answer(answerText, isRightAnswer);
        /* because the game handles only maximum 4 questions (checkbox or radioButton), it must
        * checked to avoid throwing exceptions
        */
        if (sAnswers.size() < 5) {
            sAnswers.add(answerToAdd);
        }
        return sInstance;
    }

    /**
     *
     * @return new QuizQuestion. QuestionType is dependent to Answers:
     *                   1 answer with 1 right answer option-> getQuestionType returns STRING_QUESTION
     *                   4 answer with 1 right answer option -> getQuestionType returns RADIO_QUESTION
     *                   4 answer with 2-4 right answer option -> getQuestionType returns CHECKBOX_QUESTION
     */
    public static QuizQuestion build() {
        QuizQuestion toBuild = new QuizQuestion(sImageResourceId, sQuestionText, sAnswers);
        return toBuild;
    }
}
