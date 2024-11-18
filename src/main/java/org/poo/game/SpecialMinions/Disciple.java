package org.poo.game.SpecialMinions;

import org.poo.fileio.CardInput;
import org.poo.fileio.Coordinates;
import org.poo.game.MinionCard;

import java.util.ArrayList;

public final class Disciple extends MinionCard {
    public Disciple(final CardInput input) {
        super(input);
    }

    @Override
    public void useAbility(final Coordinates attackedCoord, final ArrayList<MinionCard> row) {
        MinionCard attackedCard = row.get(attackedCoord.getY());
        attackedCard.setHealth(attackedCard.getHealth() + 2);
        this.setAttacked();
    }
}
