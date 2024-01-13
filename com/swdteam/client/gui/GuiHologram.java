package com.swdteam.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.swdteam.common.init.DMTranslationKeys;
import com.swdteam.common.tileentity.HologramTileEntity;
import com.swdteam.network.NetworkHandler;
import com.swdteam.network.packets.PacketHologramData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.IGuiEventListener;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.gui.widget.button.CheckboxButton;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class GuiHologram
  extends Screen {
  private HologramTileEntity tile;
  protected TextFieldWidget commandEdit;
  private CheckboxButton btnLocked;

  public GuiHologram(HologramTileEntity tile) {
     super((ITextComponent)new StringTextComponent("Hologram GUI"));
     this.tile = tile;
  }
  private CheckboxButton btnSmallArms; private CheckboxButton btnSolid; private CheckboxButton btnShowBase;
  private CheckboxButton btnAnimated;

  protected void func_231160_c_() {
     this.commandEdit = new TextFieldWidget(this.field_230712_o_, this.field_230708_k_ / 2 - 80, this.field_230709_l_ / 2 - 45, 160, 20, (ITextComponent)new StringTextComponent(""));
     this.commandEdit.func_146203_f(32500);
     func_230481_d_((IGuiEventListener)this.commandEdit);

     func_230480_a_((Widget)new Button(this.field_230708_k_ / 2 + 37, this.field_230709_l_ / 2 + 36, 44, 20, (ITextComponent)new TranslationTextComponent("selectWorld.edit.save"), p_86679_ -> {
            NetworkHandler.sendServerPacket(new PacketHologramData(this.tile.getPos(), this.commandEdit.func_146179_b(), this.btnSmallArms.func_212942_a(), this.btnLocked.func_212942_a(), this.btnSolid.func_212942_a(), this.btnShowBase.func_212942_a(), this.btnAnimated.func_212942_a()));

            this.field_230706_i_.func_147108_a(null);
          }));
     if (this.tile != null && this.tile.getOwnerProfile() != null) {
       this.commandEdit.func_146180_a(this.tile.getOwnerProfile().getName());
    }

     this.btnSolid = new CheckboxButton(this.field_230708_k_ / 2 - 76, this.field_230709_l_ / 2 - 14, 64, 20, (ITextComponent)DMTranslationKeys.GUI_HOLO_SOLID, this.tile.isSolid);
     func_230480_a_((Widget)this.btnSolid);

     this.btnSmallArms = new CheckboxButton(this.field_230708_k_ / 2 - 76, this.field_230709_l_ / 2 + 8, 64, 20, (ITextComponent)DMTranslationKeys.GUI_HOLO_SLIM, this.tile.hasSmallArms());
     func_230480_a_((Widget)this.btnSmallArms);

     this.btnShowBase = new CheckboxButton(this.field_230708_k_ / 2 - 76, this.field_230709_l_ / 2 + 30, 64, 20, (ITextComponent)DMTranslationKeys.GUI_HOLO_BASE, this.tile.hasBase());
     func_230480_a_((Widget)this.btnShowBase);

     this.btnLocked = new CheckboxButton(this.field_230708_k_ / 2 + 8, this.field_230709_l_ / 2 - 14, 64, 20, (ITextComponent)DMTranslationKeys.GUI_HOLO_LOCKED, this.tile.isLocked());
     func_230480_a_((Widget)this.btnLocked);

     this.btnAnimated = new CheckboxButton(this.field_230708_k_ / 2 + 8, this.field_230709_l_ / 2 + 8, 64, 20, (ITextComponent)DMTranslationKeys.GUI_HOLO_ANIMATED, this.tile.isAnimated);
     func_230480_a_((Widget)this.btnAnimated);

     super.func_231160_c_();
  }




  public void func_230430_a_(MatrixStack stack, int mouseX, int mouseY, float renderTick) {
     func_230446_a_(stack);
     ResourceLocation rl = new ResourceLocation("dalekmod", "textures/gui/hologram.png");

     (Minecraft.func_71410_x()).field_71446_o.func_110577_a(rl);
     func_238474_b_(stack, this.field_230708_k_ / 2 - 88, this.field_230709_l_ / 2 - 63, 0, 0, 176, 127);


     this.commandEdit.func_230430_a_(stack, mouseX, mouseY, renderTick);

     this.field_230712_o_.func_238421_b_(stack, DMTranslationKeys.GUI_HOLO_USERNAME.getString(), (this.field_230708_k_ / 2 - 80), (this.field_230709_l_ / 2 - 56), 5592405);

     super.func_230430_a_(stack, mouseX, mouseY, renderTick);
  }


  public void func_231023_e_() {
     this.commandEdit.func_146178_a();
     super.func_231023_e_();
  }


  public void func_231152_a_(Minecraft p_97677_, int p_97678_, int p_97679_) {
     String s = this.commandEdit.func_146179_b();
     func_231158_b_(p_97677_, p_97678_, p_97679_);
     this.commandEdit.func_146180_a(s);
  }


  public boolean func_231177_au__() {
     return false;
  }
}


