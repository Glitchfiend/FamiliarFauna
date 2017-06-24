package familiarfauna.init;

import static familiarfauna.api.FFItems.bug_net;
import static familiarfauna.api.FFItems.ff_icon;
import static familiarfauna.api.FFItems.venison_cooked;
import static familiarfauna.api.FFItems.venison_raw;

import familiarfauna.core.FamiliarFauna;
import familiarfauna.util.inventory.CreativeTabFF;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;

public class ModItems
{
    public static void init()
    {
    	registerItems();
    }
    
    public static void registerItems()
    {
    	//FF Creative Tab Icon
    	ff_icon = registerItem(new Item(), "ff_icon");
        ff_icon.setCreativeTab(null);
        
        //Bug Net
        bug_net = registerItem(new Item(), "bug_net");
        
        //Venison
        venison_raw = registerItem(new ItemFood(3, 0.3F, true), "venison_raw");
        venison_cooked = registerItem(new ItemFood(8, 0.8F, true), "venison_cooked");
    }
    
    public static Item registerItem(Item item, String name)
    {
        return registerItem(item, name, CreativeTabFF.instance);
    }
    
    public static Item registerItem(Item item, String name, CreativeTabs tab)
    {    
        item.setUnlocalizedName(name);
        if (tab != null)
        {
            item.setCreativeTab(CreativeTabFF.instance);
        }
        
        item.setRegistryName(new ResourceLocation(FamiliarFauna.MOD_ID, name));
        ForgeRegistries.ITEMS.register(item);
        //FFCommand.itemCount++;
        
        // register sub types if there are any
        if (FMLCommonHandler.instance().getSide() == Side.CLIENT)
        {
            if (item.getHasSubtypes())
            {
                NonNullList<ItemStack> subItems = NonNullList.create();
                item.getSubItems(CreativeTabFF.instance, subItems);
                for (ItemStack subItem : subItems)
                {
                    String subItemName = item.getUnlocalizedName(subItem);
                    subItemName =  subItemName.substring(subItemName.indexOf(".") + 1); // remove 'item.' from the front

                    ModelBakery.registerItemVariants(item, new ResourceLocation(FamiliarFauna.MOD_ID, subItemName));
                    ModelLoader.setCustomModelResourceLocation(item, subItem.getMetadata(), new ModelResourceLocation(FamiliarFauna.MOD_ID + ":" + subItemName, "inventory"));
                }
            }
            else
            {
                ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(FamiliarFauna.MOD_ID + ":" + name, "inventory"));
            }
        }
        
        return item;   
    }
}
