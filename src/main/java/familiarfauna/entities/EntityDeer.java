/*******************************************************************************
 * Copyright 2015-2016, the Biomes O' Plenty Team
 * 
 * This work is licensed under a Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International Public License.
 * 
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 ******************************************************************************/

package familiarfauna.entities;

import javax.annotation.Nullable;

import familiarfauna.init.ModLootTable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class EntityDeer extends EntityAnimal implements IMob {
    
    public EntityDeer(World worldIn)
    {
        super(worldIn);
        this.setSize(1.0F, 1.2F);
    }
    
    @Override
    protected void initEntityAI()
    {
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIPanic(this, 2.0D));
        this.tasks.addTask(2, new EntityAIWanderAvoidWater(this, 1.0D));
        this.tasks.addTask(3, new EntityDeer.AIAvoidEntity(this, EntityPlayer.class, 5.0F, 2.0D, 2.5D));
        this.tasks.addTask(4, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
        this.tasks.addTask(5, new EntityAILookIdle(this));
    }
    
    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(8.5D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.2D);
    }

    @Override
    public EntityDeer createChild(EntityAgeable ageable)
    {
        return new EntityDeer(this.world);
    }
    
    @Nullable
    @Override
    protected ResourceLocation getLootTable()
    {
        return ModLootTable.DEER_LOOT;
    }
    
    static class AIAvoidEntity<T extends Entity> extends EntityAIAvoidEntity<T>
    {
        private final EntityDeer deer;

        public AIAvoidEntity(EntityDeer deer, Class<T> p_i46403_2_, float p_i46403_3_, double p_i46403_4_, double p_i46403_6_)
        {
            super(deer, p_i46403_2_, p_i46403_3_, p_i46403_4_, p_i46403_6_);
            this.deer = deer;
        }
    }
}
