package lmstmyh.dropmultiplier.common;

import lmstmyh.dropmultiplier.DropMultiplier;
import net.minecraft.world.entity.ExperienceOrb;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.bus.api.SubscribeEvent;

public class EventHandler {

    @SubscribeEvent
    public void onEntityJoinWorld(EntityJoinLevelEvent event) {
        if (event.getLevel().isClientSide()) {
            return;
        }
        if (!ModConfig.MULTIPLIER_ENABLED.get() || !ModConfig.AFFECT_EXP.get()) {
            return;
        }
        if (!(event.getEntity() instanceof ExperienceOrb)) {
            return;
        }

        double multiplier = ModConfig.DROP_MULTIPLIER.get();
        if (multiplier <= 0.0D || multiplier == 1.0D) {
            return;
        }

        ExperienceOrb xpOrb = (ExperienceOrb) event.getEntity();
        int oldXP = xpOrb.getValue();
        int newXP = scaleValue(oldXP, multiplier);
        if (newXP != oldXP) {
            setOrbValue(xpOrb, newXP);
            DropMultiplier.LOGGER.debug("XP orb: " + oldXP + " -> " + newXP);
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

    private static void setOrbValue(ExperienceOrb orb, int value) {
        try {
            ExperienceOrb.class.getMethod("setValue", int.class).invoke(orb, value);
            return;
        } catch (Throwable ignored) {
            // fall through to field access
        }
        try {
            java.lang.reflect.Field f = ExperienceOrb.class.getDeclaredField("value");
            f.setAccessible(true);
            f.setInt(orb, value);
        } catch (Throwable ignored) {
            DropMultiplier.LOGGER.debug("Unable to set XP orb value: " + ignored);
        }
    }
}
