package lmstmyh.dropmultiplier.common;

import com.mojang.serialization.Codec;
import lmstmyh.dropmultiplier.DropMultiplier;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class LootModifierRegistry {

    public static final DeferredRegister<Codec<? extends IGlobalLootModifier>> GLM_SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, DropMultiplier.MODID);

    public static final RegistryObject<Codec<DropMultiplierLootModifier>> DROP_MULTIPLIER =
            GLM_SERIALIZERS.register("drop_multiplier", () -> DropMultiplierLootModifier.CODEC);

    public static void register(IEventBus modEventBus) {
        GLM_SERIALIZERS.register(modEventBus);
    }
}
