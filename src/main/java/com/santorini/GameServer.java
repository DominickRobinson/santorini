package com.santorini;

import java.io.IOException;
import java.util.Map;
import java.util.HashMap;

import org.json.JSONObject;

import com.santorini.board.Position;
import com.santorini.board.Tile;
import com.santorini.game.Game;
import com.santorini.game.Game.Phase;

import fi.iki.elonen.NanoHTTPD;

public class GameServer extends NanoHTTPD {
    
    private Game game = new Game();

    public GameServer(int port) throws IOException {
        super(port);
        start(SOCKET_READ_TIMEOUT, false);
        System.out.println("Game running on http://localhost:" + port + "\n");
    }

    @Override
    public Response serve(IHTTPSession session) {
        String uri = session.getUri();
        Method method = session.getMethod();

        System.out.println("Received request: " + session.getUri());

        try {

            if (Method.OPTIONS.equals(method)) {
                Response res = newFixedLengthResponse(Response.Status.OK, "application/json", "");
                res.addHeader("Access-Control-Allow-Origin", "*");
                res.addHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
                res.addHeader("Access-Control-Allow-Headers", "Content-Type");
                return res;
            }

            if ("/game-state".equals(uri) && Method.GET.equals(method)) {
                return finishJSONResponse(game.toJSON(), Response.Status.OK);
            }
                         
            if ("/tile-press".equals(uri) && Method.POST.equals(method)) {

                System.out.println(" should press tile...");

                Map<String, String> body = new HashMap<>();
                session.parseBody(body);
                String requestBody = body.get("postData");

                JSONObject requestJson = new JSONObject(requestBody);
                int x = requestJson.getInt("x");
                int y = requestJson.getInt("y");

                System.out.println(" should handle tile press...");

                JSONObject result = handleTilePress(x, y);

                return finishJSONResponse(result, Response.Status.OK);
            }

            if ("/new-game".equals(uri) && Method.POST.equals(method)) {
                resetGame();
                return finishJSONResponse(game.toJSON(), Response.Status.OK);
            }

            JSONObject error = new JSONObject();
            error.put("error", "Not Found");
    
            return finishJSONResponse(error, Response.Status.NOT_FOUND);

        } catch (Exception e) {

            JSONObject error = new JSONObject();
            error.put("error", e.toString());

            return finishJSONResponse(error, Response.Status.INTERNAL_ERROR);

        }
    
    }

    private Response finishJSONResponse(JSONObject json, Response.Status status) {
        Response res = newFixedLengthResponse(status, "application/json", json.toString());
        res.addHeader("Access-Control-Allow-Origin", "*");
        return res;
    }

    private JSONObject handleTilePress(int x, int y) {

        Tile tile = game.getTileAt(new Position(x, y));
        Game.Phase phase = game.getPhase();

        System.out.println("handleTilePress()");

        if (phase == Phase.SPAWN) {
            System.out.print(" SPAWN...");
            boolean spawned = game.trySpawn(tile);
            if (spawned)
                System.out.println("  successful!");
            else
                System.out.println("  failed!");
        } else if (phase == Phase.SELECT) {
            System.out.print(" SELECT...");
            boolean selected = game.trySelect(tile);
            if (selected)
                System.out.println("  successful!");
            else
                System.out.println("  failed!");
        } else if (phase == Phase.MOVE) {
            System.out.print(" MOVE...");
            if (game.getSelectedWorker() != null) {
                System.out.print(" (player selected) ");
                boolean moved = game.tryMove(tile);
                if (moved)
                    System.out.println("  successful!");
                else
                    System.out.println("  failed!");
            } else {
                System.out.print(" (no player selected) ");
                if (game.getCurrentPlayer().ownsWorker(tile.getWorker())) {
                    game.setSelectedWorker(tile.getWorker());
                }
            }
        } else if (phase == Phase.BUILD) {
            System.out.print(" BUILD...");
            boolean built = game.tryBuild(tile);
            if (built)
                System.out.println("  successful!");
            else
                System.out.println("  failed!");
        }

        return game.toJSON();
    }

    private void resetGame() {
        this.game = new Game();
    }

}
