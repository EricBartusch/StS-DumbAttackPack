package dumbAttackPack.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDiscardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDrawPileEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToHandEffect;
import dumbAttackPack.relics.FryingPan;

import static com.megacrit.cardcrawl.cards.AbstractCard.*;

public class TempCardPatches
{
    @SpirePatch(clz = ShowCardAndAddToDiscardEffect.class, method = SpirePatch.CONSTRUCTOR, paramtypez = {AbstractCard.class})
    @SpirePatch(clz = ShowCardAndAddToDiscardEffect.class, method = SpirePatch.CONSTRUCTOR, paramtypez = {AbstractCard.class, float.class, float.class})
    @SpirePatch(clz = ShowCardAndAddToHandEffect.class, method = SpirePatch.CONSTRUCTOR, paramtypez = {AbstractCard.class})
    @SpirePatch(clz = ShowCardAndAddToHandEffect.class, method = SpirePatch.CONSTRUCTOR, paramtypez = {AbstractCard.class, float.class, float.class})
    @SpirePatch(clz = ShowCardAndAddToDrawPileEffect.class, method = SpirePatch.CONSTRUCTOR, paramtypez = {AbstractCard.class, float.class, float.class, boolean.class, boolean.class, boolean.class})
    public static class AddedCardPatch
    {
        public static void Postfix(AbstractGameEffect __instance, AbstractCard card)
        {
            if (AbstractDungeon.player.hasRelic(FryingPan.ID) && card.type.equals(CardType.ATTACK) && !card.tags.contains(CardTags.STRIKE)) {
                card.name = card.name + " " + CardLibrary.getCard("Strike_R").name;
                card.tags.add(CardTags.STRIKE);
                card.tags.add(CardTagEnum.FRYING_STRIKE);
                AbstractDungeon.player.getRelic(FryingPan.ID).flash();
            }
        }
    }
}