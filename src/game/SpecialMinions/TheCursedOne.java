package game.SpecialMinions;

import fileio.CardInput;
import fileio.Coordinates;
import game.MinionCard;

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
