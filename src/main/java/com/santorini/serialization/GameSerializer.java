package com.santorini.serialization;

import org.json.JSONArray;
import org.json.JSONObject;

import com.santorini.board.Position;
import com.santorini.board.Tile;
import com.santorini.game.Game;
import com.santorini.game.Phase;
import com.santorini.game.Player;
import com.santorini.game.Worker;
import com.santorini.game.actions.TurnAction;

public class GameSerializer {
    
    /**
     * @param game The game to convert to JSON format.
     * @return The game state in JSON format.
     */
    public static JSONObject toJSON(Game game) {
        JSONObject gameJson = new JSONObject();
        gameJson.put("currentPlayer", game.getCurrentPlayer().getID());
        gameJson.put("phase", game.getPhase().toString().toLowerCase());
        gameJson.put("instruction", game.getCurrentInstruction());
        gameJson.put("canSkip", game.canSkip());
        gameJson.put("winner", game.isGameWon() ? game.getWinner().getID() : JSONObject.NULL);

        TurnAction action = game.getCurrentAction();

        JSONArray boardJson = new JSONArray();
        for (int y = 0; y < game.getBoard().getHeight(); y++) {
            JSONArray row = new JSONArray();
            for (int x = 0; x < game.getBoard().getWidth(); x++) {
                Tile tile = game.getBoard().getTileAt(new Position(x, y));
                JSONObject tileJson = new JSONObject();

                tileJson.put("x", x);
                tileJson.put("y", y);
                tileJson.put("height", tile.getTowerHeight());
                tileJson.put("hasDome", tile.hasDome());

                Worker worker = tile.getWorker();
                if (worker != null) {
                    Player owner = game.getWorkerOwner(worker);
                    tileJson.put("worker", owner.getID());
                } else {
                    tileJson.put("worker", JSONObject.NULL);
                }

                if (game.getPhase() == Phase.SPAWN && game.isValidSpawn(game.getCurrentPlayer(), tile)) {
                    tileJson.put("isValidSpawn", true);
                } else if (game.getPhase() == Phase.TURN_ACTION && !game.isGameWon()) {
                    if (action != null && action.getValidTiles(game).contains(tile)) {
                        tileJson.put("isValidAction", true);
                    }

                    Worker selectedWorker = game.getSelectedWorker();
                    if (selectedWorker != null && selectedWorker.getTile().equals(tile)) {
                        tileJson.put("hasSelectedWorker", true);
                    }
                }

                if (game.isGameWon() && game.getSelectedWorker() != null
                        && game.getSelectedWorker().getTile().equals(tile)) {
                    tileJson.put("isWinningTile", true);
                }
                

                row.put(tileJson);
            }
            boardJson.put(row);
        }

        gameJson.put("board", boardJson);
        
        return gameJson;
    }
}
