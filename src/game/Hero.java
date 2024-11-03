package game;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.CardInput;

import java.util.ArrayList;

public class Hero extends Card {
    private final int maxHp = 30;

    public Hero(final CardInput input) {
        super(input);
        this.setHealth(maxHp);
    }

    /**
     * Use the ability of the hero
     * @param targetRow The row that the ability is used on
     */
    public void useAbility(ArrayList<MinionCard> targetRow) { }

    /**
     * Transforms the data of a Hero into an JSON object node to be used
     * for the output of the program
     * @return The Hero data in JSON format
     */
    public ObjectNode toJson() {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode node = mapper.createObjectNode();
        node.put("mana", this.getMana());
        node.put("description", this.getDescription());
        ArrayNode colorsNode = mapper.createArrayNode();
        for (String color : this.getColors()) {
            colorsNode.add(color);
        }
        node.set("colors", colorsNode);
        node.put("name", this.getName());
        node.put("health", this.getHealth());
        return node;
    }
}
