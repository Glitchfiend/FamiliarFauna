package familiarfauna.entities.render;

import familiarfauna.entities.EntityTurkey;
import familiarfauna.entities.model.ModelTurkey;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderTurkey extends RenderLiving<EntityTurkey>
{
    private static final ResourceLocation TURKEY = new ResourceLocation("familiarfauna:textures/entity/turkey.png");

    public RenderTurkey(RenderManager renderManager)
    {
        super(renderManager, new ModelTurkey(), 0.6F);
    }
    
    @Override
    protected ResourceLocation getEntityTexture(EntityTurkey entity)
    {
    	return TURKEY;
    }
    
    protected float handleRotationFloat(EntityTurkey livingBase, float partialTicks)
    {
        float f = livingBase.oFlap + (livingBase.wingRotation - livingBase.oFlap) * partialTicks;
        float f1 = livingBase.oFlapSpeed + (livingBase.destPos - livingBase.oFlapSpeed) * partialTicks;
        return (MathHelper.sin(f) + 1.0F) * f1;
    }
}
