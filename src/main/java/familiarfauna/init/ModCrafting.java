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
        GameRegistry.addSmelting(FFItems.venison_raw, new ItemStack(FFItems.venison_cooked), 0.35F);
    }
    
    
    private static void addOreRegistration()
    {
        //Registration in Ore Dictionary
        OreDictionary.registerOre("rawVenison", FFItems.venison_raw);
        OreDictionary.registerOre("cookedVenison", FFItems.venison_cooked);
    }
}