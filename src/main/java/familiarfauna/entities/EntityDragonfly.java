package familiarfauna.entities;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import familiarfauna.api.FFItems;
import familiarfauna.api.FFSounds;
import familiarfauna.config.ConfigurationHandler;
import familiarfauna.init.ModLootTable;
import familiarfauna.item.ItemBugHabitat;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityMoveHelper;
import net.minecraft.entity.passive.EntityAmbientCreature;
import net.minecraft.entity.passive.EntityFlying;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;

public class EntityDragonfly extends EntityAmbientCreature implements EntityFlying
{
    private static final DataParameter<Byte> TYPE = EntityDataManager.<Byte>createKey(EntityDragonfly.class, DataSerializers.BYTE);

    public EntityDragonfly(World worldIn) {
        super(worldIn);
        this.setSize(0.7F, 0.7F);
        
        this.moveHelper = new EntityDragonfly.DragonflyMoveHelper();
        this.tasks.addTask(3, new EntityDragonfly.AIDragonflyRandomFly());
    }
    
    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(0.5D);
    }
    
    @Override
	protected void entityInit()
    {
        super.entityInit();
        this.dataManager.register(TYPE, Byte.valueOf((byte)0));
    }

    @Override
    protected SoundEvent getAmbientSound()
    {
        return FFSounds.dragonfly_ambient;
    }
    
    @Override
    public int getTalkInterval()
    {
        return 80;
    }
    
    @Override
    public void playLivingSound()
    {
        SoundEvent soundevent = this.getAmbientSound();

        if (soundevent != null)
        {
            this.playSound(soundevent, 0.5F, 1.0F);
        }
    }
    
    @Nullable
    @Override
    protected ResourceLocation getLootTable()
    {
        return ModLootTable.DRAGONFLY_LOOT;
    }
    
    @Override
    public boolean processInteract(EntityPlayer player, EnumHand hand)
    {
        ItemStack itemstack = player.getHeldItem(hand);

        if (itemstack.getItem() == FFItems.bug_net && !player.capabilities.isCreativeMode && !this.isChild())
        {
            ItemStack emptyHabitat = findEmptyHabitatStack(player);
            if (emptyHabitat != ItemStack.EMPTY)
            {
                //player.playSound(SoundEvents.ENTITY_COW_MILK, 1.0F, 1.0F);
                player.swingArm(hand);
                emptyHabitat.shrink(1);
                itemstack.damageItem(1, player);
                this.setDead();
                
                ItemStack habitat = new ItemStack(FFItems.bug_habitat);
                habitat.setTagCompound(new NBTTagCompound());
                habitat.getTagCompound().setString("Bug", "dragonfly");
                
                if (this.getCustomNameTag() != "")
                {
                    habitat.getTagCompound().setString("Name", this.getCustomNameTag());
                }
                
                habitat.getTagCompound().setInteger("Type", this.getDragonflyType());

                if (!player.inventory.addItemStackToInventory(habitat))
                {
                    player.dropItem(habitat, false);
                }
    
                return true;
            }
            else
            {
                return super.processInteract(player, hand);
            }
        }
        else
        {
            return super.processInteract(player, hand);
        }
    }
    
    @Nonnull
    public static ItemStack findEmptyHabitatStack(EntityPlayer player)
    {
        //Search every item in the player's main inventory for a bug habitat
        for (ItemStack stack : player.inventory.mainInventory)
        {
            if (isHabitatEmpty(stack))
            {
                return stack;
            }
        }
        
        return ItemStack.EMPTY;
    }
    
    public static boolean isHabitatEmpty(@Nonnull ItemStack stack)
    {
        if (!stack.isEmpty() && stack.getItem() instanceof ItemBugHabitat)
        {
            if (stack.hasTagCompound() && stack.getTagCompound().hasKey("Bug"))
            {
                return false;
            }
            else
            {
                return true;
            }
        }
        
        return false;
    }
    
    public void writeEntityToNBT(NBTTagCompound tagCompound)
    {
        super.writeEntityToNBT(tagCompound);
        tagCompound.setInteger("DragonflyType", this.getDragonflyType());
    }

    public void readEntityFromNBT(NBTTagCompound tagCompund)
    {
        super.readEntityFromNBT(tagCompund);
        this.setDragonflyType(tagCompund.getInteger("DragonflyType"));
    }
    
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData livingdata)
    {
        livingdata = super.onInitialSpawn(difficulty, livingdata);
        int i = this.rand.nextInt(4);
        boolean flag = false;

        if (livingdata instanceof EntityDragonfly.DragonflyTypeData)
        {
            i = ((EntityDragonfly.DragonflyTypeData)livingdata).typeData;
            flag = true;
        }
        else
        {
            livingdata = new EntityDragonfly.DragonflyTypeData(i);
        }

        this.setDragonflyType(i);

        return livingdata;
    }
    
    public int getDragonflyType()
    {
        return (int) this.dataManager.get(TYPE);
    }
    
    public void setDragonflyType(int dragonflyTypeId)
    {
        this.dataManager.set(TYPE, Byte.valueOf((byte)dragonflyTypeId));
    }
    
    @Override
    public boolean canBePushed()
    {
        return false;
    }

    @Override
    protected void collideWithEntity(Entity entityIn)
    {
    }

    @Override
    protected void collideWithNearbyEntities()
    {
    }
    
    @Override
    protected boolean canTriggerWalking()
    {
        return false;
    }
    
    @Override
    public void fall(float distance, float damageMultiplier)
    {
    }

    @Override
    protected void updateFallState(double y, boolean onGroundIn, IBlockState state, BlockPos pos)
    {
    }

    @Override
    public boolean doesEntityNotTriggerPressurePlate()
    {
        return true;
    }
    
    @Override
    public void travel(float strafe, float vertical, float forward)
    {
        if (this.isInWater())
        {
            this.moveRelative(strafe, vertical, forward, 0.02F);
            this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
            this.motionX *= 0.800000011920929D;
            this.motionY *= 0.800000011920929D;
            this.motionZ *= 0.800000011920929D;
        }
        else if (this.isInLava())
        {
            this.moveRelative(strafe, vertical, forward, 0.02F);
            this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
            this.motionX *= 0.5D;
            this.motionY *= 0.5D;
            this.motionZ *= 0.5D;
        }
        else
        {
            float f = 0.91F;

            if (this.onGround)
            {
                BlockPos underPos = new BlockPos(MathHelper.floor(this.posX), MathHelper.floor(this.getEntityBoundingBox().minY) - 1, MathHelper.floor(this.posZ));
                IBlockState underState = this.world.getBlockState(underPos);
                f = underState.getBlock().getSlipperiness(underState, this.world, underPos, this) * 0.91F;
            }

            float f1 = 0.16277136F / (f * f * f);
            this.moveRelative(strafe, vertical, forward, this.onGround ? 0.1F * f1 : 0.02F);
            f = 0.91F;

            if (this.onGround)
            {
                BlockPos underPos = new BlockPos(MathHelper.floor(this.posX), MathHelper.floor(this.getEntityBoundingBox().minY) - 1, MathHelper.floor(this.posZ));
                IBlockState underState = this.world.getBlockState(underPos);
                f = underState.getBlock().getSlipperiness(underState, this.world, underPos, this) * 0.91F;
            }

            this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
            this.motionX *= (double)f;
            this.motionY *= (double)f;
            this.motionZ *= (double)f;
        }

        this.prevLimbSwingAmount = this.limbSwingAmount;
        double d1 = this.posX - this.prevPosX;
        double d0 = this.posZ - this.prevPosZ;
        float f2 = MathHelper.sqrt(d1 * d1 + d0 * d0) * 4.0F;

        if (f2 > 1.0F)
        {
            f2 = 1.0F;
        }

        this.limbSwingAmount += (f2 - this.limbSwingAmount) * 0.4F;
        this.limbSwing += this.limbSwingAmount;
    }

    @Override
    public boolean isOnLadder()
    {
        return false;
    }
    
    @Override
    public void onUpdate()
    {
        super.onUpdate();

        if (!this.world.isRemote && (!(ConfigurationHandler.dragonflyEnable)))
        {
            this.setDead();
        }
    }
    
    @Override
    public boolean getCanSpawnHere()
    {
    	BlockPos blockpos = new BlockPos(this.posX, this.getEntityBoundingBox().minY, this.posZ);

        if (blockpos.getY() <= this.world.getSeaLevel())
        {
            return false;
        }
        else
        {
        	if (blockpos.getY() >= 90)
	        {
	            return false;
	        }
        	else
        	{
	        	int light = this.world.getLightFromNeighbors(blockpos);
	        	
	        	return light > 8 && super.getCanSpawnHere();
        	}
        }
    }
    
    // Helper class representing a point in space that the dragonfly is targeting for some reason
    class DragonflyMoveTargetPos
    {
        private EntityDragonfly dragonfly = EntityDragonfly.this;

        public double posX;
        public double posY;
        public double posZ;
        public double distX;
        public double distY;
        public double distZ;
        public double dist;
        public double aimX;
        public double aimY;
        public double aimZ;
        
        public DragonflyMoveTargetPos()
        {
            this(0, 0, 0);
        }
        
        public DragonflyMoveTargetPos(double posX, double posY, double posZ)
        {
            this.setTarget(posX, posY, posZ);
        }
        
        public void setTarget(double posX, double posY, double posZ)
        {
            this.posX = posX;
            this.posY = posY;
            this.posZ = posZ;
            this.refresh();
        }
        
        public void refresh()
        {
            this.distX = this.posX - this.dragonfly.posX;
            this.distY = this.posY - this.dragonfly.posY;
            this.distZ = this.posZ - this.dragonfly.posZ;
            
            this.dist = (double)MathHelper.sqrt(this.distX * this.distX + this.distY * this.distY + this.distZ * this.distZ);
            
            // (aimX,aimY,aimZ) is a unit vector in the direction we want to go
            if (this.dist == 0.0D)
            {
                this.aimX = 0.0D;
                this.aimY = 0.0D;
                this.aimZ = 0.0D;
            }
            else
            {
                this.aimX = this.distX / this.dist;
                this.aimY = this.distY / this.dist;
                this.aimZ = this.distZ / this.dist;                
            }
         }
        
        public boolean isBoxBlocked(AxisAlignedBB box)
        {
            return !this.dragonfly.world.getCollisionBoxes(this.dragonfly, box).isEmpty();
        }
        
        // check nothing will collide with the dragonfly in the direction of aim, for howFar units (or until the destination - whichever is closer)
        public boolean isPathClear(double howFar)
        {
            howFar = Math.min(howFar, this.dist);
            AxisAlignedBB box = this.dragonfly.getEntityBoundingBox();
            for (double i = 0.5D; i < howFar; ++i)
            {
                // check there's nothing in the way
                if (this.isBoxBlocked(box.offset(this.aimX * i, this.aimY * i, this.aimZ * i)))
                {
                    return false;
                }
            }
            if (this.isBoxBlocked(box.offset(this.aimX * howFar, this.aimY * howFar, this.aimZ * howFar)))
            {
                return false;
            }
            return true;
        }
        
    }
            
    class DragonflyMoveHelper extends EntityMoveHelper
    {
        // EntityMoveHelper has the boolean 'update' which is set to true when the target is changed, and set to false when a bearing is set
        // So it means 'the target has changed but we're not yet heading for it'
        // We'll re-use it here with a slightly different interpretation
        // Here it will mean 'has a target and not yet arrived'
        
        private EntityDragonfly dragonfly = EntityDragonfly.this;
        private int courseChangeCooldown = 0;
        private double closeEnough = 0.3D;
        private DragonflyMoveTargetPos targetPos = new DragonflyMoveTargetPos();

        public DragonflyMoveHelper()
        {
            super(EntityDragonfly.this);
        }
                        
        @Override
        public void setMoveTo(double x, double y, double z, double speedIn)
        {
            super.setMoveTo(x,y,z,speedIn);
            this.targetPos.setTarget(x, y, z);
        }

        @Override
        public void onUpdateMoveHelper()
        {
            // if we have arrived at the previous target, or we have no target to aim for, do nothing
            if (this.action != Action.MOVE_TO)
            {
            	return;
            }
            
            // limit the rate at which we change course
            if (this.courseChangeCooldown-- > 0)
            {
                return;
            }
            this.courseChangeCooldown += this.dragonfly.getRNG().nextInt(2) + 2;
            
            // update the target position
            this.targetPos.refresh();
            
            // accelerate the dragonfly towards the target
            double acceleration = 0.1D;
            this.dragonfly.motionX += this.targetPos.aimX * acceleration;
            this.dragonfly.motionY += this.targetPos.aimY * acceleration;
            this.dragonfly.motionZ += this.targetPos.aimZ * acceleration;
           
            // rotate to point at target
            this.dragonfly.renderYawOffset = this.dragonfly.rotationYaw = -((float)Math.atan2(this.targetPos.distX, this.targetPos.distZ)) * 180.0F / (float)Math.PI;            

            // abandon this movement if we have reached the target or there is no longer a clear path to the target
            if (!this.targetPos.isPathClear(1.0D))
            {
                this.action = Action.WAIT;
                this.courseChangeCooldown += this.dragonfly.getRNG().nextInt(30) + 15;
            }
            else if (this.targetPos.dist < this.closeEnough)
            {
                this.action = Action.WAIT;
                this.courseChangeCooldown += this.dragonfly.getRNG().nextInt(30) + 15;
            }
        }        

    }
    
    // AI class for implementing the random flying behaviour
    class AIDragonflyRandomFly extends EntityAIBase
    {
        private EntityDragonfly dragonfly = EntityDragonfly.this;
        private DragonflyMoveTargetPos targetPos = new DragonflyMoveTargetPos();
        
        public AIDragonflyRandomFly()
        {
            this.setMutexBits(1);
        }

        // should we choose a new random destination for the dragonfly to fly to?
        // yes, if the dragonfly doesn't already have a destination
        @Override
        public boolean shouldExecute()
        {
            return !this.dragonfly.getMoveHelper().isUpdating();
        }
        
        @Override
        public boolean shouldContinueExecuting() {return false;}
        
        // choose a a new random destination for the dragonfly to fly to
        @Override
        public void startExecuting()
        {            
            Random rand = this.dragonfly.getRNG();
            // pick a random nearby point and see if we can fly to it
            if (this.tryGoingRandomDirection(rand, 10.0D)) {return;}
            // pick a random closer point to fly to instead
            if (this.tryGoingRandomDirection(rand, 10.0D)) {return;}
            // try going straight along axes (try all 6 directions in random order)
            List<EnumFacing> directions = Arrays.asList(EnumFacing.values());
            Collections.shuffle(directions);
            for (EnumFacing facing : directions)
            {
                if (this.tryGoingAlongAxis(rand, facing, 10.0D)) {return;}
            }
        }
        
        
        public boolean tryGoingRandomDirection(Random rand, double maxDistance)
        {
            double dirX = ((rand.nextDouble() * 2.0D - 1.0D) * maxDistance);
            double dirY = ((rand.nextDouble() * 2.0D - 1.0D) * (maxDistance * 0.25D));
            double dirZ = ((rand.nextDouble() * 2.0D - 1.0D) * maxDistance);
            return this.tryGoing(dirX, dirY, dirZ);
        }
        
        public boolean tryGoingAlongAxis(Random rand, EnumFacing facing, double maxDistance)
        {
            double dirX = 0.0D;
            double dirY = 0.0D;
            double dirZ = 0.0D;
            switch (facing.getAxis())
            {
                case X:
                    dirX = rand.nextDouble() * facing.getAxisDirection().getOffset() * maxDistance;
                    break;
                case Y:
                    dirY = rand.nextDouble() * facing.getAxisDirection().getOffset() * (maxDistance * 0.25D);
                    break;
                case Z: default:
                    dirZ = rand.nextDouble() * facing.getAxisDirection().getOffset() * maxDistance;
                    break;
            }
            return this.tryGoing(dirX, dirY, dirZ);
        }
        
        public boolean tryGoing(double dirX, double dirY, double dirZ)
        {
            //System.out.println("("+dirX+","+dirY+","+dirZ+")");
            this.targetPos.setTarget(this.dragonfly.posX + dirX, this.dragonfly.posY + dirY, this.dragonfly.posZ + dirZ);
            //System.out.println("Testing random move target distance:"+this.targetPos.dist+" direction:("+this.targetPos.aimX+","+this.targetPos.aimY+","+this.targetPos.aimZ+")");
            boolean result = this.targetPos.isPathClear(5.0F);
            if (result)
            {
                this.dragonfly.getMoveHelper().setMoveTo(this.targetPos.posX, this.targetPos.posY, this.targetPos.posZ, 1.0D);
            }
            return result;
        }
    }
    
    public static class DragonflyTypeData implements IEntityLivingData
    {
        public int typeData;

        public DragonflyTypeData(int type)
        {
            this.typeData = type;
        }
    }
    
}
