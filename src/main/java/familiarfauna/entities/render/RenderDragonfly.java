package familiarfauna.entities.render;

import familiarfauna.entities.EntityDragonfly;
import familiarfauna.entities.model.ModelDragonfly;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderDragonfly extends RenderLiving<EntityDragonfly>
{
    private static final ResourceLocation BLUE = new ResourceLocation("familiarfauna:textures/entity/dragonfly/blue.png");
    private static final ResourceLocation RED = new ResourceLocation("familiarfauna:textures/entity/dragonfly/red.png");
    private static final ResourceLocation GREEN = new ResourceLocation("familiarfauna:textures/entity/dragonfly/green.png");
    private static final ResourceLocation BANDED = new ResourceLocation("familiarfauna:textures/entity/dragonfly/banded.png");
    
    public RenderDragonfly(RenderManager renderManager)
    {
        super(renderManager, new ModelDragonfly(), 0.25F);
        this.shadowSize = 0.0F;
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityDragonfly entity)
    {
        switch (entity.getDragonflyType())
        {
            case 0:
            default:
                return BLUE;
            case 1:
                return RED;
            case 2:
                return GREEN;
            case 3:
                return BANDED;
        }
    }

}