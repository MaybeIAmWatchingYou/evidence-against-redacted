package com.swdteam.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.swdteam.client.gui.util.GuiUtils;
import com.swdteam.client.tardis.data.ClientTardisCache;
import com.swdteam.client.tardis.data.ExteriorModels;
import com.swdteam.common.init.DMTardisRegistry;
import com.swdteam.common.tardis.Tardis;
import com.swdteam.common.tardis.TardisData;
import com.swdteam.model.javajson.JSONModel;
import com.swdteam.network.NetworkHandler;
import com.swdteam.network.packets.PacketApplyExterior;
import com.swdteam.network.packets.PacketUnlockExterior;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.IReorderingProcessor;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;

public class GuiChameleonCircuit
  extends Screen
{
   public static ResourceLocation BACKGROUND = new ResourceLocation("dalekmod", "textures/gui/chameleon_circuit.png");
   public static ResourceLocation CARTRIDGE = new ResourceLocation("dalekmod", "textures/items/chameleon_cartridge.png");
  
   public List<Tardis> tardises = new ArrayList<>();
   private int selectedExterior = 0;
  
  private Button btnApply;
  private Button btnToggle;
  private boolean unlocked = false;
  private BlockPos pos;
  private int scrollOffset;
   private int subSkinID = 0;
  
  List<IReorderingProcessor> description;
  
  public GuiChameleonCircuit(BlockPos pos) {
     super((ITextComponent)new StringTextComponent("gui_chameleon_circuit"));
     List<Tardis> td = DMTardisRegistry.getRegistryAsList();
    
     TardisData data = ClientTardisCache.getTardisData(pos);
    
     for (int i = 0; i < td.size(); i++) {
       Tardis t = td.get(i);
      
       if (t.getData().isUnlockable() || data.isUnlocked(t.getRegName())) {
         this.tardises.add(t);
      }
    } 
    
     this.pos = pos;
    
     this.description = (Minecraft.func_71410_x()).field_71466_p.func_238425_b_(((Tardis)this.tardises.get(this.selectedExterior)).getData().getDescription(), 120);
  }
  
  public void func_231160_c_() {
     super.func_231160_c_();
     this.field_230710_m_.clear();
    
     func_230480_a_((Widget)(this.btnApply = new Button(this.field_230708_k_ / 2 + 34, this.field_230709_l_ / 2 + 37, 64, 20, (ITextComponent)new StringTextComponent("Apply"), button -> {
            TardisData data = ClientTardisCache.getTardisData(this.pos);
            
            Tardis tardis = this.tardises.get(this.selectedExterior);
            
            if (tardis != null) {
              if (data.isUnlocked(tardis.getRegistryName())) {
                NetworkHandler.sendServerPacket(new PacketApplyExterior(tardis.getRegistryName(), data.getGlobalID(), this.subSkinID));
                this.field_230706_i_.func_147108_a(null);
              } else {
                NetworkHandler.sendServerPacket(new PacketUnlockExterior(tardis.getRegistryName(), data.getGlobalID()));
                checkUnlocked();
              } 
            }
          })));
     func_230480_a_((Widget)(this.btnToggle = new Button(this.field_230708_k_ / 2 - 90, this.field_230709_l_ / 2 + 37, 64, 20, (ITextComponent)new StringTextComponent("Cycle Skins"), button -> {
            Tardis tardis = this.tardises.get(this.selectedExterior);
            
            int size = tardis.getData().getSkinCount();
            if (this.subSkinID + 1 >= size) {
              this.subSkinID = 0;
            } else {
              this.subSkinID++;
            } 
          })));
     func_230480_a_((Widget)new Button(this.field_230708_k_ / 2 - 120, this.field_230709_l_ / 2 + 37, 20, 20, (ITextComponent)new StringTextComponent("<"), button -> {
            if (this.selectedExterior - 1 < 0) {
              this.selectedExterior = this.tardises.size() - 1;
            } else {
              this.selectedExterior--;
            } 
            
            checkUnlocked();
          }));
     func_230480_a_((Widget)new Button(this.field_230708_k_ / 2 - 16, this.field_230709_l_ / 2 + 37, 20, 20, (ITextComponent)new StringTextComponent(">"), button -> {
            if (this.selectedExterior + 1 >= this.tardises.size()) {
              this.selectedExterior = 0;
            } else {
              this.selectedExterior++;
            } 
            
            checkUnlocked();
          }));
     checkUnlocked();
  }

  
  public void checkUnlocked() {
     this.subSkinID = 0;
    
     Tardis tardis = this.tardises.get(this.selectedExterior);
     if (tardis != null) {
       TardisData data = ClientTardisCache.getTardisData(this.pos);
       this.unlocked = data.isUnlocked(tardis.getRegName());
    } else {
       this.unlocked = false;
    } 
    
     if (this.unlocked) {
       this.btnApply.func_238482_a_((ITextComponent)new StringTextComponent("Apply"));
      
       this.btnToggle.field_230693_o_ = tardis.getData().hasSkins();
    } else {
      
       this.btnApply.func_238482_a_((ITextComponent)new StringTextComponent("Unlock"));
       this.btnToggle.field_230693_o_ = false;
    } 
     this.description = (Minecraft.func_71410_x()).field_71466_p.func_238425_b_(((Tardis)this.tardises.get(this.selectedExterior)).getData().getDescription(), 88);
     if (this.description.size() > 7) {
       this.scrollOffset = 7 - this.description.size();
    } else {
       this.scrollOffset = 0;
    } 
  }



  
  public void func_230430_a_(MatrixStack matrixStack, int x, int y, float p_render_3_) {
     func_230446_a_(matrixStack);
    
     this.field_230706_i_.field_71446_o.func_110577_a(BACKGROUND);
    
     func_238466_a_(matrixStack, this.field_230708_k_ / 2 - 128, this.field_230709_l_ / 2 - 85, 256, 153, 0.0F, 0.0F, 256, 153, 256, 256);
    
     if (this.description.size() > 7 && this.unlocked) {
       if (this.scrollOffset != 0) {
         func_238466_a_(matrixStack, this.field_230708_k_ / 2 + 108, this.field_230709_l_ / 2 + 15, 8, 8, 0.0F, 160.0F, 8, 8, 256, 256);
      }
      
       if (this.scrollOffset != 7 - this.description.size()) {
         func_238466_a_(matrixStack, this.field_230708_k_ / 2 + 108, this.field_230709_l_ / 2 - 61, 8, 8, 0.0F, 152.0F, 8, 8, 256, 256);
      }
    } 
    
     super.func_230430_a_(matrixStack, x, y, p_render_3_);
    
     this.field_230712_o_.func_238421_b_(matrixStack, TextFormatting.GREEN + "Chameleon Panel", (this.field_230708_k_ / 2 + 26), (this.field_230709_l_ / 2 - 72), -12303292);
    
     if (this.tardises != null) {
       this.field_230712_o_.func_238421_b_(matrixStack, (this.selectedExterior + 1) + "/" + TextFormatting.GREEN + this.tardises.size(), (this.field_230708_k_ / 2 - 111), (this.field_230709_l_ / 2 - 72), -7829368);
      
       Tardis tardis = this.tardises.get(this.selectedExterior);
      
       if (tardis != null) {
        
         func_238471_a_(matrixStack, this.field_230712_o_, TextFormatting.GREEN + tardis.getData().getExteriorName().getString(), this.field_230708_k_ / 2 - 54, this.field_230709_l_ / 2 + 11, -1);
        
         JSONModel model = ExteriorModels.getModel(tardis.getData().getModel(this.subSkinID));
         if (!Minecraft.func_71410_x().func_195551_G().func_219533_b(tardis.getData().getModel(this.subSkinID))) {
           model = ExteriorModels.getModel(ClientTardisCache.DEFAULT_DATA.getTardisExterior().getData().getModel(0));
        }
         float scale = 20.0F;
         ExteriorModels.resetDoorAnimation(tardis.getData().getModel(this.subSkinID));
         if (this.unlocked) {
           GuiUtils.drawEntityOnScreen(matrixStack, (this.field_230708_k_ / 2 - 54), (this.field_230709_l_ / 2 - 7), scale, (float)(this.field_230706_i_.field_71441_e.getGameTime() % 360L * 2L), model);
           if (this.description != null && this.description.size() > 0) {
            
             int max = 7;
             if (max > this.description.size()) {
               max = this.description.size();
            }
            
             for (int i = 0; i < max; i++) {
               this.field_230712_o_.func_238422_b_(matrixStack, this.description.get(this.description.size() - max + i + this.scrollOffset), (this.field_230708_k_ / 2 + 18), (this.field_230709_l_ / 2 - 56 + i * 11), 16777215);
            }
          } 
          
           if (tardis.getData().hasSkins()) {
             matrixStack.func_227861_a_(0.0D, 0.0D, 400.0D);
             this.field_230712_o_.func_238421_b_(matrixStack, (this.subSkinID + 1) + "/" + TextFormatting.GREEN + tardis.getData().getSkinCount(), (this.field_230708_k_ / 2 - 88), (this.field_230709_l_ / 2 - 8), -7829368);
             matrixStack.func_227861_a_(0.0D, 0.0D, -400.0D);
          } 
        } else {
          
           GuiUtils.drawEntityOnScreen(matrixStack, (this.field_230708_k_ / 2 - 54), (this.field_230709_l_ / 2 - 7), scale, (float)(this.field_230706_i_.field_71441_e.getGameTime() % 360L * 2L), model, 0.0F, 1.0F, 252.0F, 120.0F);
           matrixStack.func_227860_a_();
           matrixStack.func_227861_a_(0.0D, 0.0D, 100.0D);
           func_238471_a_(matrixStack, this.field_230712_o_, "Locked", this.field_230708_k_ / 2 + 65, this.field_230709_l_ / 2 - 41, -1);
           func_238471_a_(matrixStack, this.field_230712_o_, TextFormatting.YELLOW + "Requires " + TextFormatting.GREEN + tardis.getData().getXpValue() + " EXP", this.field_230708_k_ / 2 + 65, this.field_230709_l_ / 2 - 30, -10440628);
           func_238471_a_(matrixStack, this.field_230712_o_, TextFormatting.YELLOW + "You have " + TextFormatting.GREEN + this.field_230706_i_.field_71439_g.experienceTotal + " EXP", this.field_230708_k_ / 2 + 65, this.field_230709_l_ / 2 - 19, -10440628);

          
           matrixStack.func_227865_b_();
        } 
      } 
    } 
  }


  
  public boolean func_231177_au__() {
     return false;
  }


  
  public boolean func_231044_a_(double mouseX, double mouseY, int button) {
     return super.func_231044_a_(mouseX, mouseY, button);
  }

  
  public boolean func_231043_a_(double p_231043_1_, double p_231043_3_, double p_231043_5_) {
     if (this.description.size() > 7) {
       int max = this.description.size() - 7;
       if (p_231043_5_ > 0.0D && 
         this.scrollOffset - 1 >= -max) {
         this.scrollOffset--;
      }
      
       if (p_231043_5_ < 0.0D && 
         this.scrollOffset < 0) {
         this.scrollOffset++;
      }
    } 
    
     return super.func_231043_a_(p_231043_1_, p_231043_3_, p_231043_5_);
  }
}


