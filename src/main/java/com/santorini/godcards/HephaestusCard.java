package com.santorini.godcards;

import com.santorini.board.Tile;
import com.santorini.game.Game;
import com.santorini.game.actions.DefaultSelectAction;
import com.santorini.game.actions.DefaultMoveAction;
import com.santorini.game.actions.DefaultBuildAction;
import com.santorini.game.actions.AbstractBuildAction;
import com.santorini.game.actions.TurnAction;

import java.util.List;

public class HephaestusCard extends AbstractGodCard {

    @Override
    protected List<TurnAction> getActionSequence() {
        return List.of(
            new DefaultSelectAction(),
            new DefaultMoveAction(),
            new HephaestusFirstBuild(),
            new HephaestusSecondBuild()
        );
    }

    private static class HephaestusFirstBuild extends AbstractBuildAction {
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

    private static class HephaestusSecondBuild extends AbstractBuildAction {
        private final DefaultBuildAction delegate = new DefaultBuildAction();

        @Override
        public boolean isSkippable() {
            return true;
        }

        @Override
        public List<Tile> getValidTiles(Game game) {
            Tile first = HephaestusFirstBuild.getFirstTile();
            if (first == null)
                return List.of();
            if (first.getTowerHeight() == game.getWinningHeight())
                return List.of();
            return List.of(first);
        }

        @Override
        protected void execute(Tile tile, Game game) {
            delegate.attempt(tile, game);
            HephaestusFirstBuild.clear();
        }
    }
}
