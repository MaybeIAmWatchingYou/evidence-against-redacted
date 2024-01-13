package com.swdteam.client.overlay;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.swdteam.common.init.DMItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.settings.PointOfView;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.util.ResourceLocation;



public class OverlayDalekEyestalk
  extends Overlay
{
   public static ResourceLocation OVERLAY = new ResourceLocation("dalekmod", "textures/gui/overlay/overlay.png");






  
  public void render(MatrixStack stack) {
     super.render(stack);
     ClientPlayerEntity clientPlayerEntity = (Minecraft.func_71410_x()).field_71439_g;
     if (clientPlayerEntity != null && clientPlayerEntity.getItemStackFromSlot(EquipmentSlotType.HEAD) != null && clientPlayerEntity.getItemStackFromSlot(EquipmentSlotType.HEAD).getItem() == DMItems.EYESTALK.get() && 
       (Minecraft.func_71410_x()).field_71474_y.func_243230_g() == PointOfView.FIRST_PERSON) {
       renderEyestalk();
    }
  }

  
  protected void renderEyestalk() {
    float size;
     RenderSystem.pushMatrix();
     RenderSystem.translatef(0.0F, 0.0F, -200.0F);
     RenderSystem.color4f(1.0F, 1.0F, 1.0F, 0.75F);
     RenderSystem.enableBlend();
     Minecraft.func_71410_x().func_110434_K().func_110577_a(OVERLAY);


    
     if (this.screenWidth > this.screenHeight) { size = this.screenWidth; }
     else { size = this.screenHeight; }
    
     size++;
    
     RenderSystem.translatef((this.screenWidth / 2) - size / 2.0F, (this.screenHeight / 2) - size / 2.0F, 0.0F);
     RenderSystem.translatef(-0.5F, -0.5F, 0.0F);
    
     Tessellator tessellator = Tessellator.func_178181_a();
     BufferBuilder bufferbuilder = tessellator.func_178180_c();
     bufferbuilder.func_181668_a(7, DefaultVertexFormats.field_181707_g);
     bufferbuilder.func_225582_a_(0.0D, size, 90.0D).func_225583_a_(0.0F, 1.0F).func_181675_d();
     bufferbuilder.func_225582_a_(size, size, 90.0D).func_225583_a_(1.0F, 1.0F).func_181675_d();
     bufferbuilder.func_225582_a_(size, 0.0D, 90.0D).func_225583_a_(1.0F, 0.0F).func_181675_d();
     bufferbuilder.func_225582_a_(0.0D, 0.0D, 90.0D).func_225583_a_(0.0F, 0.0F).func_181675_d();
     tessellator.func_78381_a();
     RenderSystem.disableBlend();
     RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
     RenderSystem.popMatrix();
  }

  
  public void tick() {
     super.tick();
  }
}


