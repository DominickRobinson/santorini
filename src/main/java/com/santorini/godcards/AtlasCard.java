package com.santorini.godcards;

import com.santorini.board.Tile;
import com.santorini.game.Game;
import com.santorini.game.actions.DefaultSelectAction;
import com.santorini.game.actions.DefaultMoveAction;
import com.santorini.game.actions.AbstractBuildAction;
import com.santorini.game.actions.DefaultBuildAction;
import com.santorini.game.actions.TurnAction;

import java.util.List;

public class AtlasCard extends AbstractGodCard {

    @Override
    protected List<TurnAction> getActionSequence() {
        return List.of(
                new DefaultSelectAction(), 
                new DefaultMoveAction(),
                new AtlasFirstBuild(),
                new AtlasDomeBuild());
    }
    
    private static class AtlasFirstBuild extends AbstractBuildAction {
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

    private static class AtlasDomeBuild extends AbstractBuildAction {
        private final DefaultBuildAction delegate = new DefaultBuildAction();

        @Override
        public String getName() {
            return "Build a Dome";
        }

        @Override
        public boolean isSkippable() {
            return true;
        }

        @Override
        public List<Tile> getValidTiles(Game game) {
            return List.of(AtlasFirstBuild.getFirstTile());
        }

        @Override
        protected void execute(Tile tile, Game game) {
            delegate.attempt(tile, game);
            delegate.attempt(tile, game);
            delegate.attempt(tile, game);
            delegate.attempt(tile, game);
            AtlasFirstBuild.clear();
        }
    }
}
