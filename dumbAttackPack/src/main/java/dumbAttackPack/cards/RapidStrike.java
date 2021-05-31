package dumbAttackPack.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static com.megacrit.cardcrawl.cards.red.PerfectedStrike.*;
import static dumbAttackPack.DumbAttackPack.makeID;

public class RapidStrike extends AbstractEasyCard {

    /*
    * Deal 5(6) damage times for every two of your cards containing "Strike".
    */

    public static final String ID = makeID("RapidStrike");

    public RapidStrike() {
        super(ID, 2, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY, CardColor.GREEN);
        baseDamage = 5;
        this.tags.add(CardTags.STRIKE);
    }

    @Override
    public void upp() {
        upgradeDamage(1);
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        int strikes = countCards();
        for (int i = 0; i < strikes; i = i + 2) {
            this.addToBot(new DamageAction(abstractMonster, new DamageInfo(abstractPlayer, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        }
    }

    public void applyPowers() {
        super.applyPowers();
        int strikes = countCards() - 1; // don't count the card itself
        this.rawDescription = cardStrings.DESCRIPTION;
        this.rawDescription = this.rawDescription + cardStrings.EXTENDED_DESCRIPTION[0] + strikes/2 + cardStrings.EXTENDED_DESCRIPTION[1];

        this.initializeDescription();
    }
}
