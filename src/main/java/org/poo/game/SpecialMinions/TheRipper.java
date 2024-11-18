package org.poo.game.SpecialMinions;

import org.poo.fileio.CardInput;
import org.poo.fileio.Coordinates;
import org.poo.game.MinionCard;

import java.util.ArrayList;

public final class TheRipper extends MinionCard {
    public TheRipper(final CardInput input) {
        super(input);
    }

    @Override
    public void useAbility(final Coordinates attackedCoord, final ArrayList<MinionCard> row) {
        MinionCard attackedCard = row.get(attackedCoord.getY());
        if (attackedCard.getAttackDamage() <= 2) {
            attackedCard.setAttackDamage(0);
        } else {
            attackedCard.setAttackDamage(attackedCard.getAttackDamage() - 2);
        }
        this.setAttacked();
    }
}
