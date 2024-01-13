package com.swdteam.client.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.swdteam.model.javajson.JSONModel;
import com.swdteam.model.javajson.ModelLoader;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;



public class ModelBullet
  extends EntityModel<Entity>
  implements IModelPartReloader
{
  public ModelRenderer laser;
  private JSONModel model;
  
  public ModelBullet() {
     ModelReloaderRegistry.register(this);
  }



  
  public void func_225597_a_(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {}


  
  public void func_225598_a_(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
     if (this.model != null) {
       this.model.getModelData().getModel().renderToBuffer(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha, true);
    }
  }
  
  public JSONModel getModel() {
     return this.model;
  }

  
  public void init() {
     this.model = ModelLoader.loadModel(new ResourceLocation("dalekmod", "models/entity/projectile/bullet.json"));
     if (this.model != null)
       this.laser = (ModelRenderer)this.model.getModelData().getModel().getPart("Bullet"); 
  }
}


