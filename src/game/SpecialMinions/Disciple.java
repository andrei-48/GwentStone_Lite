package game.SpecialMinions;

import fileio.CardInput;
import fileio.Coordinates;
import game.MinionCard;

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
