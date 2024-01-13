package com.swdteam.client.render.entity;
import com.swdteam.client.model.ModelOod;
import com.swdteam.common.entity.OodEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;

public class RenderOod extends LivingRenderer<OodEntity, ModelOod> {
   protected static final ResourceLocation OOD = new ResourceLocation("dalekmod", "textures/entity/ood.png");
   protected static final ResourceLocation OOD_NASTY = new ResourceLocation("dalekmod", "textures/entity/ood_hostile.png");

  public RenderOod(EntityRendererManager renderManager) {
     super(renderManager, (EntityModel)new ModelOod(1.0F), 0.5F);
  }


  public ResourceLocation getTextureLocation(OodEntity entity) {
     if (!entity.isHostile())
    {
       return OOD;
    }
     return OOD_NASTY;
  }



  protected boolean shouldShowName(OodEntity entity) {
     return (super.func_177070_b((LivingEntity)entity) && (entity.func_94059_bO() || (entity.func_145818_k_() && entity == this.field_76990_c.field_147941_i)));
  }
}


