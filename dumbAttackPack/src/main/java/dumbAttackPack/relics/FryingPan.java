package dumbAttackPack.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.red.Strike_Red;
import com.megacrit.cardcrawl.cards.tempCards.Shiv;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import dumbAttackPack.DumbAttackPack;
import dumbAttackPack.patches.CardTagEnum;
import dumbAttackPack.util.TexLoader;

import static com.megacrit.cardcrawl.cards.AbstractCard.*;
import static dumbAttackPack.DumbAttackPack.makeRelicPath;
import static dumbAttackPack.DumbAttackPack.makeRelicOutlinePath;

public class FryingPan extends CustomRelic {
    public AbstractCard.CardColor color;

    public static final String ID = DumbAttackPack.makeID("FryingPan");

    private static final Texture IMG = TexLoader.getTexture(makeRelicPath("fryingpan.png"));
    private static final Texture OUTLINE = TexLoader.getTexture(makeRelicOutlinePath("fryingpan.png"));

    public FryingPan() { super(ID, IMG, OUTLINE, RelicTier.SHOP, LandingSound.SOLID); }

    @Override
    public void onEquip() {
        for (AbstractCard c: AbstractDungeon.player.masterDeck.getAttacks().group) {
            if(!c.hasTag(CardTags.STRIKE) && !c.cardID.equals(Shiv.ID)) {
                c.name = c.originalName + " " + CardLibrary.getCard(Strike_Red.ID).name;
                c.tags.add(CardTagEnum.FRYING_STRIKE);
                c.tags.add(CardTags.STRIKE);
            }
        }
    }

    @Override
    public void onUnequip() {
        for (AbstractCard c: AbstractDungeon.player.masterDeck.getAttacks().group) {
            if(c.hasTag(CardTagEnum.FRYING_STRIKE)) {
                c.name = c.originalName;
                c.tags.remove(c.tags.indexOf(CardTagEnum.FRYING_STRIKE));
                c.tags.remove(c.tags.indexOf(CardTags.STRIKE));
            }
        }
    }

    @Override
    public void onPreviewObtainCard(AbstractCard c) {
        if (c.type.equals(CardType.ATTACK) && !c.hasTag(CardTags.STRIKE)) {
            c.name = c.originalName + " " + CardLibrary.getCard(Strike_Red.ID).name;
        }
    }

    @Override
    public void onObtainCard(AbstractCard c) {
        if (c.type.equals(CardType.ATTACK) && !c.hasTag(CardTags.STRIKE)) {
            c.name = c.originalName + " " + CardLibrary.getCard(Strike_Red.ID).name;
            c.tags.add(CardTagEnum.FRYING_STRIKE);
            c.tags.add(CardTags.STRIKE);
        }
    }


    @Override
    public float atDamageModify(float damage, AbstractCard c) {
        return c.tags.contains(CardTags.STRIKE) && !c.tags.contains(CardTagEnum.FRYING_STRIKE) ? damage + 1.0F : damage;
    }


    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
