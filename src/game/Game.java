package game;

import fileio.Coordinates;
import fileio.DecksInput;
import fileio.StartGameInput;
import game.HeroTypes.EmpressThorina;
import game.HeroTypes.GeneralKocioraw;
import game.HeroTypes.KingMudface;
import game.HeroTypes.LordRoyce;
import game.SpecialMinions.Disciple;
import game.SpecialMinions.Miraj;
import game.SpecialMinions.TheCursedOne;
import game.SpecialMinions.TheRipper;

import java.util.ArrayList;
import java.util.Random;

import static java.util.Collections.shuffle;

public final class Game {
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


    /**
     * Creates the decks arraylists for both players
     * @param gameInput The input for the current game
     * @param playerOneDecks The deck input for player one
     * @param playerTwoDecks The deck input for player two
     */
    private void createDecks(final StartGameInput gameInput,
                             final DecksInput playerOneDecks, final DecksInput playerTwoDecks) {
        this.playerOneDeck = new ArrayList<>();
        this.playerTwoDeck = new ArrayList<>();

        // set the decks for each player
        for (int i = 0; i < playerOneDecks.getNrCardsInDeck(); i++) {
            String cardName = playerOneDecks.getDecks().
                    get(gameInput.getPlayerOneDeckIdx()).get(i).getName();
            switch (cardName) {
                case "Disciple":
                    playerOneDeck.add(new
                            Disciple(playerOneDecks.getDecks().
                            get(gameInput.getPlayerOneDeckIdx()).get(i)));
                    break;
                case "Miraj":
                    playerOneDeck.add(new
                            Miraj(playerOneDecks.getDecks().
                            get(gameInput.getPlayerOneDeckIdx()).get(i)));
                    break;
                case "The Ripper":
                    playerOneDeck.add(new
                            TheRipper(playerOneDecks.getDecks().
                            get(gameInput.getPlayerOneDeckIdx()).get(i)));
                    break;
                case "The Cursed One":
                    playerOneDeck.add(new
                            TheCursedOne(playerOneDecks.getDecks().
                            get(gameInput.getPlayerOneDeckIdx()).get(i)));
                    break;
                default:
                    playerOneDeck.add(new
                            MinionCard(playerOneDecks.getDecks().
                            get(gameInput.getPlayerOneDeckIdx()).get(i)));
                    break;
            }
        }
        for (int i = 0; i < playerTwoDecks.getNrCardsInDeck(); i++) {
            String cardName = playerTwoDecks.getDecks().
                    get(gameInput.getPlayerTwoDeckIdx()).get(i).getName();
            switch (cardName) {
                case "Disciple":
                    playerTwoDeck.add(new
                            Disciple(playerTwoDecks.getDecks().
                            get(gameInput.getPlayerTwoDeckIdx()).get(i)));
                    break;
                case "Miraj":
                    playerTwoDeck.add(new
                            Miraj(playerTwoDecks.getDecks().
                            get(gameInput.getPlayerTwoDeckIdx()).get(i)));
                    break;
                case "The Ripper":
                    playerTwoDeck.add(new
                            TheRipper(playerTwoDecks.getDecks().
                            get(gameInput.getPlayerTwoDeckIdx()).get(i)));
                    break;
                case "The Cursed One":
                    playerTwoDeck.add(new
                            TheCursedOne(playerTwoDecks.getDecks().
                            get(gameInput.getPlayerTwoDeckIdx()).get(i)));
                    break;
                default:
                    playerTwoDeck.add(new
                            MinionCard(playerTwoDecks.getDecks().
                            get(gameInput.getPlayerTwoDeckIdx()).get(i)));
                    break;
            }
        }
    }

    /**
     * Gives both players their decks and sets their hero
     * @param gameInput The input for the current game
     */
    private void setPlayers(final StartGameInput gameInput) {
        // set playerOne hero and give deck
        switch (gameInput.getPlayerOneHero().getName()) {
            case "Lord Royce":
                this.playerOne = new Player(playerOneDeck,
                        new LordRoyce(gameInput.getPlayerOneHero()));
                break;
            case "Empress Thorina":
                this.playerOne = new Player(playerOneDeck,
                        new EmpressThorina(gameInput.getPlayerOneHero()));
                break;
            case "King Mudface":
                this.playerOne = new Player(playerOneDeck,
                        new KingMudface(gameInput.getPlayerOneHero()));
                break;
            case "General Kocioraw":
                this.playerOne = new Player(playerOneDeck,
                        new GeneralKocioraw(gameInput.getPlayerOneHero()));
                break;
            default:
                break;
        }

        // set playerTwo hero and give deck
        switch (gameInput.getPlayerTwoHero().getName()) {
            case "Lord Royce":
                this.playerTwo = new Player(playerTwoDeck,
                        new LordRoyce(gameInput.getPlayerTwoHero()));
                break;
            case "Empress Thorina":
                this.playerTwo = new Player(playerTwoDeck,
                        new EmpressThorina(gameInput.getPlayerTwoHero()));
                break;
            case "King Mudface":
                this.playerTwo = new Player(playerTwoDeck,
                        new KingMudface(gameInput.getPlayerTwoHero()));
                break;
            case "General Kocioraw":
                this.playerTwo = new Player(playerTwoDeck,
                        new GeneralKocioraw(gameInput.getPlayerTwoHero()));
                break;
            default:
                break;
        }
    }

    public Game(final StartGameInput gameInput,
                final DecksInput playerOneDecks, final DecksInput playerTwoDecks) {
        this.startingPlayer = gameInput.getStartingPlayer();
        this.currentPlayer = startingPlayer;
        this.currentRound = 1;
        this.board = new ArrayList<>();
        for (int i = 0; i < MAX_ROWS; i++) {
            board.add(new ArrayList<>());
        }

        this.createDecks(gameInput, playerOneDecks, playerTwoDecks);

        // shuffle the decks
        Random rand;
        rand = new Random(gameInput.getShuffleSeed());
        shuffle(playerOneDeck, rand);
        rand = new Random(gameInput.getShuffleSeed());
        shuffle(playerTwoDeck, rand);

        this.setPlayers(gameInput);

        playerOne.drawCard();
        playerTwo.drawCard();
    }

    /**
     * Returns a player object based on the given index (must be 1 or 2)
     * @param idx The index of the player (1 or 2)
     * @return The player object corresponding to the index
     */
    public Player getPlayer(final int idx) {
        if (idx == 1) {
            return playerOne;
        } else {
            return playerTwo;
        }
    }

    private void newRound() {
        currentRound++;
        playerOne.incMana(currentRound);
        playerTwo.incMana(currentRound);
        playerOne.drawCard();
        playerTwo.drawCard();
        currentPlayer = startingPlayer;
        for (ArrayList<MinionCard> row : board) {
            for (MinionCard card : row) {
                card.resetAttacked();
            }
        }
    }

    /**
     * Ends a player turn and changes the current player.
     * Also switches to a new round if both players ended their turn
     */
    public void endTurn() {
        if (currentPlayer == startingPlayer && startingPlayer == 1) {
            currentPlayer = 2;
        } else if (currentPlayer == startingPlayer && startingPlayer == 2) {
            currentPlayer = 1;
        } else {
            newRound();
        }
    }

    /**
     * Returns the object of the player whose turn it is
     * @return The player object
     */
    public Player getCurrentPlayer() {
        return this.getPlayer(this.currentPlayer);
    }

    /**
     * Return the index of the player whose turn it is
     * @return The index of the player (1 or 2)
     */
    public int getPlayerTurn() {
        return currentPlayer;
    }

    /**
     * Returns the board in it's current state
     * @return The board object (an arraylist of arraylists of cards)
     */
    public ArrayList<ArrayList<MinionCard>> getBoard() {
        return board;
    }

    /**
     * Checks if a card exists on the table on a given position
     * @param x The row that needs to be looked on
     * @param y The position of the card on the row
     * @return True if there is a card on the given position, false otherwise
     */
    public boolean cardExists(final int x, final int y) {
        return x < MAX_ROWS && y < board.get(x).size();
    }

    /**
     * Returns the object of a MinionCard in a given position
     * !!Doesn't check if the card exists!!
     * @param x The row of the card
     * @param y The position of the card on the row
     * @return The MinionCard object
     */
    public MinionCard getCard(final int x, final int y) {
        if (x < board.size() && y < board.get(x).size()) {
            return board.get(x).get(y);
        }
        return null;
    }

    /**
     * Checks if the card can be placed on it's required row
     * @param card The card that needs to be placed
     * @return True if the card can be placed, False otherwise
     */
    public boolean checkCardPlace(final MinionCard card) {
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

    /**
     * Checks if the current player has enough mana to place a card
     * @param card The card that needs to be placed
     * @return True if the player has enough mana, False otherwise
     */
    public boolean checkCardMana(final MinionCard card) {
        return getCurrentPlayer().getMana() >= card.getMana();
    }

    /**
     * Checks if the selected card is owned by the opponent
     * @param attackedCard The card that should be attacked
     * @return True if the opponent owns the card, False otherwise
     */
    public boolean isEnemy(final Coordinates attackedCard) {
        if (currentPlayer == 1 && (attackedCard.getX() == Player.PLAYER_TWO_BACK
                || attackedCard.getX() == Player.PLAYER_TWO_FRONT)) {
            return true;
        } else {
            return currentPlayer == 2 && (attackedCard.getX() == Player.PLAYER_ONE_BACK
                    || attackedCard.getX() == Player.PLAYER_ONE_FRONT);
        }
    }

    /**
     * Checks if the opponent has a 'Tank' minion on the table
     * @return True if the opponent has a 'Tank', False otherwise
     */
    public boolean opponentHasTank() {
        if (currentPlayer == 1) {
            ArrayList<MinionCard> playerTwoFrontRow = board.get(Player.PLAYER_TWO_FRONT);
            for (MinionCard card : playerTwoFrontRow) {
                if (card.isTank()) {
                    return true;
                }
            }
        } else if (currentPlayer == 2) {
            ArrayList<MinionCard> playerOneFrontRow = board.get(Player.PLAYER_ONE_FRONT);
            for (MinionCard card : playerOneFrontRow) {
                if (card.isTank()) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Returns the hero of the current player's opponent
     * @return The hero of the opponent
     */
    public Hero getOpponentHero() {
        if (currentPlayer == 1) {
            return playerTwo.getHero();
        } else {
            return playerOne.getHero();
        }
    }
}
