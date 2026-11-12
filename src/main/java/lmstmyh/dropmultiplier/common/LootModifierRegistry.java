package lmstmyh.dropmultiplier.common;

import lmstmyh.dropmultiplier.DropMultiplier;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = DropMultiplier.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class LootModifierRegistry {

    @SubscribeEvent
    public static void registerLootModifiers(RegistryEvent.Register<GlobalLootModifierSerializer<?>> event) {
        event.getRegistry().register(
                new DropMultiplierLootModifier.Serializer()
                        .setRegistryName(new ResourceLocation(DropMultiplier.MODID, "drop_multiplier"))
        );
    }
}
