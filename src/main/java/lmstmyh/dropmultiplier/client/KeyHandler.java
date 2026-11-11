package lmstmyh.dropmultiplier.client;

import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.lwjgl.glfw.GLFW;

@OnlyIn(Dist.CLIENT)
public class KeyHandler {

    public static final KeyMapping TOGGLE_KEY = new KeyMapping(
            "key.dropmultiplier.toggle",
            GLFW.GLFW_KEY_PERIOD,
            "key.categories.dropmultiplier"
    );

    public static void onRegisterKeyMappings(RegisterKeyMappingsEvent event) {
        event.register(TOGGLE_KEY);
    }

    /**
     * Poll consumeClick() during the client tick for reliable single-press detection.
     * This is more reliable than using InputEvent.Key which fires for every key state change.
     */
    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        while (TOGGLE_KEY.consumeClick()) {
            requestServerToggle();
        }
    }

    private void requestServerToggle() {
        Minecraft minecraft = Minecraft.getInstance();
        LocalPlayer player = minecraft.player;
        if (player == null) {
            return;
        }

        // In singleplayer, the integrated server runs in the same process
        // so hasPermissions(2) check works directly
        if (player.hasPermissions(2)) {
            player.connection.sendCommand("dropmultiplier toggle");
            return;
        }

        player.displayClientMessage(
                Component.translatable("msg.dropmultiplier.no_permission"),
                false
        );
    }
}
