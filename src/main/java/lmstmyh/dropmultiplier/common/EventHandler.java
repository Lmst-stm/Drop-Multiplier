package lmstmyh.dropmultiplier.common;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootPool;
import net.minecraft.world.storage.loot.LootTable;
import net.minecraft.world.storage.loot.RandomValueRange;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import java.lang.reflect.Field;
import java.util.List;

public class EventHandler {

    @SubscribeEvent
    public void onLivingDrops(LivingDropsEvent event) {
        if (!ModConfig.AFFECT_MOBS) return;

        Entity entity = event.getEntity();
        if (!WhitelistManager.isAllowed(entity)) return;

        event.getDrops().forEach(item -> {
            ItemStack stack = item.getItem();
            int originalCount = stack.getCount();
            int newCount = (int) Math.ceil(originalCount * ModConfig.DROP_MULTIPLIER);
            stack.setCount(newCount);
        });
    }

    @SubscribeEvent
    public void onBlockDrops(BlockEvent.HarvestDropsEvent event) {
        if (!ModConfig.AFFECT_BLOCKS) return;

        if (!WhitelistManager.isAllowed(event.getState().getBlock())) return;

        event.getDrops().forEach(stack -> {
            int originalCount = stack.getCount();
            int newCount = (int) Math.ceil(originalCount * ModConfig.DROP_MULTIPLIER);
            stack.setCount(newCount);
        });
    }

    @SubscribeEvent
    public void onLootTableLoad(LootTableLoadEvent event) {
        if (!ModConfig.AFFECT_LOOT) return;

        ResourceLocation name = event.getName();
        if (!WhitelistManager.isAllowed(name.toString())) return;

        LootTable table = event.getTable();

        try {

            Field poolsField = ObfuscationReflectionHelper.findField(LootTable.class, "field_186466_b");
            List<LootPool> pools = (List<LootPool>) poolsField.get(table);

            for (LootPool pool : pools) {

                Field rollsField = ObfuscationReflectionHelper.findField(LootPool.class, "field_186453_a");
                RandomValueRange range = (RandomValueRange) rollsField.get(pool);

                Field minField = ObfuscationReflectionHelper.findField(RandomValueRange.class, "field_186514_a");
                Field maxField = ObfuscationReflectionHelper.findField(RandomValueRange.class, "field_186515_b");

                int originalMin = minField.getInt(range);
                int originalMax = maxField.getInt(range);

                int newMin = (int) Math.ceil(originalMin * ModConfig.DROP_MULTIPLIER);
                int newMax = (int) Math.ceil(originalMax * ModConfig.DROP_MULTIPLIER);

                minField.setInt(range, newMin);
                maxField.setInt(range, newMax);
            }
        } catch (Exception e) {

            System.err.println("Failed to modify loot table: " + e.getMessage());
        }
    }

    @SubscribeEvent
    public void onEntityJoinWorld(EntityJoinWorldEvent event) {
        if (!ModConfig.AFFECT_EXP) return;

        if (event.getEntity() instanceof EntityXPOrb) {
            EntityXPOrb xpOrb = (EntityXPOrb) event.getEntity();
            int originalXP = xpOrb.getXpValue();
            int newXP = (int) Math.ceil(originalXP * ModConfig.DROP_MULTIPLIER);
            xpOrb.xpValue = newXP;
        }
    }
}