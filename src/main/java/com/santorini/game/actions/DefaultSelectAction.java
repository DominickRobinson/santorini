package com.santorini.game.actions;

import com.santorini.board.Tile;
import com.santorini.game.Game;
import com.santorini.game.Worker;

import java.util.List;
import java.util.ArrayList;

public class DefaultSelectAction extends AbstractSelectAction {

    @Override
    public List<Tile> getValidTiles(Game game) {
        List<Tile> validTiles = new ArrayList<>();
        for (Worker worker : game.getCurrentPlayer().getWorkers()) {
            Tile tile = worker.getTile();
            for (Tile adj : game.getBoard().getAdjacentTiles(tile)) {
                if (!adj.hasDome() && (!adj.isOccupied() || !game.getCurrentPlayer().ownsWorker(adj.getWorker()))) {
                    validTiles.add(tile);
                    break;
                }
            }
        }
        return validTiles;
    }

    @Override
    protected void execute(Tile tile, Game game) {
        if (tile.getWorker() != null && game.getCurrentPlayer().ownsWorker(tile.getWorker())) {
            game.setSelectedWorker(tile.getWorker());
        }
    }
}