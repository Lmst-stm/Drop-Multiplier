package lmstmyh.dropmultiplier.common;

import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

@EventBusSubscriber(modid = "dropmultiplier", bus = EventBusSubscriber.Bus.MOD)
public class ModConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger("DropMultiplier");
    public static final double DEFAULT_MULTIPLIER = 2.0D;
    private static ModConfigSpec CONFIG;

    public static ModConfigSpec.DoubleValue DROP_MULTIPLIER;
    public static ModConfigSpec.BooleanValue MULTIPLIER_ENABLED;
    public static ModConfigSpec.BooleanValue AFFECT_EXP;
    public static ModConfigSpec.BooleanValue AFFECT_MOBS;
    public static ModConfigSpec.BooleanValue AFFECT_BLOCKS;
    public static ModConfigSpec.BooleanValue AFFECT_LOOT;
    public static ModConfigSpec.ConfigValue<List<? extends String>> WHITELIST;
    public static ModConfigSpec.ConfigValue<List<? extends String>> BLACKLIST;

    @SuppressWarnings("deprecation")
    public static void register(net.neoforged.fml.ModContainer container) {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();

        builder.comment("General settings").push("general");

        DROP_MULTIPLIER = builder
                .comment("Drop multiplier (1.0 = normal, no upper limit)")
                .defineInRange("dropMultiplier", DEFAULT_MULTIPLIER, 0.01, Double.MAX_VALUE);

        MULTIPLIER_ENABLED = builder
                .comment("Whether the multiplier is enabled (toggle with . key)")
                .define("multiplierEnabled", true);

        AFFECT_EXP = builder
                .comment("Whether to affect experience orbs")
                .define("affectExperience", true);

        AFFECT_MOBS = builder
                .comment("Whether to affect mob drops")
                .define("affectMobs", true);

        AFFECT_BLOCKS = builder
                .comment("Whether to affect block drops")
                .define("affectBlocks", true);

        AFFECT_LOOT = builder
                .comment("Whether to affect generic loot contexts such as chests, fishing and other loot tables")
                .define("affectLoot", true);

        WHITELIST = builder
                .comment("Whitelist (entity IDs, block IDs, or pseudo loot IDs like loot:chest, loot:fishing, loot:generic). Empty = allow all")
                .defineList("whitelist", new ArrayList<>(), s -> s instanceof String);

        BLACKLIST = builder
                .comment("Blacklist (entity IDs, block IDs, or pseudo loot IDs like loot:chest, loot:fishing, loot:generic)")
                .defineList("blacklist", new ArrayList<>(), s -> s instanceof String);

        builder.pop();

        CONFIG = builder.build();

        container.registerConfig(net.neoforged.fml.config.ModConfig.Type.COMMON, CONFIG);
    }

    public static void save() {
        CONFIG.save();
    }

    @SubscribeEvent
    public static void onConfigLoad(ModConfigEvent event) {
        if (event.getConfig().getModId().equals("dropmultiplier")) {
            LOGGER.info("DropMultiplier config loaded");
        }
    }
}
