package lmstmyh.dropmultiplier;

import lmstmyh.dropmultiplier.client.ClientProxy;
import lmstmyh.dropmultiplier.common.*;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
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

    /**
     * No-arg constructor for Forge versions that do not automatically resolve
     * the FMLJavaModLoadingContext constructor.
     */
    public DropMultiplier() {
        this(FMLJavaModLoadingContext.get());
    }

    public DropMultiplier(FMLJavaModLoadingContext context) {
        instance = this;
        this.modEventBus = context.getModEventBus();
        LOGGER.info("DropMultiplier Loading...");

        // Create the sided proxy
        proxy = DistExecutor.safeRunForDist(() -> ClientProxy::new, () -> CommonProxy::new);

        // Register config
        ModConfig.register();

        // Register loot modifier serializer
        LootModifierRegistry.register(modEventBus);

        // Register lifecycle listeners
        modEventBus.addListener(this::setup);
        modEventBus.addListener(this::doClientStuff);

        // Register server events
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(new EventHandler());
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
}
