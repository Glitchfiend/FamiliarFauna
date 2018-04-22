package familiarfauna.init;

import familiarfauna.api.FFItems;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;

public class ModCrafting
{
    
    public static void init()
    {
        addOreRegistration();
        addSmeltingRecipes();
    }
    
    private static void addSmeltingRecipes()
    {
        // Register smelting recipes
    	GameRegistry.addSmelting(FFItems.turkey_leg_raw, new ItemStack(FFItems.turkey_leg_cooked), 0.35F);
        GameRegistry.addSmelting(FFItems.venison_raw, new ItemStack(FFItems.venison_cooked), 0.35F);
    }
    
    
    private static void addOreRegistration()
    {
        //Registration in Ore Dictionary
        OreDictionary.registerOre("rawTurkey", FFItems.turkey_leg_raw);
        OreDictionary.registerOre("cookedTurkey", FFItems.turkey_leg_cooked);
        OreDictionary.registerOre("foodTurkeyraw", FFItems.turkey_leg_raw);
        OreDictionary.registerOre("foodTurkeycooked", FFItems.turkey_leg_cooked);
        OreDictionary.registerOre("listAllturkeyraw", FFItems.turkey_leg_raw);
        OreDictionary.registerOre("listAllturkeycooked", FFItems.turkey_leg_cooked);
        OreDictionary.registerOre("listAllmeatraw", FFItems.turkey_leg_raw);
        OreDictionary.registerOre("listAllmeatcooked", FFItems.turkey_leg_cooked);
    	
        OreDictionary.registerOre("rawVenison", FFItems.venison_raw);
        OreDictionary.registerOre("cookedVenison", FFItems.venison_cooked);
        OreDictionary.registerOre("foodVenisonraw", FFItems.venison_raw);
        OreDictionary.registerOre("foodVenisoncooked", FFItems.venison_cooked);
        OreDictionary.registerOre("listAllvenisonraw", FFItems.venison_raw);
        OreDictionary.registerOre("listAllvenisoncooked", FFItems.venison_cooked);
        OreDictionary.registerOre("listAllmeatraw", FFItems.venison_raw);
        OreDictionary.registerOre("listAllmeatcooked", FFItems.venison_cooked);
    }
}