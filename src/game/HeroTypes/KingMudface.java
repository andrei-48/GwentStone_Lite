package game.HeroTypes;

import fileio.CardInput;
import game.Hero;
import game.MinionCard;

import java.util.ArrayList;

public final class KingMudface extends Hero {
    public KingMudface(final CardInput input) {
        super(input);
    }

    @Override
    public void useAbility(ArrayList<MinionCard> targetRow) {
        for (MinionCard minion : targetRow) {
            minion.setHealth(minion.getHealth() + 1);
        }
        this.setAttacked();
    }
}
