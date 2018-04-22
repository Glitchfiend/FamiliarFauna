package familiarfauna.entities.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelDragonfly extends ModelBase
{
    ModelRenderer body;
    ModelRenderer rightwing;
    ModelRenderer leftwing;
    ModelRenderer head;
  
	public ModelDragonfly()
	{
	    textureWidth = 64;
	    textureHeight = 32;
	    
		body = new ModelRenderer(this, 0, 0);
		body.addBox(0F, -3F, -1F, 1, 10, 1);
		body.setRotationPoint(0F, 21F, 2F);
		setRotation(body, 1.570796F, 0F, 0F);
		  
		rightwing = new ModelRenderer(this, 0, 6);
		rightwing.addBox(-7F, 0F, -4F, 7, 1, 7);
		rightwing.setRotationPoint(0F, 21F, 2F);
		setRotation(rightwing, 0F, 0F, 0F);
		  
		leftwing = new ModelRenderer(this, 0, 16);
		leftwing.addBox(0F, 0F, -4F, 7, 1, 7);
		leftwing.setRotationPoint(1F, 21F, 2F);
		setRotation(leftwing, 0F, 0F, 0F);
		  
		head = new ModelRenderer(this, 4, 0);
		head.addBox(-1F, 0F, -4F, 3, 1, 1);
		head.setRotationPoint(0F, 21F, 2F);
		setRotation(head, 0F, 0F, 0F);
	}
  
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	{
		super.render(entity, f, f1, f2, f3, f4, f5);
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		this.body.render(f5);
		this.rightwing.render(f5);
		this.leftwing.render(f5);
		this.head.render(f5);
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
	    
	    rightwing.rotateAngleZ = -(MathHelper.cos(f2 * 5.0F) * (float)Math.PI * 0.15F);
	    leftwing.rotateAngleZ = MathHelper.cos(f2 * 5.0F) * (float)Math.PI * 0.15F;
	}
}
