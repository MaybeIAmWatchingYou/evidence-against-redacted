package com.swdteam.util;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.swdteam.common.init.DMItems;
import it.unimi.dsi.fastutil.floats.FloatConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.settings.PointOfView;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3f;
import org.lwjgl.opengl.GL11;



public class RenderUtil
{
  public static void renderLevel(WorldRenderer instance, MatrixStack matrices, float p_228426_2_, long p_228426_3_, boolean p_228426_5_, ActiveRenderInfo p_228426_6_, GameRenderer p_228426_7_, LightTexture p_228426_8_, Matrix4f p_228426_9_) {
     FloatConsumer translate = f -> p_228426_6_.func_216782_a(0.0D, 0.0D, f);
     boolean anaglyphMode = ((Minecraft.func_71410_x()).field_71474_y.func_243230_g() == PointOfView.FIRST_PERSON && (Minecraft.func_71410_x()).field_71439_g.getItemStackFromSlot(EquipmentSlotType.HEAD).getItem() == DMItems.THREE_DIMENSIONAL_GLASSES.get());
     if (anaglyphMode) {
       float eyeDistance = 0.0625F;
       float angle = 0.0F;
       GL11.glClear(256);
       GL11.glColorMask(true, false, false, true);
       translate.accept(eyeDistance / 2.0F);
       matrices.func_227863_a_(Vector3f.field_229181_d_.func_229187_a_(-angle / 2.0F));
       instance.func_228426_a_(matrices, p_228426_2_, p_228426_3_, p_228426_5_, p_228426_6_, p_228426_7_, p_228426_8_, p_228426_9_);
       matrices.func_227863_a_(Vector3f.field_229181_d_.func_229187_a_(angle / 2.0F));

       GL11.glClear(256);

       GL11.glColorMask(false, true, true, true);
       translate.accept(-eyeDistance);
       matrices.func_227863_a_(Vector3f.field_229181_d_.func_229187_a_(angle / 2.0F));
       instance.func_228426_a_(matrices, p_228426_2_, p_228426_3_, p_228426_5_, p_228426_6_, p_228426_7_, p_228426_8_, p_228426_9_);
       matrices.func_227863_a_(Vector3f.field_229181_d_.func_229187_a_(-angle / 2.0F));

       GL11.glColorMask(true, true, true, true);
       translate.accept(eyeDistance / 2.0F);
    } else {
       instance.func_228426_a_(matrices, p_228426_2_, p_228426_3_, p_228426_5_, p_228426_6_, p_228426_7_, p_228426_8_, p_228426_9_);
    }
  }
}


