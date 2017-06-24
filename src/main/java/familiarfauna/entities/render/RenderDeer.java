package familiarfauna.entities.render;

import familiarfauna.entities.EntityDeer;
import familiarfauna.entities.model.ModelDeer;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderDeer extends RenderLiving<EntityDeer>
{
    private static final ResourceLocation deerTextureLocation = new ResourceLocation("familiarfauna:textures/entity/deer.png");

    public RenderDeer(RenderManager renderManager)
    {
        super(renderManager, new ModelDeer(), 0.7F);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityDeer entity)
    {
        return deerTextureLocation;
    }
}
