package com.swdteam.client.overlay;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.swdteam.common.block.IBlockTooltip;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.ITextComponent;



public class OverlayTooltip
  extends Overlay
{
  private RayTraceResult rayTraceBlock;
  
  public void render(MatrixStack stack) {
     ITextComponent tooltip = null;

    
     ClientWorld clientWorld = (Minecraft.func_71410_x()).field_71441_e;
    
     ClientPlayerEntity clientPlayerEntity = (Minecraft.func_71410_x()).field_71439_g;
    
     FontRenderer font = (Minecraft.func_71410_x()).field_71466_p;

    
     if (clientPlayerEntity != null) {
       this.rayTraceBlock = clientPlayerEntity.func_213324_a(3.5D, 0.0F, false);
      
       if (this.rayTraceBlock != null && this.rayTraceBlock.func_216346_c() == RayTraceResult.Type.BLOCK) {
         BlockRayTraceResult res = (BlockRayTraceResult)this.rayTraceBlock;
        
         BlockState state = clientWorld.getBlockState(res.func_216350_a());
        
         if (state.getBlock() instanceof IBlockTooltip) {
           tooltip = ((IBlockTooltip)state.getBlock()).getName(state, res.func_216350_a(), res.func_216347_e(), (PlayerEntity)(Minecraft.func_71410_x()).field_71439_g);
        }


        
         if (tooltip != null) {
           float stringWidth = font.func_78256_a(tooltip.getString());
           float x = (this.screenWidth / 2) - stringWidth / 2.0F;
           float y = (this.screenHeight / 2 - 14);



          
           Screen.func_238467_a_(stack, (int)(x - 3.0F), (int)(y - 3.0F), (int)(x + stringWidth + 3.0F), (int)(y + 10.0F), 1711276032);
          
           font.func_238421_b_(stack, tooltip.getString(), x, y, -1);
        } 
      } 
    } 
  }
}


