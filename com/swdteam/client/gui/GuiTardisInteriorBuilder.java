package com.swdteam.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.swdteam.client.tardis.data.ClientTardisCache;
import com.swdteam.common.container.ArsContainer;
import com.swdteam.common.init.DMTardisRegistry;
import com.swdteam.common.tardis.TardisData;
import com.swdteam.common.tardis.TardisInterior;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;
import mezz.jei.api.gui.handlers.IGuiContainerHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.renderer.Rectangle2d;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistries;

@OnlyIn(Dist.CLIENT)
public class GuiTardisInteriorBuilder
  extends ContainerScreen<ArsContainer> {
   private static final ResourceLocation BG_LOCATION = new ResourceLocation("dalekmod:textures/gui/desktop_builder.png");
  
   private int percent = 0;
  
  private Button btnBuild;
   private String building = "";
  private int scrollOffset;
   private List<InteriorBuildPartResult> items = new ArrayList<>();
  public ArsContainer container;
  private boolean completed = false;
  
  public GuiTardisInteriorBuilder(ArsContainer p_i51076_1_, PlayerInventory p_i51076_2_, ITextComponent p_i51076_3_) {
     super((Container)p_i51076_1_, p_i51076_2_, p_i51076_3_);
     p_i51076_1_.registerUpdateListener(this::containerChanged);
     this.field_238743_q_--;
     this.container = p_i51076_1_;
    
     TardisData data = ClientTardisCache.getTardisData(this.container.blockPos);
     if (data != null) {
       TardisInterior interior = (TardisInterior)DMTardisRegistry.getTardisInteriors().get(new ResourceLocation(data.getCurrentlyBuilding()));
       for (TardisInterior.BuildingRecipe.BuildingRecipePart part : interior.getRecipe().getParts()) {
         this.items.add(new InteriorBuildPartResult(part.getItem(), part.getQuantity(), data.getRecipeTotal(part.getItem())));
      }
       this.building = interior.getInteriorName().getString();
       this.completed = (data.isRecipeComplete() || (Minecraft.func_71410_x()).field_71439_g.isCreative());
       this.percent = data.getRecipePercentage();
    } else {
       Minecraft.func_71410_x().func_147108_a(null);
    } 
    
     if (this.items.size() > 3) {
       this.scrollOffset = 3 - this.items.size();
    } else {
       this.scrollOffset = 0;
    } 
  }


  
  public void func_231160_c_() {
     super.func_231160_c_();
    
     func_230480_a_((Widget)(this.btnBuild = new Button(this.field_230708_k_ / 2 + 85, this.field_230709_l_ / 2 - 79, 57, 20, (ITextComponent)new StringTextComponent("Build"), button -> Minecraft.func_71410_x().func_147108_a(new GuiConfirmDesktopChange(this)))));


    
     func_230480_a_((Widget)new Button(this.field_230708_k_ / 2 + 85, this.field_230709_l_ / 2 - 57, 57, 20, (ITextComponent)new StringTextComponent("Cancel"), button -> Minecraft.func_71410_x().func_147108_a(new GuiConfirmDesktopCancel(this))));


    
     this.btnBuild.field_230693_o_ = this.completed;
  }

  
  public void updateList(String item, int number) {
     for (int i = 0; i < this.items.size(); i++) {
       InteriorBuildPartResult part = this.items.get(i);
       if (part.stack.getItem().getRegistryName().toString().equals(item)) {
         part.has = number;
        
        break;
      } 
    } 
     TardisData data = ClientTardisCache.getTardisData(this.container.blockPos);
     if (data != null) {
       this.completed = (data.isRecipeComplete() || (Minecraft.func_71410_x()).field_71439_g.isCreative());
       this.btnBuild.field_230693_o_ = this.completed;
       this.percent = data.getRecipePercentage();
       if (this.completed) {
         (Minecraft.func_71410_x()).field_71439_g.func_184185_a(SoundEvents.field_194228_if, 1.0F, 1.0F);
      }
    } 
  }
  
  public static class InteriorBuildPartResult
  {
    private ItemStack stack;
    private int need;
    private int has;
    
    public InteriorBuildPartResult(String item, int i, int j) {
       this.stack = new ItemStack((IItemProvider)ForgeRegistries.ITEMS.getValue(new ResourceLocation(item)));
       this.need = i;
       this.has = j;
    }
    
    public int getHas() {
       return this.has;
    }
    
    public int getNeed() {
       return this.need;
    }
    
    public ItemStack getStack() {
       return this.stack;
    }
  }
  
  public void func_230430_a_(MatrixStack p_230430_1_, int p_230430_2_, int p_230430_3_, float p_230430_4_) {
     super.func_230430_a_(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);
     func_230459_a_(p_230430_1_, p_230430_2_, p_230430_3_);
    
     if (this.items != null && this.items.size() > 0) {
      
       int max = 3;
       if (max > this.items.size()) {
         max = this.items.size();
      }
      
       for (int i = 0; i < max; i++) {
         InteriorBuildPartResult ip = this.items.get(this.items.size() - max + i + this.scrollOffset);
        
         Minecraft.func_71410_x().func_175599_af().func_175042_a(ip.getStack(), this.field_230708_k_ / 2 - 36, this.field_230709_l_ / 2 - 74 + i * 18);
        
         String itemName = ip.getStack().func_200301_q().getString();
         String total = ip.getHas() + "/" + ip.getNeed();
         int maxWidth = 85 - this.field_230712_o_.func_78256_a(total);
        
         if (this.field_230712_o_.func_78256_a(itemName) > maxWidth) {
           itemName = this.field_230712_o_.func_238412_a_(itemName, maxWidth) + "...";
        }
        
         this.field_230712_o_.func_238421_b_(p_230430_1_, itemName, (this.field_230708_k_ / 2 - 16), (this.field_230709_l_ / 2 - 69 + i * 18), -1);
        
         this.field_230712_o_.func_238421_b_(p_230430_1_, total, (this.field_230708_k_ / 2 + 76 - this.field_230712_o_.func_78256_a(total)), (this.field_230709_l_ / 2 - 69 + i * 18), -1);
      } 
    } 
    
     this.field_230712_o_.func_238421_b_(p_230430_1_, "Building: " + this.building, (this.field_230708_k_ / 2 - this.field_230712_o_.func_78256_a("Building: " + this.building) / 2), (this.field_230709_l_ / 2 - 11), -13421773);
     this.field_230712_o_.func_238421_b_(p_230430_1_, this.percent + "%", (this.field_230708_k_ / 2 - this.field_230712_o_.func_78256_a(this.percent + "%") / 2 + 114), (this.field_230709_l_ / 2 - 32), -13421773);
  }
  
  protected void func_230450_a_(MatrixStack p_230450_1_, float p_230450_2_, int p_230450_3_, int p_230450_4_) {
     func_230446_a_(p_230450_1_);
    
     this.field_230706_i_.func_110434_K().func_110577_a(BG_LOCATION);
     func_238474_b_(p_230450_1_, this.field_230708_k_ / 2 - 88, this.field_230709_l_ / 2 - 83 - 1, 0, 0, 236, 167);
    
     if (this.items != null && this.items.size() > 3) {
       if (this.scrollOffset != 0) {
         func_238466_a_(p_230450_1_, this.field_230708_k_ / 2 - 47, this.field_230709_l_ / 2 - 24, 8, 8, 236.0F, 8.0F, 8, 8, 256, 256);
      }
      
       if (this.scrollOffset != 3 - this.items.size()) {
         func_238466_a_(p_230450_1_, this.field_230708_k_ / 2 - 47, this.field_230709_l_ / 2 - 76, 8, 8, 236.0F, 0.0F, 8, 8, 256, 256);
      }
    } 
     Screen.func_238466_a_(p_230450_1_, this.field_230708_k_ / 2 + 86, this.field_230709_l_ / 2 - 19, (int)(54.0F * this.percent / 100.0F), 9, 244.0F, 0.0F, 2, 2, 256, 256);
  }


  
  protected void func_230451_b_(MatrixStack p_230451_1_, int p_230451_2_, int p_230451_3_) {}


  
  private void containerChanged() {}

  
  public ArsContainer getContainer() {
     return this.container;
  }

  
  public boolean func_231043_a_(double p_231043_1_, double p_231043_3_, double p_231043_5_) {
     if (this.items.size() > 3) {
       int max = this.items.size() - 3;
       if (p_231043_5_ > 0.0D && 
         this.scrollOffset - 1 >= -max) {
         this.scrollOffset--;
      }
      
       if (p_231043_5_ < 0.0D && 
         this.scrollOffset < 0) {
         this.scrollOffset++;
      }
    } 
    
     return super.func_231043_a_(p_231043_1_, p_231043_3_, p_231043_5_);
  }
  
  public static class Handler implements IGuiContainerHandler<GuiTardisInteriorBuilder> {
    @Nonnull
    public List<Rectangle2d> getGuiExtraAreas(GuiTardisInteriorBuilder containerScreen) {
       List<Rectangle2d> list = new ArrayList<>();
       list.add(createRectangle(176, 0, 60, 84));
       return list;
    }
    
     private int getScreenWidth() { return Minecraft.func_71410_x().func_228018_at_().func_198107_o(); } private int getScreenHeight() {
       return Minecraft.func_71410_x().func_228018_at_().func_198087_p();
    }
    private Rectangle2d createRectangle(int relativeLeft, int relativeTop, int width, int height) {
       return new Rectangle2d((getScreenWidth() - 176) / 2 + relativeLeft, (getScreenHeight() - 167) / 2 + relativeTop, width, height);
    }
  }
}


