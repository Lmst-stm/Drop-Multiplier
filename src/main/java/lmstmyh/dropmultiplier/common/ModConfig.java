package lmstmyh.dropmultiplier.common;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig.Type;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid = "dropmultiplier", bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger("DropMultiplier");
    public static final double DEFAULT_MULTIPLIER = 2.0D;
    private static ForgeConfigSpec CONFIG;

    public static ForgeConfigSpec.DoubleValue DROP_MULTIPLIER;
    public static ForgeConfigSpec.BooleanValue MULTIPLIER_ENABLED;
    public static ForgeConfigSpec.BooleanValue AFFECT_EXP;
    public static ForgeConfigSpec.BooleanValue AFFECT_MOBS;
    public static ForgeConfigSpec.BooleanValue AFFECT_BLOCKS;
    public static ForgeConfigSpec.BooleanValue AFFECT_LOOT;
    public static ForgeConfigSpec.ConfigValue<List<? extends String>> WHITELIST;
    public static ForgeConfigSpec.ConfigValue<List<? extends String>> BLACKLIST;

    @SuppressWarnings("deprecation")
    public static void register() {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
        builder.comment("General settings").push("general");

        DROP_MULTIPLIER = builder.comment("Drop multiplier (1.0 = normal, no upper limit)")
                .defineInRange("dropMultiplier", DEFAULT_MULTIPLIER, 0.01, Double.MAX_VALUE);
        MULTIPLIER_ENABLED = builder.comment("Whether the multiplier is enabled (toggle with . key)")
                .define("multiplierEnabled", true);
        AFFECT_EXP = builder.comment("Whether to affect experience orbs")
                .define("affectExperience", true);
        AFFECT_MOBS = builder.comment("Whether to affect mob drops")
                .define("affectMobs", true);
        AFFECT_BLOCKS = builder.comment("Whether to affect block drops")
                .define("affectBlocks", true);
        AFFECT_LOOT = builder.comment("Whether to affect generic loot contexts such as chests, fishing and other loot tables")
                .define("affectLoot", true);
        WHITELIST = builder.comment("Whitelist (entity IDs, block IDs, or pseudo loot IDs like loot:chest, loot:fishing, loot:generic). Empty = allow all")
                .defineList("whitelist", new ArrayList<>(), s -> s instanceof String);
        BLACKLIST = builder.comment("Blacklist (entity IDs, block IDs, or pseudo loot IDs like loot:chest, loot:fishing, loot:generic)")
                .defineList("blacklist", new ArrayList<>(), s -> s instanceof String);

        builder.pop();
        CONFIG = builder.build();
        ModLoadingContext modLoadingContext = ModLoadingContext.get();
        modLoadingContext.registerConfig(Type.SERVER, CONFIG);
    }

    public static void save() { CONFIG.save(); }

    @SubscribeEvent
    public static void onConfigLoad(ModConfigEvent event) {
        if (event.getConfig().getModId().equals("dropmultiplier")) {
            LOGGER.info("DropMultiplier config loaded");
        }
    }
}
