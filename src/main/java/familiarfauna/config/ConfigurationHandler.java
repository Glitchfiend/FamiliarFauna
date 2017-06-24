package familiarfauna.config;

import java.io.File;

import familiarfauna.core.FamiliarFauna;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ConfigurationHandler
{
    public static Configuration config;

    public static final String SPAWN_SETTINGS = "Spawn Settings";
    
    public static boolean spawnButterflies;
    public static boolean spawnSnails;
    public static boolean spawnGrizzlyBears;

    public static void init(File configFile)
    {
        if (config == null)
        {
            config = new Configuration(configFile);
            loadConfiguration();
        }
    }

    private static void loadConfiguration()
    {
        try
        {
            spawnButterflies = config.getBoolean("Spawn Butterflies", SPAWN_SETTINGS, true, "Allow Butterflies to spawn.");
            spawnSnails = config.getBoolean("Spawn Snails", SPAWN_SETTINGS, true, "Allow Snails to spawn.");
            spawnGrizzlyBears = config.getBoolean("Spawn Grizzly Bears", SPAWN_SETTINGS, true, "Allow Grizzly Bears to spawn.");
        }
        catch (Exception e)
        {
            FamiliarFauna.logger.error("Familiar Fauna has encountered a problem loading misc.cfg", e);
        }
        finally
        {
            if (config.hasChanged()) config.save();
        }
    }

    @SubscribeEvent
    public void onConfigurationChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event)
    {
        if (event.getModID().equalsIgnoreCase(FamiliarFauna.MOD_ID))
        {
            loadConfiguration();
        }
    }
}