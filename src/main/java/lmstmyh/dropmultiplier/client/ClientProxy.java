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
        // Key binding API changed in 1.21.9 (KeyMapping.Category); the toggle key is not
        // registered on this branch. Use "/dropmultiplier toggle" instead.
        DropMultiplier.LOGGER.info("DropMultiplier Client Setup (key bind disabled on this version)");
    }
}
