package com.swdteam.client.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.swdteam.common.entity.CybermatEntity;
import com.swdteam.model.javajson.JSONModel;
import com.swdteam.model.javajson.ModelLoader;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class ModelCybermat
  extends EntityModel<CybermatEntity>
  implements IModelPartReloader
{
  public JSONModel model;
  public ModelRenderer body;
  public ModelRenderer tail;
  public ModelRenderer head;
  
  public ModelCybermat(float modelSize) {
     ModelReloaderRegistry.register(this);
  }


  
  public void init() {
     this.model = ModelLoader.loadModel(new ResourceLocation("dalekmod", "models/entity/cyber/cybermat.json"));
  }


  
  public void func_225598_a_(MatrixStack arg0, IVertexBuilder arg1, int arg2, int arg3, float arg4, float arg5, float arg6, float arg7) {
     this.model.getModelData().getModel().renderToBuffer(arg0, arg1, arg2, arg3, arg4, arg5, arg4, 1.0F, true);
  }






  
  public void setupAnim(CybermatEntity arg0, float arg1, float arg2, float arg3, float arg4, float arg5) {
     this.body = (ModelRenderer)this.model.getModelData().getModel().getPart("body");
     this.tail = (ModelRenderer)this.model.getModelData().getModel().getPart("tail");
     this.head = (ModelRenderer)this.model.getModelData().getModel().getPart("head");
     this.body.field_78796_g = (float)(Math.cos((arg3 * 0.9F + 0.47123894F)) * 3.1415927410125732D * 0.05000000074505806D * (1 + Math.abs(-1)));
     this.body.field_78800_c = (float)(Math.sin((arg3 * 0.9F + 0.47123894F)) * 3.1415927410125732D * 0.20000000298023224D * Math.abs(-1));
     this.tail.field_78796_g = (float)(Math.cos((arg3 * 0.9F + 0.47123894F)) * 3.1415927410125732D * 0.05000000074505806D * (1 + Math.abs(-1)));
     this.tail.field_78800_c = (float)(Math.sin((arg3 * 0.9F + 0.47123894F)) * 3.1415927410125732D * 0.20000000298023224D * Math.abs(-1));
     this.head.field_78796_g = (float)(Math.cos((arg3 * 0.9F + 0.47123894F)) * 3.1415927410125732D * 0.05000000074505806D * (1 + Math.abs(-1)));
     this.head.field_78800_c = (float)(Math.sin((arg3 * 0.9F + 0.47123894F)) * 3.1415927410125732D * 0.20000000298023224D * Math.abs(-1));
  }


  
  public JSONModel getModel() {
     return this.model;
  }
}


