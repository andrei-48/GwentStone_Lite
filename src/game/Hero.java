package game;

import fileio.CardInput;

public class Hero extends Card{
    private final int MAX_HP = 30;

    public Hero(CardInput input) {
        super(input);
        this.setHealth(MAX_HP);
    }

    // will be overridden in each hero type subclass
    public void useAbility(int targetRow) {}
}
