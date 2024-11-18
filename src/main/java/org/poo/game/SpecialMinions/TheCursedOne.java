package org.poo.game.SpecialMinions;

import org.poo.fileio.CardInput;
import org.poo.fileio.Coordinates;
import org.poo.game.MinionCard;

import java.util.ArrayList;

public final class TheCursedOne extends MinionCard {
    public TheCursedOne(final CardInput input) {
        super(input);
    }

    @Override
    public void useAbility(final Coordinates attackedCoord, final ArrayList<MinionCard> row) {
        MinionCard attackedCard = row.get(attackedCoord.getY());
        if (attackedCard.getAttackDamage() == 0) {
            row.remove(attackedCard);
        } else {
            int aux = attackedCard.getAttackDamage();
            attackedCard.setAttackDamage(attackedCard.getHealth());
            attackedCard.setHealth(aux);
        }
        this.setAttacked();
    }
}
