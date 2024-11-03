package game;

import fileio.CardInput;

import java.util.ArrayList;

public class Card {
    private int mana;
    private int attackDamage;
    private int health;
    private String description;
    private ArrayList<String> colors;
    private String name;
    private boolean attacked;

    public Card() {
    }

    public Card(final CardInput cardInput) {
        this.mana = cardInput.getMana();
        this.attackDamage = cardInput.getAttackDamage();
        this.health = cardInput.getHealth();
        this.description = cardInput.getDescription();
        this.colors = cardInput.getColors();
        this.name = cardInput.getName();
        this.attacked = false;
    }

    /**
     * Return the current health of a card
     * @return Remaining health
     */
    public int getHealth() {
        return health;
    }

    /**
     * Change the health of a card (used for dealing damage/healing)
     * @param health The new health amount
     */
    public void setHealth(final int health) {
        this.health = health;
    }

    /**
     * Get the man cost of a card
     * @return The amount of mana
     */
    public int getMana() {
        return mana;
    }

    /**
     * Get the attack damage of a card
     * @return The amount of attack damage
     */
    public int getAttackDamage() {
        return attackDamage;
    }

    /**
     * Change the attack damage of a card
     * (used for abilities that influence the attack damage)
     * @param attackDamage The new amount of attack damage
     */
    public void setAttackDamage(final int attackDamage) {
        this.attackDamage = attackDamage;
    }

    /**
     * Get the description of a card
     * @return The description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Get the colors that a card has
     * @return The list of colors
     */
    public ArrayList<String> getColors() {
        return colors;
    }

    /**
     * Get the name of a card
     * @return The name
     */
    public String getName() {
        return name;
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
     * Marks the card after attacking
     */
    public void setAttacked() {
        this.attacked = true;
    }
}
