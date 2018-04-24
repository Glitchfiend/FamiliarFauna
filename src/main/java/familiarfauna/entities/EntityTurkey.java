/*******************************************************************************
 * Copyright 2015-2016, the Biomes O' Plenty Team
 * 
 * This work is licensed under a Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International Public License.
 * 
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 ******************************************************************************/

package familiarfauna.entities;

import javax.annotation.Nullable;

import familiarfauna.api.FFSounds;
import familiarfauna.config.ConfigurationHandler;
import familiarfauna.init.ModLootTable;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAIFollowParent;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.IAnimals;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

public class EntityTurkey extends EntityAnimal implements IAnimals
{
    public float wingRotation;
    public float destPos;
    public float oFlapSpeed;
    public float oFlap;
    public float wingRotDelta = 1.0F;
	
    public EntityTurkey(World worldIn)
    {
        super(worldIn);
        this.setSize(0.8F, 1.1F);
    }
    
    @Override
    protected void entityInit()
    {
        super.entityInit();
    }
    
    @Override
    protected void initEntityAI()
    {
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIPanic(this, 1.0D));
        this.tasks.addTask(2, new EntityAIMate(this, 1.0D));
        this.tasks.addTask(4, new EntityAIFollowParent(this, 1.1D));
        this.tasks.addTask(5, new EntityAIWanderAvoidWater(this, 1.0D));
        this.tasks.addTask(8, new EntityAILookIdle(this));
    }
    
    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(5.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
    }
    
    @Override
    public void onLivingUpdate()
    {
        super.onLivingUpdate();
        this.oFlap = this.wingRotation;
        this.oFlapSpeed = this.destPos;
        this.destPos = (float)((double)this.destPos + (double)(this.onGround ? -1 : 4) * 0.3D);
        this.destPos = MathHelper.clamp(this.destPos, 0.0F, 1.0F);

        if (!this.onGround && this.wingRotDelta < 1.0F)
        {
            this.wingRotDelta = 1.0F;
        }

        this.wingRotDelta = (float)((double)this.wingRotDelta * 0.9D);

        if (!this.onGround && this.motionY < 0.0D)
        {
            this.motionY *= 0.6D;
        }

        this.wingRotation += this.wingRotDelta * 2.0F;
    }
    
    @Override
    public void fall(float distance, float damageMultiplier)
    {
    }
    
    @Override
    protected SoundEvent getAmbientSound()
    {
        return FFSounds.turkey_ambient;
    }
    
    @Override
    protected SoundEvent getHurtSound(DamageSource source)
    {
        return FFSounds.turkey_hurt;
    }
    
    @Override
    protected SoundEvent getDeathSound()
    {
        return FFSounds.turkey_dead;
    }
    
    @Override
    protected void playStepSound(BlockPos pos, Block blockIn)
    {
        this.playSound(FFSounds.turkey_step, 0.15F, 1.0F);
    }

    @Override
    public EntityTurkey createChild(EntityAgeable ageable)
    {
        return new EntityTurkey(this.world);
    }
    
    @Nullable
    @Override
    protected ResourceLocation getLootTable()
    {
        return ModLootTable.TURKEY_LOOT;
    }
    
    protected boolean canMate()
    {
        return this.isChild() && this.getHealth() >= this.getMaxHealth() && this.isInLove();
    }
    
    @Override
    public void onUpdate()
    {
        super.onUpdate();

        if (!this.world.isRemote && (!(ConfigurationHandler.turkeyEnable)))
        {
            this.setDead();
        }
    }
}
