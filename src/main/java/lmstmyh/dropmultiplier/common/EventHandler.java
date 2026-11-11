package lmstmyh.dropmultiplier.common;

import lmstmyh.dropmultiplier.DropMultiplier;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class EventHandler {

    @SubscribeEvent
    public void onEntityJoinWorld(EntityJoinWorldEvent event) {
        if (event.getWorld().isRemote()) {
            return;
        }
        if (!ModConfig.isEnabled() || !ModConfig.AFFECT_EXP.get()) {
            return;
        }
        if (!(event.getEntity() instanceof EntityXPOrb)) {
            return;
        }

        double multiplier = ModConfig.getMultiplier();
        if (multiplier <= 0.0D || multiplier == 1.0D) {
            return;
        }

        EntityXPOrb xpOrb = (EntityXPOrb) event.getEntity();
        int oldXP = xpOrb.getXpValue();
        int newXP = scaleValue(oldXP, multiplier);
        if (newXP != oldXP) {
            xpOrb.xpValue = newXP;
            DropMultiplier.logger.debug("XP orb: " + oldXP + " -> " + newXP);
        }
    }

    private int scaleValue(int originalValue, double multiplier) {
        if (originalValue <= 0) {
            return originalValue;
        }

        double scaledValue = originalValue * multiplier;
        if (scaledValue >= Integer.MAX_VALUE) {
            return Integer.MAX_VALUE;
        }

        return Math.max(0, (int) Math.round(scaledValue));
    }
}
