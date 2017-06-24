package familiarfauna.entities.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelSnail extends ModelBase
{
	ModelRenderer shell;
    ModelRenderer body;
    ModelRenderer lefteye;
    ModelRenderer righteye;

    public ModelSnail()
    {
        textureWidth = 64;
        textureHeight = 32;
    
        shell = new ModelRenderer(this, 0, 0);
        shell.addBox(-1F, 0F, -3F, 3, 8, 8);
        shell.setRotationPoint(0F, 14F, 0F);
        setRotation(shell, 0F, 0F, 0F);
        
        body = new ModelRenderer(this, 0, 16);
        body.addBox(0F, 0F, 0F, 3, 2, 12);
        body.setRotationPoint(-1F, 22F, -6F);
        setRotation(body, 0F, 0F, 0F);
        
        lefteye = new ModelRenderer(this, 22, 12);
        lefteye.addBox(0F, 0F, 0F, 1, 3, 1);
        lefteye.setRotationPoint(1F, 19F, -5F);
        setRotation(lefteye, 0F, 0F, 0F);
        
        righteye = new ModelRenderer(this, 22, 12);
        righteye.addBox(0F, 0F, 0F, 1, 3, 1);
        righteye.setRotationPoint(-1F, 19F, -5F);
        setRotation(righteye, 0F, 0F, 0F);
    }
    
    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
    	super.render(entity, f, f1, f2, f3, f4, f5);
        setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        shell.render(f5);
        body.render(f5);
        lefteye.render(f5);
        righteye.render(f5);
    }
    
    private void setRotation(ModelRenderer model, float x, float y, float z)
    {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }
    
    @Override
    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity)
    {
        super.setRotationAngles(f, f1, f2, f3, f4, f5, entity); 
    }
}