package com.swdteam.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.swdteam.client.gui.util.GuiUtils;
import com.swdteam.client.tardis.data.ClientTardisCache;
import com.swdteam.client.tardis.data.ExteriorModels;
import com.swdteam.common.init.DMTardisRegistry;
import com.swdteam.common.init.DMTranslationKeys;
import com.swdteam.common.tardis.Tardis;
import com.swdteam.common.tardis.TardisData;
import com.swdteam.model.javajson.JSONModel;
import com.swdteam.network.NetworkHandler;
import com.swdteam.network.packets.PacketSetExterior;
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
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;

public class GuiTardisExteriorSelection
  extends Screen
{
   public static ResourceLocation BACKGROUND = new ResourceLocation("dalekmod", "textures/gui/tardis_exterior_selection.png");
  public BlockPos pos;
   public int index = 0;
  
  private int subSelection;
  private Button btnToggleSkin;
   public List<Tardis> tardises = new ArrayList<>();
  
  public GuiTardisExteriorSelection(BlockPos pos) {
     super((ITextComponent)new StringTextComponent("gui_tardis_exterior_selection"));
     this.pos = pos;
    
     List<Tardis> td = DMTardisRegistry.getRegistryAsList();

    
     TardisData data = ClientTardisCache.getTardisData(pos);
    
     for (int i = 0; i < td.size(); i++) {
       Tardis t = td.get(i);
       if (t.getData().isDefaultSelection()) {
         this.tardises.add(t);
      }
    } 
  }
  
  public void func_231160_c_() {
     super.func_231160_c_();
    
     this.field_230710_m_.clear();
     this.field_230705_e_.clear();
    
     func_230480_a_((Widget)new Button(this.field_230708_k_ / 2 - 92, this.field_230709_l_ / 2 + 45, 32, 20, (ITextComponent)new StringTextComponent("<"), button -> {
            if (this.index - 1 < 0) {
              this.index = this.tardises.size() - 1;
            } else {
              this.index--;
            } 
            
            resetButtonSubSkin();
          }));
     func_230480_a_((Widget)new Button(this.field_230708_k_ / 2 - 48, this.field_230709_l_ / 2 + 45, 32, 20, (ITextComponent)new StringTextComponent(">"), button -> {
            if (this.index + 1 >= this.tardises.size()) {
              this.index = 0;
            } else {
              this.index++;
            } 
            resetButtonSubSkin();
          }));
     TranslationTextComponent selectExterior = DMTranslationKeys.GUI_TARDIS_SELECTION_COMPLETE;
     func_230480_a_((Widget)new Button(this.field_230708_k_ / 2 - 12, this.field_230709_l_ / 2 + 45, 106, 20, (ITextComponent)selectExterior, button -> {
            NetworkHandler.sendServerPacket(new PacketSetExterior(((Tardis)this.tardises.get(this.index)).getRegistryName(), this.pos, this.subSelection));
            
            Minecraft.func_71410_x().func_147108_a(null);
          }));
     TranslationTextComponent skinCycler = DMTranslationKeys.GUI_TARDIS_SELECTION_SKIN;
     func_230480_a_((Widget)(this.btnToggleSkin = new Button(this.field_230708_k_ / 2 - 12, this.field_230709_l_ / 2 + 21, 106, 20, (ITextComponent)skinCycler, button -> {
            Tardis tardis = this.tardises.get(this.index);
            int size = tardis.getData().getSkinCount();
            if (this.subSelection + 1 >= size) {
              this.subSelection = 0;
            } else {
              this.subSelection++;
            } 
          })));
  }


  
  public void func_230430_a_(MatrixStack matrixstack, int x, int y, float p_render_3_) {
     func_230446_a_(matrixstack);
     Tardis tardis = this.tardises.get(this.index);
     if (tardis.getData() != null) {
       this.field_230706_i_.field_71446_o.func_110577_a(BACKGROUND);
       func_238466_a_(matrixstack, this.field_230708_k_ / 2 - 102, this.field_230709_l_ / 2 - 72, 204, 144, 0.0F, 0.0F, 204, 144, 256, 256);
      
       TranslationTextComponent exterior = DMTranslationKeys.GUI_TARDIS_SELECTION_EXTERIOR;
       this.field_230712_o_.func_243248_b(matrixstack, (ITextComponent)exterior, (this.field_230708_k_ / 2 - 12), (this.field_230709_l_ / 2 - 60), -14405205);
       this.field_230712_o_.func_238421_b_(matrixstack, tardis.getData().getExteriorName().getString(), (this.field_230708_k_ / 2 - 12), (this.field_230709_l_ / 2 - 49), -13421773);
      
       TranslationTextComponent interior = DMTranslationKeys.GUI_TARDIS_SELECTION_INTERIOR;
       this.field_230712_o_.func_243248_b(matrixstack, (ITextComponent)interior, (this.field_230708_k_ / 2 - 12), (this.field_230709_l_ / 2 - 38), -14405205);
      
       if (tardis.getData().getInterior() != null) {
         this.field_230712_o_.func_238421_b_(matrixstack, tardis.getData().getInterior().getInteriorName().getString(), (this.field_230708_k_ / 2 - 12), (this.field_230709_l_ / 2 - 27), -13421773);
      }
      
       super.func_230430_a_(matrixstack, x, y, p_render_3_);
      
       if (tardis != null) {
         JSONModel model = ExteriorModels.getModel(tardis.getData().getModel(this.subSelection));
         ExteriorModels.resetDoorAnimation(tardis.getData().getModel(this.subSelection));
         if (!Minecraft.func_71410_x().func_195551_G().func_219533_b(tardis.getData().getModel(this.subSelection)))
           model = ExteriorModels.getModel(ClientTardisCache.DEFAULT_DATA.getTardisExterior().getData().getModel(0));
         float scale = 22.0F;
         GuiUtils.drawEntityOnScreen(matrixstack, (this.field_230708_k_ / 2 - 54), (this.field_230709_l_ / 2 + 24), scale, (float)(Minecraft.func_71410_x()).field_71441_e.getGameTime(), model);
      } 
      
       if (tardis.getData().hasSkins()) {
         matrixstack.func_227861_a_(0.0D, 0.0D, 400.0D);
         this.field_230712_o_.func_238421_b_(matrixstack, (this.subSelection + 1) + "/" + TextFormatting.GREEN + tardis.getData().getSkinCount(), (this.field_230708_k_ / 2 - 88), (this.field_230709_l_ / 2 + 30), -7829368);
         matrixstack.func_227861_a_(0.0D, 0.0D, -400.0D);
      } 
    } 
  }
  
  public void resetButtonSubSkin() {
     this.subSelection = 0;
     Tardis tardis = this.tardises.get(this.index);
     if (tardis != null && tardis.getData() != null && tardis.getData().hasSkins()) {
       this.btnToggleSkin.field_230693_o_ = true;
    } else {
       this.btnToggleSkin.field_230693_o_ = false;
    } 
  }

  
  public boolean func_231177_au__() {
     return false;
  }
}


