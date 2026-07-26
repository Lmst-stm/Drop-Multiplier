package lmstmyh.dropmultiplier.network;

import io.netty.buffer.ByteBuf;
import lmstmyh.dropmultiplier.common.ModConfig;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/**
 * Client -> Server packet.
 * Sent when a player presses the toggle key binding.
 * The server toggles the multiplier and syncs the new config to all clients.
 */
public class MessageKeyToggle implements IMessage {

    public MessageKeyToggle() {
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        // No data needed — this is a simple toggle signal
    }

    @Override
    public void toBytes(ByteBuf buf) {
    }

    public static class Handler implements IMessageHandler<MessageKeyToggle, IMessage> {

        @Override
        public IMessage onMessage(MessageKeyToggle message, MessageContext ctx) {
            EntityPlayerMP player = ctx.getServerHandler().player;

            player.getServer().addScheduledTask(() -> {
                // Toggle the enabled state
                boolean newState = !ModConfig.MULTIPLIER_ENABLED;
                ModConfig.setEnabled(newState);
                ModConfig.saveConfig();

                // Sync new config to all players
                NetworkHandler.INSTANCE.sendToAll(
                        new MessageConfigSync(
                                ModConfig.MULTIPLIER_ENABLED,
                                ModConfig.DROP_MULTIPLIER
                        )
                );
            });

            return null;
        }
    }
}
