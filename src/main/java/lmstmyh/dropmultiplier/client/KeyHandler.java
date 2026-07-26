package lmstmyh.dropmultiplier.client;

import lmstmyh.dropmultiplier.common.ModConfig;
import lmstmyh.dropmultiplier.network.MessageKeyToggle;
import lmstmyh.dropmultiplier.network.NetworkHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Keyboard;

public class KeyHandler {

    public static final KeyBinding TOGGLE_KEY = new KeyBinding(
            "key.dropmultiplier.toggle",
            Keyboard.KEY_PERIOD,
            "Drop Multiplier"
    );

    public static void registerKeys() {
        ClientRegistry.registerKeyBinding(TOGGLE_KEY);
    }

    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        if (TOGGLE_KEY.isPressed()) {
            requestServerToggle();
        }
    }

    /**
     * Sends a toggle request to the server via network packet.
     * On singleplayer, the integrated server processes it directly.
     * On multiplayer, the dedicated server handles the toggle and syncs back.
     */
    private void requestServerToggle() {
        Minecraft minecraft = Minecraft.getMinecraft();
        if (minecraft.player == null) {
            return;
        }

        // On dedicated server client: send network packet to server
        if (!minecraft.isIntegratedServerRunning()) {
            NetworkHandler.INSTANCE.sendToServer(new MessageKeyToggle());
            return;
        }

        // Singleplayer: toggle directly and sync
        boolean newState = !ModConfig.MULTIPLIER_ENABLED;
        ModConfig.setEnabled(newState);

        minecraft.player.sendMessage(
                new TextComponentString(
                        (newState ? "§a" : "§c") +
                                "Drop Multiplier " +
                                (newState ? "enabled" : "disabled") +
                                " §7(" + ModConfig.DROP_MULTIPLIER + "x)"
                )
        );
    }
}
