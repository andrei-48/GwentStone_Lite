package game.HeroTypes;

import fileio.CardInput;
import game.Hero;
import game.MinionCard;

import java.util.ArrayList;

public final class GeneralKocioraw extends Hero {
    public GeneralKocioraw(final CardInput input) {
        super(input);
    }

    @Override
    public void useAbility(final ArrayList<MinionCard> targetRow) {
        for (MinionCard minion : targetRow) {
            minion.setAttackDamage(minion.getAttackDamage() + 1);
        }
        this.setAttacked();
    }
}
