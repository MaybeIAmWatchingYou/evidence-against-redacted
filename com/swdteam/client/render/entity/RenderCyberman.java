package com.swdteam.client.render.entity;
import com.swdteam.client.model.ModelCyberman;
import com.swdteam.common.entity.CybermanEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;

public class RenderCyberman extends LivingRenderer<CybermanEntity, ModelCyberman> {
   protected static final ResourceLocation CYBUS = new ResourceLocation("dalekmod", "textures/entity/cyber/cybus_cyberman.png");
   protected static final ResourceLocation CYBUS_LEADER = new ResourceLocation("dalekmod", "textures/entity/cyber/cybus_cyberleader.png");
   protected static final ResourceLocation NEMESIS = new ResourceLocation("dalekmod", "textures/entity/cyber/nemesis_cyberman.png");
   protected static final ResourceLocation NEMESIS_LEADER = new ResourceLocation("dalekmod", "textures/entity/cyber/nemesis_cyberleader.png");
   protected static final ResourceLocation DARK_CYBUS = new ResourceLocation("dalekmod", "textures/entity/cyber/dark_cybus_cyberman.png");

  public RenderCyberman(EntityRendererManager renderManager) {
     super(renderManager, (EntityModel)new ModelCyberman(1.0F), 0.5F);
  }


  public ResourceLocation getTextureLocation(CybermanEntity entity) {
     switch (entity.getCybermanType()) { case 0:
         return CYBUS;
       case 1: return CYBUS_LEADER;
       case 2: return NEMESIS;
       case 3: return NEMESIS_LEADER;
       case 4: return DARK_CYBUS; }
      return CYBUS;
  }



  protected boolean shouldShowName(CybermanEntity entity) {
     return (super.func_177070_b((LivingEntity)entity) && (entity.func_94059_bO() || (entity.func_145818_k_() && entity == this.field_76990_c.field_147941_i)));
  }
}


