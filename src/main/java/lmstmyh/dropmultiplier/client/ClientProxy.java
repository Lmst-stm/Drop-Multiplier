package lmstmyh.dropmultiplier.client;

import lmstmyh.dropmultiplier.DropMultiplier;
import lmstmyh.dropmultiplier.common.CommonProxy;
import net.neoforged.neoforge.common.NeoForge;

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
        NeoForge.EVENT_BUS.register(new KeyHandler());
    }
}
