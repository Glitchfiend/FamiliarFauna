package familiarfauna.config;

import java.io.File;

import familiarfauna.core.FamiliarFauna;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ConfigurationHandler
{
    public static Configuration config;

    public static final String MOB_SETTINGS = "Mob Settings";
    
    public static final String BUTTERFLY_SETTINGS = "Butterfly Settings";
    public static final String DEER_SETTINGS = "Deer Settings";
    public static final String SNAIL_SETTINGS = "Snail Settings";
    
    public static boolean butterflyEnable;
    public static int butterflyWeight;
    public static int butterflyMin;
    public static int butterflyMax;
    
    public static boolean deerEnable;
    public static int deerWeight;
    public static int deerMin;
    public static int deerMax;
    public static boolean deerReplaceCows;
    
    public static boolean snailEnable;
    public static int snailWeight;
    public static int snailMin;
    public static int snailMax;

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
            butterflyEnable = config.getBoolean("Enable Butterflies", BUTTERFLY_SETTINGS, true, "Enables Butterflies.  Disabling this will remove existing Butterflies from your world.");
            butterflyWeight = config.getInt("Spawn Weight", BUTTERFLY_SETTINGS, 2, 0, Integer.MAX_VALUE, "The spawn weight for Butterflies.");
            butterflyMin = config.getInt("Min. Group Size", BUTTERFLY_SETTINGS, 2, 0, Integer.MAX_VALUE, "The minimum group size for Butterflies.");
            butterflyMax = config.getInt("Max. Group Size", BUTTERFLY_SETTINGS, 4, 0, Integer.MAX_VALUE, "The maximum group size for Butterflies.");
            
            deerEnable = config.getBoolean("Enable Deer", DEER_SETTINGS, true, "Enables Deer.  Disabling this will remove existing Deer from your world.");
            deerWeight = config.getInt("Spawn Weight", DEER_SETTINGS, 9, 0, Integer.MAX_VALUE, "The spawn weight for Deer.");
            deerMin = config.getInt("Min. Group Size", DEER_SETTINGS, 2, 0, Integer.MAX_VALUE, "The minimum group size for Deer.");
            deerMax = config.getInt("Max. Group Size", DEER_SETTINGS, 4, 0, Integer.MAX_VALUE, "The maximum group size for Deer.");
            deerReplaceCows = config.getBoolean("Replace Cows with Deer", DEER_SETTINGS, true, "Removes Cow spawns in biomes that Deer spawn in.");
            
            snailEnable = config.getBoolean("Enable Snails", SNAIL_SETTINGS, true, "Enables Snails.  Disabling this will remove existing Snails from your world.");
            snailWeight = config.getInt("Spawn Weight", SNAIL_SETTINGS, 1, 0, Integer.MAX_VALUE, "The spawn weight for Snails.");
            snailMin = config.getInt("Min. Group Size", SNAIL_SETTINGS, 1, 0, Integer.MAX_VALUE, "The minimum group size for Snails.");
            snailMax = config.getInt("Max. Group Size", SNAIL_SETTINGS, 1, 0, Integer.MAX_VALUE, "The maximum group size for Snails.");
        }
        catch (Exception e)
        {
            FamiliarFauna.logger.error("Familiar Fauna has encountered a problem loading config.cfg", e);
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