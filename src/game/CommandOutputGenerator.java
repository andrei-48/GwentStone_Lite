package game;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.ActionsInput;

import java.util.ArrayList;

public class CommandOutputGenerator {
    private final ObjectMapper mapper = new ObjectMapper();
    private final int manaErr = 1;
    private final int placeErr = 2;

    private ArrayNode getPlayerDeck(Player player) {
        ArrayList<minionCard> deck = player.getDeck();

        ArrayNode playerDeck = mapper.createArrayNode();
        for (minionCard card : deck) {
            playerDeck.add(card.toJson());
        }
        return playerDeck;
    }

    private ArrayNode getPlayerHand(Player player) {
        ArrayList<minionCard> hand = player.getHand();
        ArrayNode playerHand = mapper.createArrayNode();
        for (minionCard card : hand) {
            playerHand.add(card.toJson());
        }
        return playerHand;
    }

    private ArrayNode getCardsOnTable(ArrayList<ArrayList<minionCard>> board) {
        ArrayNode cardsOnTable = mapper.createArrayNode();
        for (ArrayList<minionCard> row : board) {
            ArrayNode cardsOnRow = mapper.createArrayNode();

            for (minionCard card : row) {
                cardsOnRow.add(card.toJson());
            }
            cardsOnTable.add(cardsOnRow);
        }
        return cardsOnTable;
    }

    /**
     * Generates the JSON objectNode for the output of each command (normal output or error)
     * @param action The current action from the input
     * @param game The current game
     * @param err Error code to print appropriate error message
     * @return The JSON objectNode with the required output
     */
    public ObjectNode generate(ActionsInput action, Game game, final int err) {
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
            case "placeCard":
                commandOutput.put("command", "placeCard");
                commandOutput.put("handIdx", action.getHandIdx());
                if (err == manaErr) {
                    commandOutput.put("error", "Not enough mana to place card on table.");
                } else if (err == placeErr)
                    commandOutput.put("error", "Cannot place card on table since row is full.");
                break;
            case "getCardsInHand":
                commandOutput.put("command", "getCardsInHand");
                commandOutput.put("playerIdx", action.getPlayerIdx());
                commandOutput.put("output", getPlayerHand(game.getPlayer(action.getPlayerIdx())));
                break;
            case "getCardsOnTable":
                commandOutput.put("command", "getCardsOnTable");
                commandOutput.put("output", getCardsOnTable(game.getBoard()));
                break;
            case "getPlayerMana":
                commandOutput.put("command", "getPlayerMana");
                commandOutput.put("playerIdx", action.getPlayerIdx());
                commandOutput.put("output", game.getPlayer(action.getPlayerIdx()).getMana());
                break;

        }
        return commandOutput;
    }
}
