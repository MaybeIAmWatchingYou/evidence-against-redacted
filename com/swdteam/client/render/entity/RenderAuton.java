package com.swdteam.client.render.entity;
import com.swdteam.client.model.ModelAuton;
import com.swdteam.common.entity.AutonEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;

public class RenderAuton extends LivingRenderer<AutonEntity, ModelAuton> {
   protected static final ResourceLocation BLUE = new ResourceLocation("dalekmod", "textures/entity/auton/auton_blue.png");
   protected static final ResourceLocation RED = new ResourceLocation("dalekmod", "textures/entity/auton/auton_red.png");
   protected static final ResourceLocation PINK = new ResourceLocation("dalekmod", "textures/entity/auton/auton_pink.png");
   protected static final ResourceLocation BLACK = new ResourceLocation("dalekmod", "textures/entity/auton/auton_black.png");
   protected static final ResourceLocation NOTCH = new ResourceLocation("dalekmod", "textures/entity/auton/auton_notch.png");
   protected static final ResourceLocation KHOTARRI = new ResourceLocation("dalekmod", "textures/entity/auton/auton_khotarri.png");
   protected static final ResourceLocation ONEWTC = new ResourceLocation("dalekmod", "textures/entity/auton/auton_1wtc.png");
   protected static final ResourceLocation ORIGINAL = new ResourceLocation("dalekmod", "textures/entity/auton/auton_original.png");

  public RenderAuton(EntityRendererManager renderManager) {
     super(renderManager, (EntityModel)new ModelAuton(1.0F), 0.5F);
  }


  public ResourceLocation getTextureLocation(AutonEntity entity) {
     switch (entity.getAutonType()) { case 0:
         return BLUE;
       case 1: return RED;
       case 2: return PINK;
       case 3: return BLACK;
       case 4: return NOTCH;
       case 5: return KHOTARRI;
       case 6: return ONEWTC;
       case 7: return ORIGINAL; }
      return BLUE;
  }



  protected boolean shouldShowName(AutonEntity entity) {
     return (super.func_177070_b((LivingEntity)entity) && (entity.func_94059_bO() || (entity.func_145818_k_() && entity == this.field_76990_c.field_147941_i)));
  }
}


