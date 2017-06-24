package familiarfauna.init;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;

import familiarfauna.core.FamiliarFauna;
import familiarfauna.entities.EntityButterfly;
import familiarfauna.entities.EntityDeer;
import familiarfauna.entities.EntitySnail;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList.EntityEggInfo;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Biomes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.SpawnListEntry;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry.EntityRegistration;

public class ModEntities
{
	public static final Map<Integer, EntityEggInfo> entityEggs = Maps.<Integer, EntityEggInfo>newLinkedHashMap();
    public static final Map<Integer, String> idToBEEntityName = Maps.<Integer, String>newLinkedHashMap();

    private static int nextFFEntityId = 1;
    
    public static void init()
    {
        //Deer
        registerFFEntityWithSpawnEgg(EntityDeer.class, "ffDeer", 80, 3, true, 0x765134, 0xF7EFE6, EnumCreatureType.CREATURE, 8, 1, 3,
                Biomes.BIRCH_FOREST, Biomes.BIRCH_FOREST_HILLS, Biomes.MUTATED_BIRCH_FOREST,
                Biomes.MUTATED_BIRCH_FOREST_HILLS, Biomes.FOREST, Biomes.FOREST_HILLS, Biomes.MUTATED_FOREST,
                Biomes.TAIGA, Biomes.TAIGA_HILLS, Biomes.REDWOOD_TAIGA, Biomes.REDWOOD_TAIGA_HILLS,
                Biomes.MUTATED_REDWOOD_TAIGA, Biomes.MUTATED_REDWOOD_TAIGA_HILLS, Biomes.MUTATED_TAIGA,
                Biomes.EXTREME_HILLS, Biomes.EXTREME_HILLS_EDGE, Biomes.EXTREME_HILLS_WITH_TREES,
                Biomes.MUTATED_EXTREME_HILLS, Biomes.MUTATED_EXTREME_HILLS_WITH_TREES, Biomes.ROOFED_FOREST,
                Biomes.MUTATED_ROOFED_FOREST, Biomes.COLD_TAIGA, Biomes.COLD_TAIGA_HILLS, Biomes.MUTATED_TAIGA_COLD,
                ModCompat.coniferous_forest);
        
        //Butterfly
    	registerFFEntityWithSpawnEgg(EntityButterfly.class, "ffButterfly", 80, 3, true, 0x282828, 0xEF6F1F, EnumCreatureType.AMBIENT, 2, 2, 4,
    	        Biomes.PLAINS, Biomes.MUTATED_PLAINS, Biomes.FOREST, Biomes.FOREST_HILLS, Biomes.MUTATED_FOREST);
    	
    	//Snail
    	registerFFEntityWithSpawnEgg(EntitySnail.class, "ffSnail", 80, 3, true, 0xA694BC, 0xCDA26E, EnumCreatureType.AMBIENT, 1, 1, 1,
    	        Biomes.SWAMPLAND, Biomes.MUTATED_SWAMPLAND, Biomes.ROOFED_FOREST, Biomes.MUTATED_ROOFED_FOREST);
    }
    
    // register an entity
    public static int registerFFEntity(Class<? extends Entity> entityClass, String entityName, int trackingRange, int updateFrequency, boolean sendsVelocityUpdates)
    {
        int ffEntityId = nextFFEntityId;
        nextFFEntityId++;
        EntityRegistry.registerModEntity(new ResourceLocation(FamiliarFauna.MOD_ID, entityName), entityClass, entityName, ffEntityId, FamiliarFauna.instance, trackingRange, updateFrequency, sendsVelocityUpdates);
        idToBEEntityName.put(ffEntityId, entityName);
        return ffEntityId;
    }
    
    // register an entity and in addition create a spawn egg for it
    public static int registerFFEntityWithSpawnEgg(Class<? extends EntityLiving> entityClass, String entityName, int trackingRange, int updateFrequency, boolean sendsVelocityUpdates, int eggBackgroundColor, int eggForegroundColor, EnumCreatureType enumCreatureType, int spawnWeight, int spawnMin, int spawnMax, Biome... biomes)
    {
        int ffEntityId = registerFFEntity(entityClass, entityName, trackingRange, updateFrequency, sendsVelocityUpdates);
        EntityRegistry.registerEgg(new ResourceLocation(FamiliarFauna.MOD_ID, entityName), eggBackgroundColor, eggForegroundColor);
        addSpawn(entityClass, spawnWeight, spawnMin, spawnMax, enumCreatureType, biomes);
        return ffEntityId;
    }
    
    public static Entity createEntityByID(int tanEntityId, World worldIn)
    {
        Entity entity = null;
        ModContainer mc = FMLCommonHandler.instance().findContainerFor(FamiliarFauna.instance);
        EntityRegistration er = EntityRegistry.instance().lookupModSpawn(mc, tanEntityId);
        if (er != null)
        {
            Class<? extends Entity> clazz = er.getEntityClass();
            try
            {
                if (clazz != null)
                {
                    entity = (Entity)clazz.getConstructor(new Class[] {World.class}).newInstance(new Object[] {worldIn});
                }
            }
            catch (Exception exception)
            {
                exception.printStackTrace();
            }            
        }
        if (entity == null)
        {
        	FamiliarFauna.logger.warn("Skipping FF Entity with id " + tanEntityId);
        }        
        return entity;
    }
    
    public static void addSpawn(Class <? extends EntityLiving > entityClass, int weightedProb, int min, int max, EnumCreatureType typeOfCreature, Biome... biomes)
    {
        for (Biome biome : biomes)
        {
            if (biome != null)
            {
                List<SpawnListEntry> spawns = biome.getSpawnableList(typeOfCreature);
    
                boolean found = false;
                for (SpawnListEntry entry : spawns)
                {
                    //Adjusting an existing spawn entry
                    if (entry.entityClass == entityClass)
                    {
                        entry.itemWeight = weightedProb;
                        entry.minGroupCount = min;
                        entry.maxGroupCount = max;
                        found = true;
                        break;
                    }
                }
    
                if (!found)
                    spawns.add(new SpawnListEntry(entityClass, weightedProb, min, max));
            }
        }
    }
}