package lmstmyh.dropmultiplier.common;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ModConfig {

    private static final Logger LOGGER = LogManager.getLogger("DropMultiplier");
    private static Configuration config;

    public static double DROP_MULTIPLIER = 2.0;
    public static boolean MULTIPLIER_ENABLED = true;
    public static boolean AFFECT_EXP = true;
    public static boolean AFFECT_MOBS = true;
    public static boolean AFFECT_BLOCKS = true;
    public static boolean AFFECT_LOOT = true;

    public static List<String> WHITELIST = new ArrayList<>();
    public static List<String> BLACKLIST = new ArrayList<>();

    public static void init(File configFile) {
        config = new Configuration(configFile);
        loadConfig();
    }

    public static void loadConfig() {
        try {
            config.load();

            Property multiplierProp = config.get(
                    Configuration.CATEGORY_GENERAL,
                    "dropMultiplier",
                    2.0,
                    "掉落倍数 (1.0 = 正常，无上限)"
            );
            DROP_MULTIPLIER = multiplierProp.getDouble();

            Property enabledProp = config.get(
                    Configuration.CATEGORY_GENERAL,
                    "multiplierEnabled",
                    true,
                    "倍数是否启用 (按 . 键切换)"
            );
            MULTIPLIER_ENABLED = enabledProp.getBoolean();

            Property expProp = config.get(
                    Configuration.CATEGORY_GENERAL,
                    "affectExperience",
                    true,
                    "是否影响经验值"
            );
            AFFECT_EXP = expProp.getBoolean();

            Property mobsProp = config.get(
                    Configuration.CATEGORY_GENERAL,
                    "affectMobs",
                    true,
                    "是否影响生物掉落"
            );
            AFFECT_MOBS = mobsProp.getBoolean();

            Property blocksProp = config.get(
                    Configuration.CATEGORY_GENERAL,
                    "affectBlocks",
                    true,
                    "是否影响方块掉落"
            );
            AFFECT_BLOCKS = blocksProp.getBoolean();

            Property lootProp = config.get(
                    Configuration.CATEGORY_GENERAL,
                    "affectLoot",
                    true,
                    "是否影响战利品"
            );
            AFFECT_LOOT = lootProp.getBoolean();

            Property whitelistProp = config.get(
                    Configuration.CATEGORY_GENERAL,
                    "whitelist",
                    new String[]{},
                    "白名单 (实体/方块/战利品表ID)"
            );
            WHITELIST = new ArrayList<>(whitelistProp.getStringList().length);
            for (String s : whitelistProp.getStringList()) {
                WHITELIST.add(s);
            }

            Property blacklistProp = config.get(
                    Configuration.CATEGORY_GENERAL,
                    "blacklist",
                    new String[]{},
                    "黑名单 (实体/方块/战利品表ID)"
            );
            BLACKLIST = new ArrayList<>(blacklistProp.getStringList().length);
            for (String s : blacklistProp.getStringList()) {
                BLACKLIST.add(s);
            }

        } catch (Exception e) {
            LOGGER.error("配置加载失败!", e);
        } finally {
            if (config.hasChanged()) {
                config.save();
            }
        }
    }

    public static void saveConfig() {
        if (config != null && config.hasChanged()) {
            config.save();
        }
    }

    public static void setMultiplier(double value) {
        DROP_MULTIPLIER = value;
        Property prop = config.get(Configuration.CATEGORY_GENERAL, "dropMultiplier", 2.0);
        prop.set(value);
        saveConfig();
    }

    public static void setEnabled(boolean enabled) {
        MULTIPLIER_ENABLED = enabled;
        Property prop = config.get(Configuration.CATEGORY_GENERAL, "multiplierEnabled", true);
        prop.set(enabled);
        saveConfig();
    }
}