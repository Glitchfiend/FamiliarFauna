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
    private static final ResourceLocation BUCK = new ResourceLocation("familiarfauna:textures/entity/deer/buck.png");
    private static final ResourceLocation DOE = new ResourceLocation("familiarfauna:textures/entity/deer/doe.png");
    private static final ResourceLocation FAWN = new ResourceLocation("familiarfauna:textures/entity/deer/fawn.png");

    public RenderDeer(RenderManager renderManager)
    {
        super(renderManager, new ModelDeer(), 0.7F);
    }
    
    @Override
    protected ResourceLocation getEntityTexture(EntityDeer entity)
    {
        if (entity.isChild())
        {
            return FAWN;
        }
        else
        {
            switch (entity.getDeerType())
            {
                case 1:
                    return DOE;
                case 0:
                default:
                    return BUCK;
            }
        }
    }
}
