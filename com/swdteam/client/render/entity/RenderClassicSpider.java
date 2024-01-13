package com.swdteam.client.render.entity;

import com.swdteam.client.model.ModelClassicSpider;
import com.swdteam.common.entity.classic.ClassicSpiderEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;

public class RenderClassicSpider extends LivingRenderer<ClassicSpiderEntity, ModelClassicSpider<ClassicSpiderEntity>> {
  public RenderClassicSpider(EntityRendererManager renderManager) {
     super(renderManager, (EntityModel)new ModelClassicSpider(), 1.0F);
  }


  public ResourceLocation getTextureLocation(ClassicSpiderEntity entity) {
     return ((ModelClassicSpider)this.field_77045_g).model.getModelData().getTexture();
  }


  protected boolean shouldShowName(ClassicSpiderEntity entity) {
     return (super.func_177070_b((LivingEntity)entity) && (entity.func_94059_bO() || (entity.func_145818_k_() && entity == this.field_76990_c.field_147941_i)));
  }
}


