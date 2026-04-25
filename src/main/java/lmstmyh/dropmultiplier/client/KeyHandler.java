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
        double current = ModConfig.DROP_MULTIPLIER;
        double newMultiplier = (current == 1.0) ? 2.0 : 1.0;
        ModConfig.setMultiplier(newMultiplier);

        Minecraft.getMinecraft().player.sendMessage(
                new TextComponentString("§a掉落倍数切换为: §e" + newMultiplier)
        );
    }
}