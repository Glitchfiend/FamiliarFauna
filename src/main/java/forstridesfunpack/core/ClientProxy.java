package forstridesfunpack.core;

import forstridesfunpack.api.IFFBlock;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.IStateMapper;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;


public class ClientProxy extends CommonProxy
{
    @Override
    public void registerItemVariantModel(Item item, String name, int metadata) 
    {
        if (item != null) 
        { 
            ModelBakery.registerItemVariants(item, new ResourceLocation("forstridesfunpack:" + name));
            ModelLoader.setCustomModelResourceLocation(item, metadata, new ModelResourceLocation(ForstridesFunpack.MOD_ID + ":" + name, "inventory"));
        }
    }
    
    @Override
    public void registerBlockSided(Block block)
    {
        if (block instanceof IFFBlock)
        {
            IFFBlock ffBlock = (IFFBlock)block;

            //Register non-rendering properties
            IProperty[] nonRenderingProperties = ffBlock.getNonRenderingProperties();

            if (nonRenderingProperties != null)
            {
                // use a custom state mapper which will ignore the properties specified in the block as being non-rendering
                IStateMapper custom_mapper = (new StateMap.Builder()).ignore(nonRenderingProperties).build();
                ModelLoader.setCustomStateMapper(block, custom_mapper);
            }
        }
    }
}
