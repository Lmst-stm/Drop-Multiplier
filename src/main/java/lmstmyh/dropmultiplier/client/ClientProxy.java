package lmstmyh.dropmultiplier.client;

import lmstmyh.dropmultiplier.common.CommonProxy;
import net.minecraftforge.common.MinecraftForge;

public class ClientProxy extends CommonProxy {

    @Override
    public void init() {
        super.init();
    }

    @Override
    public void clientInit() {
        KeyHandler.registerKeys();
        MinecraftForge.EVENT_BUS.register(new KeyHandler());
    }
}
