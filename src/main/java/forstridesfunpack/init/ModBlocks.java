package forstridesfunpack.init;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;

import forstridesfunpack.api.IFFBlock;
import forstridesfunpack.core.ForstridesFunpack;
import forstridesfunpack.util.BlockStateUtils;
import forstridesfunpack.util.inventory.CreativeTabFF;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class ModBlocks
{
    public static void init()
    {
    }

    public static void registerBlockItemModel(Block block, String stateName, int stateMeta)
    {
        Item item = Item.getItemFromBlock(block);
        ForstridesFunpack.proxy.registerItemVariantModel(item, stateName, stateMeta);
    }
    
    public static Block registerBlock(Block block, String blockName)
    {
        return registerBlock(block, blockName, CreativeTabFF.instance);
    }

    public static Block registerBlock(Block block, String blockName,CreativeTabs tab)
    {
        return registerBlock(block, blockName, tab, true);
    }
    
    public static Block registerBlock(Block block, String blockName, CreativeTabs tab, boolean registerItemModels)
    {
        Preconditions.checkNotNull(block, "Cannot register a null block");
        block.setUnlocalizedName(blockName);        
        block.setCreativeTab(tab);
        
        if (block instanceof IFFBlock)
        {
            // if this block supports the IFFBlock interface then we can determine the item block class, and sub-blocks automatically
            IFFBlock ffBlock = (IFFBlock)block;

            registerBlockWithItem(block, blockName, ffBlock.getItemClass());
            ForstridesFunpack.proxy.registerBlockSided(block);
            
            // check for missing default states
            IBlockState defaultState = block.getDefaultState();
            if (defaultState == null)
            {
                defaultState = block.getBlockState().getBaseState();
                ForstridesFunpack.logger.error("Missing default state for " + block.getUnlocalizedName());
            }
            
            // Some blocks such as doors and slabs register their items after the blocks (getItemClass returns null)
            if (registerItemModels)
            {
                // get the preset blocks variants
                ImmutableSet<IBlockState> presets = BlockStateUtils.getBlockPresets(block);
                if (presets.isEmpty())
                {
                    // block has no sub-blocks to register
                    registerBlockItemModel(block, blockName, 0);
                } else
                {
                    // register all the sub-blocks
                    for (IBlockState state : presets)
                    {
                        String stateName = ffBlock.getStateName(state);
                        int stateMeta = block.getMetaFromState(state);
                        registerBlockItemModel(block, stateName, stateMeta);
                    }
                }
            }
        }
        else
        {
            // for vanilla blocks, just register a single variant with meta=0 and assume ItemBlock for the item class
            registerBlockWithItem(block, blockName, ItemBlock.class);
            registerBlockItemModel(block, blockName, 0);
        }

        return block;
    }
    
    private static void registerBlockWithItem(Block block, String blockName, Class<? extends ItemBlock> clazz)
    {
        try
        {
            Item itemBlock = clazz != null ? clazz.getConstructor(Block.class).newInstance(block) : null;
            ResourceLocation location = new ResourceLocation(ForstridesFunpack.MOD_ID, blockName);

            block.setRegistryName(new ResourceLocation(ForstridesFunpack.MOD_ID, blockName));

            ForgeRegistries.BLOCKS.register(block);
            if (itemBlock != null)
            {
                itemBlock.setRegistryName(new ResourceLocation(ForstridesFunpack.MOD_ID, blockName));
                ForgeRegistries.ITEMS.register(itemBlock);
            }
        }
        catch (Exception e)
        {
            throw new RuntimeException("An error occurred associating an item block during registration of " + blockName, e);
        }
    }
    
}
