package com.swdteam.client.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.swdteam.common.entity.WeepingAngelEntity;
import com.swdteam.model.javajson.JSONModel;
import com.swdteam.model.javajson.ModelLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.entity.Entity;
import net.minecraft.potion.Effects;
import net.minecraft.util.ResourceLocation;

public class ModelWeepingAngel
  extends EntityModel<WeepingAngelEntity>
  implements IModelPartReloader
{
  public JSONModel model;

  public ModelWeepingAngel() {
     ModelReloaderRegistry.register(this);
  }


  public void init() {
     this.model = ModelLoader.loadModel(new ResourceLocation("dalekmod", "models/entity/weeping_angel/weeping_angel.json"));
  }



  public void setupAnim(WeepingAngelEntity entity, float p_225597_2_, float p_225597_3_, float p_225597_4_, float p_225597_5_, float p_225597_6_) {
     if (Minecraft.func_71410_x().func_193989_ak() > 1.0F);
     boolean quantum = true;
     if ((Minecraft.func_71410_x()).field_71439_g.func_70644_a(Effects.field_76440_q)) {
       if ((Minecraft.func_71410_x()).field_71439_g.getPositionVec().func_72438_d(entity.getPositionVec()) > 6.0D) {
         quantum = false;
      } else {
         quantum = true;
      }
    }

     if (quantum) {
       entity.quantum += 0.01F;
    }
  }


  public void func_225598_a_(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
     if (this.model != null) {
       this.model.getModelData().getModel().renderToBuffer(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha, true);
    }
  }


  public JSONModel getModel() {
     return this.model;
  }
}


