package lmstmyh.dropmultiplier;

import lmstmyh.dropmultiplier.client.ClientProxy;
import lmstmyh.dropmultiplier.common.CommonProxy;
import lmstmyh.dropmultiplier.common.CommandHandler;
import lmstmyh.dropmultiplier.common.ModConfig;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.*;
import org.apache.logging.log4j.Logger;

@Mod(
        modid = DropMultiplier.MODID,
        name = DropMultiplier.NAME,
        version = DropMultiplier.VERSION,
        acceptedMinecraftVersions = "[1.12.2]"
)
public class DropMultiplier {

    public static final String MODID = "dropmultiplier";
    public static final String NAME = "Drop Multiplier";
    public static final String VERSION = "1.2.1";

    public static Logger logger;

    @SidedProxy(
            clientSide = "lmstmyh.dropmultiplier.client.ClientProxy",
            serverSide = "lmstmyh.dropmultiplier.common.CommonProxy"
    )
    public static CommonProxy proxy;

    @Mod.Instance
    public static DropMultiplier instance;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        logger.info("DropMultiplier PreInitializing...");
        ModConfig.init(event.getSuggestedConfigurationFile());
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        logger.info("DropMultiplier Initializing...");
        proxy.init(event);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        logger.info("DropMultiplier PostInitializing...");
    }

    @Mod.EventHandler
    public void serverStarting(FMLServerStartingEvent event) {
        logger.info("Registering DropMultiplier commands...");
        CommandHandler.registerCommands(event);
    }
}