package game;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.ActionsInput;
import fileio.GameInput;
import fileio.Input;

import java.util.ArrayList;

public final class GameHandler {
    private final Input input;
    private int playerOneWins;
    private int playerTwoWins;
    private final CommandOutputGenerator outputGenerator = new CommandOutputGenerator();

    public static final int MANA_ERR = 1;
    public static final int PLACE_ERR = 2;
    public static final int NOT_TANK_ERR = 3;
    public static final int FROZEN_ERR = 4;
    public static final int ATTACKED_ERR = 5;
    public static final int NOT_ENEMY_ERR = 6;
    public static final int NOT_ALLY_ERR = 7;
    public static final int HERO_MANA_ERR = 8;
    public static final int HERRO_ATTACKED_ERR = 9;
    public static final int ENEMY_ROW_ERR = 10;
    public static final int ALLY_ROW_ERR = 11;

    public GameHandler(final Input input) {
        this.input = input;
        this.playerOneWins = 0;
        this.playerTwoWins = 0;
    }

    /**
     * Checks every possible error for placing a cart and return the right error code
     * @param currentGame The game in its current state
     * @param gameAction The current action
     * @return The error code if there is an error with the card placement
     * or 0 if the card can be placed
     */
    private int checkCardPlacement(final Game currentGame, final ActionsInput gameAction) {
        if (!currentGame.getCurrentPlayer().getHand().isEmpty()) {
            MinionCard chosenCard = currentGame.getCurrentPlayer().getHand().
                    get(gameAction.getHandIdx());
            if (!currentGame.checkCardMana(chosenCard)) {
                return MANA_ERR;
            }
            if (!currentGame.checkCardPlace(chosenCard)) {
                return PLACE_ERR;
            }
            return 0;
        }
        return -1;
    }

    /**
     * Checks every possible error for a card attack and returns the right error code
     * @param currentGame The game in its current state
     * @param gameAction The current action
     * @return The error code if there is an error with the attack or 0 if the attack can be done
     */
    private int checkCardAttack(final Game currentGame, final ActionsInput gameAction) {
        MinionCard attacker = currentGame.
                getCard(gameAction.getCardAttacker().getX(),
                        gameAction.getCardAttacker().getY());
        MinionCard attacked = currentGame.
                getCard(gameAction.getCardAttacked().getX(),
                        gameAction.getCardAttacked().getY());
        if (attacker != null && attacked != null) {
            if (!currentGame.isEnemy(gameAction.getCardAttacked())) {
                return NOT_ENEMY_ERR;
            }
            if (attacker.hasAttacked()) {
                return ATTACKED_ERR;
            }
            if (attacker.isFrozen()) {
                return FROZEN_ERR;
            }
            if (!attacked.isTank() && currentGame.opponentHasTank()) {
                return NOT_TANK_ERR;
            }
            return 0;
        }
        return -1;
    }

    /**
     * Checks every possible error for a card ability use and returns the right error code
     * @param currentGame The game in its current state
     * @param gameAction The current action
     * @return The error code if there is an error with the ability use
     * or 0 if the ability can be used
     */
    private int checkCardAbility(final Game currentGame, final ActionsInput gameAction) {
        MinionCard attacker = currentGame.
                getCard(gameAction.getCardAttacker().getX(),
                        gameAction.getCardAttacker().getY());
        MinionCard attacked = currentGame.
                getCard(gameAction.getCardAttacked().getX(),
                        gameAction.getCardAttacked().getY());
        if (attacker != null && attacked != null) {
            if (attacker.isFrozen()) {
                return FROZEN_ERR;
            }
            if (attacker.hasAttacked()) {
                return ATTACKED_ERR;
            }
            if (attacker.getName().equals("Disciple")) {
                if (currentGame.isEnemy(gameAction.getCardAttacked())) {
                    return NOT_ALLY_ERR;
                }
            } else {
                if (!currentGame.isEnemy(gameAction.getCardAttacked())) {
                    return NOT_ENEMY_ERR;
                }
                if (!attacked.isTank() && currentGame.opponentHasTank()) {
                    return NOT_TANK_ERR;
                }
            }
            return 0;
        }
        return -1;
    }

    /**
     * Checks every possible error for a card attack on enemy hero and returns the right error code
     * @param currentGame The game in its current state
     * @param gameAction The current action
     * @return The error code if there is an error with the attack or 0 if the attack can be done
     */
    private int checkHeroAttack(final Game currentGame, final ActionsInput gameAction) {
        MinionCard attacker = currentGame.
                getCard(gameAction.getCardAttacker().getX(),
                        gameAction.getCardAttacker().getY());
        if (attacker != null) {
            if (attacker.isFrozen()) {
                return FROZEN_ERR;
            }
            if (attacker.hasAttacked()) {
                return ATTACKED_ERR;
            }
            if (currentGame.opponentHasTank()) {
                return NOT_TANK_ERR;
            }
            return 0;
        }
        return -1;
    }

    private boolean checkGameEnded(final Game currentGame) {
        return currentGame.getOpponentHero().getHealth() <= 0;
    }

    private int checkHeroAbility(final Game currentGame, final ActionsInput gameAction) {
        Hero playerHero = currentGame.getCurrentPlayer().getHero();
        if (!currentGame.checkHeroMana()) {
            return HERO_MANA_ERR;
        }
        if (playerHero.hasAttacked()) {
            return HERRO_ATTACKED_ERR;
        }
        if (playerHero.getName().equals("Lord Royce")
                || playerHero.getName().equals("Empress Thorina")) {
            if (!currentGame.isEnemyRow(gameAction.getAffectedRow())) {
                return ENEMY_ROW_ERR;
            }
        } else {
            if (currentGame.isEnemyRow(gameAction.getAffectedRow())) {
                return ALLY_ROW_ERR;
            }
        }
        return 0;
    }

    /**
     * Takes the input associated to the instance and does the appropriate task required by it
     * @param output The output array in JSON format with every required field
     */
    public void handleInput(final ArrayNode output) {
        int error;
        for (int i = 0; i < input.getGames().size(); i++) {
            GameInput currentGameInput = input.getGames().get(i);
            Game currentGame = new Game(currentGameInput.getStartGame(),
                    input.getPlayerOneDecks(), input.getPlayerTwoDecks());

            ArrayList<ActionsInput> gameActions = currentGameInput.getActions();
            for (ActionsInput gameAction : gameActions) {
                String command = gameAction.getCommand();
                switch (command) {
                    case "placeCard":
                        error = checkCardPlacement(currentGame, gameAction);
                        if (error == 0) {
                            currentGame.getCurrentPlayer().
                                    placeCard(gameAction.getHandIdx(), currentGame);
                        } else {
                            output.add(outputGenerator.generate(gameAction, currentGame, error));
                        }
                        break;

                    case "endPlayerTurn":
                        currentGame.endTurn();
                        break;

                    case "cardUsesAttack":
                        error = checkCardAttack(currentGame, gameAction);
                        if (error == 0) {
                            currentGame.getCard(gameAction.getCardAttacker().getX(),
                                            gameAction.getCardAttacker().getY()).
                                    attack(gameAction.getCardAttacked(),
                                        currentGame.getBoard().
                                            get(gameAction.getCardAttacked().getX()));
                        } else {
                            output.add(outputGenerator.generate(gameAction, currentGame, error));
                        }
                        break;

                    case "cardUsesAbility":
                        error = checkCardAbility(currentGame, gameAction);
                        if (error == 0) {
                            currentGame.getCard(gameAction.getCardAttacker().getX(),
                                            gameAction.getCardAttacker().getY()).
                                    useAbility(gameAction.getCardAttacked(),
                                            currentGame.getBoard().
                                                    get(gameAction.getCardAttacked().getX()));
                        } else {
                            output.add(outputGenerator.generate(gameAction, currentGame, error));
                        }
                        break;

                    case "useAttackHero":
                        error = checkHeroAttack(currentGame, gameAction);
                        if (error == 0) {
                            currentGame.getCard(gameAction.getCardAttacker().getX(),
                                    gameAction.getCardAttacker().getY()).
                                    attackHero(currentGame.getOpponentHero());
                            if (checkGameEnded(currentGame)) {
                                incrementWins(currentGame.getPlayerTurn());
                                output.add(outputGenerator.generate(gameAction, currentGame, 0));
                            }
                        } else {
                            output.add(outputGenerator.generate(gameAction, currentGame, error));
                        }
                        break;

                    case "useHeroAbility":
                        error = checkHeroAbility(currentGame, gameAction);
                        if (error == 0) {
                            currentGame.getCurrentPlayer().useHeroAbility(currentGame.
                                    getBoard().get(gameAction.getAffectedRow()));
                        } else {
                            output.add(outputGenerator.generate(gameAction, currentGame, error));
                        }
                        break;

                    case "getTotalGamesPlayed":
                        output.add(gamesPlayed());
                        break;

                    case "getPlayerOneWins":
                        output.add(playerWins(1));
                        break;
                    case "getPlayerTwoWins":
                        output.add(playerWins(2));
                        break;

                    default:
                            // if the command only requires an output
                            // (doesn't interact with the game)
                            // it gets redirected to the output generator without error
                            output.add(outputGenerator.generate(gameAction, currentGame, 0));
                }
            }
        }
    }

    /**
     * Increases the amount of games won by the current player
     * @param currentPlayer The index of the current player (1 or 2)
     */
    public void incrementWins(final int currentPlayer) {
        if (currentPlayer == 1) {
            this.playerOneWins++;
        } else {
            this.playerTwoWins++;
        }
    }

    /**
     * Get the total numbers of games played
     * @return The amount of games played
     */
    public int getGamesPlayed() {
        return this.playerOneWins + this.playerTwoWins;
    }

    private ObjectNode gamesPlayed() {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode gamesPlayed = mapper.createObjectNode();
        gamesPlayed.put("command", "getTotalGamesPlayed");
        gamesPlayed.put("output", getGamesPlayed());
        return gamesPlayed;
    }

    private ObjectNode playerWins(final int idx) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode playerWins = mapper.createObjectNode();
        if (idx == 1) {
            playerWins.put("command", "getPlayerOneWins");
            playerWins.put("output", this.playerOneWins);
        } else {
            playerWins.put("command", "getPlayerTwoWins");
            playerWins.put("output", this.playerTwoWins);
        }
        return playerWins;
    }
}
