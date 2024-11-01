package game;

import com.fasterxml.jackson.databind.node.ArrayNode;
import fileio.ActionsInput;
import fileio.GameInput;
import fileio.Input;

import java.util.ArrayList;

public class GameHandler {
    private final Input input;
    private int playerOneWins;
    private int playerTwoWins;
    private final CommandOutputGenerator outputGenerator = new CommandOutputGenerator();

    private final int manaErr = 1;
    private final int placeErr = 2;

    public GameHandler(Input input) {
        this.input = input;
        this.playerOneWins = 0;
        this.playerTwoWins = 0;
    }

    public void handleInput(ArrayNode output) {
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
                            minionCard chosenCard = currentGame.getCurrentPlayer().getHand().
                                    get(gameAction.getHandIdx());
                            if (currentGame.checkCardMana(chosenCard)) {
                                if (currentGame.checkCardPlace(chosenCard)) {
                                    currentGame.getCurrentPlayer().placeCard(gameAction.getHandIdx(), currentGame);
                                } else {
                                    output.add(outputGenerator.generate(gameAction, currentGame, placeErr));
                                }
                            } else {
                                output.add(outputGenerator.generate(gameAction, currentGame, manaErr));
                            }
                        }
                        break;
                    case "endPlayerTurn":
                        currentGame.endTurn();
                        break;
                    default:
                            // if the command only requires an output (doesn't interact with the game)
                            // it gets redirected to the output generator
                            output.add(outputGenerator.generate(gameAction, currentGame, 0));
                }
            }
        }
    }

    public void incrementPlayerOneWins() {
        this.playerOneWins++;
    }

    public void incrementPlayerTwoWins() {
        this.playerTwoWins++;
    }

    public int getGamesPlayed() {
        return this.playerOneWins + this.playerTwoWins;
    }
}
