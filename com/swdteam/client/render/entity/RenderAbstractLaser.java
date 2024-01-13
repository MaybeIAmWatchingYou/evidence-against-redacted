package com.swdteam.client.render.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.swdteam.common.entity.LaserEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Matrix3f;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class RenderAbstractLaser<T extends LaserEntity> extends EntityRenderer<T> {
  public RenderAbstractLaser(EntityRendererManager renderManagerIn) {
     super(renderManagerIn);
  }


  public void render(T entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
     matrixStackIn.func_227860_a_();
     matrixStackIn.func_227863_a_(Vector3f.field_229181_d_.func_229187_a_(MathHelper.func_219799_g(partialTicks, ((LaserEntity)entityIn).field_70126_B, ((LaserEntity)entityIn).field_70177_z) - 90.0F));
     matrixStackIn.func_227863_a_(Vector3f.field_229183_f_.func_229187_a_(MathHelper.func_219799_g(partialTicks, ((LaserEntity)entityIn).field_70127_C, ((LaserEntity)entityIn).field_70125_A)));


     matrixStackIn.func_227863_a_(Vector3f.field_229179_b_.func_229187_a_(45.0F));
     matrixStackIn.func_227862_a_(0.05625F, 0.05625F, 0.05625F);
     matrixStackIn.func_227861_a_(-4.0D, 0.0D, 0.0D);
     IVertexBuilder ivertexbuilder = bufferIn.getBuffer(RenderType.func_228638_b_(func_110775_a((Entity)entityIn)));
     MatrixStack.Entry matrixstack$entry = matrixStackIn.func_227866_c_();
     Matrix4f matrix4f = matrixstack$entry.func_227870_a_();
     Matrix3f matrix3f = matrixstack$entry.func_227872_b_();
     func_229039_a_(matrix4f, matrix3f, ivertexbuilder, -7, -2, -2, 0.0F, 0.15625F, -1, 0, 0, packedLightIn);
     func_229039_a_(matrix4f, matrix3f, ivertexbuilder, -7, -2, 2, 0.15625F, 0.15625F, -1, 0, 0, packedLightIn);
     func_229039_a_(matrix4f, matrix3f, ivertexbuilder, -7, 2, 2, 0.15625F, 0.3125F, -1, 0, 0, packedLightIn);
     func_229039_a_(matrix4f, matrix3f, ivertexbuilder, -7, 2, -2, 0.0F, 0.3125F, -1, 0, 0, packedLightIn);
     func_229039_a_(matrix4f, matrix3f, ivertexbuilder, -7, 2, -2, 0.0F, 0.15625F, 1, 0, 0, packedLightIn);
     func_229039_a_(matrix4f, matrix3f, ivertexbuilder, -7, 2, 2, 0.15625F, 0.15625F, 1, 0, 0, packedLightIn);
     func_229039_a_(matrix4f, matrix3f, ivertexbuilder, -7, -2, 2, 0.15625F, 0.3125F, 1, 0, 0, packedLightIn);
     func_229039_a_(matrix4f, matrix3f, ivertexbuilder, -7, -2, -2, 0.0F, 0.3125F, 1, 0, 0, packedLightIn);

     for (int j = 0; j < 4; j++) {
       matrixStackIn.func_227863_a_(Vector3f.field_229179_b_.func_229187_a_(90.0F));
       func_229039_a_(matrix4f, matrix3f, ivertexbuilder, -8, -2, 0, 0.0F, 0.0F, 0, 1, 0, packedLightIn);
       func_229039_a_(matrix4f, matrix3f, ivertexbuilder, 8, -2, 0, 0.5F, 0.0F, 0, 1, 0, packedLightIn);
       func_229039_a_(matrix4f, matrix3f, ivertexbuilder, 8, 2, 0, 0.5F, 0.15625F, 0, 1, 0, packedLightIn);
       func_229039_a_(matrix4f, matrix3f, ivertexbuilder, -8, 2, 0, 0.0F, 0.15625F, 0, 1, 0, packedLightIn);
    }

     matrixStackIn.func_227865_b_();
     super.func_225623_a_((Entity)entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
  }

  public void func_229039_a_(Matrix4f p_229039_1_, Matrix3f p_229039_2_, IVertexBuilder p_229039_3_, int p_229039_4_, int p_229039_5_, int p_229039_6_, float p_229039_7_, float p_229039_8_, int p_229039_9_, int p_229039_10_, int p_229039_11_, int p_229039_12_) {
     p_229039_3_.func_227888_a_(p_229039_1_, p_229039_4_, p_229039_5_, p_229039_6_).func_225586_a_(255, 255, 255, 255).func_225583_a_(p_229039_7_, p_229039_8_).func_227891_b_(OverlayTexture.field_229196_a_).func_227886_a_(p_229039_12_).func_227887_a_(p_229039_2_, p_229039_9_, p_229039_11_, p_229039_10_).func_181675_d();
  }
}


