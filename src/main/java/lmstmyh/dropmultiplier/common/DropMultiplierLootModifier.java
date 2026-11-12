package lmstmyh.dropmultiplier.common;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;

import javax.annotation.Nonnull;
import java.util.List;

public class DropMultiplierLootModifier extends LootModifier {

    public static final MapCodec<DropMultiplierLootModifier> CODEC = RecordCodecBuilder.mapCodec(
            inst -> codecStart(inst).apply(inst, DropMultiplierLootModifier::new)
    );

    protected DropMultiplierLootModifier(LootItemCondition[] conditionsIn) {
        super(conditionsIn);
    }

    @Override
    @Nonnull
    protected ObjectArrayList<ItemStack> doApply(LootTable table, ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        double multiplier = ModConfig.DROP_MULTIPLIER.get();
        if (!ModConfig.MULTIPLIER_ENABLED.get() || multiplier <= 0.0D || multiplier == 1.0D) {
            return generatedLoot;
        }

        String contextId = resolveContextId(context);
        if (!shouldAffectContext(context, contextId)) {
            return generatedLoot;
        }

        RandomSource random = context.getRandom();
        ObjectArrayList<ItemStack> multipliedLoot = new ObjectArrayList<>();
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

    @Override
    public MapCodec<? extends IGlobalLootModifier> codec() {
        return CODEC;
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
            BlockState blockState = context.getParameter(LootContextParams.BLOCK_STATE);
            if (blockState != null) {
                ResourceLocation registryName = ForgeRegistriesHelper.getBlockKey(blockState.getBlock());
                return registryName == null ? "loot:block" : registryName.toString();
            }
            return "loot:block";
        }

        if (isMobLootContext(context)) {
            Entity entity = context.getParameter(LootContextParams.THIS_ENTITY);
            if (entity != null) {
                ResourceLocation registryName = ForgeRegistriesHelper.getEntityKey(entity.getType());
                return registryName == null ? "loot:mob" : registryName.toString();
            }
            return "loot:mob";
        }

        if (context.hasParameter(LootContextParams.TOOL)) {
            return "loot:fishing";
        }
        if (context.hasParameter(LootContextParams.THIS_ENTITY)) {
            return "loot:entity_context";
        }
        if (context.hasParameter(LootContextParams.ORIGIN)) {
            return "loot:chest";
        }
        return "loot:generic";
    }

    private boolean isBlockContext(LootContext context) {
        return context.hasParameter(LootContextParams.BLOCK_STATE);
    }

    private boolean isMobLootContext(LootContext context) {
        if (!context.hasParameter(LootContextParams.THIS_ENTITY)) {
            return false;
        }

        Entity entity = context.getParameter(LootContextParams.THIS_ENTITY);
        if (!(entity instanceof LivingEntity)) {
            return false;
        }

        return context.hasParameter(LootContextParams.DAMAGE_SOURCE)
                || context.hasParameter(LootContextParams.LAST_DAMAGE_PLAYER)
                || context.hasParameter(LootContextParams.ATTACKING_ENTITY)
                || context.hasParameter(LootContextParams.DIRECT_ATTACKING_ENTITY);
    }

    private int scaleCount(int originalCount, double multiplier, RandomSource random) {
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
}
