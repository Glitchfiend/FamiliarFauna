package familiarfauna.core;

import familiarfauna.entities.EntityButterfly;
import familiarfauna.entities.EntityDeer;
import familiarfauna.entities.EntityDragonfly;
import familiarfauna.entities.EntityPixie;
import familiarfauna.entities.EntitySnail;
import familiarfauna.entities.render.RenderButterfly;
import familiarfauna.entities.render.RenderDeer;
import familiarfauna.entities.render.RenderDragonfly;
import familiarfauna.entities.render.RenderPixie;
import familiarfauna.entities.render.RenderSnail;
import familiarfauna.particle.EntityBluePixieTrailFX;
import familiarfauna.particle.EntityPinkPixieTrailFX;
import familiarfauna.particle.EntityPurplePixieTrailFX;
import familiarfauna.particle.FFParticleTypes;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;


public class ClientProxy extends CommonProxy
{
	public static ResourceLocation particleTexturesLocation = new ResourceLocation("familiarfauna:textures/particles/particles.png");
	
    @Override
    public void registerRenderers()
    {
        registerEntityRenderer(EntityButterfly.class, RenderButterfly.class);
        registerEntityRenderer(EntityDragonfly.class, RenderDragonfly.class);
        registerEntityRenderer(EntityPixie.class, RenderPixie.class);
        registerEntityRenderer(EntitySnail.class, RenderSnail.class);
        registerEntityRenderer(EntityDeer.class, RenderDeer.class);
    }
    
    @Override
    public void registerItemVariantModel(Item item, String name, int metadata) 
    {
        if (item != null) 
        { 
            ModelBakery.registerItemVariants(item, new ResourceLocation("familiarfauna:" + name));
            ModelLoader.setCustomModelResourceLocation(item, metadata, new ModelResourceLocation(FamiliarFauna.MOD_ID + ":" + name, "inventory"));
        }
    }
    
    @Override
    public void spawnParticle(FFParticleTypes type, World parWorld, double x, double y, double z, Object... info)
    {
        Minecraft minecraft = Minecraft.getMinecraft();
        Particle entityFx = null;
        switch (type)
        {
        case PINK_PIXIE_TRAIL:
            entityFx = new EntityPinkPixieTrailFX(parWorld, x, y, z, MathHelper.nextDouble(parWorld.rand, -0.03, 0.03), -0.02D, MathHelper.nextDouble(parWorld.rand, -0.03, 0.03));
            break;
        case BLUE_PIXIE_TRAIL:
            entityFx = new EntityBluePixieTrailFX(parWorld, x, y, z, MathHelper.nextDouble(parWorld.rand, -0.03, 0.03), -0.02D, MathHelper.nextDouble(parWorld.rand, -0.03, 0.03));
            break;
        case PURPLE_PIXIE_TRAIL:
            entityFx = new EntityPurplePixieTrailFX(parWorld, x, y, z, MathHelper.nextDouble(parWorld.rand, -0.03, 0.03), -0.02D, MathHelper.nextDouble(parWorld.rand, -0.03, 0.03));
            break;
        default:
            break;
        }

        if (entityFx != null) {minecraft.effectRenderer.addEffect(entityFx);}
    }
    
    private static <E extends Entity> void registerEntityRenderer(Class<E> entityClass, Class<? extends Render<E>> renderClass)
    {
        RenderingRegistry.registerEntityRenderingHandler(entityClass, new EntityRenderFactory<E>(renderClass));
    }

    private static class EntityRenderFactory<E extends Entity> implements IRenderFactory<E>
    {
        private Class<? extends Render<E>> renderClass;

        private EntityRenderFactory(Class<? extends Render<E>> renderClass)
        {
            this.renderClass = renderClass;
        }

        @Override
        public Render<E> createRenderFor(RenderManager manager) 
        {
            Render<E> renderer = null;

            try 
            {
                renderer = renderClass.getConstructor(RenderManager.class).newInstance(manager);
            } 
            catch (Exception e) 
            {
                e.printStackTrace();
            }

            return renderer;
        }
    }
}
