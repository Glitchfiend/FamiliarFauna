package familiarfauna.init;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;

import familiarfauna.config.ConfigurationHandler;
import familiarfauna.core.FamiliarFauna;
import familiarfauna.entities.EntityButterfly;
import familiarfauna.entities.EntityDeer;
import familiarfauna.entities.EntityDragonfly;
import familiarfauna.entities.EntityPixie;
import familiarfauna.entities.EntitySnail;
import familiarfauna.entities.EntityTurkey;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList.EntityEggInfo;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.init.Biomes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.SpawnListEntry;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.EntityRegistry.EntityRegistration;

public class ModEntities
{
	public static final Map<Integer, EntityEggInfo> entityEggs = Maps.<Integer, EntityEggInfo>newLinkedHashMap();
    public static final Map<Integer, String> idToBEEntityName = Maps.<Integer, String>newLinkedHashMap();

    private static int nextFFEntityId = 1;
    
    public static void init()
    {
        //Butterfly
    	registerFFEntityWithSpawnEgg(EntityButterfly.class, "familiarfauna.butterfly", 80, 3, true, 0x282828, 0xEF6F1F, EnumCreatureType.AMBIENT, ConfigurationHandler.butterflyWeight, ConfigurationHandler.butterflyMin, ConfigurationHandler.butterflyMax, ModConfiguration.butterflyBiomeList);
    	
        //Deer
        //Remove cows from the biomes deer spawn in
    	if (ConfigurationHandler.deerReplaceCows)
		{
			removeSpawn(EntityCow.class, EnumCreatureType.CREATURE, ModConfiguration.deerBiomeList);
		}
        registerFFEntityWithSpawnEgg(EntityDeer.class, "familiarfauna.deer", 80, 3, true, 0x765134, 0xF7EFE6, EnumCreatureType.CREATURE, ConfigurationHandler.deerWeight, ConfigurationHandler.deerMin, ConfigurationHandler.deerMax, ModConfiguration.deerBiomeList);
    	
        //Dragonfly
    	registerFFEntityWithSpawnEgg(EntityDragonfly.class, "familiarfauna.dragonfly", 80, 3, true, 0x34406D, 0x51A1CC, EnumCreatureType.AMBIENT, ConfigurationHandler.dragonflyWeight, ConfigurationHandler.dragonflyMin, ConfigurationHandler.dragonflyMax, ModConfiguration.dragonflyBiomeList);
    	
    	//Pixie
    	registerFFEntityWithSpawnEgg(EntityPixie.class, "familiarfauna.pixie", 80, 3, true, 0xFF99E9, 0xFFFFFF, EnumCreatureType.AMBIENT, ConfigurationHandler.pixieWeight, ConfigurationHandler.pixieMin, ConfigurationHandler.pixieMax, ModConfiguration.pixieBiomeList);
    	
    	//Snail
    	registerFFEntityWithSpawnEgg(EntitySnail.class, "familiarfauna.snail", 80, 3, true, 0xA694BC, 0xCDA26E, EnumCreatureType.AMBIENT, ConfigurationHandler.snailWeight, ConfigurationHandler.snailMin, ConfigurationHandler.snailMax, ModConfiguration.snailBiomeList);
    	
    	//Turkey
        //Remove chickens from the biomes turkey spawn in
    	if (ConfigurationHandler.turkeyReplaceChickens)
		{
			removeSpawn(EntityChicken.class, EnumCreatureType.CREATURE, ModConfiguration.turkeyBiomeList);
		}
        registerFFEntityWithSpawnEgg(EntityTurkey.class, "familiarfauna.turkey", 80, 3, true, 0x6B492E, 0xE23131, EnumCreatureType.CREATURE, ConfigurationHandler.turkeyWeight, ConfigurationHandler.turkeyMin, ConfigurationHandler.turkeyMax, ModConfiguration.turkeyBiomeList);
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
    public static int registerFFEntityWithSpawnEgg(Class<? extends EntityLiving> entityClass, String entityName, int trackingRange, int updateFrequency, boolean sendsVelocityUpdates, int eggBackgroundColor, int eggForegroundColor, EnumCreatureType enumCreatureType, int spawnWeight, int spawnMin, int spawnMax, List<String> biomes)
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
    
    public static void addSpawn(Class <? extends EntityLiving > entityClass, int weightedProb, int min, int max, EnumCreatureType typeOfCreature, List<String> biomes)
    {
    	for (String biomeName : biomes)
        {
            ResourceLocation loc = new ResourceLocation(biomeName);

            if (ForgeRegistries.BIOMES.containsKey(loc))
            {
            	Biome biome = ForgeRegistries.BIOMES.getValue(loc);
            	
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
    
    public static void removeSpawn(Class <? extends EntityLiving > entityClass, EnumCreatureType typeOfCreature, List<String> biomes)
    {
    	for (String biomeName : biomes)
        {
            ResourceLocation loc = new ResourceLocation(biomeName);

            if (ForgeRegistries.BIOMES.containsKey(loc))
            {
            	Biome biome = ForgeRegistries.BIOMES.getValue(loc);
            	
                Iterator<SpawnListEntry> spawns = biome.getSpawnableList(typeOfCreature).iterator();
    
                while (spawns.hasNext())
                {
                    SpawnListEntry entry = spawns.next();
                    if (entry.entityClass == entityClass)
                    {
                        spawns.remove();
                    }
                }
            }
        }
    }
}