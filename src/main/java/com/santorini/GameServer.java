package com.santorini;

import java.io.IOException;
import org.json.JSONObject;

import com.santorini.game.Game;
import fi.iki.elonen.NanoHTTPD;

public class GameServer extends NanoHTTPD {
    
    private final Game game = new Game();

    public GameServer(int port) throws IOException {
        super(port);
        start(SOCKET_READ_TIMEOUT, false);
        System.out.println("Game running on http://localhost:/" + port + "\n");
    }

    @Override
    public Response serve(IHTTPSession session) {
        String uri = session.getUri();
        Method method = session.getMethod();

        try {

            switch (uri) {
            case "/game-state":
                if (Method.GET.equals(method)) {
                    JSONObject json = game.toJSON();
                    return finishJSONResponse(json, Response.Status.OK);
                }

                break;

            case "/spawn":
                if (Method.POST.equals(method)) {
                    // TODO: add stuff here
                    break;
                }

                break;

            case "/move":
                if (Method.POST.equals(method)) {
                    // TODO: add stuff here
                    break;
                }

                break;

            case "/build":
                if (Method.POST.equals(method)) {
                    // TODO: add stuff here
                    break;
                }

                break;

            default:
                break;

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

}
