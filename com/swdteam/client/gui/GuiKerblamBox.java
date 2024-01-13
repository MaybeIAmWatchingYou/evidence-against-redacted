package com.swdteam.client.gui;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.swdteam.common.container.KerblamBoxContainer;
import com.swdteam.common.init.DMSoundEvents;
import net.minecraft.client.gui.IHasContainer;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class GuiKerblamBox extends ContainerScreen<KerblamBoxContainer> implements IHasContainer<KerblamBoxContainer> {
   private static final ResourceLocation CONTAINER_BACKGROUND = new ResourceLocation("textures/gui/container/generic_54.png");

  public GuiKerblamBox(KerblamBoxContainer p_i51076_1_, PlayerInventory p_i51076_2_, ITextComponent p_i51076_3_) {
     super((Container)p_i51076_1_, p_i51076_2_, p_i51076_3_);
     this.field_230711_n_ = false;
     this.field_147000_g = 132;
     this.field_238745_s_ = this.field_147000_g - 94;
  }

  public void func_230430_a_(MatrixStack p_230430_1_, int p_230430_2_, int p_230430_3_, float p_230430_4_) {
     func_230446_a_(p_230430_1_);
     super.func_230430_a_(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);
     func_230459_a_(p_230430_1_, p_230430_2_, p_230430_3_);
  }


  protected void func_230450_a_(MatrixStack p_230450_1_, float p_230450_2_, int p_230450_3_, int p_230450_4_) {
     RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
     this.field_230706_i_.func_110434_K().func_110577_a(CONTAINER_BACKGROUND);
     int i = (this.field_230708_k_ - this.field_146999_f) / 2;
     int j = (this.field_230709_l_ - this.field_147000_g) / 2;
     func_238474_b_(p_230450_1_, i, j, 0, 0, this.field_146999_f, 35);
     func_238474_b_(p_230450_1_, i, j + 18 + 17, 0, 126, this.field_146999_f, 96);
  }


  public void func_231175_as__() {
     this.field_213127_e.field_70458_d.func_213823_a((SoundEvent)DMSoundEvents.BLOCK_CARDBOARD_CLOSE.get(), SoundCategory.BLOCKS, 1.0F, 1.0F);
     super.func_231175_as__();
  }
}


