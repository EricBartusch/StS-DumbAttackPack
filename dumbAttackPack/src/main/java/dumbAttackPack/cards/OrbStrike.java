package dumbAttackPack.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;

import java.util.ArrayList;
import java.util.HashSet;

import static com.megacrit.cardcrawl.cards.red.PerfectedStrike.*;
import static dumbAttackPack.DumbAttackPack.makeID;

public class OrbStrike extends AbstractEasyCard {

    /*
     * Deal 10 damage. Deals additional damage for ALL your cards containing \"Strike\" times unique Orbs channeled this combat. (If you have any empty orb slots, channel 1 random orb.) 
     */

    public static final String ID = makeID("OrbStrike");

    public OrbStrike() {
        super(ID, 2, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY, CardColor.BLUE);
        baseDamage = 10;
        this.tags.add(CardTags.STRIKE);
    }

    @Override
    public void upp() {
        this.rawDescription = this.rawDescription + cardStrings.UPGRADE_DESCRIPTION;
        this.initializeDescription();
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        this.addToBot(new DamageAction(abstractMonster, new DamageInfo(abstractPlayer, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        if (upgraded && AbstractDungeon.player.hasEmptyOrb()) {
            this.addToBot(new ChannelAction(AbstractOrb.getRandomOrb(true)));
        }
    }

    public void calculateCardDamage(AbstractMonster mo) {
        int realBaseDamage = this.baseDamage;
        this.baseDamage += uniqueOrbs() * countCards();
        super.calculateCardDamage(mo);
        this.baseDamage = realBaseDamage;
        this.isDamageModified = this.damage != this.baseDamage;
    }

    public void applyPowers() {
        int realBaseDamage = this.baseDamage;
        this.baseDamage += uniqueOrbs() * countCards();
        super.applyPowers();
        this.baseDamage = realBaseDamage;
        this.isDamageModified = this.damage != this.baseDamage;
    }

    private int uniqueOrbs() {
        ArrayList<AbstractOrb> orbs = AbstractDungeon.actionManager.orbsChanneledThisCombat;
        HashSet<String> uniqueOrbs = new HashSet();
        for (int i = 0; i < orbs.size(); i++ ) {
            uniqueOrbs.add(orbs.get(i).name);
        }
        return uniqueOrbs.size();
    }
}
