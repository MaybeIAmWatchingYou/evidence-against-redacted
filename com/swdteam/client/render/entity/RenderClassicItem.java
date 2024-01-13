package com.swdteam.client.render.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.swdteam.common.entity.ClassicItemEntity;
import java.util.List;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.entity.Entity;
import net.minecraft.item.BlockItem;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Matrix3f;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3f;

public class RenderClassicItem
  extends EntityRenderer<ClassicItemEntity>
{
  public RenderClassicItem(EntityRendererManager renderManager) {
     super(renderManager);
  }





  
  public void render(ClassicItemEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
     if (entityIn.getItem() != null && entityIn.getItem().getItem() instanceof BlockItem) {
       Block block = ((BlockItem)entityIn.getItem().getItem()).func_179223_d();
      
       MatrixStack stack = matrixStackIn;
      
       stack.func_227860_a_();

      
       float f1 = MathHelper.func_76126_a((entityIn.func_174872_o() + partialTicks) / 10.0F + entityIn.field_70290_d) * 0.1F + 0.1F;
      
       matrixStackIn.func_227861_a_(0.0D, (f1 + 0.25F), 0.0D);
      
       matrixStackIn.func_227862_a_(0.3F, 0.3F, 0.3F);
       matrixStackIn.func_227861_a_(0.5D, 0.0D, -0.5D);
       float ff = 0.5F;
       float fff = -0.5F;
       matrixStackIn.func_227861_a_(fff, 0.0D, ff);
      
       float f3 = entityIn.func_234272_a_(partialTicks) * 1.0F;
       matrixStackIn.func_227863_a_(Vector3f.field_229181_d_.func_229193_c_(f3));
      
       matrixStackIn.func_227863_a_(Vector3f.field_229178_a_.func_229187_a_(180.0F));
      
       matrixStackIn.func_227861_a_(fff, -0.30000001192092896D, -ff);
      
       matrixStackIn.func_227863_a_(Vector3f.field_229183_f_.func_229187_a_(MathHelper.func_219799_g(partialTicks, entityIn.field_70127_C, entityIn.field_70125_A)));

      
       IBakedModel ta = Minecraft.func_71410_x().func_175602_ab().func_175023_a().func_178125_b(block.getDefaultState());
      
       TextureAtlasSprite sprite = getTexture(ta, block.getDefaultState(), Direction.SOUTH, entityIn.world.rand);

      
       IVertexBuilder ivertexbuilder = bufferIn.getBuffer(RenderType.func_228640_c_(AtlasTexture.field_110575_b));
       MatrixStack.Entry matrixstack$entry = matrixStackIn.func_227866_c_();
       Matrix4f matrix4f = matrixstack$entry.func_227870_a_();
       Matrix3f matrix3f = matrixstack$entry.func_227872_b_();
      
       float offset = 0.004F;
      
       drawVertex(matrix4f, matrix3f, ivertexbuilder, 0, 0, 0, 0, 1, 0, packedLightIn, sprite.func_94209_e() + offset, sprite.func_94206_g() + offset);
       drawVertex(matrix4f, matrix3f, ivertexbuilder, 0, 1, 0, 0, 1, 0, packedLightIn, sprite.func_94209_e() + offset, sprite.func_94210_h() - offset);
       drawVertex(matrix4f, matrix3f, ivertexbuilder, 1, 1, 0, 0, 1, 0, packedLightIn, sprite.func_94212_f() - offset, sprite.func_94210_h() - offset);
       drawVertex(matrix4f, matrix3f, ivertexbuilder, 1, 0, 0, 0, 1, 0, packedLightIn, sprite.func_94212_f() - offset, sprite.func_94206_g() + offset);
      
       drawVertex(matrix4f, matrix3f, ivertexbuilder, 1, 0, 0, 0, 1, 0, packedLightIn, sprite.func_94209_e() + offset, sprite.func_94206_g() + offset);
       drawVertex(matrix4f, matrix3f, ivertexbuilder, 1, 1, 0, 0, 1, 0, packedLightIn, sprite.func_94209_e() + offset, sprite.func_94210_h() - offset);
       drawVertex(matrix4f, matrix3f, ivertexbuilder, 1, 1, 1, 0, 1, 0, packedLightIn, sprite.func_94212_f() - offset, sprite.func_94210_h() - offset);
       drawVertex(matrix4f, matrix3f, ivertexbuilder, 1, 0, 1, 0, 1, 0, packedLightIn, sprite.func_94212_f() - offset, sprite.func_94206_g() + offset);
      
       drawVertex(matrix4f, matrix3f, ivertexbuilder, 0, 0, 0, 0, 1, 0, packedLightIn, sprite.func_94209_e() + offset, sprite.func_94206_g() + offset);
       drawVertex(matrix4f, matrix3f, ivertexbuilder, 0, 1, 0, 0, 1, 0, packedLightIn, sprite.func_94209_e() + offset, sprite.func_94210_h() - offset);
       drawVertex(matrix4f, matrix3f, ivertexbuilder, 0, 1, 1, 0, 1, 0, packedLightIn, sprite.func_94212_f() - offset, sprite.func_94210_h() - offset);
       drawVertex(matrix4f, matrix3f, ivertexbuilder, 0, 0, 1, 0, 1, 0, packedLightIn, sprite.func_94212_f() - offset, sprite.func_94206_g() + offset);
      
       drawVertex(matrix4f, matrix3f, ivertexbuilder, 0, 0, 1, 0, 1, 0, packedLightIn, sprite.func_94209_e() + offset, sprite.func_94206_g() + offset);
       drawVertex(matrix4f, matrix3f, ivertexbuilder, 0, 1, 1, 0, 1, 0, packedLightIn, sprite.func_94209_e() + offset, sprite.func_94210_h() - offset);
       drawVertex(matrix4f, matrix3f, ivertexbuilder, 1, 1, 1, 0, 1, 0, packedLightIn, sprite.func_94212_f() - offset, sprite.func_94210_h() - offset);
       drawVertex(matrix4f, matrix3f, ivertexbuilder, 1, 0, 1, 0, 1, 0, packedLightIn, sprite.func_94212_f() - offset, sprite.func_94206_g() + offset);
      
       drawVertex(matrix4f, matrix3f, ivertexbuilder, 0, 1, 0, 0, 1, 0, packedLightIn, sprite.func_94209_e() + offset, sprite.func_94206_g() + offset);
       drawVertex(matrix4f, matrix3f, ivertexbuilder, 0, 1, 1, 0, 1, 0, packedLightIn, sprite.func_94209_e() + offset, sprite.func_94210_h() - offset);
       drawVertex(matrix4f, matrix3f, ivertexbuilder, 1, 1, 1, 0, 1, 0, packedLightIn, sprite.func_94212_f() - offset, sprite.func_94210_h() - offset);
       drawVertex(matrix4f, matrix3f, ivertexbuilder, 1, 1, 0, 0, 1, 0, packedLightIn, sprite.func_94212_f() - offset, sprite.func_94206_g() + offset);
      
       drawVertex(matrix4f, matrix3f, ivertexbuilder, 0, 0, 0, 0, 1, 0, packedLightIn, sprite.func_94209_e() + offset, sprite.func_94206_g() + offset);
       drawVertex(matrix4f, matrix3f, ivertexbuilder, 0, 0, 1, 0, 1, 0, packedLightIn, sprite.func_94209_e() + offset, sprite.func_94210_h() - offset);
       drawVertex(matrix4f, matrix3f, ivertexbuilder, 1, 0, 1, 0, 1, 0, packedLightIn, sprite.func_94212_f() - offset, sprite.func_94210_h() - offset);
       drawVertex(matrix4f, matrix3f, ivertexbuilder, 1, 0, 0, 0, 1, 0, packedLightIn, sprite.func_94212_f() - offset, sprite.func_94206_g() + offset);
      
       stack.func_227865_b_();
    } 
  }



  
  public void drawVertex(Matrix4f matrix, Matrix3f normals, IVertexBuilder vertexBuilder, int offsetX, int offsetY, int offsetZ, int p_229039_9_, int p_229039_10_, int p_229039_11_, int packedLightIn, float u, float v) {
     vertexBuilder.func_227888_a_(matrix, offsetX, offsetY, offsetZ).func_225586_a_(255, 255, 255, 255).func_225583_a_(u, v).func_227891_b_(OverlayTexture.field_229196_a_).func_227886_a_(packedLightIn).func_227887_a_(normals, p_229039_9_, p_229039_11_, p_229039_10_).func_181675_d();
  }

  
  private TextureAtlasSprite getTexture(IBakedModel ibakedmodel, BlockState state, Direction facing, Random rand) {
     List<BakedQuad> quadList = ibakedmodel.func_200117_a(state, facing, rand);
     TextureAtlasSprite sprite = quadList.isEmpty() ? ibakedmodel.func_177554_e() : ((BakedQuad)quadList.get(0)).func_187508_a();
     return sprite;
  }

  
  public ResourceLocation getTextureLocation(ClassicItemEntity entity) {
     return null;
  }
}


