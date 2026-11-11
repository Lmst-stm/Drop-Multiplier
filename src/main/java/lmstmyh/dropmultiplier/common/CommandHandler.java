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
                .then(Commands.literal("info").executes(CommandHandler::executeInfo))
                .then(Commands.literal("enable").executes(ctx -> executeSetEnabled(ctx, true)))
                .then(Commands.literal("disable").executes(ctx -> executeSetEnabled(ctx, false)))
                .then(Commands.literal("toggle").executes(CommandHandler::executeToggle))
                .then(Commands.literal("reset").executes(CommandHandler::executeReset))
                .then(Commands.argument("multiplier", DoubleArgumentType.doubleArg(0.01)).executes(CommandHandler::executeSet))
                .executes(CommandHandler::executeQuery)
        );
    }

    private static void sendMsg(CommandContext<CommandSourceStack> ctx, String key, Object... args) {
        ctx.getSource().sendSuccess(() -> Component.translatable(key, args), false);
    }

    private static void sendRaw(CommandContext<CommandSourceStack> ctx, String msg) {
        ctx.getSource().sendSuccess(() -> Component.literal(msg), false);
    }

    private static int executeQuery(CommandContext<CommandSourceStack> ctx) {
        sendRaw(ctx, "Multiplier: " + ModConfig.DROP_MULTIPLIER.get() + ", Enabled: " + ModConfig.MULTIPLIER_ENABLED.get());
        return Command.SINGLE_SUCCESS;
    }

    private static int executeSet(CommandContext<CommandSourceStack> ctx) {
        double multiplier = DoubleArgumentType.getDouble(ctx, "multiplier");
        ModConfig.DROP_MULTIPLIER.set(multiplier);
        ModConfig.save();
        sendRaw(ctx, "Multiplier set to: " + multiplier);
        return Command.SINGLE_SUCCESS;
    }

    private static int executeSetEnabled(CommandContext<CommandSourceStack> ctx, boolean enabled) {
        ModConfig.MULTIPLIER_ENABLED.set(enabled);
        ModConfig.save();
        sendRaw(ctx, enabled ? "Multiplier enabled!" : "Multiplier disabled!");
        return Command.SINGLE_SUCCESS;
    }

    private static int executeToggle(CommandContext<CommandSourceStack> ctx) {
        return executeSetEnabled(ctx, !ModConfig.MULTIPLIER_ENABLED.get());
    }

    private static int executeReset(CommandContext<CommandSourceStack> ctx) {
        ModConfig.DROP_MULTIPLIER.set(ModConfig.DEFAULT_MULTIPLIER);
        ModConfig.save();
        sendRaw(ctx, "Multiplier reset to " + ModConfig.DEFAULT_MULTIPLIER);
        return Command.SINGLE_SUCCESS;
    }

    private static int executeInfo(CommandContext<CommandSourceStack> ctx) {
        sendRaw(ctx, "--- DropMultiplier Info ---");
        sendRaw(ctx, "Multiplier: " + ModConfig.DROP_MULTIPLIER.get());
        sendRaw(ctx, "Enabled: " + ModConfig.MULTIPLIER_ENABLED.get());
        sendRaw(ctx, "Affect EXP: " + ModConfig.AFFECT_EXP.get());
        sendRaw(ctx, "Affect Mobs: " + ModConfig.AFFECT_MOBS.get());
        sendRaw(ctx, "Affect Blocks: " + ModConfig.AFFECT_BLOCKS.get());
        sendRaw(ctx, "Affect Loot: " + ModConfig.AFFECT_LOOT.get());
        return Command.SINGLE_SUCCESS;
    }
}
