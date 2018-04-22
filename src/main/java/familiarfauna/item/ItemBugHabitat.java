package familiarfauna.item;

import javax.annotation.Nonnull;

import familiarfauna.entities.EntityButterfly;
import familiarfauna.entities.EntityDragonfly;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemBugHabitat extends Item
{
    public ItemBugHabitat()
    {
        this.addPropertyOverride(new ResourceLocation("variant"), new IItemPropertyGetter()
        {
            @Override
            @SideOnly(Side.CLIENT)
            public float apply(@Nonnull ItemStack stack, World world, EntityLivingBase entity)
            {
                if (stack.hasTagCompound() && stack.getTagCompound().hasKey("Bug"))
                {
                    if (stack.getTagCompound().getString("Bug") == "butterfly")
                    {
                        if (stack.getTagCompound().hasKey("Type"))
                        {
                            return (stack.getTagCompound().getInteger("Type") + 1);
                        }

                        return 1;
                    }
                    if (stack.getTagCompound().getString("Bug") == "dragonfly")
                    {
                        if (stack.getTagCompound().hasKey("Type"))
                        {
                            return (stack.getTagCompound().getInteger("Type") + 1);
                        }

                        return 1;
                    }
                }
                
                return 0;
            }  
        });
        
        this.maxStackSize = 1;
    }
    
    @Override
    public String getUnlocalizedName(ItemStack stack)
    {
        if (stack.hasTagCompound() && stack.getTagCompound().hasKey("Bug"))
        {
            if (!stack.getTagCompound().hasKey("Name"))
            {
                return super.getUnlocalizedName() + "_" + stack.getTagCompound().getString("Bug");
            }
        }
        
        return super.getUnlocalizedName();
    }
    
    @Override
    public String getItemStackDisplayName(ItemStack stack)
    {
        
        if (stack.hasTagCompound() && stack.getTagCompound().hasKey("Name"))
        {
            String name = stack.getTagCompound().getString("Name");
            return I18n.translateToLocal(this.getUnlocalizedNameInefficiently(stack) + ".name").trim() + " - " + TextFormatting.ITALIC + name + TextFormatting.RESET;
        }
        
        return I18n.translateToLocal(this.getUnlocalizedNameInefficiently(stack) + ".name").trim();
    }
    
    protected Vec3d getAirPositionInFrontOfPlayer(World world, EntityPlayer player, double targetDistance)
    {
        float cosYaw = MathHelper.cos(-player.rotationYaw * 0.017453292F - (float)Math.PI);
        float sinYaw = MathHelper.sin(-player.rotationYaw * 0.017453292F - (float)Math.PI);
        float cosPitch = -MathHelper.cos(-player.rotationPitch * 0.017453292F);
        float facingX = sinYaw * cosPitch;
        float facingY = MathHelper.sin(-player.rotationPitch * 0.017453292F);
        float facingZ = cosYaw * cosPitch;

        Vec3d playerEyePosition = new Vec3d(player.posX, player.posY + (double)player.getEyeHeight(), player.posZ);
        Vec3d targetPosition = playerEyePosition.addVector((double)facingX * targetDistance, (double)facingY * targetDistance, (double)facingZ * targetDistance);

        // see if there's anything in the way
        RayTraceResult hit = this.rayTrace(world, player, true);
        if (hit == null)
        {
            return targetPosition;
        }
        else
        {
            // there's something in the way - return the point just before the collision point
            double distance = playerEyePosition.distanceTo(hit.hitVec) * 0.9;
            return playerEyePosition.addVector((double)facingX * distance, (double)facingY * distance, (double)facingZ * distance);            
        }
    }
    
    public boolean releaseBug(ItemStack stack, World world, EntityPlayer player, EnumHand hand, Vec3d releasePoint)
    {
        if (stack.hasTagCompound() && stack.getTagCompound().hasKey("Bug") && !world.isRemote)
        {
            String bugEntity = stack.getTagCompound().getString("Bug");
            String bugName = "";
            int bugType = 0;
            
            //Custom entity name
            if (stack.getTagCompound().hasKey("Name"))
            {
                bugName = stack.getTagCompound().getString("Name");
            }
            
            //Bug type (Butterfly variants, etc.)
            if (stack.getTagCompound().hasKey("Type"))
            {
                bugType = stack.getTagCompound().getInteger("Type");
            }
            
            //Butterfly
            if (bugEntity == "butterfly")
            {
                EntityButterfly butterfly = new EntityButterfly(world);
                butterfly.setButterflyType(bugType);
                butterfly.setLocationAndAngles(releasePoint.x, releasePoint.y, releasePoint.z, MathHelper.wrapDegrees(world.rand.nextFloat() * 360.0F), 0.0F);
                if (bugName != "")
                {
                    butterfly.setCustomNameTag(bugName);
                }
                
                world.spawnEntity(butterfly);
            }
            
            //Dragonfly
            if (bugEntity == "dragonfly")
            {
                EntityDragonfly dragonfly = new EntityDragonfly(world);
                dragonfly.setDragonflyType(bugType);
                dragonfly.setLocationAndAngles(releasePoint.x, releasePoint.y, releasePoint.z, MathHelper.wrapDegrees(world.rand.nextFloat() * 360.0F), 0.0F);
                if (bugName != "")
                {
                	dragonfly.setCustomNameTag(bugName);
                }
                
                world.spawnEntity(dragonfly);
            }

            stack.getTagCompound().removeTag("Bug");
            stack.getTagCompound().removeTag("Type");
            stack.getTagCompound().removeTag("Name");
            
            return true;
        }
        
        return false;
    }
    
    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
    {
        ItemStack stack = player.getHeldItem(hand);
        if (world.isRemote)
        {
            return new ActionResult<ItemStack>(EnumActionResult.FAIL, stack);
        }

        Vec3d releasePoint = this.getAirPositionInFrontOfPlayer(world, player, 2D);
        return this.releaseBug(stack, world, player, hand, releasePoint) ? new ActionResult<ItemStack>(EnumActionResult.SUCCESS, stack) : new ActionResult<ItemStack>(EnumActionResult.FAIL, stack);
    }
}
