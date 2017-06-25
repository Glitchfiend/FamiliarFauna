package familiarfauna.core;

import java.io.File;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import familiarfauna.init.ModCompat;
import familiarfauna.init.ModConfiguration;
import familiarfauna.init.ModCrafting;
import familiarfauna.init.ModEntities;
import familiarfauna.init.ModItems;
import familiarfauna.init.ModLootTable;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = FamiliarFauna.MOD_ID, version = FamiliarFauna.MOD_VERSION, name = FamiliarFauna.MOD_NAME, dependencies = "required-after:forge@[1.0.0.0,)", guiFactory = FamiliarFauna.GUI_FACTORY)
public class FamiliarFauna
{
    public static final String MOD_NAME = "FamiliarFauna";
    public static final String MOD_ID = "familiarfauna";
    public static final String MOD_VERSION = "@MOD_VERSION@";
    public static final String GUI_FACTORY = "familiarfauna.client.gui.GuiFFFactory";
    
    @Instance(MOD_ID)
    public static FamiliarFauna instance;
    
    @SidedProxy(clientSide = "familiarfauna.core.ClientProxy", serverSide = "familiarfauna.core.CommonProxy")
    public static CommonProxy proxy;
    
    public static Logger logger = LogManager.getLogger(MOD_ID);
    public static File configDirectory;
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
    	configDirectory = new File(event.getModConfigurationDirectory(), "familiarfauna");
    	ModConfiguration.init(configDirectory);
    	
    	ModEntities.init();
        ModItems.init();
        
        ModLootTable.init();
        ModCrafting.init();
        
        proxy.registerRenderers();
    }
    
    @EventHandler
    public void init(FMLInitializationEvent event) 
    {
        ModCompat.init();
    }
}
