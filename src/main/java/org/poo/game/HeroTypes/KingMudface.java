package org.poo.game.HeroTypes;

import org.poo.fileio.CardInput;
import org.poo.game.Hero;
import org.poo.game.MinionCard;

import java.util.ArrayList;

public final class KingMudface extends Hero {
    public KingMudface(final CardInput input) {
        super(input);
    }

    @Override
    public void useAbility(final ArrayList<MinionCard> targetRow) {
        for (MinionCard minion : targetRow) {
            minion.setHealth(minion.getHealth() + 1);
        }
        this.setAttacked();
    }
}
