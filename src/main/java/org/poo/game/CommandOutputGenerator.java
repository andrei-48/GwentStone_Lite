package org.poo.game;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.fileio.ActionsInput;
import org.poo.fileio.Coordinates;

import java.util.ArrayList;

public class CommandOutputGenerator {
    private final ObjectMapper mapper = new ObjectMapper();

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

    private ArrayNode getFrozenCards(final ArrayList<ArrayList<MinionCard>> board) {
        ArrayNode frozenCards = mapper.createArrayNode();
        for (ArrayList<MinionCard> row : board) {

                for (MinionCard card : row) {
                    if (card.isFrozen()) {
                        frozenCards.add(card.toJson());
                    }
                }
        }
        return frozenCards;
    }

    private ObjectNode coordsNode(final Coordinates coordinates) {
        ObjectNode coordsNode = mapper.createObjectNode();
        coordsNode.put("x", coordinates.getX());
        coordsNode.put("y", coordinates.getY());
        return coordsNode;
    }

    private String errMessage(final int err) {
        return switch (err) {
            case GameHandler.MANA_ERR -> "Not enough mana to place card on table.";
            case GameHandler.PLACE_ERR -> "Cannot place card on table since row is full.";
            case GameHandler.NOT_TANK_ERR -> "Attacked card is not of type 'Tank'.";
            case GameHandler.FROZEN_ERR -> "Attacker card is frozen.";
            case GameHandler.ATTACKED_ERR -> "Attacker card has already attacked this turn.";
            case GameHandler.NOT_ENEMY_ERR -> "Attacked card does not belong to the enemy.";
            case GameHandler.NOT_ALLY_ERR -> "Attacked card does not belong to the current player.";
            case GameHandler.HERO_MANA_ERR -> "Not enough mana to use hero's ability.";
            case GameHandler.HERRO_ATTACKED_ERR -> "Hero has already attacked this turn.";
            case GameHandler.ENEMY_ROW_ERR -> "Selected row does not belong to the enemy.";
            case GameHandler.ALLY_ROW_ERR -> "Selected row does not belong to the current player.";
            default -> "Unknown error.";
        };
    }

    /**
     * Takes the current game state and returns the right message for the winner
     * @param game The game's current state
     * @return The message for the winner
     */
    private String gameWonMessage(final Game game) {
        if (game.getPlayerTurn() == 1) {
            return "Player one killed the enemy hero.";
        } else {
            return "Player two killed the enemy hero.";
        }
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

            case "getFrozenCardsOnTable":
                commandOutput.put("command", "getFrozenCardsOnTable");
                commandOutput.set("output", getFrozenCards(game.getBoard()));
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
                break;

            case "useAttackHero":
                if (err != 0) {
                    commandOutput.put("command", "useAttackHero");
                    commandOutput.set("cardAttacker", coordsNode(action.getCardAttacker()));
                    commandOutput.put("error", errMessage(err));
                } else {
                    commandOutput.put("gameEnded", gameWonMessage(game));
                }
                break;

            case "useHeroAbility":
                commandOutput.put("command", "useHeroAbility");
                commandOutput.put("affectedRow", action.getAffectedRow());
                commandOutput.put("error", errMessage(err));
                break;

            default:
                break;
        }
        return commandOutput;
    }
}
