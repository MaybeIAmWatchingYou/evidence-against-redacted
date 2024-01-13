package com.swdteam.client.model;

import com.swdteam.common.entity.classic.ClassicSkeletonEntity;
import com.swdteam.model.javajson.JSONModel;
import com.swdteam.model.javajson.ModelLoader;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;

public class ModelClassicSkeleton
  extends BipedModel<ClassicSkeletonEntity>
  implements IModelPartReloader {
  public JSONModel model;

  public ModelClassicSkeleton(float modelSize) {
     super(modelSize);
     ModelReloaderRegistry.register(this);
  }



  public void setupAnim(ClassicSkeletonEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
     super.func_225597_a_((LivingEntity)entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
     this.field_178723_h.field_78795_f = -((float)Math.toRadians(90.0D));
     this.field_178724_i.field_78795_f = -((float)Math.toRadians(90.0D));
  }


  public void init() {
     this.model = ModelLoader.loadModel(new ResourceLocation("dalekmod", "models/entity/classic/skeleton_alt.json"));

     if (this.model != null) {

       this.field_78116_c = (ModelRenderer)this.model.getModelData().getModel().getPart("head");
       this.field_78115_e = (ModelRenderer)this.model.getModelData().getModel().getPart("body");
       this.field_178724_i = (ModelRenderer)this.model.getModelData().getModel().getPart("left_arm");
       this.field_178723_h = (ModelRenderer)this.model.getModelData().getModel().getPart("right_arm");
       this.field_178722_k = (ModelRenderer)this.model.getModelData().getModel().getPart("left_leg");
       this.field_178721_j = (ModelRenderer)this.model.getModelData().getModel().getPart("right_leg");

       this.field_178720_f.field_78806_j = false;
    }
  }


  public JSONModel getModel() {
     return this.model;
  }
}


