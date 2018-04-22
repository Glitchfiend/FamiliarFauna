package familiarfauna.entities.render;

import familiarfauna.entities.EntitySnail;
import familiarfauna.entities.model.ModelSnail;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderSnail extends RenderLiving<EntitySnail>
{
    private static final ResourceLocation NORMAL = new ResourceLocation("familiarfauna:textures/entity/snail/snail.png");
    private static final ResourceLocation GARY = new ResourceLocation("familiarfauna:textures/entity/snail/snail_gary.png");

    public RenderSnail(RenderManager renderManager)
    {
        super(renderManager, new ModelSnail(), 0.25F);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntitySnail entity)
    {
    	String s = TextFormatting.getTextWithoutFormattingCodes(entity.getName());
    	
    	if (s != null && "Gary".equals(s))
        {
            return GARY;
        }
    	else
    	{
    		return NORMAL;
    	}
    }
}
