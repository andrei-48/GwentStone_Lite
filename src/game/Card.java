package game;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.CardInput;

import java.util.ArrayList;

public class Card {
    private int mana;
    private int attackDamage;
    private int health;
    private String description;
    private ArrayList<String> colors;
    private String name;

    public Card() {
    }

    public Card(final CardInput cardInput) {
        this.mana = cardInput.getMana();
        this.attackDamage = cardInput.getAttackDamage();
        this.health = cardInput.getHealth();
        this.description = cardInput.getDescription();
        this.colors = cardInput.getColors();
        this.name = cardInput.getName();
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
    public void setHealth(int health) {
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
    public void setAttackDamage(int attackDamage) {
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
     * Transforms the data of a card into an JSON object node to be used
     * for the output of the program
     * @return The card data in JSON format
     */
    public ObjectNode toJson() {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode node = mapper.createObjectNode();
        node.put("mana", mana);
        node.put("attackDamage", attackDamage);
        node.put("health", health);
        node.put("description", description);
        ArrayNode colorsNode = mapper.createArrayNode();
        for (String color : colors) {
            colorsNode.add(color);
        }
        node.put("colors", colorsNode);
        node.put("name", name);
        return node;
    }
}
