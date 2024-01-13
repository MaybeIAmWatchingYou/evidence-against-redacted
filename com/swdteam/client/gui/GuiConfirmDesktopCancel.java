package com.swdteam.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.swdteam.client.tardis.data.ClientTardisCache;
import com.swdteam.common.tardis.TardisData;
import com.swdteam.network.NetworkHandler;
import com.swdteam.network.packets.PacketConfirmDesktopChange;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class GuiConfirmDesktopCancel
  extends Screen {
   public static ResourceLocation TEXTURE = new ResourceLocation("dalekmod", "textures/gui/confirm_dialogue.png");

  private GuiTardisInteriorBuilder screen;

  public GuiConfirmDesktopCancel(GuiTardisInteriorBuilder screen) {
     super((ITextComponent)new TranslationTextComponent("gui.confirm_desktop_switch"));
     this.screen = screen;
  }


  public void func_231160_c_() {
     super.func_231160_c_();

     func_230480_a_((Widget)new Button(this.field_230708_k_ / 2 + 2, this.field_230709_l_ / 2, 94, 20, (ITextComponent)new StringTextComponent("Confirm"), button -> {
            TardisData data = ClientTardisCache.getTardisData(this.screen.container.blockPos);

            if (data != null) {
              NetworkHandler.sendServerPacket(new PacketConfirmDesktopChange(data.getGlobalID(), true));
            }
            Minecraft.func_71410_x().func_147108_a(null);
          }));
     func_230480_a_((Widget)new Button(this.field_230708_k_ / 2 - 96, this.field_230709_l_ / 2, 94, 20, (ITextComponent)new StringTextComponent("Back"), button -> Minecraft.func_71410_x().func_147108_a((Screen)this.screen)));
  }




  public boolean func_231177_au__() {
     return false;
  }


  public void func_230430_a_(MatrixStack matrixstack, int p_render_1_, int p_render_2_, float p_render_3_) {
     func_230446_a_(matrixstack);

     this.field_230706_i_.func_110434_K().func_110577_a(TEXTURE);
     func_238474_b_(matrixstack, this.field_230708_k_ / 2 - 102, this.field_230709_l_ / 2 - 28 - 1, 0, 0, 204, 56);

     this.field_230712_o_.func_238421_b_(matrixstack, "Are you sure you want to stop", (this.field_230708_k_ / 2 - 94), (this.field_230709_l_ / 2 - 22), -12303292);
     this.field_230712_o_.func_238421_b_(matrixstack, "building? Progress will be lost", (this.field_230708_k_ / 2 - 94), (this.field_230709_l_ / 2 - 11), -12303292);

     super.func_230430_a_(matrixstack, p_render_1_, p_render_2_, p_render_3_);
  }
}


