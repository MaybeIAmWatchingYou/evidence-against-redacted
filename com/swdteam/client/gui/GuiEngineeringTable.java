package com.swdteam.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.swdteam.common.container.EngineeringTableContainer;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class GuiEngineeringTable extends ContainerScreen<EngineeringTableContainer> {
   private static final ResourceLocation BG_LOCATION = new ResourceLocation("dalekmod:textures/gui/engineering_table.png");

  private EngineeringTableContainer container;

  public GuiEngineeringTable(EngineeringTableContainer container, PlayerInventory inventory, ITextComponent textComponent) {
     super((Container)container, inventory, textComponent);
     container.registerUpdateListener(this::containerChanged);
     this.field_238743_q_--;
     this.container = container;
  }

  public void func_230430_a_(MatrixStack stack, int i1, int i2, float f) {
     super.func_230430_a_(stack, i1, i2, f);
     func_230459_a_(stack, i1, i2);
  }


  protected void func_230450_a_(MatrixStack stack, float f, int i1, int i2) {
     func_230446_a_(stack);
     RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
     this.field_230706_i_.func_110434_K().func_110577_a(BG_LOCATION);

     func_238474_b_(stack, this.field_230708_k_ / 2 - 88, this.field_230709_l_ / 2 - 83, 0, 0, 176, 166);
  }



  protected void func_230451_b_(MatrixStack stack, int i1, int i2) {
     super.func_230451_b_(stack, i1, i2);
  }


  private void containerChanged() {}


  public EngineeringTableContainer getContainer() {
     return this.container;
  }
}


