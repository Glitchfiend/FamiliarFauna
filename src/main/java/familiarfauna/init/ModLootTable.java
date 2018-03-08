package familiarfauna.init;

import familiarfauna.core.FamiliarFauna;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootTableList;

public class ModLootTable
{
	public static ResourceLocation BUTTERFLY_LOOT;
	public static ResourceLocation DRAGONFLY_LOOT;
    public static ResourceLocation DEER_LOOT;
    public static ResourceLocation SNAIL_LOOT;
    
    public static void init()
    {
        BUTTERFLY_LOOT = LootTableList.register(new ResourceLocation(FamiliarFauna.MOD_ID, "entities/butterfly"));
        DRAGONFLY_LOOT = LootTableList.register(new ResourceLocation(FamiliarFauna.MOD_ID, "entities/dragonfly"));
        DEER_LOOT = LootTableList.register(new ResourceLocation(FamiliarFauna.MOD_ID, "entities/deer"));
        SNAIL_LOOT = LootTableList.register(new ResourceLocation(FamiliarFauna.MOD_ID, "entities/snail"));
    }
}
