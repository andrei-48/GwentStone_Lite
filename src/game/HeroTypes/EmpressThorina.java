package game.HeroTypes;

import fileio.CardInput;
import game.Hero;
import game.MinionCard;

import java.util.ArrayList;

public final class EmpressThorina extends Hero {
    public EmpressThorina(final CardInput input) {
        super(input);
    }

    @Override
    public void useAbility(final ArrayList<MinionCard> targetRow) {
        MinionCard minionTarget = targetRow.get(0);
        int maxHealth = minionTarget.getHealth();
        for (MinionCard minion : targetRow) {
            if (minion.getHealth() > maxHealth) {
                maxHealth = minion.getHealth();
                minionTarget = minion;
            }
        }
        targetRow.remove(minionTarget);
        this.setAttacked();
    }
}
