package com.example.csongor.quizapp;

import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by csongor on 1/20/18.
 */

public class Question implements QuizQuestion {

    private int questionType, imageResourceId;
    private String question;
    private List<AnswerOption> answerOptions;


    /**
     * @param imageResourceId image in drawable to show
     * @param question        the question that shold be displayed
     * @param answerOptions   the possible answers. The AnswerOption List will determine the QuizQuestionType:
     *                        1: The list has only 1 and only 1 AnswerOption -> STRING_QUESTION
     *                        2: The list has 4 AnswerOption with 1 rightAnswer -> RADIO_QUESTION
     *                        3: The list has 4 AnswerOption with multiple rightAnswer -> CHECKBOX_QUESTION
     */
    public Question(int imageResourceId, String question, List<AnswerOption> answerOptions) {
        this.imageResourceId = imageResourceId;
        this.question = question;
        this.answerOptions = answerOptions;
        this.questionType = setupType();
    }


    /**
     * @return the Type or questionType
     * this is the helper method for constructor
     */
    private int setupType() {
        int rightAnswers = 0;
        for (AnswerOption answer : answerOptions
                ) {
            if (answer.isRightAnswer()) {
                rightAnswers++;
            }
        }
        if (rightAnswers > 1) {
            return CHECKBOX_QUESTION;
        } else if (answerOptions.size() > 1) {
            return RADIO_QUESTION;
        } else return STRING_QUESTION;
    }


    /**
     * @return imageResourceId
     */
    public int getImageResourceId() {
        return imageResourceId;
    }

    /**
     * @return the String for the question.
     */
    public String getQuestion() {
        return question;
    }

    /**
     * @return the list of AnswerOptions.In STRING_QUESTION type this string is the right answer
     */
    public List<AnswerOption> getAnswerOptions() {
        return answerOptions;
    }

    @Override
    public int getQuestionType() {
        return this.questionType;
    }

    /**
     * @return the maximum points (for instance CheckBox questions can worth 2-4 points depending on
     *  the number of right answer options)
     */
    @Override
    public int getMaxPoints() {
        int toReturn=0;
       for (AnswerOption answer:answerOptions
             ) {
            if(answer.isRightAnswer()) toReturn++;
        }
        return toReturn;
    }


}
