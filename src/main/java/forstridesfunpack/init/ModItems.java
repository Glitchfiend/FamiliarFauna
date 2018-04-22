package forstridesfunpack.init;

import static forstridesfunpack.api.FFItems.ff_icon;
import forstridesfunpack.core.ForstridesFunpack;
import forstridesfunpack.util.inventory.CreativeTabFF;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
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
        
        item.setRegistryName(new ResourceLocation(ForstridesFunpack.MOD_ID, name));
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

                    ModelBakery.registerItemVariants(item, new ResourceLocation(ForstridesFunpack.MOD_ID, subItemName));
                    ModelLoader.setCustomModelResourceLocation(item, subItem.getMetadata(), new ModelResourceLocation(ForstridesFunpack.MOD_ID + ":" + subItemName, "inventory"));
                }
            }
            else
            {
                ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(ForstridesFunpack.MOD_ID + ":" + name, "inventory"));
            }
        }
        
        return item;   
    }
}
