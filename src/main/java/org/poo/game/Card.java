package org.poo.game;

import org.poo.fileio.CardInput;

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

    public final int getHealth() {
        return health;
    }

    public final void setHealth(final int health) {
        this.health = health;
    }


    public final int getMana() {
        return mana;
    }


    public final int getAttackDamage() {
        return attackDamage;
    }


    public final void setAttackDamage(final int attackDamage) {
        this.attackDamage = attackDamage;
    }


    public final String getDescription() {
        return description;
    }


    public final ArrayList<String> getColors() {
        return colors;
    }


    public final String getName() {
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
