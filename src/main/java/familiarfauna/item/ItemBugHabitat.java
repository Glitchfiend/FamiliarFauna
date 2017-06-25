package familiarfauna.item;

import javax.annotation.Nonnull;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemBugHabitat extends Item
{
    public ItemBugHabitat()
    {
        this.addPropertyOverride(new ResourceLocation("occupied"), new IItemPropertyGetter()
        {
            @Override
            @SideOnly(Side.CLIENT)
            public float apply(@Nonnull ItemStack stack, World world, EntityLivingBase entity)
            {
                return 0;
            }  
        });
        
        this.maxStackSize = 1;
    }
}
