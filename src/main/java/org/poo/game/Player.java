package org.poo.game;

import java.util.ArrayList;

public final class Player {
    private ArrayList<MinionCard> hand;
    private ArrayList<MinionCard> deck;
    private final Hero hero;
    private int mana;

    private static final int MAX_MANA_GAIN = 10;
    public static final int PLAYER_ONE_FRONT = 2;
    public static final int PLAYER_TWO_FRONT = 1;
    public static final int PLAYER_ONE_BACK = 3;
    public static final int PLAYER_TWO_BACK = 0;

    public Player(final ArrayList<MinionCard> deck, final Hero hero) {
        this.deck = deck;
        this.hand = new ArrayList<>();
        this.hero = hero;
        this.mana = 1;
    }

    public ArrayList<MinionCard> getHand() {
        return hand;
    }

    public ArrayList<MinionCard> getDeck() {
        return deck;
    }

    public Hero getHero() {
        return hero;
    }

    public int getMana() {
        return mana;
    }

    /**
     * Uses the ability of the player's hero
     * @param targetRow The row that the ability will be used on
     */
    public void useHeroAbility(final ArrayList<MinionCard> targetRow) {
        this.mana -= hero.getMana();
        hero.useAbility(targetRow);
    }

    /**
     * Places a card with the given index from the hand on the board
     * @param idx the index of the card in hand
     * @param game the current game
     */
    public void placeCard(final int idx, final Game game) {
        MinionCard card = hand.get(idx);
        this.mana -= card.getMana();
        if (game.getPlayerTurn() == 1) {
            if (card.isFrontRow()) {
                // place the card on the front row of playerOne
                game.getBoard().get(PLAYER_ONE_FRONT).add(card);
            } else {
                // place the card on the back row of playerOne
                game.getBoard().get(PLAYER_ONE_BACK).add(card);
            }
        } else {
            if (card.isFrontRow()) {
                // place the card on the front row of playerTwo
                game.getBoard().get(PLAYER_TWO_FRONT).add(card);
            } else {
                // place the card on the back row of playerTwo
                game.getBoard().get(PLAYER_TWO_BACK).add(card);
            }
        }
        hand.remove(idx);
    }

    /**
     * Draws a card for the player from the deck and adds it to the hand
     */
    public void drawCard() {
        if (!this.deck.isEmpty()) {
            this.hand.add(this.deck.remove(0));
        }
    }

    /**
     * Gives the player mana based on the current round
     * @param currentRound The number of the current round
     */
    public void incMana(final int currentRound) {
        this.mana += Math.min(currentRound, MAX_MANA_GAIN);
    }
}
