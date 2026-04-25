package lmstmyh.dropmultiplier.common;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

public class CommandHandler extends CommandBase {

    public static void registerCommands(FMLServerStartingEvent event) {
        event.registerServerCommand(new CommandHandler());
    }

    @Override
    public String getName() {
        return "dropmultiplier";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "/dropmultiplier <倍数> 或 /dropmultiplier reset 或 /dropmultiplier info";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (args.length == 0) {
            sender.sendMessage(new TextComponentString(
                    "§a当前掉落倍数: §e" + ModConfig.DROP_MULTIPLIER
            ));
            return;
        }

        if (args[0].equalsIgnoreCase("reset")) {
            ModConfig.setMultiplier(1.0);
            sender.sendMessage(new TextComponentString("§a已重置为默认倍数: 1.0"));
            return;
        }

        if (args[0].equalsIgnoreCase("info")) {
            sender.sendMessage(new TextComponentString("§a=== DropMultiplier 信息 ==="));
            sender.sendMessage(new TextComponentString("§a当前倍数: §e" + ModConfig.DROP_MULTIPLIER));
            sender.sendMessage(new TextComponentString("§a影响经验: §e" + ModConfig.AFFECT_EXP));
            sender.sendMessage(new TextComponentString("§a影响生物: §e" + ModConfig.AFFECT_MOBS));
            sender.sendMessage(new TextComponentString("§a影响方块: §e" + ModConfig.AFFECT_BLOCKS));
            sender.sendMessage(new TextComponentString("§a影响战利品: §e" + ModConfig.AFFECT_LOOT));
            sender.sendMessage(new TextComponentString("§a白名单数量: §e" + ModConfig.WHITELIST.size()));
            sender.sendMessage(new TextComponentString("§a黑名单数量: §e" + ModConfig.BLACKLIST.size()));
            return;
        }

        try {
            double multiplier = Double.parseDouble(args[0]);

            // 无限制版本：允许任何正数倍数
            if (multiplier <= 0) {
                sender.sendMessage(new TextComponentString("§c倍数必须大于 0"));
                return;
            }

            ModConfig.setMultiplier(multiplier);
            sender.sendMessage(new TextComponentString(
                    "§a掉落倍数已设置为: §e" + multiplier
            ));

        } catch (NumberFormatException e) {
            sender.sendMessage(new TextComponentString("§c请输入有效的数字"));
        }
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 2; // OP权限
    }
}