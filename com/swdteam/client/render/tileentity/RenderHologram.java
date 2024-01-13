package com.swdteam.client.render.tileentity;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.swdteam.common.tileentity.HologramTileEntity;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.client.renderer.model.ModelHelper;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;



public class RenderHologram
  extends TileEntityRenderer<HologramTileEntity>
{
  public static Entity ent;
   public static ResourceLocation texture = new ResourceLocation("dalekmod", "textures/tileentity/hologram.png");

  public RenderHologram(TileEntityRendererDispatcher dispatcher) {
     super(dispatcher);
  }



  public void render(HologramTileEntity tile, float partialTicks, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int combinedLightIn, int combinedOverlayIn) {
     RenderType type = getRenderType(tile.getOwnerProfile(), tile.isSolid);
     IVertexBuilder ivertexbuilder = iRenderTypeBuffer.getBuffer((type == null) ? RenderType.func_228637_a_(texture, true) : type);

     matrixStack.func_227860_a_();
     matrixStack.func_227861_a_(0.5D, 0.0D, 0.5D);

     if (!tile.isSolid) matrixStack.func_227862_a_(1.0F, 1.0F + (tile.getWorld()).rand.nextFloat() / 80.0F, 1.0F); 
     matrixStack.func_227862_a_(0.939F * tile.scale, 0.939F * tile.scale, 0.939F * tile.scale);
     matrixStack.func_227861_a_(0.0D, 1.5D, 0.0D);
     matrixStack.func_227863_a_(Vector3f.field_229179_b_.func_229187_a_(180.0F));
     matrixStack.func_227863_a_(Vector3f.field_229181_d_.func_229187_a_(tile.rotation));

     PlayerModel<LivingEntity> model = new PlayerModel(0.0F, tile.hasSmallArms());
     model.field_217114_e = false;

     setupAnim(model, (Minecraft.func_71410_x()).field_71439_g.field_70173_aa, tile.isAnimated);

     if (tile.hasBase()) matrixStack.func_227861_a_(0.0D, -0.15D, 0.0D);

     if (tile.isSolid) { model.func_225598_a_(matrixStack, ivertexbuilder, combinedLightIn, combinedOverlayIn, 1.0F, 1.0F, 1.0F, 1.0F); }
     else { model.func_225598_a_(matrixStack, ivertexbuilder, combinedLightIn, combinedOverlayIn, 0.5F, 0.5F, 1.0F, 0.4F); }

     matrixStack.func_227865_b_();
  }

  private static RenderType getRenderType(@Nullable GameProfile profile, boolean solid) {
     ResourceLocation resourcelocation = texture;

     if (profile != null) {
       Minecraft minecraft = Minecraft.func_71410_x();
       Map<MinecraftProfileTexture.Type, MinecraftProfileTexture> map = minecraft.func_152342_ad().func_152788_a(profile);

       resourcelocation = map.containsKey(MinecraftProfileTexture.Type.SKIN) ? minecraft.func_152342_ad().func_152792_a(map.get(MinecraftProfileTexture.Type.SKIN), MinecraftProfileTexture.Type.SKIN) : DefaultPlayerSkin.func_177334_a(PlayerEntity.func_146094_a(profile));
    }

     if (!solid) return RenderType.func_228652_i_(resourcelocation); 
     return RenderType.func_228644_e_(resourcelocation);
  }


  private static void setupAnim(PlayerModel<LivingEntity> model, float time, boolean isAnimated) {
     model.field_178724_i.field_78797_d = 2.0F;
     model.field_178723_h.field_78797_d = 2.0F;
     if (isAnimated) ModelHelper.func_239101_a_(model.field_178723_h, model.field_178724_i, time); 
     model.field_178734_a.func_217177_a(model.field_178724_i);
     model.field_178732_b.func_217177_a(model.field_178723_h);
  }
}


