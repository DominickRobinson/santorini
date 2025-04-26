package com.santorini.godcards;

import com.santorini.game.Game;
import com.santorini.game.Worker;
import com.santorini.game.actions.DefaultBuildAction;
import com.santorini.game.actions.DefaultMoveAction;
import com.santorini.game.actions.DefaultSelectAction;
import com.santorini.game.actions.TurnIterator;

import java.util.List;

/**
 * The default GodCard logic used when no god ability is selected.
 * Implements a basic turn: Move -> Build
 */
public class DefaultLogicCard implements GodCard {

    private final TurnIterator turnIterator;

    public DefaultLogicCard() {
        this.turnIterator = new TurnIterator(List.of(
            new DefaultSelectAction(),
            new DefaultMoveAction(),
            new DefaultBuildAction()
        ));
    }

    @Override
    public TurnIterator getTurnIterator() {
        return turnIterator;
    }

    @Override
    public boolean checkWinCondition(Worker worker, Game game) {
        return worker.onTowerWithHeight(game.getWinningHeight());
    }

    @Override
    public void endTurn(Game game) {
        game.setSelectedWorker(null);
        game.nextTurn();
    }
}
