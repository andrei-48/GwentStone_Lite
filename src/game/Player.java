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
        this.mana = 0;
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

    public void placeCard(int idx) {
        minionCard card = hand.get(idx);
        if (card.getMana() <= this.mana) {
        }
    }

    public void drawCard() {
        this.hand.add(this.deck.remove(0));
    }

    public void incMana(int mana) {
        if (mana <= MAX_MANA_GAIN) {
            this.mana += mana;
        } else {
            this.mana += MAX_MANA_GAIN;
        }
    }
}
