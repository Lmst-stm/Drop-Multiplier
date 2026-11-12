package lmstmyh.dropmultiplier.common;

import com.google.gson.JsonObject;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DropMultiplierLootModifier extends LootModifier {

    protected DropMultiplierLootModifier(LootItemCondition[] conditionsIn) {
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
            BlockState blockState = context.getParamOrNull(LootContextParams.BLOCK_STATE);
            ResourceLocation registryName = blockState.getBlock().getRegistryName();
            return registryName == null ? "loot:block" : registryName.toString();
        }

        if (isMobLootContext(context)) {
            Entity entity = context.getParamOrNull(LootContextParams.THIS_ENTITY);
            ResourceLocation registryName = net.minecraftforge.registries.ForgeRegistries.ENTITIES.getKey(entity.getType());
            return registryName == null ? "loot:mob" : registryName.toString();
        }

        if (context.hasParam(LootContextParams.TOOL)) {
            return "loot:fishing";
        }
        if (context.hasParam(LootContextParams.THIS_ENTITY)) {
            return "loot:entity_context";
        }
        if (context.hasParam(LootContextParams.ORIGIN)) {
            return "loot:chest";
        }
        return "loot:generic";
    }

    private boolean isBlockContext(LootContext context) {
        return context.hasParam(LootContextParams.BLOCK_STATE);
    }

    private boolean isMobLootContext(LootContext context) {
        if (!context.hasParam(LootContextParams.THIS_ENTITY)) {
            return false;
        }

        Entity entity = context.getParamOrNull(LootContextParams.THIS_ENTITY);
        if (!(entity instanceof LivingEntity)) {
            return false;
        }

        return context.hasParam(LootContextParams.DAMAGE_SOURCE)
                || context.hasParam(LootContextParams.LAST_DAMAGE_PLAYER)
                || context.hasParam(LootContextParams.KILLER_ENTITY)
                || context.hasParam(LootContextParams.DIRECT_KILLER_ENTITY);
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
        public DropMultiplierLootModifier read(ResourceLocation name, JsonObject object, LootItemCondition[] conditionsIn) {
            return new DropMultiplierLootModifier(conditionsIn);
        }

        @Override
        public JsonObject write(DropMultiplierLootModifier instance) {
            return makeConditions(instance.conditions);
        }
    }
}
