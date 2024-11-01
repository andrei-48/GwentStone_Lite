package game;

import fileio.DecksInput;
import fileio.StartGameInput;
import game.HeroTypes.EmpressThorina;
import game.HeroTypes.GeneralKocioraw;
import game.HeroTypes.KingMudface;
import game.HeroTypes.LordRoyce;

import java.util.ArrayList;
import java.util.Random;

import static java.util.Collections.shuffle;

public class Game {
    private ArrayList<ArrayList<MinionCard>> board;
    private final int startingPlayer;
    private int currentRound;
    private int currentPlayer;
    private Player playerOne;
    private Player playerTwo;
    private ArrayList<MinionCard> playerOneDeck;
    private ArrayList<MinionCard> playerTwoDeck;

    private static final int MAX_ROWS = 4;
    private static final int MAX_CARDS_ON_ROW = 5;

    public Game(StartGameInput gameInput, DecksInput playerOneDecks, DecksInput playerTwoDecks) {
        this.startingPlayer = gameInput.getStartingPlayer();
        this.currentPlayer = startingPlayer;
        this.currentRound = 1;
        this.board = new ArrayList<>();
        for (int i = 0; i < MAX_ROWS; i++) {
            board.add(new ArrayList<>());
        }
        this.playerOneDeck = new ArrayList<>();
        this.playerTwoDeck = new ArrayList<>();

        // set the decks for each player
        for (int i = 0; i < playerOneDecks.getNrCardsInDeck(); i++) {
            playerOneDeck.add(new
                    MinionCard(playerOneDecks.getDecks().get(gameInput.getPlayerOneDeckIdx()).get(i)));
        }
        for (int i = 0; i < playerTwoDecks.getNrCardsInDeck(); i++) {
            playerTwoDeck.add(new
                    MinionCard(playerTwoDecks.getDecks().get(gameInput.getPlayerTwoDeckIdx()).get(i)));
        }

        // shuffle the decks
        Random rand;
        rand = new Random(gameInput.getShuffleSeed());
        shuffle(playerOneDeck, rand);
        rand = new Random(gameInput.getShuffleSeed());
        shuffle(playerTwoDeck, rand);

        // set playerOne hero
        switch (gameInput.getPlayerOneHero().getName()) {
            case "Lord Royce":
                playerOne = new Player(playerOneDeck,
                        new LordRoyce(gameInput.getPlayerOneHero()));
                break;
            case "Empress Thorina":
                playerOne = new Player(playerOneDeck,
                        new EmpressThorina(gameInput.getPlayerOneHero()));
                break;
            case "King Mudface":
                playerOne = new Player(playerOneDeck,
                        new KingMudface(gameInput.getPlayerOneHero()));
                break;
            case "General Kocioraw":
                playerOne = new Player(playerOneDeck,
                        new GeneralKocioraw(gameInput.getPlayerOneHero()));
                break;
        }

        // set playerTwo hero
        switch (gameInput.getPlayerTwoHero().getName()) {
            case "Lord Royce":
                playerTwo = new Player(playerTwoDeck,
                        new LordRoyce(gameInput.getPlayerTwoHero()));
                break;
            case "Empress Thorina":
                playerTwo = new Player(playerTwoDeck,
                        new EmpressThorina(gameInput.getPlayerTwoHero()));
                break;
            case "King Mudface":
                playerTwo = new Player(playerTwoDeck,
                        new KingMudface(gameInput.getPlayerTwoHero()));
                break;
            case "General Kocioraw":
                playerTwo = new Player(playerTwoDeck,
                        new GeneralKocioraw(gameInput.getPlayerTwoHero()));
                break;
        }
        playerOne.drawCard();
        playerTwo.drawCard();
    }


    public Player getPlayer(int idx) {
        if (idx == 1)
            return playerOne;
        else
            return playerTwo;
    }

    private void newRound() {
        currentRound++;
        playerOne.incMana(currentRound);
        playerTwo.incMana(currentRound);
        playerOne.drawCard();
        playerTwo.drawCard();
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

    public Player getCurrentPlayer() {
        return this.getPlayer(this.currentPlayer);
    }

    public int getPlayerTurn() {
        return currentPlayer;
    }

    public ArrayList<ArrayList<MinionCard>> getBoard() {
        return board;
    }

    public boolean checkCardPlace(MinionCard card) {
        if (currentPlayer == 1) {
            if (card.isFrontRow()) {
                return board.get(Player.PLAYER_ONE_FRONT).size() < MAX_CARDS_ON_ROW;
            } else {
                return board.get(Player.PLAYER_ONE_BACK).size() < MAX_CARDS_ON_ROW;
            }
        } else {
            if (card.isFrontRow()) {
                return board.get(Player.PLAYER_TWO_FRONT).size() < MAX_CARDS_ON_ROW;
            } else {
                return board.get(Player.PLAYER_TWO_BACK).size() < MAX_CARDS_ON_ROW;
            }
        }
    }

    public boolean checkCardMana(MinionCard card) {
        return getCurrentPlayer().getMana() >= card.getMana();
    }
}
