package lmstmyh.dropmultiplier.common;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import lmstmyh.dropmultiplier.DropMultiplier;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.neoforge.registries.DeferredHolder;

public class LootModifierRegistry {

    public static final DeferredRegister<MapCodec<? extends IGlobalLootModifier>> GLM_SERIALIZERS =
            DeferredRegister.create(net.neoforged.neoforge.registries.NeoForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, DropMultiplier.MODID);

    public static final DeferredHolder<MapCodec<? extends IGlobalLootModifier>, MapCodec<? extends IGlobalLootModifier>> DROP_MULTIPLIER =
            GLM_SERIALIZERS.register("drop_multiplier", () -> DropMultiplierLootModifier.CODEC);

    public static void register(IEventBus modEventBus) {
        GLM_SERIALIZERS.register(modEventBus);
    }
}
