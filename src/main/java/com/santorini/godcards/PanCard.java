package com.santorini.godcards;

import com.santorini.board.Tile;
import com.santorini.game.Game;
import com.santorini.game.Worker;
import com.santorini.game.actions.DefaultSelectAction;
import com.santorini.game.actions.AbstractMoveAction;
import com.santorini.game.actions.DefaultBuildAction;
import com.santorini.game.actions.DefaultMoveAction;
import com.santorini.game.actions.TurnAction;

import java.util.List;

public class PanCard extends AbstractGodCard {

    private int heightBeforeMove = -1;

    @Override
    protected List<TurnAction> getActionSequence() {
        return List.of(
            new DefaultSelectAction(),
            new PanMove(),
            new DefaultBuildAction()
        );
    }

    private class PanMove extends AbstractMoveAction {
        private final DefaultMoveAction delegate = new DefaultMoveAction();

        @Override
        public List<Tile> getValidTiles(Game game) {
            return delegate.getValidTiles(game);
        }

        @Override
        protected void execute(Tile to, Game game) {
            heightBeforeMove = game.getSelectedWorker().getTile().getTowerHeight();
            delegate.attempt(to, game);
        }
    }

    @Override
    public boolean checkWinCondition(Worker worker, Game game) {
        int heightAfterMove = worker.getTile().getTowerHeight();
        boolean winByReachingTop = heightAfterMove == game.getWinningHeight();
        boolean winByDropping = (heightBeforeMove - heightAfterMove) >= 2;
        return winByReachingTop || winByDropping;
    }

    @Override
    public void endTurn(Game game) {
        super.endTurn(game);

        heightBeforeMove = -1;
    }
} 
