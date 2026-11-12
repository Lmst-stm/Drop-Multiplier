package lmstmyh.dropmultiplier;

import lmstmyh.dropmultiplier.client.ClientProxy;
import lmstmyh.dropmultiplier.common.*;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod("dropmultiplier")
public class DropMultiplier {

    public static final String MODID = "dropmultiplier";
    public static final String NAME = "Drop Multiplier";
    public static final String VERSION = "1.20.1-5.2.1";

    public static final Logger LOGGER = LoggerFactory.getLogger(NAME);

    public static CommonProxy proxy;

    public static DropMultiplier instance;

    private IEventBus modEventBus;

    public DropMultiplier(IEventBus modEventBus, ModContainer container) {
        instance = this;
        this.modEventBus = modEventBus;
        LOGGER.info("DropMultiplier Loading...");

        // Create the sided proxy
        proxy = createProxy();

        // Register config
        ModConfig.register(container);

        // Register loot modifier serializer
        LootModifierRegistry.register(modEventBus);

        // Register lifecycle listeners
        modEventBus.addListener(this::setup);
        modEventBus.addListener(this::doClientStuff);

        // Register server events
        NeoForge.EVENT_BUS.register(this);
        NeoForge.EVENT_BUS.register(new EventHandler());
    }

    public IEventBus getModEventBus() {
        return modEventBus;
    }

    private void setup(final FMLCommonSetupEvent event) {
        LOGGER.info("DropMultiplier Initializing...");
        proxy.init();
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        LOGGER.info("DropMultiplier Client Setup...");
        proxy.clientInit();
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        LOGGER.info("Registering DropMultiplier commands...");
        CommandHandler.registerCommands(event);
    }

    private static CommonProxy createProxy() {
        try {
            return (CommonProxy) Class.forName("lmstmyh.dropmultiplier.client.ClientProxy")
                    .getDeclaredConstructor().newInstance();
        } catch (Throwable t) {
            return new CommonProxy();
        }
    }
}
