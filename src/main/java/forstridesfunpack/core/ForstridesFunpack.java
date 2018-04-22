package forstridesfunpack.core;

import java.io.File;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import forstridesfunpack.init.ModCrafting;
import forstridesfunpack.init.ModItems;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = ForstridesFunpack.MOD_ID, version = ForstridesFunpack.MOD_VERSION, name = ForstridesFunpack.MOD_NAME, dependencies = "required-after:forge@[1.0.0.0,);" + "after:biomesoplenty;")
public class ForstridesFunpack
{
    public static final String MOD_NAME = "ForstridesFunpack";
    public static final String MOD_ID = "forstridesfunpack";
    public static final String MOD_VERSION = "@MOD_VERSION@";
    
    @Instance(MOD_ID)
    public static ForstridesFunpack instance;
    
    @SidedProxy(clientSide = "forstridesfunpack.core.ClientProxy", serverSide = "forstridesfunpack.core.CommonProxy")
    public static CommonProxy proxy;
    
    public static Logger logger = LogManager.getLogger(MOD_ID);
    public static File configDirectory;
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        ModItems.init();
        ModCrafting.init();
    }
}
