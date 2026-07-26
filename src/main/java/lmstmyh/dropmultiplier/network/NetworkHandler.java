package lmstmyh.dropmultiplier.network;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

/**
 * Server-side network handler for DropMultiplier.
 * Handles all packet registration and network channel setup.
 */
public class NetworkHandler {

    public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel("dropmultiplier");

    private static int packetId = 0;

    public static void init() {
        // Client -> Server: player presses toggle key
        INSTANCE.registerMessage(MessageKeyToggle.Handler.class, MessageKeyToggle.class, packetId++, Side.SERVER);
        // Server -> Client: sync config state to clients
        INSTANCE.registerMessage(MessageConfigSync.Handler.class, MessageConfigSync.class, packetId++, Side.CLIENT);
    }
}
