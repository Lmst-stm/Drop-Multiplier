package lmstmyh.dropmultiplier.common;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.Block;
import net.minecraft.core.registries.BuiltInRegistries;

/**
 * Helper class to avoid deprecated API calls on BuiltInRegistries.
 */
public class ForgeRegistriesHelper {

    public static ResourceLocation getBlockKey(Block block) {
        return BuiltInRegistries.BLOCK.getKey(block);
    }

    public static ResourceLocation getEntityKey(EntityType<?> type) {
        return BuiltInRegistries.ENTITY_TYPE.getKey(type);
    }
}
