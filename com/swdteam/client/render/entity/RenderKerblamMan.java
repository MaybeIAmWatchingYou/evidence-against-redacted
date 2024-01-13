package com.swdteam.client.render.entity;
import com.swdteam.client.model.ModelKerblamMan;
import com.swdteam.common.entity.KerblamManEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;

public class RenderKerblamMan extends LivingRenderer<KerblamManEntity, ModelKerblamMan> {
  public RenderKerblamMan(EntityRendererManager renderManager) {
     super(renderManager, (EntityModel)new ModelKerblamMan(1.0F), 0.5F);
  }

  
  public ResourceLocation getTextureLocation(KerblamManEntity entity) {
     return ((ModelKerblamMan)this.field_77045_g).model.getModelData().getTexture();
  }

  
  protected boolean shouldShowName(KerblamManEntity entity) {
     return (super.func_177070_b((LivingEntity)entity) && (entity.func_94059_bO() || (entity.func_145818_k_() && entity == this.field_76990_c.field_147941_i)));
  }
}


