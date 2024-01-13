package com.swdteam.client.render.entity;

import com.swdteam.client.model.ModelCybermat;
import com.swdteam.common.entity.CybermatEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;

public class RenderCybermat
  extends LivingRenderer<CybermatEntity, ModelCybermat> {
   protected static final ResourceLocation BLUE = new ResourceLocation("dalekmod", "textures/entity/cyber/cybermat_closed.png");

  public RenderCybermat(EntityRendererManager renderManager) {
     super(renderManager, (EntityModel)new ModelCybermat(0.3F), 0.2F);
  }


  public ResourceLocation getTextureLocation(CybermatEntity entity) {
     return BLUE;
  }


  protected boolean shouldShowName(CybermatEntity entity) {
     return (super.func_177070_b((LivingEntity)entity) && (entity.func_94059_bO() || (entity.func_145818_k_() && entity == this.field_76990_c.field_147941_i)));
  }
}


