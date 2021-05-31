package dumbAttackPack.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.EmptyOrbSlot;

import static com.megacrit.cardcrawl.cards.red.PerfectedStrike.*;
import static dumbAttackPack.DumbAttackPack.makeID;

public class OrbStrike extends AbstractEasyCard {

    /*
     * (Channel 1 random orb.) Deal 10 damage. Deals additional damage for ALL your cards containing \"Strike\" times channeled orbs.
     */

    public static final String ID = makeID("OrbStrike");

    public OrbStrike() {
        super(ID, 2, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY, CardColor.BLUE);
        baseDamage = 4;
        this.tags.add(CardTags.STRIKE);
    }

    @Override
    public void upp() {
        this.rawDescription = cardStrings.UPGRADE_DESCRIPTION + this.rawDescription;
        this.initializeDescription();
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        if (upgraded && AbstractDungeon.player.hasEmptyOrb()) {
            this.addToBot(new ChannelAction(AbstractOrb.getRandomOrb(true)));
        }
        this.addToBot(new DamageAction(abstractMonster, new DamageInfo(abstractPlayer, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_LIGHT));

    }

    public void calculateCardDamage(AbstractMonster mo) {
        int realBaseDamage = this.baseDamage;
        this.baseDamage += countChanneledOrbs() * countCards();
        super.calculateCardDamage(mo);
        this.baseDamage = realBaseDamage;
        this.isDamageModified = this.damage != this.baseDamage;
    }

    public void applyPowers() {
        int realBaseDamage = this.baseDamage;
        this.baseDamage += countChanneledOrbs() * countCards();
        super.applyPowers();
        this.baseDamage = realBaseDamage;
        this.isDamageModified = this.damage != this.baseDamage;
    }

    private int countChanneledOrbs() {
        int orbs = 0;
        for(int i = 0; i < AbstractDungeon.player.orbs.size(); ++i) {
            if (!(AbstractDungeon.player.orbs.get(i) instanceof EmptyOrbSlot)) {
                orbs++;
            }
        }

        // Manually add newly channeled orb so preview damage on hover will be accurate
        if (upgraded && AbstractDungeon.player.hasEmptyOrb()) {
            orbs++;
        }
        return orbs;
    }
}
