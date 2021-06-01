package dumbAttackPack.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;

import static com.megacrit.cardcrawl.cards.red.PerfectedStrike.*;
import static dumbAttackPack.DumbAttackPack.makeID;

public class OrbStrike extends AbstractEasyCard {

    /*
     * (Channel 1 random orb.) Deal 10 damage. Deals additional damage for ALL your cards containing \"Strike\" plus orbs channeled this combat.
     */

    public static final String ID = makeID("OrbStrike");

    public OrbStrike() {
        super(ID, 2, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY, CardColor.BLUE);
        baseDamage = 10;
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
        if (upgraded) { //account for orb to be channeled.
            baseDamage++;
        }
        this.baseDamage += AbstractDungeon.actionManager.orbsChanneledThisCombat.size() + countCards();
        super.calculateCardDamage(mo);
        this.baseDamage = realBaseDamage;
        this.isDamageModified = this.damage != this.baseDamage;
    }

    public void applyPowers() {
        int realBaseDamage = this.baseDamage;
        this.baseDamage += AbstractDungeon.actionManager.orbsChanneledThisCombat.size() +  countCards();
        super.applyPowers();
        this.baseDamage = realBaseDamage;
        this.isDamageModified = this.damage != this.baseDamage;
    }
}
