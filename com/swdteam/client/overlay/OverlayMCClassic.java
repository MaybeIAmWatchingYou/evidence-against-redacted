package com.swdteam.client.overlay;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.swdteam.common.init.DMDimensions;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class OverlayMCClassic
  extends Overlay
{
   public static ResourceLocation ICONS = new ResourceLocation("dalekmod", "textures/gui/icons.png");




  
  @OnlyIn(Dist.CLIENT)
  public void render(MatrixStack stack) {
     super.render(stack);
    
     if ((Minecraft.func_71410_x()).field_71439_g != null) {
       ClientPlayerEntity clientPlayerEntity = (Minecraft.func_71410_x()).field_71439_g;
      
       int screenWidth = Minecraft.func_71410_x().func_228018_at_().func_198107_o();
       int screenHeight = Minecraft.func_71410_x().func_228018_at_().func_198087_p();
      
       if (((PlayerEntity)clientPlayerEntity).world.getDimensionKey().equals(DMDimensions.CLASSIC)) {
         if (!clientPlayerEntity.func_175149_v()) {
           String s = "0.30";
           if (!clientPlayerEntity.isCreative()) {
             s = "0.26 SURVIVAL TEST";
          }
           (Minecraft.func_71410_x()).field_71466_p.func_238405_a_(stack, s, 2.0F, 2.0F, -1);
        } 
        
         if (!clientPlayerEntity.isCreative() && !clientPlayerEntity.func_175149_v()) {
          
           Minecraft.func_71410_x().func_110434_K().func_110577_a(ICONS);
           for (int i = 0; i < 10; i++) {
             AbstractGui.func_238463_a_(stack, screenWidth / 2 - 91 + i * 8, screenHeight - 32, 0.0F, 0.0F, 9, 9, 64, 64);
             int health = (int)Math.floor((clientPlayerEntity.func_110143_aJ() / 2.0F));
             if (i < health) {
               AbstractGui.func_238463_a_(stack, screenWidth / 2 - 91 + i * 8, screenHeight - 32, 9.0F, 0.0F, 9, 9, 64, 64);
            }
          } 
          
           float extraH = clientPlayerEntity.func_110143_aJ() / 2.0F % 1.0F;
           if (extraH >= 0.5D && extraH < 1.0F) {
             int j = (int)Math.floor((clientPlayerEntity.func_110143_aJ() / 2.0F));
             AbstractGui.func_238463_a_(stack, screenWidth / 2 - 91 + j * 8, screenHeight - 32, 18.0F, 0.0F, 9, 9, 64, 64);
          } 
          
           int arrows = ((PlayerEntity)clientPlayerEntity).field_71071_by.func_213901_a(Items.field_151032_g);
           arrows += ((PlayerEntity)clientPlayerEntity).field_71071_by.func_213901_a(Items.field_185166_h);
           arrows += ((PlayerEntity)clientPlayerEntity).field_71071_by.func_213901_a(Items.field_185167_i);
           (Minecraft.func_71410_x()).field_71466_p.func_238405_a_(stack, "Arrows: " + arrows, (screenWidth / 2 + 91 - (Minecraft.func_71410_x()).field_71466_p.func_78256_a("Arrows: " + arrows)), (screenHeight - 32), -1);
          
           (Minecraft.func_71410_x()).field_71466_p.func_238405_a_(stack, "Score: " + TextFormatting.YELLOW + clientPlayerEntity.func_71037_bA(), (screenWidth - (Minecraft.func_71410_x()).field_71466_p.func_78256_a("Score: " + clientPlayerEntity.func_71037_bA()) - 2), 2.0F, -1);
           float foodLevel = clientPlayerEntity.getFoodStats().getFoodLevel() * 5.0F;
           (Minecraft.func_71410_x()).field_71466_p.func_238405_a_(stack, "Hunger: " + TextFormatting.YELLOW + foodLevel + "%", (screenWidth - (Minecraft.func_71410_x()).field_71466_p.func_78256_a("Hunger: " + foodLevel + "%") - 2), 12.0F, -1);
        } 
      } 
    } 
  }

  
  public void tick() {
     super.tick();
  }
}


