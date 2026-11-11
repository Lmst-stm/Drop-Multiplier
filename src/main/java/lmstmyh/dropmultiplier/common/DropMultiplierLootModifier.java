package lmstmyh.dropmultiplier.common;

import com.google.gson.JsonObject;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameters;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DropMultiplierLootModifier extends LootModifier {

    protected DropMultiplierLootModifier(ILootCondition[] conditionsIn) {
        super(conditionsIn);
    }

    @Override
    protected List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context) {
        double multiplier = ModConfig.DROP_MULTIPLIER.get();
        if (!ModConfig.MULTIPLIER_ENABLED.get() || multiplier <= 0.0D || multiplier == 1.0D) {
            return generatedLoot;
        }

        String contextId = resolveContextId(context);
        if (!shouldAffectContext(context, contextId)) {
            return generatedLoot;
        }

        Random random = context.getRandom();
        List<ItemStack> multipliedLoot = new ArrayList<>();
        boolean changed = false;

        for (ItemStack stack : generatedLoot) {
            if (stack.isEmpty()) {
                continue;
            }

            int scaledCount = scaleCount(stack.getCount(), multiplier, random);
            if (scaledCount <= 0) {
                changed = true;
                continue;
            }

            changed |= scaledCount != stack.getCount();
            appendScaledStacks(multipliedLoot, stack, scaledCount);
        }

        return changed ? multipliedLoot : generatedLoot;
    }

    private boolean shouldAffectContext(LootContext context, String contextId) {
        if (isBlockContext(context)) {
            return ModConfig.AFFECT_BLOCKS.get() && WhitelistManager.isAllowed(contextId);
        }

        if (isMobLootContext(context)) {
            return ModConfig.AFFECT_MOBS.get() && WhitelistManager.isAllowed(contextId);
        }

        return ModConfig.AFFECT_LOOT.get() && WhitelistManager.isAllowed(contextId);
    }

    private String resolveContextId(LootContext context) {
        if (isBlockContext(context)) {
            BlockState blockState = context.get(LootParameters.BLOCK_STATE);
            ResourceLocation registryName = blockState.getBlock().getRegistryName();
            return registryName == null ? "loot:block" : registryName.toString();
        }

        if (isMobLootContext(context)) {
            Entity entity = context.get(LootParameters.THIS_ENTITY);
            ResourceLocation registryName = entity.getType().getRegistryName();
            return registryName == null ? "loot:mob" : registryName.toString();
        }

        if (context.has(LootParameters.TOOL)) {
            return "loot:fishing";
        }
        if (context.has(LootParameters.THIS_ENTITY)) {
            return "loot:entity_context";
        }
        if (context.has(LootParameters.POSITION)) {
            return "loot:chest";
        }
        return "loot:generic";
    }

    private boolean isBlockContext(LootContext context) {
        return context.has(LootParameters.BLOCK_STATE);
    }

    private boolean isMobLootContext(LootContext context) {
        if (!context.has(LootParameters.THIS_ENTITY)) {
            return false;
        }

        Entity entity = context.get(LootParameters.THIS_ENTITY);
        if (!(entity instanceof LivingEntity)) {
            return false;
        }

        return context.has(LootParameters.DAMAGE_SOURCE)
                || context.has(LootParameters.LAST_DAMAGE_PLAYER)
                || context.has(LootParameters.KILLER_ENTITY)
                || context.has(LootParameters.DIRECT_KILLER_ENTITY);
    }

    private int scaleCount(int originalCount, double multiplier, Random random) {
        if (originalCount <= 0) {
            return 0;
        }

        double scaled = originalCount * multiplier;
        if (scaled >= Integer.MAX_VALUE) {
            return Integer.MAX_VALUE;
        }

        int scaledCount = (int) Math.floor(scaled);
        double fraction = scaled - scaledCount;
        if (fraction > 0.0D && random.nextDouble() < fraction) {
            scaledCount++;
        }
        return Math.max(0, scaledCount);
    }

    private void appendScaledStacks(List<ItemStack> target, ItemStack source, int totalCount) {
        int remaining = totalCount;
        int maxStackSize = Math.max(1, source.getMaxStackSize());

        while (remaining > 0) {
            int batchSize = Math.min(remaining, maxStackSize);
            ItemStack copy = source.copy();
            copy.setCount(batchSize);
            target.add(copy);
            remaining -= batchSize;
        }
    }

    public static class Serializer extends GlobalLootModifierSerializer<DropMultiplierLootModifier> {

        @Override
        public DropMultiplierLootModifier read(ResourceLocation name, JsonObject object, ILootCondition[] conditionsIn) {
            return new DropMultiplierLootModifier(conditionsIn);
        }

    }
}
