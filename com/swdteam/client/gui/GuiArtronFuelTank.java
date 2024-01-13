package com.swdteam.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.swdteam.common.container.ArtronFuelTankContainer;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class GuiArtronFuelTank extends ContainerScreen<ArtronFuelTankContainer> {
   private static final ResourceLocation BG_LOCATION = new ResourceLocation("dalekmod:textures/gui/artron_fuel_tank.png");
  
  private ArtronFuelTankContainer container;
  
  public GuiArtronFuelTank(ArtronFuelTankContainer p_i51076_1_, PlayerInventory p_i51076_2_, ITextComponent p_i51076_3_) {
     super((Container)p_i51076_1_, p_i51076_2_, p_i51076_3_);
     p_i51076_1_.registerUpdateListener(this::containerChanged);
     this.field_238743_q_--;
     this.container = p_i51076_1_;
  }
  
  public void func_230430_a_(MatrixStack p_230430_1_, int p_230430_2_, int p_230430_3_, float p_230430_4_) {
     super.func_230430_a_(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);
     func_230459_a_(p_230430_1_, p_230430_2_, p_230430_3_);
  }

  
  protected void func_230450_a_(MatrixStack p_230450_1_, float p_230450_2_, int p_230450_3_, int p_230450_4_) {
     func_230446_a_(p_230450_1_);
     RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
     this.field_230706_i_.func_110434_K().func_110577_a(BG_LOCATION);
    
     func_238474_b_(p_230450_1_, this.field_230708_k_ / 2 - 88, this.field_230709_l_ / 2 - 84, 0, 0, 176, 172);
    
     double fuel = (int)(getContainer()).tile.charge;
    
     fuel = Math.floor(fuel * 100.0D) / 100.0D;
     if (fuel > 100.0D) {
       fuel = 100.0D;
    }
     if (fuel < 0.0D) {
       fuel = 0.0D;
    }
    
     func_238474_b_(p_230450_1_, this.field_230708_k_ / 2 - 80, this.field_230709_l_ / 2 - 69, 0, 223, (int)(160.0D * fuel / 100.0D), 16);
     String s = "Charging: " + fuel + "%";

    
     this.field_230712_o_.func_238421_b_(p_230450_1_, s, (this.field_230708_k_ / 2 - this.field_230712_o_.func_78256_a(s) / 2), (this.field_230709_l_ / 2 - 65), -14540254);
  }


  
  protected void func_230451_b_(MatrixStack p_230451_1_, int p_230451_2_, int p_230451_3_) {}


  
  private void containerChanged() {}

  
  public ArtronFuelTankContainer getContainer() {
     return this.container;
  }
}


