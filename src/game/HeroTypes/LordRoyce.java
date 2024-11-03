package game.HeroTypes;

import fileio.CardInput;
import game.Hero;
import game.MinionCard;

import java.util.ArrayList;

public final class LordRoyce extends Hero {
    public LordRoyce(final CardInput input) {
        super(input);
    }

    @Override
    public void useAbility(ArrayList<MinionCard> targetRow) {
        for (MinionCard minion : targetRow) {
            minion.setFrozen();
        }
        this.setAttacked();
    }
}
