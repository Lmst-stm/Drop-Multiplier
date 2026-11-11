package lmstmyh.dropmultiplier.common;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

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
        if (obj instanceof Entity) {
            Entity entity = (Entity) obj;
            EntityType<?> type = entity.getType();
            ResourceLocation regName = type.getRegistryName();
            if (regName != null) {
                return regName.toString();
            }
            return entity.getClass().getName();
        } else if (obj instanceof Block) {
            ResourceLocation regName = ((Block) obj).getRegistryName();
            if (regName != null) {
                return regName.toString();
            }
            return obj.getClass().getName();
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
