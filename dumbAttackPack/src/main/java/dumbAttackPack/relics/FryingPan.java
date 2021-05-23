package dumbAttackPack.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import dumbAttackPack.DefaultMod;
import dumbAttackPack.patches.CardTagEnum;
import dumbAttackPack.util.TextureLoader;

import static com.megacrit.cardcrawl.cards.AbstractCard.*;
import static dumbAttackPack.DefaultMod.makeRelicOutlinePath;
import static dumbAttackPack.DefaultMod.makeRelicPath;

public class FryingPan extends CustomRelic {

    public static final String ID = DefaultMod.makeID("FryingPan");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("fryingpan.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("fryingpan.png"));

    public FryingPan() { super(ID, IMG, OUTLINE, RelicTier.SHOP, LandingSound.SOLID); }

    @Override
    public void onEquip() {
        for (AbstractCard c: AbstractDungeon.player.masterDeck.getAttacks().group) {
            if(!c.hasTag(CardTags.STRIKE)) {
                c.name = c.name + " " + CardLibrary.getCard("Strike_R").name;
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
            c.name = c.name + " " + CardLibrary.getCard("Strike_R").name;
        }
    }

    @Override
    public void onObtainCard(AbstractCard c) {
        if (c.type.equals(CardType.ATTACK) && !c.hasTag(CardTags.STRIKE)) {
            c.name = c.name + " " + CardLibrary.getCard("Strike_R").name;
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
