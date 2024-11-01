package game;

import fileio.CardInput;

public class MinionCard extends Card {
    private final boolean tank;
    private boolean frozen;
    private final boolean frontRow;

    public MinionCard(CardInput cardInput) {
        super(cardInput);
        this.tank = false;
        this.frozen = false;
        this.frontRow = cardInput.getName().equals("Goliath") || cardInput.getName().equals("Warden")
                || cardInput.getName().equals("The Ripper") || cardInput.getName().equals("Miraj");
    }

    public boolean isTank() {
        return tank;
    }
    public boolean isFrozen() {
        return frozen;
    }
    public boolean isFrontRow() {
        return frontRow;
    }
}
