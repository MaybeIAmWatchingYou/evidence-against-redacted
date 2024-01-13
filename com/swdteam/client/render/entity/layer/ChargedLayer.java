package com.swdteam.client.render.entity.layer;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.swdteam.client.data.DamageMap;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class ChargedLayer<T extends Entity, M extends EntityModel<T>>
  extends LayerRenderer<T, M> {
  public ChargedLayer(IEntityRenderer<T, M> p_i226038_1_) {
     super(p_i226038_1_);
  }

  public void func_225628_a_(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, T entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
     if (entitylivingbaseIn instanceof PlayerEntity) {
       PlayerEntity player = (PlayerEntity)entitylivingbaseIn;

       if (DamageMap.getEntities().contains(player))
         if (player.field_70737_aN != 0) {


           EntityModel<T> entitymodel = getModel();
           EntityModel<T> entitymodelGlow = getGlowModel();

           entitymodel.func_212843_a_((Entity)entitylivingbaseIn, limbSwing, limbSwingAmount, partialTicks);
           getModel().func_217111_a(entitymodel);
           entitymodelGlow.func_212843_a_((Entity)entitylivingbaseIn, limbSwing, limbSwingAmount, partialTicks);
           getModel().func_217111_a(entitymodelGlow);

           IVertexBuilder ivertexbuilder = bufferIn.getBuffer(RenderType.func_228644_e_(getTexture()));
           entitymodel.func_225597_a_((Entity)entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
           entitymodel.func_225598_a_(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.field_229196_a_, 0.5F, 0.5F, 1.0F, 1.0F);


           ivertexbuilder = bufferIn.getBuffer(RenderType.func_228652_i_(getTexture()));
           entitymodelGlow.func_225597_a_((Entity)entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
           entitymodelGlow.func_225598_a_(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.field_229196_a_, 0.0F, 0.4F, 1.0F, 1.0F);
        } else {

           DamageMap.getEntities().remove(player);
        }
    }
  }

  protected abstract float func_225634_a_(float paramFloat);

  protected abstract ResourceLocation getTexture();

  protected abstract EntityModel<T> getModel();

  protected abstract EntityModel<T> getGlowModel();
}


