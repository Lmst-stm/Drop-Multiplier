package lmstmyh.dropmultiplier.client;

import lmstmyh.dropmultiplier.DropMultiplier;
import lmstmyh.dropmultiplier.common.CommonProxy;
import net.minecraftforge.common.MinecraftForge;

public class ClientProxy extends CommonProxy {

    @Override
    public void init() {
        super.init();
    }

    @Override
    public void clientInit() {
        // Register key mappings via mod event bus
        DropMultiplier.instance.getModEventBus().addListener(KeyHandler::onRegisterKeyMappings);
        // Register key input handler on Forge event bus
        MinecraftForge.EVENT_BUS.register(new KeyHandler());
    }
}
