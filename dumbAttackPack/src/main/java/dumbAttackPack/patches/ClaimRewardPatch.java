package dumbAttackPack.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rewards.RewardItem;
import dumbAttackPack.relics.FryingPan;


@SpirePatch(clz = RewardItem.class, method = "claimReward")
public class ClaimRewardPatch {

    @SpirePostfixPatch
    public static void Postfix(RewardItem __instance) {
        if(__instance.type.equals(RewardItem.RewardType.CARD) && AbstractDungeon.player.hasRelic(FryingPan.ID)) {
            for(AbstractCard c : __instance.cards) {
                if(c.type.equals(AbstractCard.CardType.ATTACK)) {
                    AbstractDungeon.player.getRelic(FryingPan.ID).flash();
                    return;
                }
            }
        }
    }
}
