package lmstmyh.dropmultiplier;

import lmstmyh.dropmultiplier.client.ClientProxy;
import lmstmyh.dropmultiplier.common.CommandHandler;
import lmstmyh.dropmultiplier.common.CommonProxy;
import lmstmyh.dropmultiplier.common.ModConfig;
import lmstmyh.dropmultiplier.network.MessageConfigSync;
import lmstmyh.dropmultiplier.network.NetworkHandler;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
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
    public static final String VERSION = "1.3.0";

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
        // Register server-side player events for config sync
        MinecraftForge.EVENT_BUS.register(this);
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

    /**
     * When a player joins the server, sync current config to their client.
     */
    @SubscribeEvent
    public void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.player instanceof EntityPlayerMP) {
            // Send current config to the newly connected player
            NetworkHandler.INSTANCE.sendTo(
                    new MessageConfigSync(ModConfig.MULTIPLIER_ENABLED, ModConfig.DROP_MULTIPLIER),
                    (EntityPlayerMP) event.player
            );
            logger.debug("Synced config to player: " + event.player.getName());
        }
    }
}
