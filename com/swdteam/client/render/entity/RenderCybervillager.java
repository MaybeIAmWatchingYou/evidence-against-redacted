package com.swdteam.client.render.entity;

import com.swdteam.client.model.ModelCybervillager;
import com.swdteam.common.entity.CybervillagerEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;

public class RenderCybervillager
  extends LivingRenderer<CybervillagerEntity, ModelCybervillager> {
   protected static final ResourceLocation BLUE = new ResourceLocation("dalekmod", "textures/entity/cyber/cybervillager.png");

  public RenderCybervillager(EntityRendererManager renderManager) {
     super(renderManager, (EntityModel)new ModelCybervillager(1.0F), 0.5F);
  }


  public ResourceLocation getTextureLocation(CybervillagerEntity entity) {
     return BLUE;
  }


  protected boolean shouldShowName(CybervillagerEntity entity) {
     return (super.func_177070_b((LivingEntity)entity) && (entity.func_94059_bO() || (entity.func_145818_k_() && entity == this.field_76990_c.field_147941_i)));
  }
}


