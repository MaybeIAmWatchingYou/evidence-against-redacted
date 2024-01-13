package com.swdteam.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.swdteam.common.init.DMKerblamStock;
import com.swdteam.common.kerblam.KerblamItem;
import com.swdteam.network.NetworkHandler;
import com.swdteam.network.packets.PacketRequestKerblamStore;
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


public class GuiKerblam
  extends Screen
{
  private ITextComponent itemName;
  private ITextComponent priceXP;
   private List<ITextComponent> tags = new ArrayList<>();

   private int selected = -1;
   private int hovered = -1;

   public static Map<ResourceLocation, Integer> BASKET = new HashMap<>();

   private static ResourceLocation KERBLAM_BG = new ResourceLocation("dalekmod", "textures/gui/kerblam_gui.png");
  private boolean isLoading = true;
   private List<ResourceLocation> items = new ArrayList<>();
  private Button addToBasketBtn;
  private Button basketBtn;
   private int page = 0; private Button leftBtn; private Button rightBtn;
   private String msg = "";


  public GuiKerblam() {
     super((ITextComponent)new StringTextComponent("Kerblam Store"));
     NetworkHandler.sendServerPacket(new PacketRequestKerblamStore());
  }

  protected static boolean canFit(ItemStack newItem) {
     int slots = 24;
     for (Map.Entry<ResourceLocation, Integer> basketItem : BASKET.entrySet()) {
       int amount = ((Integer)basketItem.getValue()).intValue();
       ItemStack item = ((KerblamItem)DMKerblamStock.getItems().get(basketItem.getKey())).getItemStack();
       int maxStack = item.func_77976_d();
       slots -= (int)Math.ceil((amount / maxStack));
    }
     return (slots > 0);
  }


  public void func_231160_c_() {
     super.func_231160_c_();

     func_230480_a_((Widget)(this.addToBasketBtn = new Button(this.field_230708_k_ / 2 - 40, this.field_230709_l_ / 2 + 73, 80, 20, (ITextComponent)new StringTextComponent("Add to Basket"), button -> {
            ResourceLocation rl = this.items.get(this.selected);
            KerblamItem item = (KerblamItem)DMKerblamStock.getItems().get(rl);
            if (!canFit(item.getItemStack())) {
              this.msg = TextFormatting.RED + "Maximum order size reached";
              return;
            }
            if (BASKET.containsKey(rl)) {
              BASKET.replace(rl, Integer.valueOf(((Integer)BASKET.get(rl)).intValue() + 1));
            } else {
              BASKET.put(rl, Integer.valueOf(1));
            }
            basketUpdate();
          })));
     func_230480_a_((Widget)(this.basketBtn = new Button(this.field_230708_k_ / 2 + 42, this.field_230709_l_ / 2 - 86, 80, 20, (ITextComponent)new StringTextComponent("Basket (0)"), button -> Minecraft.func_71410_x().func_147108_a(new GuiKerblamBasket(this)))));



     func_230480_a_((Widget)(this.leftBtn = new Button(this.field_230708_k_ / 2 - 116, this.field_230709_l_ / 2 + 73, 32, 20, (ITextComponent)new StringTextComponent("<"), button -> {
            int maxPages = (int)Math.ceil((this.items.size() / 66.0F)) - 1;

            if (this.page - 1 <= 0) {
              this.page--;
              if (this.page == maxPages) {
                this.rightBtn.field_230693_o_ = false;
              } else {
                this.rightBtn.field_230693_o_ = true;
              }
              if (this.page == 0) {
                this.leftBtn.field_230693_o_ = false;
              } else {
                this.leftBtn.field_230693_o_ = true;
              }
            }
          })));
     func_230480_a_((Widget)(this.rightBtn = new Button(this.field_230708_k_ / 2 + 86, this.field_230709_l_ / 2 + 73, 32, 20, (ITextComponent)new StringTextComponent(">"), button -> {
            int maxPages = (int)Math.ceil((this.items.size() / 66.0F)) - 1;

            if (this.page + 1 <= maxPages) {
              this.page++;
              if (this.page == maxPages) {
                this.rightBtn.field_230693_o_ = false;
              } else {
                this.rightBtn.field_230693_o_ = true;
              }
              if (this.page == 0) {
                this.leftBtn.field_230693_o_ = false;
              } else {
                this.leftBtn.field_230693_o_ = true;
              }
            }
          })));
     this.leftBtn.field_230693_o_ = false;
     this.rightBtn.field_230693_o_ = false;
     this.addToBasketBtn.field_230693_o_ = (this.selected != -1);
     if (!this.isLoading) {
       basketUpdate();
    }
  }

  private void basketUpdate() {
     int total = 0;
     for (Map.Entry<ResourceLocation, Integer> entry : BASKET.entrySet()) {
       int value = ((Integer)entry.getValue()).intValue();
       total += value;
    }
     this.basketBtn.field_230693_o_ = (total > 0);
     this.basketBtn.func_238482_a_((ITextComponent)new StringTextComponent("Basket (" + total + ")"));

     int maxPages = (int)Math.ceil((this.items.size() / 66.0F)) - 1;
     if (this.page < maxPages) {
       this.rightBtn.field_230693_o_ = true;
    } else {
       this.rightBtn.field_230693_o_ = false;
    }
     if (this.page > 0) {
       this.leftBtn.field_230693_o_ = true;
    } else {
       this.leftBtn.field_230693_o_ = false;
    }
  }




  public void func_230430_a_(MatrixStack matrixstack, int x, int y, float renderTick) {
     this.tags.clear();
     this.hovered = -1;
     this.field_230706_i_.func_110434_K().func_110577_a(KERBLAM_BG);
     func_238474_b_(matrixstack, this.field_230708_k_ / 2 - 128, this.field_230709_l_ / 2 - 99, 0, 0, 256, 198);

     if (this.isLoading) {
       func_238471_a_(matrixstack, this.field_230712_o_, "Loading", this.field_230708_k_ / 2, this.field_230709_l_ / 2 - 6, -1);
    } else {

       int totalCost = 0;
       this.field_230706_i_.func_110434_K().func_110577_a(KERBLAM_BG);

       for (Map.Entry<ResourceLocation, Integer> entry : BASKET.entrySet()) {
         ResourceLocation key = entry.getKey();
         int value = ((Integer)entry.getValue()).intValue();
         KerblamItem item = (KerblamItem)DMKerblamStock.getItems().get(key);
         totalCost += item.getPrice() * value;
      }

       this.field_230712_o_.func_238421_b_(matrixstack, TextFormatting.GREEN + "XP: " + TextFormatting.RESET + (Minecraft.func_71410_x()).field_71439_g.experienceTotal + " | " + TextFormatting.GREEN + "Cost XP: " + TextFormatting.RESET + totalCost, (this.field_230708_k_ / 2 - 120), (this.field_230709_l_ / 2 - 78), -1);

       int maxItems = (this.items.size() < 66) ? this.items.size() : 66;

       for (int i = 0; i < maxItems; i++) {
         int index = 66 * this.page + i;

         if (index >= this.items.size()) {
          break;
        }

         this.field_230706_i_.func_110434_K().func_110577_a(KERBLAM_BG);
         KerblamItem item = (KerblamItem)DMKerblamStock.getItems().get(this.items.get(index));

         int xPos = this.field_230708_k_ / 2 - 120 + i % 11 * 22;
         int yPos = this.field_230709_l_ / 2 - 62 + i / 11 * 22;
         int offset = 0;
         if (x >= xPos && x < xPos + 20 && y >= yPos && y < yPos + 20) {
           offset = 20;
           this.hovered = index;
           this.itemName = item.getItemStack().func_200301_q();
           this.priceXP = (ITextComponent)new StringTextComponent(TextFormatting.GOLD + "Price: " + TextFormatting.RESET + item.getPrice() + TextFormatting.GREEN + " XP");
           this.tags.add(this.itemName);
           this.tags.add(this.priceXP);
        }
         if (this.selected == index) {
           offset = 40;
        }
         func_238474_b_(matrixstack, xPos, yPos, 0 + offset, 198, 20, 20);

         Minecraft.func_71410_x().func_175599_af().func_175042_a(item.getItemStack(), xPos + 2, yPos + 2);
      }
    }

     super.func_230430_a_(matrixstack, x, y, renderTick);
     if (this.tags.size() > 0) {
       func_243308_b(matrixstack, this.tags, x, y);
    }
  }

  public void setupStoreData() {
     this.items.clear();
     for (Map.Entry<ResourceLocation, KerblamItem> entry : (Iterable<Map.Entry<ResourceLocation, KerblamItem>>)DMKerblamStock.getItems().entrySet()) {
       ResourceLocation key = entry.getKey();
       this.items.add(key);
    }
     this.isLoading = false;

     this.basketBtn.field_230693_o_ = true;
     this.selected = -1;
     basketUpdate();
     this.addToBasketBtn.field_230693_o_ = false;
  }


  public boolean func_231044_a_(double p_231044_1_, double p_231044_3_, int p_231044_5_) {
     if (p_231044_5_ == 0 && this.hovered != -1) {
       this.selected = this.hovered;
       this.addToBasketBtn.field_230693_o_ = true;
    }
     return super.func_231044_a_(p_231044_1_, p_231044_3_, p_231044_5_);
  }


  public boolean func_231177_au__() {
     return false;
  }
}


