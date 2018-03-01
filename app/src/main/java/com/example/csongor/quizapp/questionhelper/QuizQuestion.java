package com.example.csongor.quizapp.questionhelper;

import com.example.csongor.quizapp.questionhelper.Answer;

import java.io.Serializable;
import java.util.List;

/**
 * Created by csongor on 1/20/18.
 */

public class QuizQuestion implements Serializable {

    public static final int STRING_QUESTION=0;
    public static final int RADIO_QUESTION=1;
    public static final int CHECKBOX_QUESTION=2;

    private int mQuestionType, mImageResourceId;
    private String mQuestion;
    private List<Answer> mAnswers;


    /**
     * @param imageResourceId image in drawable to show
     * @param question        the mQuestion that shold be displayed
     * @param answers   the possible mAnswers. The AnswerOption List will determine the QuizQuestionType:
     *                        1: The list has 1 and only 1 AnswerOption -> STRING_QUESTION
     *                        2: The list has 4 AnswerOption with 1 rightAnswer -> RADIO_QUESTION
     *                        3: The list has 4 AnswerOption with multiple rightAnswer -> CHECKBOX_QUESTION
     */
    public QuizQuestion(int imageResourceId, String question, List<Answer> answers) {
        this.mImageResourceId = imageResourceId;
        this.mQuestion = question;
        this.mAnswers = answers;
        this.mQuestionType = setupType();
    }

    /**
     * @return mImageResourceId
     */
    public int getImageResourceId() {
        return mImageResourceId;
    }

    /**
     * @return the String for the mQuestion.
     */
    public String getQuestion() {
        return mQuestion;
    }

    /**
     * @return the list of AnswerOptions.In STRING_QUESTION type this string is the right answer
     */
    public List<Answer> getAnswers() {
        return mAnswers;
    }


    /**
     * @return the QuizQuestion type
     */
    public int getQuestionType() {
        return this.mQuestionType;
    }

    /**
     * @return the number of right mAnswers (for instance CheckBox questions can have 2-4 good mAnswers depending on
     *  the number of right answer options) It is necessary at evaluation to compare right and only right
     *  mAnswers
     */

    public int getRightAnswerNumber() {
       int toReturn=0;
       for (Answer answer: mAnswers
             ) {
            if(answer.isRightAnswer()) toReturn++;
        }
        return toReturn;
    }

    /**
     * @return the Type or mQuestionType
     * this is the helper method for constructor
     */
    private int setupType() {
        int rightAnswers = 0;
        for (Answer answer : mAnswers
                ) {
            if (answer.isRightAnswer()) {
                rightAnswers++;
            }
        }
        if (rightAnswers > 1) {
            return CHECKBOX_QUESTION;
        } else if (mAnswers.size() > 1) {
            return RADIO_QUESTION;
        } else return STRING_QUESTION;
    }

}
