package familiarfauna.init;

import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class ModCompat
{
    public static Biome coniferous_forest = null;
    
    public static void init()
    {
        coniferous_forest = ForgeRegistries.BIOMES.getValue(new ResourceLocation("biomesoplenty:coniferous_forest"));
    }
}
