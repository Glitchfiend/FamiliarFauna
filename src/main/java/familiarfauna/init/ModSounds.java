package familiarfauna.init;

import static familiarfauna.api.FFSounds.deer_dead;
import static familiarfauna.api.FFSounds.deer_hurt;
import static familiarfauna.api.FFSounds.deer_step;
import static familiarfauna.api.FFSounds.dragonfly_ambient;

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
