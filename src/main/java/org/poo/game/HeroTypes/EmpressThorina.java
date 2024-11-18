package org.poo.game.HeroTypes;

import org.poo.fileio.CardInput;
import org.poo.game.Hero;
import org.poo.game.MinionCard;

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
