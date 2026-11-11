package lmstmyh.dropmultiplier;

import lmstmyh.dropmultiplier.client.ClientProxy;
import lmstmyh.dropmultiplier.common.CommonProxy;
import lmstmyh.dropmultiplier.common.CommandHandler;
import lmstmyh.dropmultiplier.common.EventHandler;
import lmstmyh.dropmultiplier.common.ModConfig;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("dropmultiplier")
public class DropMultiplier {

    public static final String MODID = "dropmultiplier";
    public static final String NAME = "Drop Multiplier";
    public static final String VERSION = "1.16.5-2.5.4";

    public static Logger logger = LogManager.getLogger();

    public static CommonProxy proxy;

    public static DropMultiplier instance;

    public DropMultiplier() {
        instance = this;
        logger.info("DropMultiplier Loading...");

        // Create the sided proxy first so lifecycle callbacks never see a null reference.
        proxy = DistExecutor.safeRunForDist(() -> ClientProxy::new, () -> CommonProxy::new);

        // Register config
        ModConfig.register();

        // Register lifecycle listeners
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);

        // Register server events
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(new EventHandler());
    }

    private void setup(final FMLCommonSetupEvent event) {
        logger.info("DropMultiplier Initializing...");
        proxy.init();
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        logger.info("DropMultiplier Client Setup...");
        proxy.clientInit();
    }

    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event) {
        logger.info("Registering DropMultiplier commands...");
        CommandHandler.registerCommands(event);
    }
}
