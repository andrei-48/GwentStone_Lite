package org.poo.game.SpecialMinions;

import org.poo.fileio.CardInput;
import org.poo.fileio.Coordinates;
import org.poo.game.MinionCard;

import java.util.ArrayList;

public final class Miraj extends MinionCard {
    public Miraj(final CardInput input) {
        super(input);
    }

    @Override
    public void useAbility(final Coordinates attackedCoord, final ArrayList<MinionCard> row) {
        int aux = this.getHealth();
        MinionCard attackedCard = row.get(attackedCoord.getY());
        this.setHealth(attackedCard.getHealth());
        attackedCard.setHealth(aux);
        this.setAttacked();
    }
}
