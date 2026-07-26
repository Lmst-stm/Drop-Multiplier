package lmstmyh.dropmultiplier.common;

import lmstmyh.dropmultiplier.network.NetworkHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy {

    public void preInit(FMLPreInitializationEvent event) {
    }

    public void init(FMLInitializationEvent event) {
        // Register network channel (server-side packet handling)
        NetworkHandler.init();

        // Register event handlers
        MinecraftForge.EVENT_BUS.register(new EventHandler());
    }
}
