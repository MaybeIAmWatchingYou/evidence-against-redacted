package com.swdteam.client.render.entity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.swdteam.client.model.ModelWeepingAngel;
import com.swdteam.common.entity.WeepingAngelEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3f;

public class RenderWeepingAngel extends LivingRenderer<WeepingAngelEntity, ModelWeepingAngel> {
  public RenderWeepingAngel(EntityRendererManager renderManager) {
     super(renderManager, (EntityModel)new ModelWeepingAngel(), 0.0F);
  }



  public ResourceLocation getTextureLocation(WeepingAngelEntity entity) {
     return ((ModelWeepingAngel)this.field_77045_g).model.getModelData().getTexture();
  }


  protected boolean shouldShowName(WeepingAngelEntity entity) {
     return (super.func_177070_b((LivingEntity)entity) && (entity.func_94059_bO() || (entity.func_145818_k_() && entity == this.field_76990_c.field_147941_i)));
  }

  protected void setupRotations(WeepingAngelEntity p_225621_1_, MatrixStack p_225621_2_, float p_225621_3_, float p_225621_4_, float p_225621_5_) {
     p_225621_2_.func_227863_a_(Vector3f.field_229181_d_.func_229187_a_(180.0F - p_225621_4_));
     float f = (float)(p_225621_1_.world.getGameTime() - p_225621_1_.lastHit) + p_225621_5_;
     if (f < 3.0F) {
       p_225621_2_.func_227863_a_(Vector3f.field_229181_d_.func_229187_a_(MathHelper.func_76126_a(f / 1.5F * 3.1415927F) * 1.5F));
    }

     p_225621_4_ += (float)(Math.cos(p_225621_1_.field_70173_aa * 3.25D) * Math.PI * 0.4000000059604645D);
  }
}


