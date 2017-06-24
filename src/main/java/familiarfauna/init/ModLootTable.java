package familiarfauna.init;

import familiarfauna.core.FamiliarFauna;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootTableList;

public class ModLootTable
{
    public static ResourceLocation DEER_LOOT;
    
    public static void init()
    {
        DEER_LOOT = LootTableList.register(new ResourceLocation(FamiliarFauna.MOD_ID, "entities/deer"));
    }
}
