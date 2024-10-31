package game;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.ActionsInput;

import java.util.ArrayList;

public class CommandOutputGenerator {
    private final ObjectMapper mapper = new ObjectMapper();

    private ArrayNode getPlayerDeck(Player player) {
        ArrayList<Card> deck = player.getDeck();

        ArrayNode playerDeck = mapper.createArrayNode();
        for (Card card : deck) {
            playerDeck.add(card.toJson());
        }
        return playerDeck;
    }
    public ObjectNode generate(ActionsInput action, Game game) {
        ObjectNode commandOutput = mapper.createObjectNode();
        switch (action.getCommand()) {
            case "getPlayerDeck":
                commandOutput.put("command", "getPlayerDeck");
                commandOutput.put("playerIdx", action.getPlayerIdx());
                commandOutput.put("output", getPlayerDeck(game.getPlayer(action.getPlayerIdx())));
                break;
            case "getPlayerHero":
                commandOutput.put("command", "getPlayerHero");
                commandOutput.put("playerIdx", action.getPlayerIdx());
                commandOutput.put("output", game.getPlayer(action.getPlayerIdx()).getHero().toJson());
                break;
            case "getPlayerTurn":
                commandOutput.put("command", "getPlayerTurn");
                commandOutput.put("output", game.getPlayerTurn());
                break;
        }
        return commandOutput;
    }
}
