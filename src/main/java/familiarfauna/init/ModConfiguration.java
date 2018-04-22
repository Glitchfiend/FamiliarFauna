package familiarfauna.init;

import java.io.File;

import familiarfauna.config.ConfigurationHandler;
import net.minecraftforge.common.MinecraftForge;

public class ModConfiguration
{
    public static void init(File configDirectory)
    {
        ConfigurationHandler.init(new File(configDirectory, "config.cfg"));
        MinecraftForge.EVENT_BUS.register(new ConfigurationHandler());
    }
}