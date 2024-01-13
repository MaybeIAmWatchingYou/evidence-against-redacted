package com.swdteam.client.gui;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.swdteam.common.container.RoundelContainer;
import com.swdteam.common.crafting.RoundelBuilderRecipe;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.SimpleSound;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class GuiRoundelBuilder extends ContainerScreen<RoundelContainer> {
   private static final ResourceLocation BG_LOCATION = new ResourceLocation("dalekmod:textures/gui/roundel_builder.png");
  private float scrollOffs;
  private boolean scrolling;
  private int startIndex;
  private boolean displayRecipes;
  
  public GuiRoundelBuilder(RoundelContainer p_i51076_1_, PlayerInventory p_i51076_2_, ITextComponent p_i51076_3_) {
     super((Container)p_i51076_1_, p_i51076_2_, p_i51076_3_);
     p_i51076_1_.registerUpdateListener(this::containerChanged);
     this.field_238743_q_--;
  }
  
  public void func_230430_a_(MatrixStack p_230430_1_, int p_230430_2_, int p_230430_3_, float p_230430_4_) {
     super.func_230430_a_(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);
     func_230459_a_(p_230430_1_, p_230430_2_, p_230430_3_);
  }

  
  protected void func_230450_a_(MatrixStack p_230450_1_, float p_230450_2_, int p_230450_3_, int p_230450_4_) {
     func_230446_a_(p_230450_1_);
     RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
     this.field_230706_i_.func_110434_K().func_110577_a(BG_LOCATION);
     int i = this.field_147003_i;
     int j = this.field_147009_r;
     func_238474_b_(p_230450_1_, i, j, 0, 0, this.field_146999_f, this.field_147000_g);
     int k = (int)(41.0F * this.scrollOffs);
     func_238474_b_(p_230450_1_, i + 119, j + 15 + k, 176 + (isScrollBarActive() ? 0 : 12), 0, 12, 15);
     int l = this.field_147003_i + 52;
     int i1 = this.field_147009_r + 14;
     int j1 = this.startIndex + 12;
     renderButtons(p_230450_1_, p_230450_3_, p_230450_4_, l, i1, j1);
     renderRecipes(l, i1, j1);
  }
  
  protected void func_230459_a_(MatrixStack p_230459_1_, int p_230459_2_, int p_230459_3_) {
     super.func_230459_a_(p_230459_1_, p_230459_2_, p_230459_3_);
     if (this.displayRecipes) {
       int i = this.field_147003_i + 52;
       int j = this.field_147009_r + 14;
       int k = this.startIndex + 12;
       List<RoundelBuilderRecipe> list = ((RoundelContainer)this.field_147002_h).getRecipes();
      
       for (int l = this.startIndex; l < k && l < ((RoundelContainer)this.field_147002_h).getNumRecipes(); l++) {
         int i1 = l - this.startIndex;
         int j1 = i + i1 % 4 * 16;
         int k1 = j + i1 / 4 * 18 + 2;
         if (p_230459_2_ >= j1 && p_230459_2_ < j1 + 16 && p_230459_3_ >= k1 && p_230459_3_ < k1 + 18) {
           func_230457_a_(p_230459_1_, ((RoundelBuilderRecipe)list.get(l)).func_77571_b(), p_230459_2_, p_230459_3_);
        }
      } 
    } 
  }

  
  private void renderButtons(MatrixStack p_238853_1_, int p_238853_2_, int p_238853_3_, int p_238853_4_, int p_238853_5_, int p_238853_6_) {
     for (int i = this.startIndex; i < p_238853_6_ && i < ((RoundelContainer)this.field_147002_h).getNumRecipes(); i++) {
       int j = i - this.startIndex;
       int k = p_238853_4_ + j % 4 * 16;
       int l = j / 4;
       int i1 = p_238853_5_ + l * 18 + 2;
       int j1 = this.field_147000_g;
       if (i == ((RoundelContainer)this.field_147002_h).getSelectedRecipeIndex()) {
         j1 += 18;
       } else if (p_238853_2_ >= k && p_238853_3_ >= i1 && p_238853_2_ < k + 16 && p_238853_3_ < i1 + 18) {
         j1 += 36;
      } 
      
       func_238474_b_(p_238853_1_, k, i1 - 1, 0, j1, 16, 18);
    } 
  }

  
  private void renderRecipes(int p_214142_1_, int p_214142_2_, int p_214142_3_) {
     List<RoundelBuilderRecipe> list = ((RoundelContainer)this.field_147002_h).getRecipes();
    
     for (int i = this.startIndex; i < p_214142_3_ && i < ((RoundelContainer)this.field_147002_h).getNumRecipes(); i++) {
       int j = i - this.startIndex;
       int k = p_214142_1_ + j % 4 * 16;
       int l = j / 4;
       int i1 = p_214142_2_ + l * 18 + 2;
       this.field_230706_i_.func_175599_af().func_180450_b(((RoundelBuilderRecipe)list.get(i)).func_77571_b(), k, i1);
    } 
  }

  
  public boolean func_231044_a_(double p_231044_1_, double p_231044_3_, int p_231044_5_) {
     this.scrolling = false;
     if (this.displayRecipes) {
       int i = this.field_147003_i + 52;
       int j = this.field_147009_r + 14;
       int k = this.startIndex + 12;
      
       for (int l = this.startIndex; l < k; l++) {
         int i1 = l - this.startIndex;
         double d0 = p_231044_1_ - (i + i1 % 4 * 16);
         double d1 = p_231044_3_ - (j + i1 / 4 * 18);
         if (d0 >= 0.0D && d1 >= 0.0D && d0 < 16.0D && d1 < 18.0D && ((RoundelContainer)this.field_147002_h).func_75140_a((PlayerEntity)this.field_230706_i_.field_71439_g, l)) {
           Minecraft.func_71410_x().func_147118_V().func_147682_a((ISound)SimpleSound.func_184371_a(SoundEvents.field_219720_mm, 1.0F));
           this.field_230706_i_.field_71442_b.func_78756_a(((RoundelContainer)this.field_147002_h).field_75152_c, l);
           return true;
        } 
      } 
      
       i = this.field_147003_i + 119;
       j = this.field_147009_r + 9;
       if (p_231044_1_ >= i && p_231044_1_ < (i + 12) && p_231044_3_ >= j && p_231044_3_ < (j + 54)) {
         this.scrolling = true;
      }
    } 
    
     return super.func_231044_a_(p_231044_1_, p_231044_3_, p_231044_5_);
  }
  
  public boolean func_231045_a_(double p_231045_1_, double p_231045_3_, int p_231045_5_, double p_231045_6_, double p_231045_8_) {
     if (this.scrolling && isScrollBarActive()) {
       int i = this.field_147009_r + 14;
       int j = i + 54;
       this.scrollOffs = ((float)p_231045_3_ - i - 7.5F) / ((j - i) - 15.0F);
       this.scrollOffs = MathHelper.func_76131_a(this.scrollOffs, 0.0F, 1.0F);
       this.startIndex = (int)((this.scrollOffs * getOffscreenRows()) + 0.5D) * 4;
       return true;
    } 
     return super.func_231045_a_(p_231045_1_, p_231045_3_, p_231045_5_, p_231045_6_, p_231045_8_);
  }

  
  public boolean func_231043_a_(double p_231043_1_, double p_231043_3_, double p_231043_5_) {
     if (isScrollBarActive()) {
       int i = getOffscreenRows();
       this.scrollOffs = (float)(this.scrollOffs - p_231043_5_ / i);
       this.scrollOffs = MathHelper.func_76131_a(this.scrollOffs, 0.0F, 1.0F);
       this.startIndex = (int)((this.scrollOffs * i) + 0.5D) * 4;
    } 
    
     return true;
  }
  
  private boolean isScrollBarActive() {
     return (this.displayRecipes && ((RoundelContainer)this.field_147002_h).getNumRecipes() > 12);
  }
  
  protected int getOffscreenRows() {
     return (((RoundelContainer)this.field_147002_h).getNumRecipes() + 4 - 1) / 4 - 3;
  }
  
  private void containerChanged() {
     this.displayRecipes = ((RoundelContainer)this.field_147002_h).hasInputItem();
     if (!this.displayRecipes) {
       this.scrollOffs = 0.0F;
       this.startIndex = 0;
    } 
  }
}


