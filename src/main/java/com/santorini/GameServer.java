package com.santorini;

import java.io.IOException;
import java.util.Map;
import java.util.HashMap;
import java.util.function.Supplier;

import org.json.JSONObject;

import com.santorini.board.Position;
import com.santorini.board.Tile;
import com.santorini.game.Game;
import com.santorini.game.Player;
import com.santorini.godcards.ApolloCard;
import com.santorini.godcards.ArtemisCard;
import com.santorini.godcards.AtlasCard;
import com.santorini.godcards.DemeterCard;
import com.santorini.godcards.HephaestusCard;
import com.santorini.godcards.HermesCard;
import com.santorini.godcards.MinotaurCard;
import com.santorini.godcards.PanCard;
import com.santorini.godcards.PrometheusCard;
import com.santorini.godcards.DefaultLogicCard;
import com.santorini.godcards.GodCard;
import com.santorini.serialization.GameSerializer;

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
                return finishJSONResponse(GameSerializer.toJSON(game), Response.Status.OK);
            }
                         
            if ("/tile-press".equals(uri) && Method.POST.equals(method)) {

                Map<String, String> body = new HashMap<>();
                session.parseBody(body);
                String requestBody = body.get("postData");
                JSONObject requestJson = new JSONObject(requestBody);

                int x = requestJson.getInt("x");
                int y = requestJson.getInt("y");

                JSONObject result = handleTilePress(x, y);

                return finishJSONResponse(result, Response.Status.OK);
            }

            if ("/skip".equals(uri) && Method.POST.equals(method)) {
                game.trySkip();
                return finishJSONResponse(GameSerializer.toJSON(game), Response.Status.OK);
            }            

            if ("/new-game".equals(uri) && Method.POST.equals(method)) {
                Map<String, String> body = new HashMap<>();
                session.parseBody(body);
                String requestBody = body.get("postData");
                JSONObject requestJson = new JSONObject(requestBody);

                resetGame(requestJson);
                return finishJSONResponse(GameSerializer.toJSON(game), Response.Status.OK);
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

    private JSONObject handleTilePress(int x, int y) throws Exception {
        Tile tile = game.getTileAt(new Position(x, y));
        game.handleTilePress(tile);

        return GameSerializer.toJSON(game);
    }

    private void resetGame(JSONObject config) {
        this.game = new Game();

        String godCard1 = config.optString("player1GodCard", "none").toLowerCase();
        String godCard2 = config.optString("player2GodCard", "none").toLowerCase();
    
        assignGodCard(game.getPlayer(1), godCard1);
        assignGodCard(game.getPlayer(2), godCard2);
    }

    private static final Map<String, Supplier<GodCard>> GOD_CARD_MAP = Map.ofEntries(
        Map.entry("apollo", ApolloCard::new),
        Map.entry("artemis", ArtemisCard::new),
        // Map.entry("athena", AthenaCard::new),
        Map.entry("atlas", AtlasCard::new),
        Map.entry("demeter", DemeterCard::new),
        Map.entry("hephaestus", HephaestusCard::new),
        Map.entry("hermes", HermesCard::new),
        Map.entry("minotaur", MinotaurCard::new),
        Map.entry("pan", PanCard::new),
        Map.entry("prometheus", PrometheusCard::new)
    );

    private void assignGodCard(Player player, String god) {
        player.setGodCard(
            GOD_CARD_MAP.getOrDefault(god.toLowerCase(), DefaultLogicCard::new).get()
        );
    }
}
