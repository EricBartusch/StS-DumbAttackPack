package dumbAttackPack.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;

import java.util.ArrayList;

@SpirePatch(clz = AbstractCard.class, method = "makeStatEquivalentCopy")
public class CopyStatPatch {

    @SpirePostfixPatch
    public static AbstractCard Postfix(AbstractCard __result, AbstractCard __instance) {
        __result.tags = new ArrayList(__instance.tags);
        return __result;
    }
}