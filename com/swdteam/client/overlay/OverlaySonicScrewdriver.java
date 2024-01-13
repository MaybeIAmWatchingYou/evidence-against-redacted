package com.swdteam.client.overlay;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.swdteam.client.init.BusClientEvents;
import com.swdteam.common.init.DMSonicRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;





public class OverlaySonicScrewdriver
  extends Overlay
{
   public static ResourceLocation SONIC_ICONS = new ResourceLocation("dalekmod", "textures/gui/sonic/sonic_ui.png");


  private RayTraceResult rayTraceBlock;



  public void render(MatrixStack stack) {
     ClientWorld clientWorld = (Minecraft.func_71410_x()).field_71441_e;
     ClientPlayerEntity clientPlayerEntity = (Minecraft.func_71410_x()).field_71439_g;

     FontRenderer font = (Minecraft.func_71410_x()).field_71466_p;

     if (clientPlayerEntity != null && (clientPlayerEntity.func_184614_ca().getItem() instanceof com.swdteam.common.item.sonics.SonicScrewdriverCustomizedItem || clientPlayerEntity.func_184592_cb().getItem() instanceof com.swdteam.common.item.sonics.SonicScrewdriverCustomizedItem)) {

       this.rayTraceBlock = clientPlayerEntity.func_213324_a(5.0D, 0.0F, false);

       if (this.rayTraceBlock != null && this.rayTraceBlock.func_216346_c() == RayTraceResult.Type.BLOCK) {
         BlockRayTraceResult res = (BlockRayTraceResult)this.rayTraceBlock;
         BlockState state = clientWorld.getBlockState(res.func_216350_a());

         if (DMSonicRegistry.SONIC_LOOKUP.containsKey(state.getBlock())) {
           DMSonicRegistry.ISonicInteraction sonic = (DMSonicRegistry.ISonicInteraction)DMSonicRegistry.SONIC_LOOKUP.get(state.getBlock());
           (Minecraft.func_71410_x()).field_71446_o.func_110577_a(SONIC_ICONS);
           Screen.func_238466_a_(stack, this.screenWidth / 2 - 20, this.screenHeight / 2 - 8, 16, 16, 16.0F, 0.0F, 16, 16, 256, 256);
           int percent = (int)(BusClientEvents.sonicTimer * 100.0F / sonic.scanTime());
           int progrss = (int)(0.16F * percent);
           Screen.func_238466_a_(stack, this.screenWidth / 2 - 20, this.screenHeight / 2 - 8, 16, 16 - progrss, 32.0F, 0.0F, 16, 16 - progrss, 256, 256);
        } else {
           BusClientEvents.sonicTimer = 0;
           BusClientEvents.sonicExecuted = false;
        }
      }
    }
  }
}


