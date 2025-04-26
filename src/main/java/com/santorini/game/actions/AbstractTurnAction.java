package com.santorini.game.actions;

import com.santorini.board.Tile;
import com.santorini.game.Game;

public abstract class AbstractTurnAction implements TurnAction {

    @Override
    public final boolean attempt(Tile tile, Game game) {
        if (getValidTiles(game).contains(tile)) {
            execute(tile, game);
            return true;
        }
        return false;
    }

    protected abstract void execute(Tile tile, Game game);
}
