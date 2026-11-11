package lmstmyh.dropmultiplier.common;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;

public class ForgeRegistriesHelper {

    public static ResourceLocation getBlockKey(Block block) {
        return ForgeRegistries.BLOCKS.getKey(block);
    }

    public static ResourceLocation getEntityKey(EntityType<?> type) {
        return ForgeRegistries.ENTITY_TYPES.getKey(type);
    }
}
