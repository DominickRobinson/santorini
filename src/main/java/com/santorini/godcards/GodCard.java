package com.santorini.godcards;

import com.santorini.game.Game;
import com.santorini.game.Worker;
import com.santorini.game.actions.TurnIterator;

public interface GodCard {

    /**
     * @return An iterator of TurnActions that define this GodCard's turn sequence.
     */
    TurnIterator getTurnIterator();

    /**
     * Checks if the given worker has met the win condition.
     * @param worker The worker to check.
     * @param game The current game state.
     * @return true if the worker has won the game.
     */
    boolean checkWinCondition(Worker worker, Game game);

    /**
     * Ends the current player's turn.
     * @param game The current game state.
     */
    void endTurn(Game game);

}
