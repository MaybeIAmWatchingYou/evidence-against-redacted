package com.swdteam.client.render.entity;

import com.swdteam.client.model.ModelDalekBase;
import com.swdteam.common.entity.dalek.DalekEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;


@OnlyIn(Dist.CLIENT)
public class RenderDalek
  extends LivingRenderer<DalekEntity, ModelDalekBase>
{
  public RenderDalek(EntityRendererManager rendererManager) {
     super(rendererManager, (EntityModel)new ModelDalekBase(null), 0.8F);
  }


  public Vector3d getRenderOffset(DalekEntity entity, float f) {
     if (entity.getDalekData() != null && entity.getDalekData().getModel() != null) {
       this.field_77045_g = (EntityModel)entity.getDalekData().getModel();
    }

     return super.func_225627_b_((Entity)entity, f);
  }


  public ResourceLocation getTextureLocation(DalekEntity entity) {
     if (((ModelDalekBase)func_217764_d()).getModelData() != null && ((ModelDalekBase)func_217764_d()).getModelData().getModelData() != null) {
       return ((ModelDalekBase)this.field_77045_g).getModelData().getModelData().getTexture();
    }
     return TextureManager.field_194008_a;
  }



  protected boolean shouldShowName(DalekEntity entity) {
     return (super.func_177070_b((LivingEntity)entity) && (entity.func_94059_bO() || (entity.func_145818_k_() && entity == this.field_76990_c.field_147941_i)));
  }
}


