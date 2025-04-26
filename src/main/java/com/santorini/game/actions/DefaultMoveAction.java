package com.santorini.game.actions;

import com.santorini.board.Tile;
import com.santorini.game.Game;
import com.santorini.game.Worker;

import java.util.List;
import java.util.ArrayList;

public class DefaultMoveAction extends AbstractMoveAction {

    @Override
    public List<Tile> getValidTiles(Game game) {
        Worker worker = game.getSelectedWorker();
        if (worker == null) return List.of();

        Tile from = worker.getTile();
        List<Tile> adj = game.getBoard().getAdjacentTiles(from);
        List<Tile> valid = new ArrayList<>();

        for (Tile to : adj) {
            int heightDiff = to.getTowerHeight() - from.getTowerHeight();
            if (!to.hasDome() && !to.isOccupied() && heightDiff <= game.getMaxClimbHeight()) {
                valid.add(to);
            }
        }
        return valid;
    }

    @Override
    protected void execute(Tile tile, Game game) {
        Worker worker = game.getSelectedWorker();
        if (worker == null) throw new IllegalStateException("No worker selected before move");
        worker.moveTo(tile);
    }
}
