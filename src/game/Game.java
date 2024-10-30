package game;

import fileio.DecksInput;
import fileio.StartGameInput;

import java.util.ArrayList;
import java.util.Random;

import static java.util.Collections.shuffle;

public class Game {
    private ArrayList<Card>[] rows;
    private int startingPlayer;
    private int currentRound;
    private int currentPlayer;
    private Player playerOne;
    private Player playerTwo;
    private ArrayList<Card> playerOneDeck;
    private ArrayList<Card> playerTwoDeck;

    public static final int MAX_ROWS = 4;

    public Game(StartGameInput gameInput, DecksInput playerOneDecks, DecksInput playerTwoDecks) {
        this.startingPlayer = gameInput.getStartingPlayer();
        this.currentPlayer = startingPlayer;
        this.currentRound = 1;
        this.rows = new ArrayList[MAX_ROWS];
        for (int i = 0; i < MAX_ROWS; i++) {
            this.rows[i] = new ArrayList<>();
        }
        this.playerOneDeck = new ArrayList<>();
        this.playerTwoDeck = new ArrayList<>();

        // set the decks for each player
        for (int i = 0; i < playerOneDecks.getNrCardsInDeck(); i++) {
            playerOneDeck.add(new
                    Card(playerOneDecks.getDecks().get(gameInput.getPlayerOneDeckIdx()).get(i)));
        }
        for (int i = 0; i < playerTwoDecks.getNrCardsInDeck(); i++) {
            playerTwoDeck.add(new
                    Card(playerTwoDecks.getDecks().get(gameInput.getPlayerTwoDeckIdx()).get(i)));
        }

        // shuffle the decks
        Random rand = new Random(gameInput.getPlayerOneDeckIdx());
        shuffle(playerOneDeck, rand);
        shuffle(playerTwoDeck, rand);

        playerOne = new Player(playerOneDeck, new Hero(gameInput.getPlayerOneHero()));
        playerTwo = new Player(playerTwoDeck, new Hero(gameInput.getPlayerTwoHero()));
    }


    public Player getPlayer(int idx) {
        if(idx == 1)
            return playerOne;
        else
            return playerTwo;
    }

    private void newRound() {
        currentRound++;
        playerOne.incMana(currentRound);
        playerTwo.incMana(currentRound);
        currentPlayer = startingPlayer;
    }

    public void endTurn() {
        if(currentPlayer == startingPlayer && startingPlayer == 1) {
            currentPlayer = 2;
        } else if (currentPlayer == startingPlayer && startingPlayer == 2) {
            currentPlayer = 1;
        } else {
            newRound();
        }
    }

    public int getPlayerTurn() {
        return currentPlayer;
    }
}
