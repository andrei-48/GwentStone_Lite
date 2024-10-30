package game;

import fileio.Input;

public class GameHandler {
    private Input input;
    private int playerOneWins;
    private int playerTwoWins;
    private int playerGamesPlayed;

    public GameHandler(Input input) {
        this.input = input;
        this.playerOneWins = 0;
        this.playerTwoWins = 0;
        this.playerGamesPlayed = 0;
    }

    public void incrementPlayerOneWins() {
        this.playerOneWins++;
    }
    public void incrementPlayerTwoWins() {
        this.playerTwoWins++;
    }
}
