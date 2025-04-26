package com.santorini.game.actions;

import com.santorini.board.Tile;
import com.santorini.game.Game;

import java.util.List;

public interface TurnAction {
    String getName();
    boolean isSkippable();
    List<Tile> getValidTiles(Game game);
    boolean attempt(Tile tile, Game game);
}
