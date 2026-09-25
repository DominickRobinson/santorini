package com.santorini.browser;

import org.teavm.jso.JSExport;
import com.santorini.game.Game;
import com.santorini.board.Position;
import com.santorini.godcards.*;
import com.santorini.serialization.GameSerializer;

/** Browser entry points; all rules remain in the original game classes. */
public final class BrowserGame {
    private static Game game = new Game();

    @JSExport
    public static String newGame(String player1, String player2) {
        game = new Game();
        game.getPlayer(1).setGodCard(card(player1));
        game.getPlayer(2).setGodCard(card(player2));
        return state();
    }

    @JSExport
    public static String state() { return GameSerializer.toJSON(game).toString(); }

    @JSExport
    public static String tilePress(int x, int y) {
        game.handleTilePress(game.getTileAt(new Position(x, y)));
        return state();
    }

    @JSExport
    public static String skip() { game.trySkip(); return state(); }

    private static GodCard card(String name) {
        return switch (name.toLowerCase()) {
            case "apollo" -> new ApolloCard();
            case "artemis" -> new ArtemisCard();
            case "atlas" -> new AtlasCard();
            case "demeter" -> new DemeterCard();
            case "hephaestus" -> new HephaestusCard();
            case "minotaur" -> new MinotaurCard();
            case "pan" -> new PanCard();
            case "prometheus" -> new PrometheusCard();
            default -> new DefaultLogicCard();
        };
    }
}
