package familiarfauna.entities;

import javax.annotation.Nullable;

import familiarfauna.config.ConfigurationHandler;
import familiarfauna.init.ModLootTable;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.monster.IMob;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EntitySnail extends EntityCreature implements IMob
{
    public EntitySnail(World worldIn)
    {
        super(worldIn);
        this.setSize(0.5F, 0.5F);
    }
    
    @Override
    protected void initEntityAI()
    {
        this.tasks.addTask(0, new EntityAIWander(this, 0.6D));
    }
    
    @Nullable
    @Override
    protected ResourceLocation getLootTable()
    {
        return ModLootTable.SNAIL_LOOT;
    }
    
    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(1.5D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.2D);
    }
    
    @Override
    protected void playStepSound(BlockPos pos, Block blockIn)
    {
        this.playSound(SoundEvents.BLOCK_SLIME_STEP, 0.05F, 1.0F);
    }

    @Override
    protected float getSoundVolume()
    {
        return 0.2F;
    }
    
    @Override
    public boolean canBreatheUnderwater()
    {
        return true;
    }
    
    @Override
    public void onUpdate()
    {
        super.onUpdate();

        if (!this.world.isRemote && (!(ConfigurationHandler.snailEnable)))
        {
            this.setDead();
        }
    }
}
