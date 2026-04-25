package lmstmyh.dropmultiplier.client;

import lmstmyh.dropmultiplier.common.ModConfig;
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
            toggleMultiplier();
        }
    }

    private void toggleMultiplier() {
        boolean currentlyEnabled = ModConfig.MULTIPLIER_ENABLED;
        ModConfig.setEnabled(!currentlyEnabled);

        Minecraft.getMinecraft().player.sendMessage(
                new TextComponentString(
                        (currentlyEnabled ? "§c倍数已关闭" : "§a倍数已开启")
                                + " §7(当前 " + ModConfig.DROP_MULTIPLIER + "x)")
                );
    }
}