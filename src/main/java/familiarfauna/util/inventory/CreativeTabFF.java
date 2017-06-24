package familiarfauna.util.inventory;

import familiarfauna.api.FFItems;
import familiarfauna.core.FamiliarFauna;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityList;
import net.minecraft.init.Items;
import net.minecraft.item.ItemMonsterPlacer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class CreativeTabFF extends CreativeTabs
{
    public static final CreativeTabs instance = new CreativeTabFF(CreativeTabs.getNextID(), "tabFamiliarFauna");

    private CreativeTabFF(int index, String label)
    {
        super(index, label);
    }

    @Override
    public ItemStack getTabIconItem()
    {
        return new ItemStack(FFItems.ff_icon);
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void displayAllRelevantItems(NonNullList<ItemStack> itemList)
    {
        super.displayAllRelevantItems(itemList);
        for (EntityList.EntityEggInfo eggInfo : EntityList.ENTITY_EGGS.values())
        {
            if (eggInfo.spawnedID.getResourceDomain().equals(FamiliarFauna.MOD_ID))
            {
                ItemStack itemstack = new ItemStack(Items.SPAWN_EGG, 1);
                ItemMonsterPlacer.applyEntityIdToItemStack(itemstack, eggInfo.spawnedID);
                itemList.add(itemstack);
            }
        }
    }
}
