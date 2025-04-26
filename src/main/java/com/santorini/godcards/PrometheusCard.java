package com.santorini.godcards;

import com.santorini.board.Tile;
import com.santorini.game.Game;
import com.santorini.game.actions.DefaultSelectAction;
import com.santorini.game.actions.DefaultMoveAction;
import com.santorini.game.actions.AbstractMoveAction;
import com.santorini.game.actions.DefaultBuildAction;
import com.santorini.game.actions.AbstractBuildAction;
import com.santorini.game.actions.TurnAction;

import java.util.List;
import java.util.stream.Collectors;

public class PrometheusCard extends AbstractGodCard {

    @Override
    protected List<TurnAction> getActionSequence() {
        return List.of(
                new DefaultSelectAction(),
                new PrometheusFirstBuild(), 
                new PrometheusMove(), 
                new DefaultBuildAction()
        );
    }
    
    private class PrometheusFirstBuild extends AbstractBuildAction {
        private final DefaultBuildAction delegate = new DefaultBuildAction();
        private int initialHeight = -1;
        private boolean executed = false;

        @Override
        public boolean isSkippable() {
            return true;
        }

        @Override
        public List<Tile> getValidTiles(Game game) {
            return delegate.getValidTiles(game);
        }

        @Override
        protected void execute(Tile to, Game game) {
            Tile initialTile = game.getSelectedWorker().getTile();
            delegate.attempt(to, game);
            executed = true;
            initialHeight = initialTile.getTowerHeight();
        }

        public boolean hasBuilt() {
            return executed;
        }
   
        public int getInitialHeight() {
            return initialHeight;
        }

        public void reset() {
            executed = false;
            initialHeight = -1;
        }
    }

    private class PrometheusMove extends AbstractMoveAction {
        private final DefaultMoveAction delegate = new DefaultMoveAction();

        @Override
        public List<Tile> getValidTiles(Game game) {
            PrometheusFirstBuild firstBuild = findFirstBuild();
            if (firstBuild == null || !firstBuild.hasBuilt()) {
                return delegate.getValidTiles(game);
            }

            int baseHeight = firstBuild.getInitialHeight();
            return delegate.getValidTiles(game).stream().filter(tile -> tile.getTowerHeight() <= baseHeight)
                    .collect(Collectors.toList());
        }

        @Override
        protected void execute(Tile to, Game game) {
            delegate.attempt(to, game);

        }

        private PrometheusFirstBuild findFirstBuild() {
            return getTurnIterator().getAllActions().stream().filter(a -> a instanceof PrometheusFirstBuild)
                    .map(a -> (PrometheusFirstBuild) a).findFirst().orElse(null);
        }
    }
    
    @Override
    public void endTurn(Game game) {
        super.endTurn(game);

        getTurnIterator().getAllActions().stream()
            .filter(a -> a instanceof PrometheusFirstBuild)
            .map(a -> (PrometheusFirstBuild) a)
            .forEach(PrometheusFirstBuild::reset);
    }
}    
