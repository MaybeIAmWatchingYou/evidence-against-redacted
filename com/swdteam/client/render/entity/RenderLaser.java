package com.swdteam.client.render.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.swdteam.client.model.ModelBullet;
import com.swdteam.client.model.ModelLaser;
import com.swdteam.common.entity.LaserEntity;
import com.swdteam.common.init.DMProjectiles;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Matrix3f;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RenderLaser
  extends EntityRenderer<LaserEntity>
{
   private static ModelLaser MODEL = new ModelLaser();
   private static ModelBullet MODEL_BULLET = new ModelBullet();

  
  public RenderLaser(EntityRendererManager renderManagerIn) {
     super(renderManagerIn);
  }


  
  public ResourceLocation getTextureLocation(LaserEntity entity) {
     if (entity.getLaserType() == DMProjectiles.BULLET) {
       if (MODEL_BULLET.getModel() != null) {
         return MODEL_BULLET.getModel().getModelData().getTexture();
      }
    }
     else if (MODEL.getModel() != null) {
       return MODEL.getModel().getModelData().getTexture();
    } 
    
     return null;
  }


  
  public void render(LaserEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
     DMProjectiles.Laser laser = entityIn.getLaserType();
     if (!laser.doesRender())
      return; 
     matrixStackIn.func_227860_a_();
     matrixStackIn.func_227861_a_(0.0D, 0.15000000596046448D, 0.0D);
    
     matrixStackIn.func_227863_a_(Vector3f.field_229181_d_.func_229187_a_(MathHelper.func_219799_g(partialTicks, entityIn.field_70126_B, entityIn.field_70177_z) - 90.0F));
     matrixStackIn.func_227863_a_(Vector3f.field_229183_f_.func_229187_a_(MathHelper.func_219799_g(partialTicks, entityIn.field_70127_C, entityIn.field_70125_A)));
    
     IVertexBuilder ivertexbuilder = bufferIn.getBuffer(RenderType.func_228638_b_(getTextureLocation(entityIn)));
    
     matrixStackIn.func_227861_a_(0.0D, -1.4500000476837158D, 0.0D);
    
     if (entityIn.getLaserType() == DMProjectiles.BULLET) {
       if (MODEL_BULLET.laser != null) {
         MODEL_BULLET.laser.field_78796_g = (float)Math.toRadians(90.0D);
      }
      
       MODEL_BULLET.func_225598_a_(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.field_229196_a_, laser.getColor()[0], laser.getColor()[1], laser.getColor()[2], 1.0F);
      
       matrixStackIn.func_227865_b_();
    } else {
       if (MODEL.laser != null) {
         MODEL.laser.field_78796_g = (float)Math.toRadians(90.0D);
      }
      
       MODEL.func_225598_a_(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.field_229196_a_, laser.getColor()[0], laser.getColor()[1], laser.getColor()[2], 1.0F);
      
       matrixStackIn.func_227865_b_();
    } 

    
     super.func_225623_a_((Entity)entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
  }

  
  public void func_229039_a_(Matrix4f p_229039_1_, Matrix3f p_229039_2_, IVertexBuilder p_229039_3_, int p_229039_4_, int p_229039_5_, int p_229039_6_, float p_229039_7_, float p_229039_8_, int p_229039_9_, int p_229039_10_, int p_229039_11_, int p_229039_12_) {
     p_229039_3_.func_227888_a_(p_229039_1_, p_229039_4_, p_229039_5_, p_229039_6_).func_225586_a_(255, 255, 255, 255).func_225583_a_(p_229039_7_, p_229039_8_).func_227891_b_(OverlayTexture.field_229196_a_).func_227886_a_(p_229039_12_).func_227887_a_(p_229039_2_, p_229039_9_, p_229039_11_, p_229039_10_).func_181675_d();
  }
}


