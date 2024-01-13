package com.swdteam.client.render.entity;
import com.swdteam.client.model.ModelCyberdrone;
import com.swdteam.common.entity.CyberdroneEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;

public class RenderCyberdrone extends LivingRenderer<CyberdroneEntity, ModelCyberdrone> {
  public RenderCyberdrone(EntityRendererManager renderManager) {
     super(renderManager, (EntityModel)new ModelCyberdrone(), 0.5F);
  }

  
  public ResourceLocation getTextureLocation(CyberdroneEntity entity) {
     return ((ModelCyberdrone)this.field_77045_g).model.getModelData().getTexture();
  }

  
  protected boolean shouldShowName(CyberdroneEntity entity) {
     return entity.func_145818_k_();
  }
}


