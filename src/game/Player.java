package game;

import java.util.ArrayList;

public class Player {
    private ArrayList<Card> hand;
    private ArrayList<Card> deck;
    private Hero hero;
    private int mana;

    public static final int MAX_MANA_GAIN = 10;

    public Player(ArrayList<Card> deck, Hero hero) {
        this.deck = deck;
        this.hand = new ArrayList<>();
        this.hero = hero;
        this.mana = 0;
    }

    public ArrayList<Card> getHand() {
        return hand;
    }

    public ArrayList<Card> getDeck() {
        return deck;
    }

    public Hero getHero() {
        return hero;
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
