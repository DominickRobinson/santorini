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

public class ArtemisCard extends AbstractGodCard {

    private Tile firstMoveFrom = null;

    @Override
    protected List<TurnAction> getActionSequence() {
        return List.of(
            new DefaultSelectAction(),
            new ArtemisMove(true),
            new ArtemisMove(false),
            new DefaultBuildAction()
        );
    }

    private class ArtemisMove extends AbstractMoveAction {
        private final boolean isFirstMove;

        ArtemisMove(boolean isFirstMove) {
            this.isFirstMove = isFirstMove;
        }

        @Override
        public boolean isSkippable() {
            return !isFirstMove;
        }

        @Override
        public List<Tile> getValidTiles(Game game) {
            Worker worker = game.getSelectedWorker();
            if (worker == null) return List.of();

            Tile from = worker.getTile();
            List<Tile> valid = new ArrayList<>();

            for (Tile to : game.getBoard().getAdjacentTiles(from)) {
                int heightDiff = to.getTowerHeight() - from.getTowerHeight();
                boolean validMove = !to.hasDome() && !to.isOccupied() && heightDiff <= game.getMaxClimbHeight();
                if (validMove && (!isFirstMove && to.equals(firstMoveFrom))) continue;
                if (validMove) valid.add(to);
            }

            return valid;
        }

        @Override
        protected void execute(Tile to, Game game) {
            Worker selected = game.getSelectedWorker();
            if (selected == null) throw new IllegalStateException("No worker selected");

            if (isFirstMove) {
                firstMoveFrom = selected.getTile();
            } else {
                firstMoveFrom = null;
            }

            selected.moveTo(to);
        }
    }
}    
