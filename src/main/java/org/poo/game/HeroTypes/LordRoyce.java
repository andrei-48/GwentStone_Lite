package org.poo.game.HeroTypes;

import org.poo.fileio.CardInput;
import org.poo.game.Hero;
import org.poo.game.MinionCard;

import java.util.ArrayList;

public final class LordRoyce extends Hero {
    public LordRoyce(final CardInput input) {
        super(input);
    }

    @Override
    public void useAbility(final ArrayList<MinionCard> targetRow) {
        for (MinionCard minion : targetRow) {
            minion.setFrozen();
        }
        this.setAttacked();
    }
}
