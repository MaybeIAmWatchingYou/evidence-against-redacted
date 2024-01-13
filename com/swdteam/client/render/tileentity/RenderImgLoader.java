package com.swdteam.client.render.tileentity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.swdteam.client.model.ModelPlane;
import com.swdteam.common.block.ImageLoaderBlock;
import com.swdteam.common.tileentity.DMTileEntityBase;
import com.swdteam.common.tileentity.ImageLoaderTileEntity;
import com.swdteam.util.TextureUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.state.Property;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.world.LightType;

public class RenderImgLoader extends TileEntityRenderer<DMTileEntityBase> {
  public RenderImgLoader(TileEntityRendererDispatcher dispatcher) {
     super(dispatcher);
     pl = new ModelPlane(16, 16);
  }

  public static ModelPlane pl;

  public void render(DMTileEntityBase dmTileEntityBase, float partialTicks, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int combinedLightIn, int combinedOverlayIn) {
     if (dmTileEntityBase instanceof ImageLoaderTileEntity) {
       ImageLoaderTileEntity te = (ImageLoaderTileEntity)dmTileEntityBase;
       ResourceLocation texture = (te.getImgName() != null && te.getImgName().length() > 0) ? TextureUtil.loadImageLoader(te.getImgName()) : TextureManager.field_194008_a;
       IVertexBuilder ivertexbuilder = iRenderTypeBuffer.getBuffer(RenderType.func_228638_b_(texture));
       matrixStack.func_227860_a_();
       matrixStack.func_227861_a_(0.5D, 1.5D, 0.5D);
       matrixStack.func_227863_a_(new Quaternion(0.0F, 0.0F, 180.0F, true));

       if (!te.isDoom()) {
         matrixStack.func_227863_a_(new Quaternion(0.0F, ((Direction)te.func_195044_w().get((Property)ImageLoaderBlock.FACING)).func_185119_l(), 0.0F, true));
      } else {
         matrixStack.func_227863_a_(new Quaternion(0.0F, 90.0F, 0.0F, true));
      }

       matrixStack.func_227861_a_(te.getOffsetX(), -te.getOffsetY(), (-0.99F + te.getOffsetZ()));

       if (te.isDoom()) {
         matrixStack.func_227861_a_(0.0D, 0.0D, 0.5D);
         float rot = (Minecraft.func_71410_x().func_175598_ae()).field_217783_c.func_216778_f() + 90.0F;
         matrixStack.func_227863_a_(Vector3f.field_229181_d_.func_229187_a_(rot));
         matrixStack.func_227861_a_(0.0D, 0.0D, -0.5D);
      }

       matrixStack.func_227861_a_(0.0D, 1.0D, 0.0D);
       matrixStack.func_227862_a_(te.getScaleX(), te.getScaleY(), 1.0F);
       matrixStack.func_227861_a_(0.0D, -1.0D, 0.0D);
       pl.func_225598_a_(matrixStack, ivertexbuilder, combinedLightIn, combinedOverlayIn, 1.0F, 1.0F, 1.0F, 1.0F);
       matrixStack.func_227865_b_();
    }
  }

  public final int getPackedLight(DMTileEntityBase entityIn, float partialTicks) {
     return LightTexture.func_228451_a_(getBlockLight(entityIn, partialTicks), entityIn.getWorld().func_226658_a_(LightType.SKY, entityIn.getPos()));
  }

  protected int getBlockLight(DMTileEntityBase entityIn, float partialTicks) {
     return entityIn.getWorld().func_226658_a_(LightType.BLOCK, new BlockPos((Vector3i)entityIn.getPos()));
  }
}


