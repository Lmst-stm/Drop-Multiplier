package lmstmyh.dropmultiplier.common;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraftforge.event.server.ServerStartingEvent;

public class CommandHandler {

    public static void registerCommands(ServerStartingEvent event) {
        CommandDispatcher<CommandSourceStack> dispatcher = event.getServer().getCommands().getDispatcher();

        dispatcher.register(
                LiteralArgumentBuilder.<CommandSourceStack>literal("dropmultiplier")
                        .requires(source -> source.hasPermission(2))
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

    /**
     * Send a translatable message to the command source.
     * This avoids hardcoded Chinese string encoding issues entirely.
     */
    private static void sendMsg(CommandContext<CommandSourceStack> ctx, String key, Object... args) {
        ctx.getSource().sendSuccess(new net.minecraft.network.chat.TranslatableComponent(key, args), false);
    }

    private static int executeQuery(CommandContext<CommandSourceStack> context) {
        sendMsg(context, "msg.dropmultiplier.query.multiplier", ModConfig.DROP_MULTIPLIER.get());
        sendMsg(context, "msg.dropmultiplier.query.enabled", ModConfig.MULTIPLIER_ENABLED.get());
        return Command.SINGLE_SUCCESS;
    }

    private static int executeSet(CommandContext<CommandSourceStack> context) {
        double multiplier = DoubleArgumentType.getDouble(context, "multiplier");
        ModConfig.DROP_MULTIPLIER.set(multiplier);
        ModConfig.save();

        sendMsg(context, "msg.dropmultiplier.set", multiplier);
        return Command.SINGLE_SUCCESS;
    }

    private static int executeSetEnabled(CommandContext<CommandSourceStack> context, boolean enabled) {
        ModConfig.MULTIPLIER_ENABLED.set(enabled);
        ModConfig.save();

        sendMsg(context, enabled ? "msg.dropmultiplier.enabled" : "msg.dropmultiplier.disabled");
        return Command.SINGLE_SUCCESS;
    }

    private static int executeToggle(CommandContext<CommandSourceStack> context) {
        return executeSetEnabled(context, !ModConfig.MULTIPLIER_ENABLED.get());
    }

    private static int executeReset(CommandContext<CommandSourceStack> context) {
        ModConfig.DROP_MULTIPLIER.set(ModConfig.DEFAULT_MULTIPLIER);
        ModConfig.save();

        sendMsg(context, "msg.dropmultiplier.reset", ModConfig.DEFAULT_MULTIPLIER);
        return Command.SINGLE_SUCCESS;
    }

    private static int executeInfo(CommandContext<CommandSourceStack> context) {
        sendMsg(context, "msg.dropmultiplier.info.header");
        sendMsg(context, "msg.dropmultiplier.query.multiplier", ModConfig.DROP_MULTIPLIER.get());
        sendMsg(context, "msg.dropmultiplier.query.enabled", ModConfig.MULTIPLIER_ENABLED.get());
        sendMsg(context, "msg.dropmultiplier.info.exp", ModConfig.AFFECT_EXP.get());
        sendMsg(context, "msg.dropmultiplier.info.mobs", ModConfig.AFFECT_MOBS.get());
        sendMsg(context, "msg.dropmultiplier.info.blocks", ModConfig.AFFECT_BLOCKS.get());
        sendMsg(context, "msg.dropmultiplier.info.loot", ModConfig.AFFECT_LOOT.get());
        sendMsg(context, "msg.dropmultiplier.info.whitelist_size", ModConfig.WHITELIST.get().size());
        sendMsg(context, "msg.dropmultiplier.info.blacklist_size", ModConfig.BLACKLIST.get().size());
        return Command.SINGLE_SUCCESS;
    }
}
