package familiarfauna.entities.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelButterfly extends ModelBase
{
	public ModelRenderer body;
    public ModelRenderer rightwing;
    public ModelRenderer leftwing;
    public ModelRenderer antennae;

    public ModelButterfly()
    {
        this.textureWidth = 64;
        this.textureHeight = 32;
        
        body = new ModelRenderer(this, 0, 0);
        body.addBox(0F, -2F, -1F, 1, 5, 1);
        body.setRotationPoint(0F, 21F, 2F);
        setRotation(body, 1.570796F, 0F, 0F);
        
        antennae = new ModelRenderer(this, 4, 0);
        antennae.addBox(-1F, 0F, -2F, 3, 1, 2);
        antennae.setRotationPoint(0F, 21.5F, 0F);
        setRotation(antennae, 0F, 0F, 0F);
        
        rightwing = new ModelRenderer(this, 0, 6);
        rightwing.addBox(-6F, 0F, -4F, 6, 1, 9);
        rightwing.setRotationPoint(0F, 21.5F, 2F);
        setRotation(rightwing, 0F, 0F, 0F);
        
        leftwing = new ModelRenderer(this, 0, 16);
        leftwing.addBox(0F, 0F, -4F, 6, 1, 9);
        leftwing.setRotationPoint(1F, 21.5F, 2F);
        setRotation(leftwing, 0F, 0F, 0F);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        super.render(entity, f, f1, f2, f3, f4, f5);
        setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        this.body.render(f5);
        this.leftwing.render(f5);
        this.rightwing.render(f5);
        this.antennae.render(f5);
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
        
        rightwing.rotateAngleZ = -(MathHelper.cos(f2 * 1.7F) * (float)Math.PI * 0.2F);
        leftwing.rotateAngleZ = MathHelper.cos(f2 * 1.7F) * (float)Math.PI * 0.2F;
    }

}