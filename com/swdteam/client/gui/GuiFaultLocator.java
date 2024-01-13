package com.swdteam.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.swdteam.client.tardis.data.ClientTardisCache;
import com.swdteam.common.container.FaultLocatorContainer;
import com.swdteam.common.tardis.TardisData;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;
import mezz.jei.api.gui.handlers.IGuiContainerHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.renderer.Rectangle2d;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class GuiFaultLocator
  extends ContainerScreen<FaultLocatorContainer>
{
   private static final ResourceLocation BG_LOCATION = new ResourceLocation("dalekmod:textures/gui/fault_locator.png");
  
  private FaultLocatorContainer container;
  
  public GuiFaultLocator(FaultLocatorContainer p_i51076_1_, PlayerInventory p_i51076_2_, ITextComponent p_i51076_3_) {
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
    
     func_238474_b_(p_230450_1_, this.field_230708_k_ / 2 - 128 + 1, this.field_230709_l_ / 2 - 89, 0, 0, 256, 172);
    
     TardisData data = ClientTardisCache.getTardisData(this.container.tile.getPos());
     if (data != null) {
       int fuel = (int)(100.0D - data.getFuel());
       if (fuel > 100) {
         fuel = 100;
      }
       if (fuel < 0) {
         fuel = 0;
      }
      
       Screen.func_238466_a_(p_230450_1_, this.field_230708_k_ / 2 - 116, this.field_230709_l_ / 2 - 79, 23, fuel, 0.0F, 240.0F, 0, 0, 256, 256);
      
       int jj = (int)(54.0F * data.getFluidLinkFlightTime() / 100.0F);
       Screen.func_238466_a_(p_230450_1_, this.field_230708_k_ / 2 - 28, this.field_230709_l_ / 2 - 71, jj, 16, ((jj < 35) ? ((jj < 15) ? 32 : 16) : false), 172.0F, 48, 16, 256, 256);
       jj = (int)(54.0F * data.getFluidLinkFuelConsumption() / 100.0F);
       Screen.func_238466_a_(p_230450_1_, this.field_230708_k_ / 2 - 28, this.field_230709_l_ / 2 - 51, jj, 16, ((jj < 35) ? ((jj < 15) ? 32 : 16) : false), 188.0F, 48, 16, 256, 256);
       jj = (int)(54.0F * data.getFluidLinkAccuracy() / 100.0F);
       Screen.func_238466_a_(p_230450_1_, this.field_230708_k_ / 2 - 28, this.field_230709_l_ / 2 - 31, jj, 16, ((jj < 35) ? ((jj < 15) ? 32 : 16) : false), 204.0F, 48, 16, 256, 256);
      
       this.field_230712_o_.func_238421_b_(p_230450_1_, "Fuel", (this.field_230708_k_ / 2 - 73), (this.field_230709_l_ / 2 - 81), -12303292);
       this.field_230712_o_.func_238421_b_(p_230450_1_, "Fluid Link", (this.field_230708_k_ / 2 + 20), (this.field_230709_l_ / 2 - 83), -12303292);
      
       this.field_230712_o_.func_238421_b_(p_230450_1_, "Flight", (this.field_230708_k_ / 2 - 25), (this.field_230709_l_ / 2 - 67), -1);
       this.field_230712_o_.func_238421_b_(p_230450_1_, "Fuel", (this.field_230708_k_ / 2 - 25), (this.field_230709_l_ / 2 - 47), -1);
       this.field_230712_o_.func_238421_b_(p_230450_1_, "Accuracy", (this.field_230708_k_ / 2 - 25), (this.field_230709_l_ / 2 - 27), -1);
      
       this.field_230712_o_.func_238421_b_(p_230450_1_, "???", (this.field_230708_k_ / 2 + 49), (this.field_230709_l_ / 2 - 67), -11447983);
       this.field_230712_o_.func_238421_b_(p_230450_1_, "???", (this.field_230708_k_ / 2 + 49), (this.field_230709_l_ / 2 - 47), -11447983);
       this.field_230712_o_.func_238421_b_(p_230450_1_, "???", (this.field_230708_k_ / 2 + 49), (this.field_230709_l_ / 2 - 27), -11447983);

      
       int col = -11207596;
       if (data.getFuel() < 20.0D) {
         col = -1881526;
      }
       String s = (Math.floor(data.getFuel() * 100.0D) / 100.0D) + "%";
       this.field_230712_o_.func_238421_b_(p_230450_1_, s, (this.field_230708_k_ / 2 - this.field_230712_o_.func_78256_a(s) / 2 + -62), (this.field_230709_l_ / 2 - 51), col);
    } 
  }


  
  protected void func_230451_b_(MatrixStack p_230451_1_, int p_230451_2_, int p_230451_3_) {}


  
  private void containerChanged() {}

  
  public FaultLocatorContainer getContainer() {
     return this.container;
  }
  
  public static class Handler implements IGuiContainerHandler<GuiFaultLocator> {
    @Nonnull
    public List<Rectangle2d> getGuiExtraAreas(GuiFaultLocator containerScreen) {
       List<Rectangle2d> list = new ArrayList<>();
       list.add(createRectangle(0, 0, 39, 120));
       list.add(createRectangle(215, 0, 41, 85));
       return list;
    }
    
     private int getScreenWidth() { return Minecraft.func_71410_x().func_228018_at_().func_198107_o(); } private int getScreenHeight() {
       return Minecraft.func_71410_x().func_228018_at_().func_198087_p();
    }
    private Rectangle2d createRectangle(int relativeLeft, int relativeTop, int width, int height) {
       return new Rectangle2d((getScreenWidth() - 256) / 2 + relativeLeft, (getScreenHeight() - 172) / 2 + relativeTop, width, height);
    }
  }
}


