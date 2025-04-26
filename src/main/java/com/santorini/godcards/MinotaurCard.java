package com.santorini.godcards;

import com.santorini.board.Position;
import com.santorini.board.Tile;
import com.santorini.game.Game;
import com.santorini.game.Worker;
import com.santorini.game.actions.DefaultSelectAction;
import com.santorini.game.actions.AbstractMoveAction;
import com.santorini.game.actions.DefaultBuildAction;
import com.santorini.game.actions.TurnAction;

import java.util.ArrayList;
import java.util.List;

public class MinotaurCard extends AbstractGodCard {

    @Override
    protected List<TurnAction> getActionSequence() {
        return List.of(
            new DefaultSelectAction(),
            new MinotaurMoveAction(),
            new DefaultBuildAction()
        );
    }

    private static class MinotaurMoveAction extends AbstractMoveAction {

        private Position getPushPosition(Tile from, Tile to) {
            int dx = to.getPosition().getX() - from.getPosition().getX();
            int dy = to.getPosition().getY() - from.getPosition().getY();
            return new Position(to.getPosition().getX() + dx, to.getPosition().getY() + dy);
        }

        @Override
        public List<Tile> getValidTiles(Game game) {
            Worker worker = game.getSelectedWorker();
            if (worker == null) return List.of();

            Tile from = worker.getTile();
            List<Tile> adj = game.getBoard().getAdjacentTiles(from);
            List<Tile> valid = new ArrayList<>();

            for (Tile to : adj) {
                int heightDiff = to.getTowerHeight() - from.getTowerHeight();

                if (to.isOccupied()) {
                    Worker target = to.getWorker();
                    if (game.getCurrentPlayer().ownsWorker(target)) continue;

                    Position newPos = getPushPosition(from, to);
                    if (!game.getBoard().isValidPosition(newPos)) continue;

                    Tile pushTile = game.getBoard().getTileAt(newPos);
                    if (pushTile.isOccupied() || pushTile.hasDome()) continue;

                    if (!to.hasDome() && heightDiff <= game.getMaxClimbHeight()) {
                        valid.add(to);
                    }
                } else if (!to.hasDome() && !to.isOccupied() && heightDiff <= game.getMaxClimbHeight()) {
                    valid.add(to);
                }
            }

            return valid;
        }

        @Override
        protected void execute(Tile to, Game game) {
            Worker worker = game.getSelectedWorker();
            if (worker == null) throw new IllegalStateException("No worker selected");

            if (to.isOccupied()) {
                Worker opponent = to.getWorker();
                Tile from = worker.getTile();
                Position pushPos = getPushPosition(from, to);
                Tile pushTile = game.getBoard().getTileAt(pushPos);
                opponent.moveTo(pushTile);
            }

            worker.moveTo(to);
        }
    }
}