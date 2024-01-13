package com.swdteam.client.overlay;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.swdteam.client.tardis.data.ClientTardisCache;
import com.swdteam.common.init.DMFlightMode;
import com.swdteam.common.init.DMSoundEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.SimpleSound;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class OverlayFlightMode extends Overlay {
   public static ResourceLocation OVERLAY = new ResourceLocation("dalekmod", "textures/gui/overlay/flight_overlay.png");


  
  private int tick;


  
  public void render(MatrixStack stack) {
     super.render(stack);
     ClientPlayerEntity clientPlayerEntity = (Minecraft.func_71410_x()).field_71439_g;
    
     if (clientPlayerEntity != null && DMFlightMode.isInFlight((PlayerEntity)(Minecraft.func_71410_x()).field_71439_g)) {
       renderView();
      
       this.game.field_71466_p.func_238405_a_(stack, "=== Flight ===", 8.0F, 8.0F, -1);
       double fuel = Math.round(ClientTardisCache.getTardisData(DMFlightMode.getTardisID((Minecraft.func_71410_x()).field_71439_g.getUniqueID())).getFuel() * 100.0D) / 100.0D;
       if (fuel > 0.0D) {
         this.game.field_71466_p.func_238405_a_(stack, "Fuel: " + fuel, 8.0F, 20.0F, -1);
      } else {
         this.game.field_71466_p.func_238405_a_(stack, "Out of fuel", 8.0F, 20.0F, -65536);
      } 
       this.game.field_71466_p.func_238405_a_(stack, "X: " + (Math.round((this.game.field_71439_g.getPositionVec()).x * 100.0D) / 100.0D), 8.0F, 32.0F, -1);
       this.game.field_71466_p.func_238405_a_(stack, "Y: " + (Math.round((this.game.field_71439_g.getPositionVec()).y * 100.0D) / 100.0D), 8.0F, 44.0F, -1);
       this.game.field_71466_p.func_238405_a_(stack, "Z: " + (Math.round((this.game.field_71439_g.getPositionVec()).z * 100.0D) / 100.0D), 8.0F, 56.0F, -1);
      
       if ((Minecraft.func_71410_x()).field_71439_g.isOnGround()) {
         this.game.field_71466_p.func_243246_a(stack, (ITextComponent)(new StringTextComponent("Hold ")).func_230529_a_((ITextComponent)(new TranslationTextComponent(this.game.field_71474_y.field_228046_af_.getKey().func_197935_d())).func_240702_b_(" to exit flight")), 8.0F, 68.0F, -659185);
      }
    } 
  }
  
  protected void renderView() {
    float size;
     RenderSystem.pushMatrix();
     RenderSystem.translatef(0.0F, 0.0F, -200.0F);
     RenderSystem.enableBlend();
    
     RenderSystem.color4f(1.0F, 1.0F, 1.0F, 0.9F);
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
     ClientPlayerEntity clientPlayerEntity = this.game.field_71439_g;
    
     if (clientPlayerEntity != null && DMFlightMode.isInFlight((PlayerEntity)(Minecraft.func_71410_x()).field_71439_g) && 
       !clientPlayerEntity.isOnGround()) {
       if (Minecraft.func_71410_x().func_147113_T())
        return; 
       this.tick++;
      
       if (this.tick == 195) {
         this.game.func_147118_V().func_147682_a((ISound)SimpleSound.func_184371_a((SoundEvent)DMSoundEvents.TARDIS_FLIGHT_LOOP.get(), 1.0F));
         this.tick = 0;
      } 
    } 
  }
}


