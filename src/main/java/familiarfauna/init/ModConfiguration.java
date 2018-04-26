package familiarfauna.init;

import java.io.File;
import java.util.List;

import com.google.common.collect.Lists;
import com.google.gson.reflect.TypeToken;

import familiarfauna.config.ConfigurationHandler;
import familiarfauna.util.config.JsonUtil;
import net.minecraftforge.common.MinecraftForge;

public class ModConfiguration
{
	public static List<String> butterflyBiomeList = Lists.newArrayList();
	public static List<String> deerBiomeList = Lists.newArrayList();
	public static List<String> dragonflyBiomeList = Lists.newArrayList();
	public static List<String> pixieBiomeList = Lists.newArrayList();
	public static List<String> snailBiomeList = Lists.newArrayList();
	public static List<String> turkeyBiomeList = Lists.newArrayList();
	
    public static void init(File configDirectory)
    {
        ConfigurationHandler.init(new File(configDirectory, "config.cfg"));
        MinecraftForge.EVENT_BUS.register(new ConfigurationHandler());
        
        ////////////////////////////////////////////////////////////////////////////////////////////
        //Butterfly Biomes
        List<String> butterflyBiomes = Lists.newArrayList("minecraft:plains", "minecraft:mutated_plains", "minecraft:forest", "minecraft:forest_hills",
        	"minecraft:mutated_forest", "minecraft:jungle", "minecraft:jungle_hills", "minecraft:jungle_edge",
        	"minecraft:mutated_jungle", "minecraft:mutated_jungle_edge",
        	
        	"biomesoplenty:cherry_blossom_grove", "biomesoplenty:eucalyptus_forest", "biomesoplenty:flower_field", "biomesoplenty:flower_island",
        	"biomesoplenty:grassland", "biomesoplenty:grove", "biomesoplenty:lavender_fields", "biomesoplenty:meadow", "biomesoplenty:mystic_grove",
        	"biomesoplenty:orchard", "biomesoplenty:rainforest", "biomesoplenty:sacred_springs", "biomesoplenty:tropical_island",
        	
        	"traverse:lush_hills", "traverse:meadow", "traverse:mini_jungle", "traverse:rocky_plains");
        
        butterflyBiomeList = JsonUtil.getOrCreateConfigFile(configDirectory, "butterfly_biomes.json", butterflyBiomes, new TypeToken<List<String>>(){}.getType());

        ////////////////////////////////////////////////////////////////////////////////////////////
        //Deer Biomes
        List<String> deerBiomes = Lists.newArrayList("minecraft:birch_forest", "minecraft:birch_forest_hills", "minecraft:mutated_birch_forest",
        	"minecraft:mutated_birch_forest_hills", "minecraft:forest", "minecraft:forest_hills", "minecraft:mutated_forest",
        	"minecraft:taiga", "minecraft:taiga_hills", "minecraft:mutated_taiga", "minecraft:redwood_taiga", "minecraft:redwood_taiga_hills",
        	"minecraft:mutated_redwood_taiga", "minecraft:mutated_redwood_taiga_hills", "minecraft:taiga_cold", "minecraft:taiga_cold_hills",
        	"minecraft:mutated_taiga_cold", "minecraft:extreme_hills", "minecraft:smaller_extreme_hills", "minecraft:extreme_hills_with_trees",
        	"minecraft:mutated_extreme_hills", "minecraft:mutated_extreme_hills_with_trees", "minecraft:roofed_forest", "minecraft:mutated_roofed_forest",
        	
        	"biomesoplenty:boreal_forest", "biomesoplenty:coniferous_forest", "biomesoplenty:dead_forest", "biomesoplenty:grove", "biomesoplenty:maple_woods",
        	"biomesoplenty:meadow", "biomesoplenty:mountain_foothills", "biomesoplenty:mystic_grove", "biomesoplenty:redwood_forest",
        	"biomesoplenty:seasonal_forest", "biomesoplenty:shield", "biomesoplenty:snowy_coniferous_forest", "biomesoplenty:snowy_forest",
        	"biomesoplenty:temperate_rainforest", "biomesoplenty:woodland",
        	
        	"traverse:autumnal_woods", "traverse:autumnal_wooded_hills", "traverse:birch_forested_hills", "traverse:foreseted_hills",
        	"traverse:snowy_coniferous_forest", "traverse:temperate_rainforest", "traverse:woodlands");
        
        deerBiomeList = JsonUtil.getOrCreateConfigFile(configDirectory, "deer_biomes.json", deerBiomes, new TypeToken<List<String>>(){}.getType());
        
        ////////////////////////////////////////////////////////////////////////////////////////////
        //Dragonfly Biomes
        List<String> dragonflyBiomes = Lists.newArrayList("minecraft:swampland", "minecraft:mutated_swampland",
        		
        	"biomesoplenty:bayou", "biomesoplenty:bog", "biomesoplenty:dead_swamp", "biomesoplenty:fen", "biomesoplenty:land_of_lakes",
        	"biomesoplenty:lush_swamp", "biomesoplenty:marsh", "biomesoplenty:shield", "biomesoplenty:temperate_rainforest", "biomesoplenty:wetland",
        	
        	"traverse:green_swamp");
        
        dragonflyBiomeList = JsonUtil.getOrCreateConfigFile(configDirectory, "dragonfly_biomes.json", dragonflyBiomes, new TypeToken<List<String>>(){}.getType());

        ////////////////////////////////////////////////////////////////////////////////////////////
        //Pixie Biomes
        List<String> pixieBiomes = Lists.newArrayList("minecraft:mutated_forest",
        		
        	"biomesoplenty:mystic_grove");
        
        pixieBiomeList = JsonUtil.getOrCreateConfigFile(configDirectory, "pixie_biomes.json", pixieBiomes, new TypeToken<List<String>>(){}.getType());

        ////////////////////////////////////////////////////////////////////////////////////////////
        //Snail Biomes
        List<String> snailBiomes = Lists.newArrayList("minecraft:swampland", "minecraft:mutated_swampland",
        		
        	"biomesoplenty:bayou", "biomesoplenty:bog", "biomesoplenty:dead_swamp", "biomesoplenty:fen", "biomesoplenty:lush_swamp",
        	"biomesoplenty:marsh", "biomesoplenty:moor", "biomesoplenty:quagmire", "biomesoplenty:wetland");
        
        snailBiomeList = JsonUtil.getOrCreateConfigFile(configDirectory, "snail_biomes.json", snailBiomes, new TypeToken<List<String>>(){}.getType());

        ////////////////////////////////////////////////////////////////////////////////////////////
        //Turkey Biomes
        List<String> turkeyBiomes = Lists.newArrayList("minecraft:birch_forest", "minecraft:birch_forest_hills", "minecraft:mutated_birch_forest",
        	"minecraft:mutated_birch_forest_hills", "minecraft:forest", "minecraft:forest_hills", "minecraft:mutated_forest",
        	"minecraft:taiga", "minecraft:taiga_hills", "minecraft:mutated_taiga", "minecraft:redwood_taiga", "minecraft:redwood_taiga_hills",
        	"minecraft:mutated_redwood_taiga", "minecraft:mutated_redwood_taiga_hills", "minecraft:taiga_cold", "minecraft:taiga_cold_hills",
        	"minecraft:mutated_taiga_cold", "minecraft:extreme_hills", "minecraft:smaller_extreme_hills", "minecraft:extreme_hills_with_trees",
        	"minecraft:mutated_extreme_hills", "minecraft:mutated_extreme_hills_with_trees", "minecraft:roofed_forest", "minecraft:mutated_roofed_forest",
        	
        	"biomesoplenty:boreal_forest", "biomesoplenty:coniferous_forest", "biomesoplenty:dead_forest", "biomesoplenty:grove", "biomesoplenty:maple_woods",
        	"biomesoplenty:mountain_foothills", "biomesoplenty:redwood_forest", "biomesoplenty:seasonal_forest", "biomesoplenty:shield",
        	"biomesoplenty:snowy_coniferous_forest", "biomesoplenty:snowy_forest", "biomesoplenty:temperate_rainforest", "biomesoplenty:woodland",
        	
        	"traverse:autumnal_woods", "traverse:autumnal_wooded_hills", "traverse:birch_forested_hills", "traverse:foreseted_hills",
        	"traverse:snowy_coniferous_forest", "traverse:temperate_rainforest", "traverse:woodlands");
        
        turkeyBiomeList = JsonUtil.getOrCreateConfigFile(configDirectory, "turkey_biomes.json", turkeyBiomes, new TypeToken<List<String>>(){}.getType());

        ////////////////////////////////////////////////////////////////////////////////////////////
    }
}