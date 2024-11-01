package game;

import fileio.CardInput;
import fileio.Coordinates;

import java.util.ArrayList;

public class MinionCard extends Card {
    private final boolean tank;
    private boolean frozen;
    private final boolean frontRow;
    private boolean attacked;

    public MinionCard(final CardInput cardInput) {
        super(cardInput);
        this.tank = cardInput.getName().equals("Goliath")
                || cardInput.getName().equals("Warden");
        this.frozen = false;
        this.frontRow = cardInput.getName().equals("Goliath")
                || cardInput.getName().equals("Warden")
                || cardInput.getName().equals("The Ripper")
                || cardInput.getName().equals("Miraj");
        this.attacked = false;
    }

    /**
     * Checks if the card is a 'Tank'
     * @return True if the card is a 'Tank', False otherwise
     */
    public boolean isTank() {
        return tank;
    }

    /**
     * Checks if the card is a frozen
     * @return True if the card is a frozen, False otherwise
     */
    public boolean isFrozen() {
        return frozen;
    }

    /**
     * Checks if the card has to be placed on the front row
     * @return True if the card has to be placed on the front row, False otherwise
     */
    public boolean isFrontRow() {
        return frontRow;
    }

    /**
     * Checks if the card has attacked this round
     * @return True if the card has attacked this round, False otherwise
     */
    public boolean hasAttacked() {
        return attacked;
    }

    /**
     * Used when starting a new round. Sets the 'attacked' field
     * back to false
     */
    public void resetAttacked() {
        attacked = false;
    }
    /**
     * Uses the attack on another card
     * @param attackedCoord The coordinates of the attacked card
     * @param row  The row of the attacked card
     */
    public void attack(final Coordinates attackedCoord, final ArrayList<MinionCard> row) {
        MinionCard attackedCard = row.get(attackedCoord.getY());
        attackedCard.setHealth(attackedCard.getHealth() - this.getAttackDamage());
        this.attacked = true;
        if (attackedCard.getHealth() <= 0) {
            row.remove(attackedCard);
        }
    }
}
