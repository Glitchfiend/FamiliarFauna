package familiarfauna.entities.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelTurkey extends ModelBase
{
	public ModelRenderer body;
	public ModelRenderer leftLeg;
	public ModelRenderer rightLeg;
	public ModelRenderer chest;
	public ModelRenderer neck;
	public ModelRenderer beak;
	public ModelRenderer gizzard;
	public ModelRenderer plume;
	public ModelRenderer leftWing;
	public ModelRenderer rightWing;

    public ModelTurkey()
    {
        this.textureWidth = 64;
        this.textureHeight = 32;
        
        body = new ModelRenderer(this, 20, 0);
        body.addBox(-5F, 0F, -6F, 10, 7, 12);
        body.setRotationPoint(0F, 12F, 0F);
        setRotation(body, 0F, 0F, 0F);
        
        leftLeg = new ModelRenderer(this, 38, 19);
        leftLeg.addBox(0F, 0F, -5F, 5, 5, 5);
        leftLeg.setRotationPoint(0F, 19F, 2F);
        setRotation(leftLeg, 0F, 0F, 0F);
        
        rightLeg = new ModelRenderer(this, 38, 19);
        rightLeg.addBox(-5F, 0F, -5F, 5, 5, 5);
        rightLeg.setRotationPoint(0F, 19F, 2F);
        setRotation(rightLeg, 0F, 0F, 0F);
        
        chest = new ModelRenderer(this, 0, 24);
        chest.addBox(-3F, -4F, -4F, 6, 4, 4);
        chest.setRotationPoint(0F, 15F, -3F);
        setRotation(chest, 0.7853982F, 0F, 0F);
        
        neck = new ModelRenderer(this, 0, 12);
        neck.addBox(-2F, -8F, -5F, 4, 8, 3);
        setRotation(neck, 0F, 0F, 0F);
        this.chest.addChild(this.neck);
        
        beak = new ModelRenderer(this, 38, 29);
        beak.addBox(-1F, -6F, -7F, 2, 1, 2);
        setRotation(beak, 0F, 0F, 0F);
        this.chest.addChild(this.beak);
        
        gizzard = new ModelRenderer(this, 58, 19);
        gizzard.addBox(-1F, -5F, -6F, 2, 6, 1);
        setRotation(gizzard, 0F, 0F, 0F);
        this.chest.addChild(this.gizzard);
        
        plume = new ModelRenderer(this, 0, 0);
        plume.addBox(-8F, -9F, 6F, 16, 12, 0);
        plume.setRotationPoint(0F, 12F, 0F);
        setRotation(plume, -0.2617994F, 0F, 0F);
        
        leftWing = new ModelRenderer(this, 20, 19);
        leftWing.addBox(0F, 0F, 0F, 1, 5, 8);
        leftWing.setRotationPoint(5F, 13F, -4F);
        setRotation(leftWing, 0F, 0F, 0F);
        
        rightWing = new ModelRenderer(this, 20, 19);
        rightWing.addBox(-1F, 0F, 0F, 1, 5, 8);
        rightWing.setRotationPoint(-5F, 13F, -4F);
        setRotation(rightWing, 0F, 0F, 0F);
    }

    @Override
    public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale)
    {
        this.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entityIn);

        if (this.isChild)
        {
            float f = 2.0F;
            GlStateManager.pushMatrix();
            GlStateManager.scale(0.6F, 0.6F, 0.6F);
            GlStateManager.translate(0.0F, 16.0F * scale, 0.0F);
            body.render(scale);
            leftLeg.render(scale);
            rightLeg.render(scale);
            chest.render(scale);
            leftWing.render(scale);
            rightWing.render(scale);
            GlStateManager.popMatrix();
        }
        else
        {
            body.render(scale);
            leftLeg.render(scale);
            rightLeg.render(scale);
            chest.render(scale);
            plume.render(scale);
            leftWing.render(scale);
            rightWing.render(scale);
        }
    }
    
    private void setRotation(ModelRenderer model, float x, float y, float z)
    {
      model.rotateAngleX = x;
      model.rotateAngleY = y;
      model.rotateAngleZ = z;
    }
    
    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn)
    {
        this.chest.rotateAngleX = headPitch * 0.017453292F + 0.7853982F;
        this.neck.rotateAngleX = headPitch * 0.017453292F - 0.7853982F;
        this.beak.rotateAngleX = headPitch * 0.017453292F - 0.7853982F;
        this.gizzard.rotateAngleX = headPitch * 0.017453292F - 0.7853982F;
        this.chest.rotateAngleY = netHeadYaw * 0.017453292F;
        this.rightLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        this.leftLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
        this.rightWing.rotateAngleZ = ageInTicks;
        this.leftWing.rotateAngleZ = -ageInTicks;
    }
}