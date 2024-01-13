package com.swdteam.client.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;


public class ModelPlane
  extends Model
{
  public ModelRenderer Shape1;
  
  public ModelPlane(int w, int h) {
     super(RenderType::func_228640_c_);
     this.field_78090_t = w;
     this.field_78089_u = h;
    
     this.Shape1 = new ModelRenderer(this, 0, 0);
     this.Shape1.func_228300_a_(-8.0F, -8.0F, 0.0F, 16.0F, 16.0F, 0.001F);
     this.Shape1.func_78793_a(0.0F, 16.0F, 7.9F);
     this.Shape1.func_78787_b(w, h);
     this.Shape1.field_78809_i = true;
     setRotation(this.Shape1, 0.0F, 0.0F, 0.0F);
  }

  
  private void setRotation(ModelRenderer model, float x, float y, float z) {
     model.field_78795_f = x;
     model.field_78796_g = y;
     model.field_78808_h = z;
  }


  
  public void func_225598_a_(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
     this.Shape1.func_228308_a_(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
  }
}


