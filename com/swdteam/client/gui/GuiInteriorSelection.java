package com.swdteam.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.swdteam.common.init.DMTardisRegistry;
import com.swdteam.common.tardis.TardisInterior;
import com.swdteam.network.NetworkHandler;
import com.swdteam.network.packets.PacketStartInteriorBuild;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;


public class GuiInteriorSelection
  extends Screen
{
  private TardisInterior interior;
  private List<TardisInterior> interiors;
  private int index;
  private BlockPos pos;
   public static ResourceLocation TEXTURE = new ResourceLocation("dalekmod", "textures/gui/interior_selection_gui.png");
  
  public GuiInteriorSelection(BlockPos pos) {
     super((ITextComponent)new TranslationTextComponent("gui.interior_selection"));
     this.pos = pos;
    
     this.interiors = new ArrayList<>();
     List<TardisInterior> til = DMTardisRegistry.getInteriorRegistryAsList();
    
     for (int i = 0; i < til.size(); i++) {
       TardisInterior ti = til.get(i);
       if (ti.getRecipe() != null && ti.getRecipe().getParts() != null && (ti.getRecipe().getParts()).length > 0) {
         this.interiors.add(ti);
      }
    } 
    
     if (this.interiors == null || (this.interiors != null && this.interiors.size() <= 0)) {
       Minecraft.func_71410_x().func_147108_a(null);
    }
    
     this.interior = this.interiors.get(this.index);
  }

  
  public void func_231160_c_() {
     super.func_231160_c_();
     this.field_230710_m_.clear();
    
     func_230480_a_((Widget)new Button(this.field_230708_k_ / 2 - 32, this.field_230709_l_ / 2 + 75, 64, 20, (ITextComponent)new StringTextComponent("Select"), button -> {
            NetworkHandler.sendServerPacket(new PacketStartInteriorBuild(this.pos, this.interior.getRegKey()));
            
            Minecraft.func_71410_x().func_147108_a(null);
          }));
     func_230480_a_((Widget)new Button(this.field_230708_k_ / 2 - 116, this.field_230709_l_ / 2 + 75, 32, 20, (ITextComponent)new StringTextComponent("<"), button -> {
            if (this.index - 1 >= 0) {
              this.index--;
            } else {
              this.index = this.interiors.size() - 1;
            } 
            
            this.interior = this.interiors.get(this.index);
          }));
     func_230480_a_((Widget)new Button(this.field_230708_k_ / 2 + 86, this.field_230709_l_ / 2 + 75, 32, 20, (ITextComponent)new StringTextComponent(">"), button -> {
            if (this.index + 1 < this.interiors.size()) {
              this.index++;
            } else {
              this.index = 0;
            } 
            this.interior = this.interiors.get(this.index);
          }));
  }

  
  public boolean func_231177_au__() {
     return false;
  }

  
  public void func_230430_a_(MatrixStack matrixstack, int p_render_1_, int p_render_2_, float p_render_3_) {
     func_230446_a_(matrixstack);

    
     if (this.interior != null) {
       this.field_230706_i_.func_110434_K().func_110577_a(this.interior.getPreviewImage());
       func_238474_b_(matrixstack, this.field_230708_k_ / 2 - 128 + 17, this.field_230709_l_ / 2 - 99 + 16, 0, 0, 224, 151);
      
       func_238467_a_(matrixstack, this.field_230708_k_ / 2 - 111, this.field_230709_l_ / 2 + 68, this.field_230708_k_ / 2 + 113, this.field_230709_l_ / 2 + 53, -13619081);
      
       this.field_230712_o_.func_238421_b_(matrixstack, this.interior.getInteriorName().getString(), (this.field_230708_k_ / 2 - this.field_230712_o_.func_238414_a_(this.interior.getInteriorName()) / 2), (this.field_230709_l_ / 2 + 57), -1);
    } 

    
     this.field_230706_i_.func_110434_K().func_110577_a(TEXTURE);
     func_238474_b_(matrixstack, this.field_230708_k_ / 2 - 128 + 1, this.field_230709_l_ / 2 - 99, 0, 0, 256, 199);

    
     super.func_230430_a_(matrixstack, p_render_1_, p_render_2_, p_render_3_);
  }
}


