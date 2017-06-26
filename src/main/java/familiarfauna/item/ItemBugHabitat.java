package familiarfauna.item;

import javax.annotation.Nonnull;

import familiarfauna.api.FFItems;
import familiarfauna.entities.EntityButterfly;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.translation.I18n;
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
                if (stack.hasTagCompound() && stack.getTagCompound().hasKey("Bug"))
                {
                    return 1;
                }
                else
                {
                    return 0;
                }
            }  
        });
        
        this.maxStackSize = 1;
    }
    
    @Override
    public String getItemStackDisplayName(ItemStack stack)
    {
        String bugString = "";
        
        if (stack.hasTagCompound() && stack.getTagCompound().hasKey("Bug"))
        {
            String bugName = stack.getTagCompound().getString("Bug");
            
            bugString = " - " + I18n.translateToLocal("entity.familiarfauna." + bugName + ".name");
        }
        
        return I18n.translateToLocal(this.getUnlocalizedNameInefficiently(stack) + ".name").trim() + bugString;
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
        RayTraceResult hit = world.rayTraceBlocks(playerEyePosition, targetPosition, true, false, false);
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
        if (stack.hasTagCompound() && stack.getTagCompound().hasKey("Bug"))
        {
            String bugName = stack.getTagCompound().getString("Bug");
            int bugType = 0;
            
            if (stack.getTagCompound().hasKey("Type"))
            {
                bugType = stack.getTagCompound().getInteger("Type");
            }
            
            if (bugName == "butterfly")
            {
                EntityButterfly bug = new EntityButterfly(world);
                
                bug.setButterflyType(bugType);
                bug.setLocationAndAngles(releasePoint.x, releasePoint.y, releasePoint.z, MathHelper.wrapDegrees(world.rand.nextFloat() * 360.0F), 0.0F);
                
                world.spawnEntity(bug);
                bug.playLivingSound();
            }
            
            stack.shrink(1);
            
            if (stack.isEmpty())
            {
                player.setHeldItem(hand, new ItemStack(FFItems.bug_habitat));
            }
            else if (!player.inventory.addItemStackToInventory(new ItemStack(FFItems.bug_habitat)))
            {
                player.dropItem(new ItemStack(FFItems.bug_habitat), false);
            }
            
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

        Vec3d releasePoint = this.getAirPositionInFrontOfPlayer(world, player, 0.8D);
        return this.releaseBug(stack, world, player, hand, releasePoint) ? new ActionResult<ItemStack>(EnumActionResult.SUCCESS, stack) : new ActionResult<ItemStack>(EnumActionResult.FAIL, stack);
    }
    
    
    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        ItemStack stack = player.getHeldItem(hand);
        if (world.isRemote)
        {
            return EnumActionResult.FAIL;
        }

        double distX = hitX - player.posX;
        double distY = hitY - (player.posY + (double)player.getEyeHeight());
        double distZ = hitZ - player.posZ;                
        double a = 0.9D;
        Vec3d releasePoint = new Vec3d(player.posX + a * distX, player.posY + (double)player.getEyeHeight() + a * distY, player.posZ + a * distZ);
        return this.releaseBug(stack, world, player, hand, releasePoint) ? EnumActionResult.SUCCESS : EnumActionResult.FAIL;
    }
}
