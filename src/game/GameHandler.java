package game;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.ActionsInput;
import fileio.GameInput;
import fileio.Input;

import java.util.ArrayList;

public class GameHandler {
    private Input input;
    private int playerOneWins;
    private int playerTwoWins;
    private final CommandOutputGenerator outputGenerator = new CommandOutputGenerator();

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
                        break;
                        case "endPlayerTurn":
                            currentGame.endTurn();
                            break;
                        default:
                            output.add(outputGenerator.generate(gameAction, currentGame));
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
