package familiarfauna.entities.render;

import familiarfauna.entities.EntityButterfly;
import familiarfauna.entities.model.ModelButterfly;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderButterfly extends RenderLiving<EntityButterfly>
{
    private static final ResourceLocation ORANGE = new ResourceLocation("familiarfauna:textures/entity/butterfly/orange.png");
    private static final ResourceLocation BLUE = new ResourceLocation("familiarfauna:textures/entity/butterfly/blue.png");
    private static final ResourceLocation PURPLE = new ResourceLocation("familiarfauna:textures/entity/butterfly/purple.png");
    
    public RenderButterfly(RenderManager renderManager)
    {
        super(renderManager, new ModelButterfly(), 0.25F);
        this.shadowSize = 0.0F;
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityButterfly entity)
    {
        switch (entity.getButterflyType())
        {
            case 0:
            default:
                return ORANGE;
            case 1:
                return BLUE;
            case 2:
                return PURPLE;
        }
    }

}