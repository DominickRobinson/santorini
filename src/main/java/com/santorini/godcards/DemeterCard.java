package com.santorini.godcards;

import com.santorini.board.Tile;
import com.santorini.game.Game;
import com.santorini.game.Worker;
import com.santorini.game.actions.DefaultSelectAction;
import com.santorini.game.actions.DefaultMoveAction;
import com.santorini.game.actions.DefaultBuildAction;
import com.santorini.game.actions.AbstractBuildAction;
import com.santorini.game.actions.TurnAction;


import java.util.List;
import java.util.stream.Collectors;

public class DemeterCard extends AbstractGodCard {

    @Override
    protected List<TurnAction> getActionSequence() {
        return List.of(
            new DefaultSelectAction(),
            new DefaultMoveAction(),
            new DemeterFirstBuild(),
            new DemeterSecondBuild()
        );
    }

    private static class DemeterFirstBuild extends AbstractBuildAction {
        private final DefaultBuildAction delegate = new DefaultBuildAction();
        private static Tile firstTile;

        @Override
        public List<Tile> getValidTiles(Game game) {
            return delegate.getValidTiles(game);
        }

        @Override
        protected void execute(Tile tile, Game game) {
            delegate.attempt(tile, game);
            firstTile = tile;
        }

        public static Tile getFirstTile() {
            return firstTile;
        }

        public static void clear() {
            firstTile = null;
        }
    }

    private static class DemeterSecondBuild extends AbstractBuildAction {
        private final DefaultBuildAction delegate = new DefaultBuildAction();

        @Override
        public boolean isSkippable() {
            return true;
        }

        @Override
        public List<Tile> getValidTiles(Game game) {
            Tile first = DemeterFirstBuild.getFirstTile();
            return delegate.getValidTiles(game).stream()
                .filter(tile -> !tile.equals(first))
                .collect(Collectors.toList());
        }

        @Override
        protected void execute(Tile tile, Game game) {
            Worker worker = game.getSelectedWorker();
            worker.buildAt(tile);
            DemeterFirstBuild.clear();
        }
    }
}
