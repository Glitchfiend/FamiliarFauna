package familiarfauna.init;

import static familiarfauna.api.FFSounds.*;

import familiarfauna.core.FamiliarFauna;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class ModSounds 
{
    public static void init()
    {
        deer_hurt = registerSound("entity.familiarfauna.deer.hurt");
        deer_dead = registerSound("entity.familiarfauna.deer.dead");
        deer_step = SoundEvents.ENTITY_SHEEP_STEP;
        dragonfly_ambient = registerSound("entity.familiarfauna.dragonfly.ambient");
        pixie_ambient = registerSound("entity.familiarfauna.pixie.ambient");
        pixie_hurt = registerSound("entity.familiarfauna.pixie.hurt");
        turkey_ambient = registerSound("entity.familiarfauna.turkey.ambient");
        turkey_hurt = registerSound("entity.familiarfauna.turkey.hurt");
        turkey_dead = registerSound("entity.familiarfauna.turkey.dead");
        turkey_step = SoundEvents.ENTITY_CHICKEN_STEP;
    }
    
    private static SoundEvent registerSound(String soundName)
    {
        ResourceLocation location = new ResourceLocation(FamiliarFauna.MOD_ID, soundName);
        SoundEvent event = new SoundEvent(location);
        event.setRegistryName(location);
        ForgeRegistries.SOUND_EVENTS.register(event);
        return event;
    }
}
