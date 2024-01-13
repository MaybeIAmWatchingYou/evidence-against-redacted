package com.swdteam.client.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.swdteam.common.entity.CyberdroneEntity;
import com.swdteam.model.javajson.JSONModel;
import com.swdteam.model.javajson.ModelLoader;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class ModelCyberdrone
  extends EntityModel<CyberdroneEntity>
  implements IModelPartReloader
{
  public JSONModel model;

  public ModelCyberdrone() {
     ModelReloaderRegistry.register(this);
  }



  public void setupAnim(CyberdroneEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {}



  public void func_225598_a_(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
     if (this.model != null) {
       this.model.getModelData().getModel().func_225598_a_(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }
  }

  public JSONModel getModel() {
     return this.model;
  }


  public void init() {
     this.model = ModelLoader.loadModel(new ResourceLocation("dalekmod", "models/entity/cyber/cyberdrone.json"));
  }
}


