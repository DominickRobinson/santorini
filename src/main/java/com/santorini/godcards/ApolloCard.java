package com.santorini.godcards;

import com.santorini.board.Tile;
import com.santorini.game.Game;
import com.santorini.game.Worker;
import com.santorini.game.actions.DefaultSelectAction;
import com.santorini.game.actions.DefaultBuildAction;
import com.santorini.game.actions.AbstractMoveAction;
import com.santorini.game.actions.TurnAction;

import java.util.ArrayList;
import java.util.List;

public class ApolloCard extends AbstractGodCard {

    @Override
    protected List<TurnAction> getActionSequence() {
        return List.of(
            new DefaultSelectAction(),
            new ApolloMoveAction(),
            new DefaultBuildAction()
        );
    }

    private static class ApolloMoveAction extends AbstractMoveAction {
        @Override
        public List<Tile> getValidTiles(Game game) {
            Worker worker = game.getSelectedWorker();
            if (worker == null) return List.of();

            Tile from = worker.getTile();
            List<Tile> adj = game.getBoard().getAdjacentTiles(from);
            List<Tile> valid = new ArrayList<>();

            for (Tile to : adj) {
                int heightDiff = to.getTowerHeight() - from.getTowerHeight();
                boolean isOpponent = to.isOccupied() && !game.getCurrentPlayer().ownsWorker(to.getWorker());

                if (!to.hasDome() && heightDiff <= game.getMaxClimbHeight() && (!to.isOccupied() || isOpponent)) {
                    valid.add(to);
                }
            }

            return valid;
        }

        @Override
        protected void execute(Tile to, Game game) {
            Worker selected = game.getSelectedWorker();
            if (selected == null) throw new IllegalStateException("No worker selected");

            if (to.isOccupied()) {
                Worker opponent = to.getWorker();
                selected.swapWith(opponent);
            } else {
                selected.moveTo(to);
            }
        }
    }
}
