package dumbAttackPack.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.CleaveEffect;

import static com.megacrit.cardcrawl.cards.red.PerfectedStrike.*;
import static dumbAttackPack.DumbAttackPack.makeID;

public class PatientStrike extends AbstractEasyCard {

    /*
     * Retain. Deal Deals 1(2) damage for ALL your cards containing \"Strike\" to ALL enemies. When Retained, increase additional damage by 1 this combat.
     */

    public static final String ID = makeID("PatientStrike");

    public PatientStrike() {
        super(ID, 2, CardType.ATTACK, CardRarity.COMMON, CardTarget.ALL_ENEMY, CardColor.PURPLE);
        baseDamage = 0;
        baseMagicNumber = 1;
        this.magicNumber = this.baseMagicNumber;
        this.tags.add(CardTags.STRIKE);
        this.selfRetain = true;
    }

    @Override
    public void upp() {
        this.upgradeMagicNumber(1);
    }

    public void onRetained() {
        if (upgraded) {
            this.upgradeDamage((magicNumber - 1) * (countCards() + 1)); //subtract 1 from magic number because upgrade should only simulate a turn of retain, not double retain value

        } else {
            this.upgradeDamage(magicNumber * (countCards() + 1)); //add one for the card itself, as it's not found in hand array
        }
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        this.addToBot(new SFXAction("ATTACK_DAGGER_1"));
        this.addToBot(new VFXAction(abstractPlayer, new CleaveEffect(), 0.1F));
        this.addToBot(new DamageAllEnemiesAction(abstractPlayer, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.NONE));
    }

    public void calculateCardDamage(AbstractMonster mo) {
        int realBaseDamage = this.baseDamage;
        this.baseDamage += this.magicNumber * countCards();
        super.calculateCardDamage(mo);
        this.baseDamage = realBaseDamage;
        this.isDamageModified = this.damage != this.baseDamage;
    }

    public void applyPowers() {
        int realBaseDamage = this.baseDamage;
        this.baseDamage += this.magicNumber * countCards();
        super.applyPowers();
        this.baseDamage = realBaseDamage;
        this.isDamageModified = this.damage != this.baseDamage;
    }
}
