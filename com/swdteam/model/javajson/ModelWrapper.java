package com.swdteam.model.javajson;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.swdteam.main.DMConfig;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.Model;




public class ModelWrapper
  extends Model
{
  public JSONModel model;
   public List<ModelRendererWrapper> renderList = new ArrayList<>();
   public Map<String, ModelRendererWrapper> partsList = new HashMap<>();
   public float modelScale = 1.0F;

  public ModelWrapper(int texWidth, int texHeight) {
     super(RenderType::func_228640_c_);
     this.field_78089_u = texHeight;
     this.field_78090_t = texWidth;
  }

  public void renderToBuffer(MatrixStack matrixStackIn, IRenderTypeBuffer iRenderTypeBuffer, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
     renderToBuffer(matrixStackIn, iRenderTypeBuffer, iRenderTypeBuffer.getBuffer(RenderType.func_228640_c_(this.model.getModelData().getTexture())), packedLightIn, packedOverlayIn, red, green, blue, alpha);
  }

  public void renderToBuffer(MatrixStack matrixStackIn, IRenderTypeBuffer iRenderTypeBuffer, IVertexBuilder defaultBuffer, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
     if (this.model != null && this.model.getModelData() != null) {
       boolean lightmap = (this.model.getModelData().getLightMap() != null && ((Boolean)DMConfig.CLIENT.renderLightmaps.get()).booleanValue() && this.model.render_lightmap);
       func_225598_a_(matrixStackIn, defaultBuffer, packedLightIn, packedOverlayIn, red, green, blue, alpha);
       if (lightmap) func_225598_a_(matrixStackIn, iRenderTypeBuffer.getBuffer(RenderType.func_228637_a_(this.model.getModelData().getLightMap(), true)), packedLightIn, packedOverlayIn, red, green, blue, alpha);



       if (this.model.shouldRenderSnowMap()) {
         matrixStackIn.func_227860_a_();
         func_225598_a_(matrixStackIn, iRenderTypeBuffer.getBuffer(RenderType.func_230167_a_(this.model.getModelData().getSnowMap(), true)), packedLightIn, packedOverlayIn, red, green, blue, alpha);
         matrixStackIn.func_227865_b_();
      }

       this.model.setRenderSnowMap(false);
    }
  }

  @Deprecated
  public void func_225598_a_(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
     matrixStackIn.func_227860_a_();

     matrixStackIn.func_227861_a_(0.0D, 1.5D, 0.0D);
     matrixStackIn.func_227862_a_(this.modelScale, this.modelScale, this.modelScale);
     matrixStackIn.func_227861_a_(0.0D, -1.5D, 0.0D);

     for (int i = 0; i < this.renderList.size(); i++) {
       ModelRendererWrapper r = this.renderList.get(i);
       r.func_228309_a_(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
    }

     matrixStackIn.func_227865_b_();
  }

  @Deprecated
  public void renderToBuffer(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha, boolean lightmap) {
     func_225598_a_(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
     if (lightmap && this.model.getModelData().getLightMap() != null && ((Boolean)DMConfig.CLIENT.renderLightmaps.get()).booleanValue())
       func_225598_a_(matrixStackIn, Minecraft.func_71410_x().func_228019_au_().func_228487_b_().getBuffer(RenderType.func_228637_a_(this.model.getModelData().getLightMap(), true)), packedLightIn, packedOverlayIn, red, green, blue, alpha);
  }

  public ModelRendererWrapper getPart(String s) {
     if (this.partsList.containsKey(s)) return this.partsList.get(s);
     return ModelLoader.NULL_PART;
  }
}


