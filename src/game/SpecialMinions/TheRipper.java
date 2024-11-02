package game.SpecialMinions;

import fileio.CardInput;
import fileio.Coordinates;
import game.MinionCard;

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
