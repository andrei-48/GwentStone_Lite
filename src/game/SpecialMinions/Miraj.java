package game.SpecialMinions;

import fileio.CardInput;
import fileio.Coordinates;
import game.MinionCard;

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
