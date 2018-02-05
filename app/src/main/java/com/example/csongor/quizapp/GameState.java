package com.example.csongor.quizapp;

/**
 * Created by csongor on 2/2/18.
 *
 * Through this interface can game handle different button
 * behaviours depending on game state
 */

public interface GameState {
    /**
     * method for pressing right button
     */
    void doRightButtonAction();

    /**
     * method for pressing left button
     */
    void doLeftButtonAction();
}
