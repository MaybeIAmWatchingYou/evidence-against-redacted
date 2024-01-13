package com.swdteam.client.overlay;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.swdteam.common.init.DMDimensions;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;


public class OverlayInfdev
  extends Overlay
{
   public static ResourceLocation ICONS = new ResourceLocation("dalekmod", "textures/gui/icons.png");
   public static ResourceLocation ICONS_INFDEV = new ResourceLocation("dalekmod", "textures/gui/infdev_icons.png");

  @OnlyIn(Dist.CLIENT)
  public void render(MatrixStack stack) {
     ClientPlayerEntity clientPlayerEntity = (Minecraft.func_71410_x()).field_71439_g;
     if ((Minecraft.func_71410_x()).field_71439_g.getEntityWorld().getDimensionKey().equals(DMDimensions.INFDEV)) {
       (Minecraft.func_71410_x()).field_71466_p.func_238405_a_(stack, "Minecraft Infdev", 2.0F, 4.0F, -1);

       if (!clientPlayerEntity.isCreative() && !clientPlayerEntity.func_175149_v()) {

         Minecraft.func_71410_x().func_110434_K().func_110577_a(ICONS);
         for (int i = 0; i < 10; i++) {
           AbstractGui.func_238463_a_(stack, this.screenWidth / 2 - 91 + i * 8, this.screenHeight - 32, 0.0F, 0.0F, 9, 9, 64, 64);
           int health = (int)Math.floor((clientPlayerEntity.func_110143_aJ() / 2.0F));
           if (i < health) {
             AbstractGui.func_238463_a_(stack, this.screenWidth / 2 - 91 + i * 8, this.screenHeight - 32, 9.0F, 0.0F, 9, 9, 64, 64);
          }
        }

         float extraH = clientPlayerEntity.func_110143_aJ() / 2.0F % 1.0F;
         if (extraH >= 0.5D && extraH < 1.0F) {
           int k = (int)Math.floor((clientPlayerEntity.func_110143_aJ() / 2.0F));
           AbstractGui.func_238463_a_(stack, this.screenWidth / 2 - 91 + k * 8, this.screenHeight - 32, 18.0F, 0.0F, 9, 9, 64, 64);
        }

         int armourX = -10;
         Minecraft.func_71410_x().func_110434_K().func_110577_a(ICONS_INFDEV);
         for (int j = 0; j < 10; j++) {
           AbstractGui.func_238463_a_(stack, this.screenWidth / 2 - armourX + j * 8, this.screenHeight - 32, 0.0F, 0.0F, 9, 9, 64, 64);
           int armour = (int)Math.floor((clientPlayerEntity.func_70658_aO() / 2.0F));
           if (j < armour) {
             AbstractGui.func_238463_a_(stack, this.screenWidth / 2 - armourX + j * 8, this.screenHeight - 32, 9.0F, 0.0F, 9, 9, 64, 64);
          }
        }

         float extraA = clientPlayerEntity.func_70658_aO() / 2.0F % 1.0F;
         if (extraA >= 0.5D && extraA < 1.0F) {
           int k = (int)Math.floor((clientPlayerEntity.func_110143_aJ() / 2.0F));
           AbstractGui.func_238463_a_(stack, this.screenWidth / 2 - armourX + k * 8, this.screenHeight - 32, 18.0F, 0.0F, 9, 9, 64, 64);
        }

         (Minecraft.func_71410_x()).field_71466_p.func_238405_a_(stack, "Score: " + TextFormatting.YELLOW + clientPlayerEntity.func_71037_bA(), (this.screenWidth - (Minecraft.func_71410_x()).field_71466_p.func_78256_a("Score: " + clientPlayerEntity.func_71037_bA()) - 2), 2.0F, -1);
         float foodLevel = clientPlayerEntity.getFoodStats().getFoodLevel() * 5.0F;
         (Minecraft.func_71410_x()).field_71466_p.func_238405_a_(stack, "Hunger: " + TextFormatting.YELLOW + foodLevel + "%", (this.screenWidth - (Minecraft.func_71410_x()).field_71466_p.func_78256_a("Hunger: " + foodLevel + "%") - 2), 12.0F, -1);
      }
       super.render(stack);
    }
  }
}


