package lmstmyh.dropmultiplier.network;

import io.netty.buffer.ByteBuf;
import lmstmyh.dropmultiplier.common.ModConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Server -> Client packet.
 * Syncs the current multiplier config to a client.
 * Used after config changes (commands, toggle key) to keep clients in sync.
 */
public class MessageConfigSync implements IMessage {

    private boolean enabled;
    private double multiplier;

    public MessageConfigSync() {
    }

    public MessageConfigSync(boolean enabled, double multiplier) {
        this.enabled = enabled;
        this.multiplier = multiplier;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.enabled = buf.readBoolean();
        this.multiplier = buf.readDouble();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeBoolean(enabled);
        buf.writeDouble(multiplier);
    }

    public static class Handler implements IMessageHandler<MessageConfigSync, IMessage> {

        @Override
        @SideOnly(Side.CLIENT)
        public IMessage onMessage(MessageConfigSync message, MessageContext ctx) {
            Minecraft.getMinecraft().addScheduledTask(() -> {
                // Apply the server's config to the client-side cache
                ModConfig.MULTIPLIER_ENABLED = message.enabled;
                ModConfig.DROP_MULTIPLIER = message.multiplier;

                // Show notification to the player
                if (Minecraft.getMinecraft().player != null) {
                    Minecraft.getMinecraft().player.sendMessage(
                            new TextComponentString(
                                    (message.enabled ? "§a" : "§c") +
                                            "Drop Multiplier " +
                                            (message.enabled ? "enabled" : "disabled") +
                                            " §7(" + message.multiplier + "x)"
                            )
                    );
                }
            });

            return null;
        }
    }
}
