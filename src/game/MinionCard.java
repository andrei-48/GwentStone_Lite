package game;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.CardInput;
import fileio.Coordinates;

import java.util.ArrayList;

public class MinionCard extends Card {
    private final boolean tank;
    private boolean frozen;
    private final boolean frontRow;


    public MinionCard(final CardInput cardInput) {
        super(cardInput);
        this.tank = cardInput.getName().equals("Goliath")
                || cardInput.getName().equals("Warden");
        this.frozen = false;
        this.frontRow = cardInput.getName().equals("Goliath")
                || cardInput.getName().equals("Warden")
                || cardInput.getName().equals("The Ripper")
                || cardInput.getName().equals("Miraj");
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
     * Sets the current card as frozen
     */
    public void setFrozen() {
        this.frozen = true;
    }

    /**
     * Unfreezes the current card
     */
    public void unsetFrozen() {
        this.frozen = false;
    }

    /**
     * Checks if the card has to be placed on the front row
     * @return True if the card has to be placed on the front row, False otherwise
     */
    public boolean isFrontRow() {
        return frontRow;
    }

    /**
     * Uses the attack on another card
     * @param attackedCoord The coordinates of the attacked card
     * @param row The row of the attacked card
     */
    public void attack(final Coordinates attackedCoord, final ArrayList<MinionCard> row) {
        MinionCard attackedCard = row.get(attackedCoord.getY());
        attackedCard.setHealth(attackedCard.getHealth() - this.getAttackDamage());
        this.setAttacked();
        if (attackedCard.getHealth() <= 0) {
            row.remove(attackedCard);
        }
    }

    /**
     * Uses the attack on the enemy hero
     * @param hero The enemy's hero
     */
    public void attackHero(final Hero hero) {
        hero.setHealth(hero.getHealth() - this.getAttackDamage());
        this.setAttacked();
    }

    /**
     * Uses the card's ability on another card
     * Method is overridden in every special minion type class !!!
     * @param attackedCoord The coordinates of the attacked card
     * @param row The row of the attacked card
     */
    public void useAbility(final Coordinates attackedCoord, final ArrayList<MinionCard> row) { }

    /**
     * Transforms the data of a card into an JSON object node to be used
     * for the output of the program
     * @return The card data in JSON format
     */
    public ObjectNode toJson() {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode node = mapper.createObjectNode();
        node.put("mana", this.getMana());
        node.put("attackDamage", this.getAttackDamage());
        node.put("health", this.getHealth());
        node.put("description", this.getDescription());
        ArrayNode colorsNode = mapper.createArrayNode();
        for (String color : this.getColors()) {
            colorsNode.add(color);
        }
        node.set("colors", colorsNode);
        node.put("name", this.getName());
        return node;
    }
}
