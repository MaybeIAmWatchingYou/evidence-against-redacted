package com.swdteam.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.swdteam.common.init.DMSonicRegistry;
import com.swdteam.common.tileentity.tardis.SonicInterfaceTileEntity;
import com.swdteam.network.NetworkHandler;
import com.swdteam.network.packets.PacketApplySonicSkin;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class GuiSonicWorkbench
  extends Screen
{
  public boolean isUnlocked = false;
   public static ResourceLocation BACKGROUND = new ResourceLocation("dalekmod", "textures/gui/sonic/sonic_workbench_skins.png");
  public ItemStack insertedSonic;
  public DMSonicRegistry.SonicData hovered;
  public DMSonicRegistry.SonicData selected;
  public Button applyBtn;
  public SonicInterfaceTileEntity panel;
   public String msg = "";

  public GuiSonicWorkbench(SonicInterfaceTileEntity panel) {
     super((ITextComponent)new StringTextComponent("Sonic Workbench"));
     this.panel = panel;
     this.selected = DMSonicRegistry.getSonic(panel.getScrewdriver());
     this.insertedSonic = this.panel.getScrewdriver();
  }


  protected void func_231160_c_() {
     super.func_231160_c_();

     func_230480_a_((Widget)(this.applyBtn = new Button(this.field_230708_k_ / 2 - 78, this.field_230709_l_ / 2 - -48, 64, 20, (ITextComponent)new StringTextComponent("Apply"), button -> NetworkHandler.sendServerPacket(new PacketApplySonicSkin(this.selected.getStack().getItem().getRegistryName(), this.panel.getPos())))));



     checkButtonState();
  }



  public void func_230430_a_(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
     this.hovered = null;
     func_230446_a_(matrixStack);

     this.field_230706_i_.field_71446_o.func_110577_a(BACKGROUND);
     func_238466_a_(matrixStack, this.field_230708_k_ / 2 - 128, this.field_230709_l_ / 2 - 82, 256, 164, 0.0F, 0.0F, 256, 256, 256, 256);

     for (int i = 0; i < DMSonicRegistry.SONICS.size(); i++) {

       DMSonicRegistry.SonicData data = DMSonicRegistry.SONICS.get(i);

       int xP = this.field_230708_k_ / 2 - 99 + i % 6 * 18;
       int yP = this.field_230709_l_ / 2 - 36 + i / 6 * 18;


       if (i < 24) {
         Minecraft.func_71410_x().func_175599_af().func_175042_a(data.getStack(), xP, yP);
      }

       if (mouseX >= xP && mouseX < xP + 18 && mouseY >= yP && mouseY < yP + 18) {
         func_238467_a_(matrixStack, xP, yP, xP + 16, yP + 16, 2013265919);
         this.hovered = data;
      }
    }

     if (this.selected != null) {
       this; func_238472_a_(matrixStack, this.field_230712_o_, this.selected.getStack().func_200301_q(), this.field_230708_k_ / 2 - -9, this.field_230709_l_ / 2 - 66, -1);

       if (this.isUnlocked) {
         this.field_230712_o_.func_238421_b_(matrixStack, "Owned", (this.field_230708_k_ / 2 - -65), (this.field_230709_l_ / 2 - 66), -14505438);
      } else {
         this.field_230712_o_.func_238421_b_(matrixStack, "XP:" + this.selected.getXpValue(), (this.field_230708_k_ / 2 + 64), (this.field_230709_l_ / 2 - 66), -14505438);
      }

       renderGuiItem(this.selected.getStack(), this.field_230708_k_ / 2 - -55, this.field_230709_l_ / 2 + 0);

       if (!this.isUnlocked) {
         matrixStack.func_227861_a_(0.0D, 0.0D, 500.0D);
         this; func_238471_a_(matrixStack, this.field_230712_o_, TextFormatting.RED + "Locked", this.field_230708_k_ / 2 + 62, this.field_230709_l_ / 2 + 4, -1);
         matrixStack.func_227861_a_(0.0D, 0.0D, -500.0D);
      }
    }
     this; func_238471_a_(matrixStack, this.field_230712_o_, this.msg, this.field_230708_k_ / 2 + 62, this.field_230709_l_ / 2 + 58, -48060);


     String totalXp = "You have: " + (Minecraft.func_71410_x()).field_71439_g.experienceTotal + " XP";
     this.field_230712_o_.func_238421_b_(matrixStack, totalXp, (this.field_230708_k_ / 2 - this.field_230712_o_.func_78256_a(totalXp) / 2 - 44), (this.field_230709_l_ / 2 - 48), -13421773);

     super.func_230430_a_(matrixStack, mouseX, mouseY, partialTicks);
  }


  protected void renderGuiItem(ItemStack p_191962_1_, int p_191962_2_, int p_191962_3_) {
     IBakedModel p_191962_4_ = Minecraft.func_71410_x().func_175599_af().func_184393_a(p_191962_1_, (World)null, (LivingEntity)null);
     RenderSystem.pushMatrix();
     (Minecraft.func_71410_x()).field_71446_o.func_110577_a(AtlasTexture.field_110575_b);
     (Minecraft.func_71410_x()).field_71446_o.func_229267_b_(AtlasTexture.field_110575_b).func_174937_a(false, false);
     RenderSystem.enableRescaleNormal();
     RenderSystem.enableAlphaTest();
     RenderSystem.defaultAlphaFunc();
     RenderSystem.enableBlend();


     RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);

     RenderSystem.translatef(p_191962_2_, p_191962_3_, 100.0F);
     RenderSystem.translatef(8.0F, 8.0F, 0.0F);
     RenderSystem.scalef(1.0F, -1.0F, 1.0F);
     RenderSystem.rotatef(45.0F, 1.0F, 0.0F, 0.0F);
     RenderSystem.rotatef((float)((Minecraft.func_71410_x()).field_71441_e.getGameTime() * 4L), 0.0F, 1.0F, 0.0F);
     RenderSystem.scalef(80.0F, 80.0F, 80.0F);
     MatrixStack matrixstack = new MatrixStack();
     IRenderTypeBuffer.Impl irendertypebuffer$impl = Minecraft.func_71410_x().func_228019_au_().func_228487_b_();
     boolean flag = !p_191962_4_.func_230044_c_();
     if (flag) {
       RenderHelper.func_227783_c_();
    }

     Minecraft.func_71410_x().func_175599_af().func_229111_a_(p_191962_1_, ItemCameraTransforms.TransformType.FIXED, false, matrixstack, (IRenderTypeBuffer)irendertypebuffer$impl, 15728880, OverlayTexture.func_229200_a_(0.0F, !this.isUnlocked), p_191962_4_);
     irendertypebuffer$impl.func_228461_a_();

     RenderSystem.enableDepthTest();
     if (flag) {
       RenderHelper.func_227784_d_();
    }

     RenderSystem.disableAlphaTest();
     RenderSystem.disableRescaleNormal();
     RenderSystem.popMatrix();
  }


  public boolean func_231177_au__() {
     return false;
  }


  public boolean func_231044_a_(double p_231044_1_, double p_231044_3_, int p_231044_5_) {
     if (this.hovered != null) {
       this.selected = this.hovered;
       checkButtonState();
    }
     return super.func_231044_a_(p_231044_1_, p_231044_3_, p_231044_5_);
  }

  public void checkButtonState() {
     if (this.panel.isUnlocked(this.selected.getStack())) {
       this.applyBtn.func_238482_a_((ITextComponent)new StringTextComponent("Apply"));
       this.isUnlocked = true;
    } else {
       this.applyBtn.func_238482_a_((ITextComponent)new StringTextComponent("Unlock"));
       this.isUnlocked = false;
    }
  }
}


