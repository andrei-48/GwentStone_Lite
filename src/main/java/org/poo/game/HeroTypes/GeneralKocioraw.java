package org.poo.game.HeroTypes;

import org.poo.fileio.CardInput;
import org.poo.game.Hero;
import org.poo.game.MinionCard;

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
