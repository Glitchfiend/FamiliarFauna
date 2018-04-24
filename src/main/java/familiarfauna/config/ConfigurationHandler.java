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
    public static final String DRAGONFLY_SETTINGS = "Dragonfly Settings";
    public static final String PIXIE_SETTINGS = "Pixie Settings";
    public static final String SNAIL_SETTINGS = "Snail Settings";
    public static final String TURKEY_SETTINGS = "Turkey Settings";
    
    public static boolean butterflyEnable;
    public static int butterflyWeight;
    public static int butterflyMin;
    public static int butterflyMax;
    
    public static boolean deerEnable;
    public static int deerWeight;
    public static int deerMin;
    public static int deerMax;
    public static boolean deerReplaceCows;
    
    public static boolean dragonflyEnable;
    public static int dragonflyWeight;
    public static int dragonflyMin;
    public static int dragonflyMax;
    
    public static boolean pixieEnable;
    public static int pixieWeight;
    public static int pixieMin;
    public static int pixieMax;
    
    public static boolean snailEnable;
    public static int snailWeight;
    public static int snailMin;
    public static int snailMax;
    
    public static boolean turkeyEnable;
    public static int turkeyWeight;
    public static int turkeyMin;
    public static int turkeyMax;
    public static boolean turkeyReplaceChickens;

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
            butterflyWeight = config.getInt("Spawn Weight", BUTTERFLY_SETTINGS, 1, 0, Integer.MAX_VALUE, "The spawn weight for Butterflies.");
            butterflyMin = config.getInt("Min. Group Size", BUTTERFLY_SETTINGS, 2, 0, Integer.MAX_VALUE, "The minimum group size for Butterflies.");
            butterflyMax = config.getInt("Max. Group Size", BUTTERFLY_SETTINGS, 4, 0, Integer.MAX_VALUE, "The maximum group size for Butterflies.");
            
            deerEnable = config.getBoolean("Enable Deer", DEER_SETTINGS, true, "Enables Deer.  Disabling this will remove existing Deer from your world.");
            deerWeight = config.getInt("Spawn Weight", DEER_SETTINGS, 8, 0, Integer.MAX_VALUE, "The spawn weight for Deer.");
            deerMin = config.getInt("Min. Group Size", DEER_SETTINGS, 2, 0, Integer.MAX_VALUE, "The minimum group size for Deer.");
            deerMax = config.getInt("Max. Group Size", DEER_SETTINGS, 4, 0, Integer.MAX_VALUE, "The maximum group size for Deer.");
            deerReplaceCows = config.getBoolean("Replace Cows with Deer", DEER_SETTINGS, true, "Removes Cow spawns in biomes that Deer spawn in.");
            
            dragonflyEnable = config.getBoolean("Enable Dragonflies", DRAGONFLY_SETTINGS, true, "Enables Dragonflies.  Disabling this will remove existing Dragonflies from your world.");
            dragonflyWeight = config.getInt("Spawn Weight", DRAGONFLY_SETTINGS, 1, 0, Integer.MAX_VALUE, "The spawn weight for Dragonflies.");
            dragonflyMin = config.getInt("Min. Group Size", DRAGONFLY_SETTINGS, 1, 0, Integer.MAX_VALUE, "The minimum group size for Dragonflies.");
            dragonflyMax = config.getInt("Max. Group Size", DRAGONFLY_SETTINGS, 2, 0, Integer.MAX_VALUE, "The maximum group size for Dragonflies.");
            
            pixieEnable = config.getBoolean("Enable Pixies", PIXIE_SETTINGS, true, "Enables Pixies.  Disabling this will remove existing Pixies from your world.");
            pixieWeight = config.getInt("Spawn Weight", PIXIE_SETTINGS, 1, 0, Integer.MAX_VALUE, "The spawn weight for Pixies.");
            pixieMin = config.getInt("Min. Group Size", PIXIE_SETTINGS, 1, 0, Integer.MAX_VALUE, "The minimum group size for Pixies.");
            pixieMax = config.getInt("Max. Group Size", PIXIE_SETTINGS, 1, 0, Integer.MAX_VALUE, "The maximum group size for Pixies.");
            
            snailEnable = config.getBoolean("Enable Snails", SNAIL_SETTINGS, true, "Enables Snails.  Disabling this will remove existing Snails from your world.");
            snailWeight = config.getInt("Spawn Weight", SNAIL_SETTINGS, 1, 0, Integer.MAX_VALUE, "The spawn weight for Snails.");
            snailMin = config.getInt("Min. Group Size", SNAIL_SETTINGS, 1, 0, Integer.MAX_VALUE, "The minimum group size for Snails.");
            snailMax = config.getInt("Max. Group Size", SNAIL_SETTINGS, 1, 0, Integer.MAX_VALUE, "The maximum group size for Snails.");
            
            turkeyEnable = config.getBoolean("Enable Turkey", TURKEY_SETTINGS, true, "Enables Turkey.  Disabling this will remove existing Turkey from your world.");
            turkeyWeight = config.getInt("Spawn Weight", TURKEY_SETTINGS, 10, 0, Integer.MAX_VALUE, "The spawn weight for Turkey.");
            turkeyMin = config.getInt("Min. Group Size", TURKEY_SETTINGS, 3, 0, Integer.MAX_VALUE, "The minimum group size for Turkey.");
            turkeyMax = config.getInt("Max. Group Size", TURKEY_SETTINGS, 4, 0, Integer.MAX_VALUE, "The maximum group size for Turkey.");
            turkeyReplaceChickens = config.getBoolean("Replace Chickens with Turkey", TURKEY_SETTINGS, true, "Removes Chicken spawns in biomes that Turkey spawn in.");
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