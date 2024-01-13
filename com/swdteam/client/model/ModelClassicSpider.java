package com.swdteam.client.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.swdteam.model.javajson.JSONModel;
import com.swdteam.model.javajson.ModelLoader;
import net.minecraft.client.renderer.entity.model.SegmentedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;




public class ModelClassicSpider<T extends Entity>
  extends SegmentedModel<T>
  implements IModelPartReloader
{
  public JSONModel model;
  private ModelRenderer spiderHead;
  private ModelRenderer spiderNeck;
  private ModelRenderer spiderBody;
  private ModelRenderer spiderLeg1;
  private ModelRenderer spiderLeg2;
  private ModelRenderer spiderLeg3;
  private ModelRenderer spiderLeg4;
  private ModelRenderer spiderLeg5;
  private ModelRenderer spiderLeg6;
  private ModelRenderer spiderLeg7;
  private ModelRenderer spiderLeg8;
  
  public ModelClassicSpider() {
     ModelReloaderRegistry.register(this);
  }

  
  public void func_225598_a_(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
     if (this.model != null) {
       this.model.getModelData().getModel().renderToBuffer(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha, true);
    }
  }



  
  public void func_225597_a_(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
     this.spiderHead.field_78796_g = netHeadYaw * 0.017453292F;
     this.spiderHead.field_78795_f = headPitch * 0.017453292F;
    
     float f = 0.7853982F;
     this.spiderLeg1.field_78808_h = -0.7853982F;
     this.spiderLeg2.field_78808_h = 0.7853982F;
     this.spiderLeg3.field_78808_h = -0.58119464F;
     this.spiderLeg4.field_78808_h = 0.58119464F;
     this.spiderLeg5.field_78808_h = -0.58119464F;
     this.spiderLeg6.field_78808_h = 0.58119464F;
     this.spiderLeg7.field_78808_h = -0.7853982F;
     this.spiderLeg8.field_78808_h = 0.7853982F;
    
     float f1 = -0.0F;
    
     float f2 = 0.3926991F;
     this.spiderLeg1.field_78796_g = 0.7853982F;
     this.spiderLeg2.field_78796_g = -0.7853982F;
     this.spiderLeg3.field_78796_g = 0.3926991F;
     this.spiderLeg4.field_78796_g = -0.3926991F;
     this.spiderLeg5.field_78796_g = -0.3926991F;
     this.spiderLeg6.field_78796_g = 0.3926991F;
     this.spiderLeg7.field_78796_g = -0.7853982F;
     this.spiderLeg8.field_78796_g = 0.7853982F;
     float f3 = -(MathHelper.func_76134_b(limbSwing * 0.6662F * 2.0F + 0.0F) * 0.4F) * limbSwingAmount;
     float f4 = -(MathHelper.func_76134_b(limbSwing * 0.6662F * 2.0F + 3.1415927F) * 0.4F) * limbSwingAmount;
     float f5 = -(MathHelper.func_76134_b(limbSwing * 0.6662F * 2.0F + 1.5707964F) * 0.4F) * limbSwingAmount;
     float f6 = -(MathHelper.func_76134_b(limbSwing * 0.6662F * 2.0F + 4.712389F) * 0.4F) * limbSwingAmount;
     float f7 = Math.abs(MathHelper.func_76126_a(limbSwing * 0.6662F + 0.0F) * 0.4F) * limbSwingAmount;
     float f8 = Math.abs(MathHelper.func_76126_a(limbSwing * 0.6662F + 3.1415927F) * 0.4F) * limbSwingAmount;
     float f9 = Math.abs(MathHelper.func_76126_a(limbSwing * 0.6662F + 1.5707964F) * 0.4F) * limbSwingAmount;
     float f10 = Math.abs(MathHelper.func_76126_a(limbSwing * 0.6662F + 4.712389F) * 0.4F) * limbSwingAmount;
     this.spiderLeg1.field_78796_g += f3;
     this.spiderLeg2.field_78796_g += -f3;
     this.spiderLeg3.field_78796_g += f4;
     this.spiderLeg4.field_78796_g += -f4;
     this.spiderLeg5.field_78796_g += f5;
     this.spiderLeg6.field_78796_g += -f5;
     this.spiderLeg7.field_78796_g += f6;
     this.spiderLeg8.field_78796_g += -f6;
     this.spiderLeg1.field_78808_h += f7;
     this.spiderLeg2.field_78808_h += -f7;
     this.spiderLeg3.field_78808_h += f8;
     this.spiderLeg4.field_78808_h += -f8;
     this.spiderLeg5.field_78808_h += f9;
     this.spiderLeg6.field_78808_h += -f9;
     this.spiderLeg7.field_78808_h += f10;
     this.spiderLeg8.field_78808_h += -f10;
  }


  
  public void init() {
     this.model = ModelLoader.loadModel(new ResourceLocation("dalekmod", "models/entity/classic/spider.json"));
     if (this.model != null) {
       this.spiderHead = (ModelRenderer)this.model.getModelData().getModel().getPart("head");
       this.spiderNeck = (ModelRenderer)this.model.getModelData().getModel().getPart("body0");
       this.spiderBody = (ModelRenderer)this.model.getModelData().getModel().getPart("body1");
       this.spiderLeg1 = (ModelRenderer)this.model.getModelData().getModel().getPart("leg0");
       this.spiderLeg2 = (ModelRenderer)this.model.getModelData().getModel().getPart("leg1");
       this.spiderLeg3 = (ModelRenderer)this.model.getModelData().getModel().getPart("leg2");
       this.spiderLeg4 = (ModelRenderer)this.model.getModelData().getModel().getPart("leg3");
       this.spiderLeg5 = (ModelRenderer)this.model.getModelData().getModel().getPart("leg4");
       this.spiderLeg6 = (ModelRenderer)this.model.getModelData().getModel().getPart("leg5");
       this.spiderLeg7 = (ModelRenderer)this.model.getModelData().getModel().getPart("leg6");
       this.spiderLeg8 = (ModelRenderer)this.model.getModelData().getModel().getPart("leg7");
    } 
  }

  
  public Iterable<ModelRenderer> func_225601_a_() {
     return null;
  }

  
  public JSONModel getModel() {
     return this.model;
  }
}


