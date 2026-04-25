package lmstmyh.dropmultiplier.common;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.util.ResourceLocation;

public class WhitelistManager {

    public static boolean isAllowed(Object obj) {
        String id = getIdentifier(obj);

        if (!ModConfig.BLACKLIST.isEmpty()) {
            for (String black : ModConfig.BLACKLIST) {
                if (matchesPattern(id, black)) {
                    return false;
                }
            }
        }

        if (ModConfig.WHITELIST.isEmpty()) {
            return true;
        }

        for (String white : ModConfig.WHITELIST) {
            if (matchesPattern(id, white)) {
                return true;
            }
        }

        return false;
    }

    private static String getIdentifier(Object obj) {
        if (obj instanceof Entity) {
            Entity entity = (Entity) obj;
            String entityId = EntityList.getEntityString(entity);
            if (entityId != null) {
                return entityId;
            }
            return entity.getClass().getName();
        } else if (obj instanceof Block) {
            return ((Block) obj).getRegistryName().toString();
        } else if (obj instanceof ResourceLocation) {
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