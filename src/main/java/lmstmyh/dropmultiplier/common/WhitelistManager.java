package lmstmyh.dropmultiplier.common;

import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.Block;
import net.minecraft.core.registries.BuiltInRegistries;

import java.util.List;

public class WhitelistManager {

    public static boolean isAllowed(Object obj) {
        String id = getIdentifier(obj);

        List<? extends String> blacklist = ModConfig.BLACKLIST.get();
        if (!blacklist.isEmpty()) {
            for (String black : blacklist) {
                if (matchesPattern(id, black)) {
                    return false;
                }
            }
        }

        List<? extends String> whitelist = ModConfig.WHITELIST.get();
        if (whitelist.isEmpty()) {
            return true;
        }

        for (String white : whitelist) {
            if (matchesPattern(id, white)) {
                return true;
            }
        }

        return false;
    }

    private static String getIdentifier(Object obj) {
        if (obj instanceof Entity entity) {
            EntityType<?> type = entity.getType();
            Identifier regName = BuiltInRegistries.ENTITY_TYPE.getKey(type);
            if (regName != null) {
                return regName.toString();
            }
            return entity.getClass().getName();
        } else if (obj instanceof Block block) {
            Identifier regName = BuiltInRegistries.BLOCK.getKey(block);
            if (regName != null) {
                return regName.toString();
            }
            return obj.getClass().getName();
        } else if (obj instanceof Identifier) {
            return obj.toString();
        } else if (obj instanceof String) {
            return (String) obj;
        }
        return obj.getClass().getName();
    }

    private static boolean matchesPattern(String id, String pattern) {
        if (pattern.endsWith("*")) {
            String prefix = pattern.substring(0, pattern.length() - 1);
            return id.startsWith(prefix);
        }
        return id.equals(pattern);
    }
}
