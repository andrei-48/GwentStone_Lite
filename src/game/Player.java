package game;

import java.util.ArrayList;

public class Player {
    private ArrayList<minionCard> hand;
    private ArrayList<minionCard> deck;
    private Hero hero;
    private int mana;

    public static final int MAX_MANA_GAIN = 10;

    public Player(ArrayList<minionCard> deck, Hero hero) {
        this.deck = deck;
        this.hand = new ArrayList<>();
        this.hero = hero;
        this.mana = 1;
    }

    public ArrayList<minionCard> getHand() {
        return hand;
    }

    public ArrayList<minionCard> getDeck() {
        return deck;
    }

    public Hero getHero() {
        return hero;
    }

    public int getMana() {
        return mana;
    }

    /**
     * Places a card with the given index from the hand on the board
     * @param idx the index of the card in hand
     * @param game the current game
     */
    public void placeCard(int idx, Game game) {
        minionCard card = hand.get(idx);
        this.mana -= card.getMana();
        if(game.getPlayerTurn() == 1) {
            if (card.isFrontRow()) {
                // place the card on the front row of playerOne
                game.getBoard().get(2).add(card);
            } else {
                // place the card on the back row of playerOne
                game.getBoard().get(3).add(card);
            }
        } else {
            if (card.isFrontRow()) {
                // place the card on the front row of playerTwo
                game.getBoard().get(1).add(card);
            } else {
                // place the card on the back row of playerTwo
                game.getBoard().get(0).add(card);
            }
        }
        hand.remove(idx);
    }

    public void drawCard() {
        if(!this.deck.isEmpty())
            this.hand.add(this.deck.remove(0));
    }

    public void incMana(int mana) {
        this.mana += Math.min(mana, MAX_MANA_GAIN);
    }
}
