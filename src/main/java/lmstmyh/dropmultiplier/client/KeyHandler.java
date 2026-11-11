package lmstmyh.dropmultiplier.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import org.lwjgl.glfw.GLFW;

public class KeyHandler {

    public static final KeyBinding TOGGLE_KEY = new KeyBinding(
            "key.dropmultiplier.toggle",
            GLFW.GLFW_KEY_PERIOD,
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

    private void requestServerToggle() {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.player == null) {
            return;
        }

        if (minecraft.isSingleplayer() || minecraft.player.hasPermissionLevel(2)) {
            minecraft.player.sendChatMessage("/dropmultiplier toggle");
            return;
        }

        minecraft.player.sendMessage(
                new TextComponentString(
                        "\u00a7e\u6309\u952e\u53ea\u80fd\u89e6\u53d1\u670d\u52a1\u5668\u547d\u4ee4\uff0c\u8bf7\u786e\u4fdd\u4f60\u6709 OP \u6743\u9650"
                )
        );
    }
}
