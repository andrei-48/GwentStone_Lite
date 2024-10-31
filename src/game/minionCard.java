package game;

import fileio.CardInput;

public class minionCard extends Card {
    private boolean tank;
    private boolean frozen;
    private boolean frontRow;

    public minionCard(CardInput cardInput) {
        super(cardInput);
        this.tank = false;
        this.frozen = false;
        this.frontRow = cardInput.getName().equals("Goliath") || cardInput.getName().equals("Warden");
    }
}
