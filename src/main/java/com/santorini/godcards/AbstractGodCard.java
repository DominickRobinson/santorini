package com.santorini.godcards;

import com.santorini.game.Game;
import com.santorini.game.actions.TurnIterator;
import com.santorini.game.Worker;
import com.santorini.game.actions.TurnAction;

import java.util.List;

public abstract class AbstractGodCard implements GodCard {

    private final TurnIterator turnIterator;

    public AbstractGodCard() {
        this.turnIterator = new TurnIterator(getActionSequence());
    }

    protected abstract List<TurnAction> getActionSequence();

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
        game.nextTurn();
    }
}
