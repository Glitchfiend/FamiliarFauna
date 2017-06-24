package familiarfauna.core;

import familiarfauna.entities.EntityButterfly;
import familiarfauna.entities.EntityDeer;
import familiarfauna.entities.EntitySnail;
import familiarfauna.entities.render.RenderButterfly;
import familiarfauna.entities.render.RenderDeer;
import familiarfauna.entities.render.RenderSnail;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;


public class ClientProxy extends CommonProxy
{
    @Override
    public void registerRenderers()
    {
        registerEntityRenderer(EntityButterfly.class, RenderButterfly.class);
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
