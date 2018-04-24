package familiarfauna.core;

import familiarfauna.particle.FFParticleTypes;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.world.World;

public class CommonProxy
{
	public void registerRenderers() {}
    public void registerItemVariantModel(Item item, String name, int metadata) {}
    public void registerNonRenderingProperties(Block block) {}
    public void spawnParticle(FFParticleTypes type, World parWorld, double x, double y, double z, Object... info) {}
}
