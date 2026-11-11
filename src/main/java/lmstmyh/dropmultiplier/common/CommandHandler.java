package lmstmyh.dropmultiplier.common;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;

public class CommandHandler {

    public static void registerCommands(FMLServerStartingEvent event) {
        CommandDispatcher<CommandSource> dispatcher = event.getServer().getCommandManager().getDispatcher();

        dispatcher.register(
                LiteralArgumentBuilder.<CommandSource>literal("dropmultiplier")
                        .requires(source -> source.hasPermissionLevel(2))
                        .then(Commands.literal("info")
                                .executes(CommandHandler::executeInfo))
                        .then(Commands.literal("enable")
                                .executes(context -> executeSetEnabled(context, true)))
                        .then(Commands.literal("disable")
                                .executes(context -> executeSetEnabled(context, false)))
                        .then(Commands.literal("toggle")
                                .executes(CommandHandler::executeToggle))
                        .then(Commands.literal("reset")
                                .executes(CommandHandler::executeReset))
                        .then(Commands.argument("multiplier", DoubleArgumentType.doubleArg(0.01))
                                .executes(CommandHandler::executeSet))
                        .executes(CommandHandler::executeQuery)
        );
    }

    private static int executeQuery(CommandContext<CommandSource> context) {
        context.getSource().sendFeedback(
                new StringTextComponent("\u00a7a\u5f53\u524d\u6389\u843d\u500d\u6570: \u00a7e" + ModConfig.getMultiplier()),
                false
        );
        context.getSource().sendFeedback(
                new StringTextComponent("\u00a7a\u500d\u6570\u5f00\u5173: \u00a7e" + ModConfig.isEnabled()),
                false
        );
        return Command.SINGLE_SUCCESS;
    }

    private static int executeSet(CommandContext<CommandSource> context) {
        double multiplier = DoubleArgumentType.getDouble(context, "multiplier");
        ModConfig.setMultiplier(multiplier);
        ModConfig.save();

        context.getSource().sendFeedback(
                new StringTextComponent("\u00a7a\u6389\u843d\u500d\u6570\u5df2\u8bbe\u7f6e\u4e3a: \u00a7e" + multiplier),
                false
        );
        return Command.SINGLE_SUCCESS;
    }

    private static int executeSetEnabled(CommandContext<CommandSource> context, boolean enabled) {
        ModConfig.setEnabled(enabled);
        ModConfig.save();

        context.getSource().sendFeedback(
                new StringTextComponent(enabled ? "\u00a7a\u6389\u843d\u500d\u6570\u5df2\u5f00\u542f" : "\u00a7c\u6389\u843d\u500d\u6570\u5df2\u5173\u95ed"),
                false
        );
        return Command.SINGLE_SUCCESS;
    }

    private static int executeToggle(CommandContext<CommandSource> context) {
        return executeSetEnabled(context, !ModConfig.isEnabled());
    }

    private static int executeReset(CommandContext<CommandSource> context) {
        ModConfig.setMultiplier(ModConfig.DEFAULT_MULTIPLIER);
        ModConfig.save();

        context.getSource().sendFeedback(
                new StringTextComponent("\u00a7a\u5df2\u91cd\u7f6e\u4e3a\u9ed8\u8ba4\u500d\u6570: " + ModConfig.DEFAULT_MULTIPLIER),
                false
        );
        return Command.SINGLE_SUCCESS;
    }

    private static int executeInfo(CommandContext<CommandSource> context) {
        context.getSource().sendFeedback(
                new StringTextComponent("\u00a7a=== DropMultiplier \u4fe1\u606f ==="), false
        );
        context.getSource().sendFeedback(
                new StringTextComponent("\u00a7a\u5f53\u524d\u500d\u6570: \u00a7e" + ModConfig.getMultiplier()), false
        );
        context.getSource().sendFeedback(
                new StringTextComponent("\u00a7a\u500d\u6570\u5f00\u5173: \u00a7e" + ModConfig.isEnabled()), false
        );
        context.getSource().sendFeedback(
                new StringTextComponent("\u00a7a\u5f71\u54cd\u7ecf\u9a8c: \u00a7e" + ModConfig.AFFECT_EXP.get()), false
        );
        context.getSource().sendFeedback(
                new StringTextComponent("\u00a7a\u5f71\u54cd\u751f\u7269: \u00a7e" + ModConfig.AFFECT_MOBS.get()), false
        );
        context.getSource().sendFeedback(
                new StringTextComponent("\u00a7a\u5f71\u54cd\u65b9\u5757: \u00a7e" + ModConfig.AFFECT_BLOCKS.get()), false
        );
        context.getSource().sendFeedback(
                new StringTextComponent("\u00a7a\u5f71\u54cd\u6218\u5229\u54c1: \u00a7e" + ModConfig.AFFECT_LOOT.get()), false
        );
        context.getSource().sendFeedback(
                new StringTextComponent("\u00a7a\u767d\u540d\u5355\u6570\u91cf: \u00a7e" + ModConfig.WHITELIST.get().size()), false
        );
        context.getSource().sendFeedback(
                new StringTextComponent("\u00a7a\u9ed1\u540d\u5355\u6570\u91cf: \u00a7e" + ModConfig.BLACKLIST.get().size()), false
        );
        return Command.SINGLE_SUCCESS;
    }
}
