package com.swdteam.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.swdteam.common.init.DMKerblamStock;
import com.swdteam.common.kerblam.KerblamItem;
import com.swdteam.network.NetworkHandler;
import com.swdteam.network.packets.PacketKerblamPurchase;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;



public class GuiKerblamBasket
  extends Screen
{
  private ITextComponent itemName;
  private ITextComponent priceXP;
   private List<ITextComponent> tags = new ArrayList<>();
  
   private ResourceLocation selected = null;
   private ResourceLocation hovered = null;
  
   private static ResourceLocation KERBLAM_BG = new ResourceLocation("dalekmod", "textures/gui/kerblam_gui.png"); private Button removeFromBasketBtn;
  private Button backBtn;
   private String msg = "";
  private Button minusBtn;
  private Button addBtn;
   private int scrollOffset = 0; private Button buyBtn; private GuiKerblam prevScreen;
  
  public GuiKerblamBasket(GuiKerblam previousScreen) {
     super((ITextComponent)new StringTextComponent("Kerblam Store"));
     this.prevScreen = previousScreen;
  }

  
  public void func_231160_c_() {
     super.func_231160_c_();
    
     func_230480_a_((Widget)(this.removeFromBasketBtn = new Button(this.field_230708_k_ / 2 - 78, this.field_230709_l_ / 2 + 73, 120, 20, (ITextComponent)new StringTextComponent("Remove from Basket"), button -> {
            GuiKerblam.BASKET.remove(this.selected);
            
            this.selected = null;
            this.addBtn.field_230693_o_ = false;
            this.minusBtn.field_230693_o_ = false;
            this.removeFromBasketBtn.field_230693_o_ = false;
            this.scrollOffset = 0;
          })));
     func_230480_a_((Widget)(this.backBtn = new Button(this.field_230708_k_ / 2 + 42, this.field_230709_l_ / 2 - 86, 80, 20, (ITextComponent)new StringTextComponent("Back"), button -> Minecraft.func_71410_x().func_147108_a(this.prevScreen))));


    
     func_230480_a_((Widget)(this.minusBtn = new Button(this.field_230708_k_ / 2 - 122, this.field_230709_l_ / 2 + 73, 20, 20, (ITextComponent)new StringTextComponent("-"), button -> {
            if (this.selected != null) {
              if (((Integer)GuiKerblam.BASKET.get(this.selected)).intValue() - 1 > 0) {
                GuiKerblam.BASKET.replace(this.selected, Integer.valueOf(((Integer)GuiKerblam.BASKET.get(this.selected)).intValue() - 1));
              } else {
                GuiKerblam.BASKET.remove(this.selected);
                
                this.selected = null;
                this.minusBtn.field_230693_o_ = false;
                this.addBtn.field_230693_o_ = false;
                this.removeFromBasketBtn.field_230693_o_ = false;
                this.scrollOffset = 0;
              } 
            } else {
              this.minusBtn.field_230693_o_ = false;
            } 
          })));
     func_230480_a_((Widget)(this.addBtn = new Button(this.field_230708_k_ / 2 - 100, this.field_230709_l_ / 2 + 73, 20, 20, (ITextComponent)new StringTextComponent("+"), button -> {
            if (this.selected != null) {
              KerblamItem item = (KerblamItem)DMKerblamStock.getItems().get(this.selected);
              ItemStack newItem = item.getItemStack();
              newItem.func_190920_e(1);
              if (!GuiKerblam.canFit(newItem)) {
                this.msg = TextFormatting.RED + "Maximum order size reached";
                return;
              } 
              GuiKerblam.BASKET.replace(this.selected, Integer.valueOf(((Integer)GuiKerblam.BASKET.get(this.selected)).intValue() + 1));
            } else {
              this.addBtn.field_230693_o_ = false;
            } 
          })));
     func_230480_a_((Widget)(this.buyBtn = new Button(this.field_230708_k_ / 2 + 57, this.field_230709_l_ / 2 + 73, 64, 20, (ITextComponent)new StringTextComponent("Buy"), button -> {
            if (GuiKerblam.BASKET.size() > 0) {
              Map<String, Integer> map = new HashMap<>();
              
              for (Map.Entry<ResourceLocation, Integer> entry : GuiKerblam.BASKET.entrySet()) {
                map.put(((ResourceLocation)entry.getKey()).getNamespace() + ":" + ((ResourceLocation)entry.getKey()).getPath(), entry.getValue());
              }
              
              NetworkHandler.sendServerPacket(new PacketKerblamPurchase(map));
              Minecraft.func_71410_x().func_147108_a(null);
            } 
          })));
     this.minusBtn.field_230693_o_ = (this.selected != null);
     this.addBtn.field_230693_o_ = (this.selected != null);
     this.removeFromBasketBtn.field_230693_o_ = (this.selected != null);
  }

  
  public void func_230430_a_(MatrixStack matrixstack, int x, int y, float renderTick) {
     this.tags.clear();
     this.hovered = null;
     this.field_230706_i_.func_110434_K().func_110577_a(KERBLAM_BG);
     func_238474_b_(matrixstack, this.field_230708_k_ / 2 - 128, this.field_230709_l_ / 2 - 99, 0, 0, 256, 198);
    
     int totalCost = 0;
     int index = 0;
    
     if (DMKerblamStock.getItems().size() > 0) {
       for (Map.Entry<ResourceLocation, Integer> entry : GuiKerblam.BASKET.entrySet()) {
         this.field_230706_i_.func_110434_K().func_110577_a(KERBLAM_BG);
        
         ResourceLocation key = entry.getKey();
         int value = ((Integer)entry.getValue()).intValue();
         KerblamItem item = (KerblamItem)DMKerblamStock.getItems().get(key);
         totalCost += item.getPrice() * value;
        
         int ii = index + this.scrollOffset;
         if (ii >= 0 && ii < 6) {
           int xPos = this.field_230708_k_ / 2 - 120;
           int yPos = this.field_230709_l_ / 2 - 62 + (index + this.scrollOffset) * 22;
           int offset = 0;
          
           if (x >= xPos && x < xPos + 240 && y >= yPos && y < yPos + 20) {
             offset = 20;
             this.hovered = key;
             this.itemName = item.getItemStack().func_200301_q();
             this.priceXP = (ITextComponent)new StringTextComponent(TextFormatting.GOLD + "Price: " + TextFormatting.RESET + item.getPrice() + TextFormatting.GREEN + " XP");
             this.tags.add(this.itemName);
             this.tags.add(this.priceXP);
          } 
           if (this.selected == key) {
             offset = 40;
          }
          
           func_238474_b_(matrixstack, xPos, yPos, 0 + offset, 198, 18, 20);
           func_238474_b_(matrixstack, xPos + 222, yPos, 2 + offset, 198, 18, 20);
           Screen.func_238466_a_(matrixstack, xPos + 1, yPos, 222, 20, (1 + offset), 198.0F, 18, 20, 256, 256);
          
           this.field_230712_o_.func_243248_b(matrixstack, item.getItemStack().func_200301_q(), (xPos + 23), (yPos + 7), 5592405);
           this.field_230712_o_.func_243248_b(matrixstack, item.getItemStack().func_200301_q(), (xPos + 22), (yPos + 6), 16777215);
          
           String s = "Quantity: " + value;
           this.field_230712_o_.func_238421_b_(matrixstack, s, (xPos + 235 - this.field_230712_o_.func_78256_a(s)), (yPos + 7), 5592405);
           this.field_230712_o_.func_238421_b_(matrixstack, s, (xPos + 234 - this.field_230712_o_.func_78256_a(s)), (yPos + 6), 16777215);
          
           Minecraft.func_71410_x().func_175599_af().func_175042_a(item.getItemStack(), xPos + 2, yPos + 2);
        } 
         index++;
      } 
    }
     Minecraft mc = Minecraft.func_71410_x();
     if (mc != null) this.field_230712_o_.func_238421_b_(matrixstack, TextFormatting.GREEN + "XP: " + TextFormatting.RESET + mc.field_71439_g.experienceTotal + " | " + TextFormatting.GREEN + "Cost XP: " + TextFormatting.RESET + totalCost, (this.field_230708_k_ / 2 - 120), (this.field_230709_l_ / 2) - 77.5F, -1);
    
     super.func_230430_a_(matrixstack, x, y, renderTick);
     if (this.tags.size() > 0) {
       func_243308_b(matrixstack, this.tags, x, y);
    }
  }


  
  public boolean func_231044_a_(double p_231044_1_, double p_231044_3_, int p_231044_5_) {
     if (p_231044_5_ == 0 && this.hovered != null) {
       this.selected = this.hovered;
       this.removeFromBasketBtn.field_230693_o_ = true;
       this.minusBtn.field_230693_o_ = true;
       this.addBtn.field_230693_o_ = true;
    } 
     return super.func_231044_a_(p_231044_1_, p_231044_3_, p_231044_5_);
  }

  
  public boolean func_231043_a_(double p_231043_1_, double p_231043_3_, double p_231043_5_) {
     if (GuiKerblam.BASKET.size() > 6) {
       int max = GuiKerblam.BASKET.size() - 6;
       if (p_231043_5_ < 0.0D && 
         this.scrollOffset - 1 >= -max) {
         this.scrollOffset--;
      }
      
       if (p_231043_5_ > 0.0D && 
         this.scrollOffset < 0) {
         this.scrollOffset++;
      }
    } 
    
     return super.func_231043_a_(p_231043_1_, p_231043_3_, p_231043_5_);
  }

  
  public boolean func_231177_au__() {
     return false;
  }
}


