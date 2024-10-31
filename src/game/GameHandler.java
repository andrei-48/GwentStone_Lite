package game;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import fileio.ActionsInput;
import fileio.GameInput;
import fileio.Input;

import java.util.ArrayList;

public class GameHandler {
    private Input input;
    private int playerOneWins;
    private int playerTwoWins;

    public GameHandler(Input input) {
        this.input = input;
        this.playerOneWins = 0;
        this.playerTwoWins = 0;
    }

    private ArrayNode getPlayerDeck(Player player) {
        ArrayList<Card> deck = player.getDeck();
        ObjectMapper mapper = new ObjectMapper();

        ArrayNode playerDeck = mapper.createArrayNode();
        for (Card card : deck) {
            playerDeck.add(card.toJson());
        }
        return playerDeck;
    }

    public void handleInput(ArrayNode output) {
        for (int i = 0; i < input.getGames().size(); i++) {
            GameInput currentGameInput = input.getGames().get(i);
            Game currentGame = new Game(currentGameInput.getStartGame(),
                    input.getPlayerOneDecks(), input.getPlayerOneDecks());

            ArrayList<ActionsInput> gameActions = currentGameInput.getActions();
            for (ActionsInput gameAction : gameActions) {
                String command = gameAction.getCommand();
                switch (command) {
                    case "GetPlayerDeck":
                        output.add(getPlayerDeck(currentGame.getPlayer(gameAction.getPlayerIdx())));
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
