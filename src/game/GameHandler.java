package game;

import com.fasterxml.jackson.databind.node.ArrayNode;
import fileio.ActionsInput;
import fileio.GameInput;
import fileio.Input;

import java.util.ArrayList;

public final class GameHandler {
    private final Input input;
    private int playerOneWins;
    private int playerTwoWins;
    private final CommandOutputGenerator outputGenerator = new CommandOutputGenerator();

    private final int manaErr = 1;
    private final int placeErr = 2;
    private final int notTankErr = 3;
    private final int frozenErr = 4;
    private final int attackedErr = 5;
    private final int notEnemyErr = 6;

    public GameHandler(final Input input) {
        this.input = input;
        this.playerOneWins = 0;
        this.playerTwoWins = 0;
    }

    private int checkCardAttack(Game currentGame, ActionsInput gameAction) {
        int err = 0;
        MinionCard attacker = currentGame.
                getCard(gameAction.getCardAttacker().getX(),
                        gameAction.getCardAttacker().getY());
        MinionCard attacked = currentGame.
                getCard(gameAction.getCardAttacked().getX(),
                        gameAction.getCardAttacked().getY());
        if (currentGame.cardExists(gameAction.getCardAttacker().getX(),
                gameAction.getCardAttacker().getY())
                && currentGame.cardExists(gameAction.getCardAttacked().getX(),
                gameAction.getCardAttacked().getY())) {
            
        }
    }
    /**
     * Takes the input associated to the instance and does the appropriate task required by it
     * @param output The output array in JSON format with every required field
     */
    public void handleInput(final ArrayNode output) {
        for (int i = 0; i < input.getGames().size(); i++) {
            GameInput currentGameInput = input.getGames().get(i);
            Game currentGame = new Game(currentGameInput.getStartGame(),
                    input.getPlayerOneDecks(), input.getPlayerTwoDecks());

            ArrayList<ActionsInput> gameActions = currentGameInput.getActions();
            for (ActionsInput gameAction : gameActions) {
                String command = gameAction.getCommand();
                switch (command) {
                    case "placeCard":
                        if (!currentGame.getCurrentPlayer().getHand().isEmpty()) {
                            MinionCard chosenCard = currentGame.getCurrentPlayer().getHand().
                                    get(gameAction.getHandIdx());
                            if (currentGame.checkCardMana(chosenCard)) {
                                if (currentGame.checkCardPlace(chosenCard)) {
                                    currentGame.getCurrentPlayer().
                                            placeCard(gameAction.getHandIdx(), currentGame);
                                } else {
                                    output.add(outputGenerator.
                                            generate(gameAction, currentGame, placeErr));
                                }
                            } else {
                                output.add(outputGenerator.
                                        generate(gameAction, currentGame, manaErr));
                            }
                        }
                        break;
                    case "endPlayerTurn":
                        currentGame.endTurn();
                        break;
                    case "cardUsesAttack":
                        MinionCard attacker = currentGame.
                                getCard(gameAction.getCardAttacker().getX(),
                                        gameAction.getCardAttacker().getY());
                        MinionCard attacked = currentGame.
                                getCard(gameAction.getCardAttacked().getX(),
                                        gameAction.getCardAttacked().getY());
                        if (currentGame.cardExists(gameAction.getCardAttacker().getX(),
                                gameAction.getCardAttacker().getY())
                            && currentGame.cardExists(gameAction.getCardAttacked().getX(),
                                gameAction.getCardAttacked().getY())) {
                            if (currentGame.isEnemy(gameAction.getCardAttacked())) {
                                if (!attacker.hasAttacked()) {
                                   if (!attacker.isFrozen()) {
                                      if (attacked.isTank() || !currentGame.opponentHasTank()) {
                                         attacker.attack(gameAction.getCardAttacked(),
                                                 currentGame.getBoard().
                                                         get(gameAction.getCardAttacked().getX()));
                                       } else {
                                            output.add(outputGenerator.
                                                    generate(gameAction, currentGame, notTankErr));
                                        }
                                } else {
                                      output.add(outputGenerator.
                                              generate(gameAction, currentGame, frozenErr));
                                 }
                                } else {
                                    output.add(outputGenerator.
                                            generate(gameAction, currentGame, attackedErr));
                                }
                            } else {
                                output.add(outputGenerator.
                                        generate(gameAction, currentGame, notEnemyErr));
                            }
                        }
                        break;
                    default:
                            // if the command only requires an output
                            // (doesn't interact with the game)
                            // it gets redirected to the output generator
                            output.add(outputGenerator.generate(gameAction, currentGame, 0));
                }
            }
        }
    }

    /**
     * Increases the amount of games won by PlayerOne
     */
    public void incrementPlayerOneWins() {
        this.playerOneWins++;
    }

    /**
     * Increases the amount of games won by PlayerTwo
     */
    public void incrementPlayerTwoWins() {
        this.playerTwoWins++;
    }

    /**
     * Get the total numbers of games played
     * @return The amount of games played
     */
    public int getGamesPlayed() {
        return this.playerOneWins + this.playerTwoWins;
    }
}
