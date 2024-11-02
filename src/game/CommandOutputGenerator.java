package game;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.ActionsInput;
import fileio.Coordinates;

import java.util.ArrayList;

public class CommandOutputGenerator {
    private final ObjectMapper mapper = new ObjectMapper();
    private final int manaErr = 1;
    private final int placeErr = 2;
    private final int notTankErr = 3;
    private final int frozenErr = 4;
    private final int attackedErr = 5;
    private final int notEnemyErr = 6;
    private final int notAllyErr = 7;

    private ArrayNode getPlayerDeck(final Player player) {
        ArrayList<MinionCard> deck = player.getDeck();

        ArrayNode playerDeck = mapper.createArrayNode();
        for (MinionCard card : deck) {
            playerDeck.add(card.toJson());
        }
        return playerDeck;
    }

    private ArrayNode getPlayerHand(final Player player) {
        ArrayList<MinionCard> hand = player.getHand();
        ArrayNode playerHand = mapper.createArrayNode();
        for (MinionCard card : hand) {
            playerHand.add(card.toJson());
        }
        return playerHand;
    }

    private ArrayNode getCardsOnTable(final ArrayList<ArrayList<MinionCard>> board) {
        ArrayNode cardsOnTable = mapper.createArrayNode();
        for (ArrayList<MinionCard> row : board) {
            ArrayNode cardsOnRow = mapper.createArrayNode();

            for (MinionCard card : row) {
                cardsOnRow.add(card.toJson());
            }
            cardsOnTable.add(cardsOnRow);
        }
        return cardsOnTable;
    }

    private ObjectNode coordsNode(final Coordinates coordinates) {
        ObjectNode coordsNode = mapper.createObjectNode();
        coordsNode.put("x", coordinates.getX());
        coordsNode.put("y", coordinates.getY());
        return coordsNode;
    }

    private String errMessage(final int err) {
        return switch (err) {
            case manaErr -> "Not enough mana to place card on table.";
            case placeErr -> "Cannot place card on table since row is full.";
            case notTankErr -> "Attacked card is not of type 'Tank'.";
            case frozenErr -> "Attacker card is frozen.";
            case attackedErr -> "Attacker card has already attacked this turn.";
            case notEnemyErr -> "Attacked card does not belong to the enemy.";
            case notAllyErr -> "Attacked card does not belong to the current player.";
            default -> "Unknown error.";
        };
    }

    /**
     * Generates the JSON objectNode for the output of each command (normal output or error)
     * @param action The current action from the input
     * @param game The current game
     * @param err Error code to print appropriate error message
     * @return The JSON objectNode with the required output
     */
    public ObjectNode generate(final ActionsInput action, final Game game, final int err) {
        ObjectNode commandOutput = mapper.createObjectNode();
        switch (action.getCommand()) {
            case "getPlayerDeck":
                commandOutput.put("command", "getPlayerDeck");
                commandOutput.put("playerIdx", action.getPlayerIdx());
                commandOutput.set("output", getPlayerDeck(game.getPlayer(action.getPlayerIdx())));
                break;
            case "getPlayerHero":
                commandOutput.put("command", "getPlayerHero");
                commandOutput.put("playerIdx", action.getPlayerIdx());
                commandOutput.set("output",
                        game.getPlayer(action.getPlayerIdx()).getHero().toJson());
                break;
            case "getPlayerTurn":
                commandOutput.put("command", "getPlayerTurn");
                commandOutput.put("output", game.getPlayerTurn());
                break;
            case "placeCard":
                commandOutput.put("command", "placeCard");
                commandOutput.put("handIdx", action.getHandIdx());
                commandOutput.put("error", errMessage(err));
                break;
            case "getCardsInHand":
                commandOutput.put("command", "getCardsInHand");
                commandOutput.put("playerIdx", action.getPlayerIdx());
                commandOutput.set("output", getPlayerHand(game.getPlayer(action.getPlayerIdx())));
                break;
            case "getCardsOnTable":
                commandOutput.put("command", "getCardsOnTable");
                commandOutput.set("output", getCardsOnTable(game.getBoard()));
                break;
            case "getPlayerMana":
                commandOutput.put("command", "getPlayerMana");
                commandOutput.put("playerIdx", action.getPlayerIdx());
                commandOutput.put("output", game.getPlayer(action.getPlayerIdx()).getMana());
                break;
            case "cardUsesAttack":
                commandOutput.put("command", "cardUsesAttack");
                commandOutput.set("cardAttacker", coordsNode(action.getCardAttacker()));
                commandOutput.set("cardAttacked", coordsNode(action.getCardAttacked()));
                commandOutput.put("error", errMessage(err));
                break;
            case "cardUsesAbility":
                commandOutput.put("command", "cardUsesAbility");
                commandOutput.set("cardAttacker", coordsNode(action.getCardAttacker()));
                commandOutput.set("cardAttacked", coordsNode(action.getCardAttacked()));
                commandOutput.put("error", errMessage(err));
                break;
            case "getCardAtPosition":
                commandOutput.put("command", "getCardAtPosition");
                commandOutput.put("x", action.getX());
                commandOutput.put("y", action.getY());
                if (game.cardExists(action.getX(), action.getY())) {
                    commandOutput.set("output",
                            game.getCard(action.getX(), action.getY()).toJson());
                } else {
                    commandOutput.put("output", "No card available at that position.");
                }
            default:
                break;
        }
        return commandOutput;
    }
}
