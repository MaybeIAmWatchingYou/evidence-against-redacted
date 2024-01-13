package com.swdteam.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.swdteam.common.init.DMBlocks;
import com.swdteam.common.init.DMItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

public class GuiTardisPrompt extends Screen {
   private int screen = 0;
  private ItemStack[] controls;
  
  public GuiTardisPrompt() {
     super((ITextComponent)new StringTextComponent("TARDIS Prompt"));
     this.controls = new ItemStack[13];
     this.controls[0] = new ItemStack((IItemProvider)DMBlocks.FLIGHT_LEVER.get());
     this.controls[1] = new ItemStack((IItemProvider)DMBlocks.FAST_RETURN_LEVER.get());
     this.controls[2] = new ItemStack((IItemProvider)DMBlocks.DIMENSION_SELECTOR_PANEL.get());
     this.controls[3] = new ItemStack((IItemProvider)DMBlocks.COORD_PANEL.get());
     this.controls[4] = new ItemStack((IItemProvider)DMBlocks.CHAMELEON_PANEL.get());
     this.controls[5] = new ItemStack((IItemProvider)DMBlocks.WAYPOINT_PANEL.get());
     this.controls[6] = new ItemStack((IItemProvider)DMBlocks.ARS.get());
     this.controls[7] = new ItemStack((IItemProvider)DMBlocks.FAULT_LOCATOR.get());
     this.controls[8] = new ItemStack((IItemProvider)DMItems.ACCURACY_FLUID_LINK.get());
     this.controls[9] = new ItemStack((IItemProvider)DMItems.FLIGHT_FLUID_LINK.get());
     this.controls[10] = new ItemStack((IItemProvider)DMItems.FUEL_FLUID_LINK.get());
     this.controls[11] = new ItemStack((IItemProvider)DMItems.ARTRON.get());
     this.controls[12] = new ItemStack((IItemProvider)DMBlocks.ARTRON_FUEL_TANK.get());
  }

  
  private Button prevButton;
  private Button nextButton;
  
  public void func_231160_c_() {
     func_230480_a_((Widget)(this.prevButton = new Button(this.field_230708_k_ / 2 - 120, this.field_230709_l_ / 2 + 73, 78, 20, (ITextComponent)new StringTextComponent("Previous"), button -> {
            if (this.screen > 0) {
              this.screen--;
            }
            
            if (this.screen <= 0) {
              this.prevButton.field_230693_o_ = false;
            } else {
              this.nextButton.field_230693_o_ = true;
            } 
          })));
    
     func_230480_a_((Widget)new Button(this.field_230708_k_ / 2 - 40, this.field_230709_l_ / 2 + 73, 78, 20, (ITextComponent)new StringTextComponent("Close"), button -> Minecraft.func_71410_x().func_147108_a(null)));


    
     func_230480_a_((Widget)(this.nextButton = new Button(this.field_230708_k_ / 2 + 40, this.field_230709_l_ / 2 + 73, 78, 20, (ITextComponent)new StringTextComponent("Next"), button -> {
            this.screen++;
            
            if (this.screen > 0) {
              this.prevButton.field_230693_o_ = true;
            }
            
            if (this.screen >= 4) {
              this.nextButton.field_230693_o_ = false;
            }
          })));
     if (this.screen == 0) {
       this.prevButton.field_230693_o_ = false;
    }
    
     if (this.screen == 4) {
       this.nextButton.field_230693_o_ = false;
    }
    
     super.func_231160_c_();
  }



  
  public void func_230430_a_(MatrixStack p_230430_1_, int p_230430_2_, int p_230430_3_, float p_230430_4_) {
     (Minecraft.func_71410_x()).field_71446_o.func_110577_a(new ResourceLocation("dalekmod", "textures/gui/tardis_prompt.png"));
     func_238474_b_(p_230430_1_, this.field_230708_k_ / 2 - 128, this.field_230709_l_ / 2 - 99, 0, 0, 256, 199);
    
     String welcomeMessage = "Welcome to your TARDIS!";
     int sWidth = this.field_230712_o_.func_78256_a(welcomeMessage);
     this.field_230712_o_.func_238421_b_(p_230430_1_, welcomeMessage, (this.field_230708_k_ / 2 - sWidth / 2), (this.field_230709_l_ / 2 - 88), -14277082);
    
     if (this.screen == 0) {
       this.field_230712_o_.func_238421_b_(p_230430_1_, "This short guide will help you understand", (this.field_230708_k_ / 2 - 110), (this.field_230709_l_ / 2 - 70), -10066330);
       this.field_230712_o_.func_238421_b_(p_230430_1_, "the workings of your new TARDIS.", (this.field_230708_k_ / 2 - 110), (this.field_230709_l_ / 2 - 58), -10066330);

      
       this.field_230712_o_.func_238421_b_(p_230430_1_, "How do I fly the TARDIS?", (this.field_230708_k_ / 2 - 110), (this.field_230709_l_ / 2 - 40), -13421569);
       this.field_230712_o_.func_238421_b_(p_230430_1_, "All the basic controls needed to fly your", (this.field_230708_k_ / 2 - 110), (this.field_230709_l_ / 2 - 28), -10066330);
       this.field_230712_o_.func_238421_b_(p_230430_1_, "TARDIS can be found on the console.", (this.field_230708_k_ / 2 - 110), (this.field_230709_l_ / 2 - 16), -10066330);
      
       Minecraft.func_71410_x().func_175599_af().func_175042_a(this.controls[0], this.field_230708_k_ / 2 - 110, this.field_230709_l_ / 2 - 4);
       Minecraft.func_71410_x().func_175599_af().func_175042_a(this.controls[1], this.field_230708_k_ / 2 - 110 + 20, this.field_230709_l_ / 2 - 4);
       Minecraft.func_71410_x().func_175599_af().func_175042_a(this.controls[2], this.field_230708_k_ / 2 - 110 + 40, this.field_230709_l_ / 2 - 4);
       Minecraft.func_71410_x().func_175599_af().func_175042_a(this.controls[3], this.field_230708_k_ / 2 - 110 + 60, this.field_230709_l_ / 2 - 4);
       Minecraft.func_71410_x().func_175599_af().func_175042_a(this.controls[5], this.field_230708_k_ / 2 - 110 + 80, this.field_230709_l_ / 2 - 4);
      
       Minecraft.func_71410_x().func_175599_af().func_175042_a(this.controls[4], this.field_230708_k_ / 2 - 110 + 160, this.field_230709_l_ / 2 + 13);
       this.field_230712_o_.func_238421_b_(p_230430_1_, "How do I disguise the TARDIS?", (this.field_230708_k_ / 2 - 110), (this.field_230709_l_ / 2 + 18), -13421569);
       this.field_230712_o_.func_238421_b_(p_230430_1_, "You can change your exterior using the", (this.field_230708_k_ / 2 - 110), (this.field_230709_l_ / 2 + 30), -10066330);
       this.field_230712_o_.func_238421_b_(p_230430_1_, "Chameleon Circuit panel. Disguises can be", (this.field_230708_k_ / 2 - 110), (this.field_230709_l_ / 2 + 42), -10066330);
       this.field_230712_o_.func_238421_b_(p_230430_1_, "bought with EXP or unlocked with cartridges.", (this.field_230708_k_ / 2 - 110), (this.field_230709_l_ / 2 + 54), -10066330);
     } else if (this.screen == 1) {
       this.field_230712_o_.func_238421_b_(p_230430_1_, "How do I reset the console room?", (this.field_230708_k_ / 2 - 110), (this.field_230709_l_ / 2 - 70), -13421569);
       this.field_230712_o_.func_238421_b_(p_230430_1_, "You can get a new interior using the ARS", (this.field_230708_k_ / 2 - 110), (this.field_230709_l_ / 2 - 58), -10066330);
       this.field_230712_o_.func_238421_b_(p_230430_1_, "Block. Materials are required for crafting", (this.field_230708_k_ / 2 - 110), (this.field_230709_l_ / 2 - 46), -10066330);
       this.field_230712_o_.func_238421_b_(p_230430_1_, "an interior. Once collected you can start", (this.field_230708_k_ / 2 - 110), (this.field_230709_l_ / 2 - 34), -10066330);
       this.field_230712_o_.func_238421_b_(p_230430_1_, "the build process.", (this.field_230708_k_ / 2 - 110), (this.field_230709_l_ / 2 - 22), -10066330);
      
       this.field_230712_o_.func_238421_b_(p_230430_1_, "ARS Block", (this.field_230708_k_ / 2 - 90), (this.field_230709_l_ / 2 - 4), -14509790);
       Minecraft.func_71410_x().func_175599_af().func_175042_a(this.controls[6], this.field_230708_k_ / 2 - 110, this.field_230709_l_ / 2 - 8);
      
       this.field_230712_o_.func_238421_b_(p_230430_1_, "How can I protect my TARDIS?", (this.field_230708_k_ / 2 - 110), (this.field_230709_l_ / 2 + 16), -13421569);
       this.field_230712_o_.func_238421_b_(p_230430_1_, "Anyone can enter your TARDIS and interact", (this.field_230708_k_ / 2 - 110), (this.field_230709_l_ / 2 + 28), -10066330);
       this.field_230712_o_.func_238421_b_(p_230430_1_, "with it. You should use your key to lock", (this.field_230708_k_ / 2 - 110), (this.field_230709_l_ / 2 + 40), -10066330);
       this.field_230712_o_.func_238421_b_(p_230430_1_, "your TARDIS when it's not in use.", (this.field_230708_k_ / 2 - 110), (this.field_230709_l_ / 2 + 52), -10066330);
     } else if (this.screen == 2) {
       this.field_230712_o_.func_238421_b_(p_230430_1_, "How do I fuel my TARDIS?", (this.field_230708_k_ / 2 - 110), (this.field_230709_l_ / 2 - 70), -13421569);
       this.field_230712_o_.func_238421_b_(p_230430_1_, "The TARDIS runs on Artron. This fuel can", (this.field_230708_k_ / 2 - 110), (this.field_230709_l_ / 2 - 58), -10066330);
       this.field_230712_o_.func_238421_b_(p_230430_1_, "be obtained using an Artron Fuel Tank.", (this.field_230708_k_ / 2 - 110), (this.field_230709_l_ / 2 - 46), -10066330);
       this.field_230712_o_.func_238421_b_(p_230430_1_, "The tank will slowly fill with energy. The", (this.field_230708_k_ / 2 - 110), (this.field_230709_l_ / 2 - 34), -10066330);
       this.field_230712_o_.func_238421_b_(p_230430_1_, "built up energy can be extracted at any", (this.field_230708_k_ / 2 - 110), (this.field_230709_l_ / 2 - 22), -10066330);
       this.field_230712_o_.func_238421_b_(p_230430_1_, "time using a glass bottle.", (this.field_230708_k_ / 2 - 110), (this.field_230709_l_ / 2 - 10), -10066330);
      
       this.field_230712_o_.func_238421_b_(p_230430_1_, "Artron Energy", (this.field_230708_k_ / 2 - 90), (this.field_230709_l_ / 2 + 5), -14509790);
       Minecraft.func_71410_x().func_175599_af().func_175042_a(this.controls[11], this.field_230708_k_ / 2 - 110, this.field_230709_l_ / 2);
      
       this.field_230712_o_.func_238421_b_(p_230430_1_, "Fuel Tank", (this.field_230708_k_ / 2 + 42), (this.field_230709_l_ / 2 + 5), -14509790);
       Minecraft.func_71410_x().func_175599_af().func_175042_a(this.controls[12], this.field_230708_k_ / 2 + 20, this.field_230709_l_ / 2);
      
       this.field_230712_o_.func_238421_b_(p_230430_1_, "Fuel Tank", (this.field_230708_k_ / 2 - 12), (this.field_230709_l_ / 2 + 53), -14509790);
       Minecraft.func_71410_x().func_175599_af().func_175042_a(this.controls[7], this.field_230708_k_ / 2 - 32, this.field_230709_l_ / 2 + 48);
      
       this.field_230712_o_.func_238421_b_(p_230430_1_, "Bottles of Artron Energy can be placed", (this.field_230708_k_ / 2 - 110), (this.field_230709_l_ / 2 + 22), -10066330);
       this.field_230712_o_.func_238421_b_(p_230430_1_, "into the left-hand fuel slot of the", (this.field_230708_k_ / 2 - 110), (this.field_230709_l_ / 2 + 34), -10066330);
       this.field_230712_o_.func_238421_b_(p_230430_1_, "Fault Locator.", (this.field_230708_k_ / 2 - 110), (this.field_230709_l_ / 2 + 46), -10066330);
     } else if (this.screen == 3) {
       this.field_230712_o_.func_238421_b_(p_230430_1_, "How does the Fluid Link system work?", (this.field_230708_k_ / 2 - 110), (this.field_230709_l_ / 2 - 70), -13421569);
       this.field_230712_o_.func_238421_b_(p_230430_1_, "The TARDIS uses fluid links to control it's", (this.field_230708_k_ / 2 - 110), (this.field_230709_l_ / 2 - 58), -10066330);
       this.field_230712_o_.func_238421_b_(p_230430_1_, "performance levels. It is vital to keep their", (this.field_230708_k_ / 2 - 110), (this.field_230709_l_ / 2 - 46), -10066330);
       this.field_230712_o_.func_238421_b_(p_230430_1_, "levels maintained to ensure reliability", (this.field_230708_k_ / 2 - 110), (this.field_230709_l_ / 2 - 34), -10066330);
       this.field_230712_o_.func_238421_b_(p_230430_1_, "The Fluid Link levels can be monitored and", (this.field_230708_k_ / 2 - 110), (this.field_230709_l_ / 2 - 22), -10066330);
       this.field_230712_o_.func_238421_b_(p_230430_1_, "maintained in the Fault Locator block.", (this.field_230708_k_ / 2 - 110), (this.field_230709_l_ / 2 - 10), -10066330);
      
       this.field_230712_o_.func_238421_b_(p_230430_1_, "Fuel (Optimizes fuel consumption)", (this.field_230708_k_ / 2 - 90), (this.field_230709_l_ / 2 + 5), -10066330);
       this.field_230712_o_.func_238421_b_(p_230430_1_, "Fuel", (this.field_230708_k_ / 2 - 90), (this.field_230709_l_ / 2 + 5), -14509790);
       Minecraft.func_71410_x().func_175599_af().func_175042_a(this.controls[10], this.field_230708_k_ / 2 - 110, this.field_230709_l_ / 2 + 1);
      
       this.field_230712_o_.func_238421_b_(p_230430_1_, "Accuracy (Aids landing calculations)", (this.field_230708_k_ / 2 - 90), (this.field_230709_l_ / 2 + 25), -10066330);
       this.field_230712_o_.func_238421_b_(p_230430_1_, "Accuracy", (this.field_230708_k_ / 2 - 90), (this.field_230709_l_ / 2 + 25), -14509790);
       Minecraft.func_71410_x().func_175599_af().func_175042_a(this.controls[8], this.field_230708_k_ / 2 - 110, this.field_230709_l_ / 2 + 21);
      
       this.field_230712_o_.func_238421_b_(p_230430_1_, "Flight (Decreases flight times)", (this.field_230708_k_ / 2 - 90), (this.field_230709_l_ / 2 + 45), -10066330);
       this.field_230712_o_.func_238421_b_(p_230430_1_, "Flight", (this.field_230708_k_ / 2 - 90), (this.field_230709_l_ / 2 + 45), -14509790);
       Minecraft.func_71410_x().func_175599_af().func_175042_a(this.controls[9], this.field_230708_k_ / 2 - 110, this.field_230709_l_ / 2 + 41);
    }
     else if (this.screen == 4) {
       this.field_230712_o_.func_238421_b_(p_230430_1_, "How do I assign the door location?", (this.field_230708_k_ / 2 - 110), (this.field_230709_l_ / 2 - 70), -13421569);
       this.field_230712_o_.func_238421_b_(p_230430_1_, "The TARDIS door position can be changed", (this.field_230708_k_ / 2 - 110), (this.field_230709_l_ / 2 - 58), -10066330);
       this.field_230712_o_.func_238421_b_(p_230430_1_, "by using the /tardis-door command.", (this.field_230708_k_ / 2 - 110), (this.field_230709_l_ / 2 - 46), -10066330);
      
       this.field_230712_o_.func_238421_b_(p_230430_1_, "How do I give away my TARDIS?", (this.field_230708_k_ / 2 - 110), (this.field_230709_l_ / 2 - 32), -13421569);
       this.field_230712_o_.func_238421_b_(p_230430_1_, "TARDIS ownership can be transferred", (this.field_230708_k_ / 2 - 110), (this.field_230709_l_ / 2 - 20), -10066330);
       this.field_230712_o_.func_238421_b_(p_230430_1_, "using the /tardis-transfer-owner command.", (this.field_230708_k_ / 2 - 110), (this.field_230709_l_ / 2 - 8), -10066330);
      
       this.field_230712_o_.func_238421_b_(p_230430_1_, "How do I use waypoints?", (this.field_230708_k_ / 2 - 110), (this.field_230709_l_ / 2 + 6), -13421569);
       this.field_230712_o_.func_238421_b_(p_230430_1_, "Waypoints can be created using Data", (this.field_230708_k_ / 2 - 110), (this.field_230709_l_ / 2 + 18), -10066330);
       this.field_230712_o_.func_238421_b_(p_230430_1_, "Modules. You can insert these into the", (this.field_230708_k_ / 2 - 110), (this.field_230709_l_ / 2 + 30), -10066330);
       this.field_230712_o_.func_238421_b_(p_230430_1_, "Data Writer block. A written cartridge can", (this.field_230708_k_ / 2 - 110), (this.field_230709_l_ / 2 + 42), -10066330);
       this.field_230712_o_.func_238421_b_(p_230430_1_, "then be inserted into the Waypoint Panel.", (this.field_230708_k_ / 2 - 110), (this.field_230709_l_ / 2 + 54), -10066330);
    } 
     super.func_230430_a_(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);
  }
}


