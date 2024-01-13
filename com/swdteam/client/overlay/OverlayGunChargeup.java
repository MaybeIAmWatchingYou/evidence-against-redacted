package com.swdteam.client.overlay;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.swdteam.common.item.GunItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.settings.PointOfView;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;


public class OverlayGunChargeup
  extends Overlay
{
   public static ResourceLocation UI = new ResourceLocation("dalekmod", "textures/gui/overlay/gun_charge.png");



  public void render(MatrixStack stack) {
     ClientPlayerEntity clientPlayerEntity = (Minecraft.func_71410_x()).field_71439_g;

     if (clientPlayerEntity != null) {
       ItemStack item = clientPlayerEntity.func_184607_cu();
       if (item != null && item.getItem() instanceof GunItem && (Minecraft.func_71410_x()).field_71474_y.func_243230_g() == PointOfView.FIRST_PERSON) {
         GunItem laserGun = (GunItem)item.getItem();
         int timeUsed = item.getItem().func_77626_a(item) - clientPlayerEntity.func_184605_cv();
         float timeRequired = laserGun.requiredChargeTime;
         float percentCompleted = timeUsed / timeRequired;
         if (percentCompleted > 1.0F) percentCompleted = 1.0F;

         (Minecraft.func_71410_x()).field_71446_o.func_110577_a(UI);
         Screen.func_238463_a_(stack, this.screenWidth / 2 - 16, this.screenHeight / 2 + 8, 0.0F, 0.0F, 32, 8, 32, 32);
         Screen.func_238466_a_(stack, this.screenWidth / 2 - 15, this.screenHeight / 2 + 9, (int)(30.0F * percentCompleted), 6, 0.0F, 8.0F, 2, 2, 32, 32);
      }
    }
  }
}


