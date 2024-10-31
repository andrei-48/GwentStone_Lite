package game;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.CardInput;

public class Hero extends Card{
    private final int maxHp = 30;

    public Hero(CardInput input) {
        super(input);
        this.setHealth(maxHp);
    }

    // will be overridden in each hero type subclass
    public void useAbility(int targetRow) {}

    @Override
    public ObjectNode toJson() {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode node = mapper.createObjectNode();
        node.put("mana", this.getMana());
        node.put("description", this.getDescription());
        ArrayNode colorsNode = mapper.createArrayNode();
        for (String color : this.getColors()) {
            colorsNode.add(color);
        }
        node.put("colors", colorsNode);
        node.put("name", this.getName());
        node.put("health", this.getHealth());
        return node;
    }
}
