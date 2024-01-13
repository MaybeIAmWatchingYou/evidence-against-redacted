package com.swdteam.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.swdteam.client.tardis.data.ClientTardisCache;
import com.swdteam.common.init.DMItems;
import com.swdteam.common.init.DMTranslationKeys;
import com.swdteam.common.tardis.TardisData;
import com.swdteam.common.tileentity.tardis.DataWriterTileEntity;
import com.swdteam.network.NetworkHandler;
import com.swdteam.network.packets.PacketEjectWaypointCartridge;
import com.swdteam.util.SWDMathUtils;
import com.swdteam.util.math.Position;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SharedConstants;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fml.ModList;
import org.apache.commons.lang3.StringUtils;



public class GuiWaypointWriter
  extends Screen
{
   public static ResourceLocation BACKGROUND = new ResourceLocation("dalekmod", "textures/gui/waypoint_monitor.png");
   public String waypointName = "";
  private BlockPos position;
  private boolean writable = true;
  public static String COMMAND_RESPONSE;
  
  public GuiWaypointWriter(BlockPos pos) {
     super((ITextComponent)new StringTextComponent("gui_waypoint_writer"));
     this.position = pos;


    
     TileEntity te = (Minecraft.func_71410_x()).field_71441_e.getTileEntity(this.position);
     if (te instanceof DataWriterTileEntity) {
       DataWriterTileEntity tet = (DataWriterTileEntity)te;
       if (tet.cartridge != null && tet.cartridge.getItem() instanceof com.swdteam.common.item.DataModuleItem && 
         tet.cartridge.getItem() == DMItems.DATA_MODULE.get() && 
         tet.cartridge.func_77942_o() && tet.cartridge.func_77978_p().func_74767_n("written")) {
         this.writable = false;
      }
    } 
  }


  
  public void func_231160_c_() {
     COMMAND_RESPONSE = "";
     super.func_231160_c_();
  }


  
  public void func_230430_a_(MatrixStack matrixstack, int x, int y, float p_render_3_) {
     func_230446_a_(matrixstack);
    
     this.field_230706_i_.field_71446_o.func_110577_a(BACKGROUND);
     func_238466_a_(matrixstack, this.field_230708_k_ / 2 - 128, this.field_230709_l_ / 2 - 99, 256, 199, 0.0F, 0.0F, 256, 199, 256, 256);
    
     TranslationTextComponent waypointWriter = DMTranslationKeys.GUI_WAYPOINT_WRITER_NAME;
     this.field_230712_o_.func_243248_b(matrixstack, (ITextComponent)waypointWriter, (this.field_230708_k_ / 2 - 106), (this.field_230709_l_ / 2 - 72), -12255420);
     this.field_230712_o_.func_238421_b_(matrixstack, "-----------------------------------", (this.field_230708_k_ / 2 - 106), (this.field_230709_l_ / 2 - 60), -12255420);
    
     if (this.writable) {
       TardisData data = ClientTardisCache.getTardisData((Minecraft.func_71410_x()).field_71439_g.getPosition());
      
       if (data != null && data.getCurrentLocation() != null) {
         String dir = SWDMathUtils.rotationToCardinal(data.getCurrentLocation().getFacing());
        
         TranslationTextComponent location = DMTranslationKeys.GUI_WAYPOINT_WRITER_LOCATION;
         this.field_230712_o_.func_238421_b_(matrixstack, location.getString() + ": " + data.getCurrentLocation().getDimension(), (this.field_230708_k_ / 2 - 106), (this.field_230709_l_ / 2 - 48), -12255420);
         Position position = data.getCurrentLocation().getPosition();
         TranslationTextComponent pos = DMTranslationKeys.GUI_WAYPOINT_WRITER_POSITION;
         this.field_230712_o_.func_238421_b_(matrixstack, pos.getString() + ": " + position.func_82615_a() + ", " + position.func_82617_b() + ", " + position.func_82616_c(), (this.field_230708_k_ / 2 - 106), (this.field_230709_l_ / 2 - 34), -12255420);
         TranslationTextComponent facing = DMTranslationKeys.GUI_WAYPOINT_WRITER_FACING;
         this.field_230712_o_.func_238421_b_(matrixstack, facing.getString() + ": " + dir, (this.field_230708_k_ / 2 - 106), (this.field_230709_l_ / 2 - 20), -12255420);
        
         TileEntity te = (Minecraft.func_71410_x()).field_71441_e.getTileEntity(this.position);
         if (te instanceof DataWriterTileEntity) {
           DataWriterTileEntity tet = (DataWriterTileEntity)te;
           if (tet.cartridge != null && tet.cartridge.getItem() instanceof com.swdteam.common.item.DataModuleItem) {
             TranslationTextComponent confirm1 = DMTranslationKeys.GUI_WAYPOINT_WRITER_CONFIRM_LINE_1;
             this.field_230712_o_.func_243248_b(matrixstack, (ITextComponent)confirm1, (this.field_230708_k_ / 2 - 106), (this.field_230709_l_ / 2 + 8), -12255420);
             TranslationTextComponent confirm2 = DMTranslationKeys.GUI_WAYPOINT_WRITER_CONFIRM_LINE_2;
             this.field_230712_o_.func_243248_b(matrixstack, (ITextComponent)confirm2, (this.field_230708_k_ / 2 - 106), (this.field_230709_l_ / 2 + 22), -12255420);
             this.field_230712_o_.func_238421_b_(matrixstack, COMMAND_RESPONSE, (this.field_230708_k_ / 2 - 106), (this.field_230709_l_ / 2 + 50), -12255420);
          } else {
             TranslationTextComponent noInput = DMTranslationKeys.GUI_WAYPOINT_WRITER_EMPTY;
             this.field_230712_o_.func_243248_b(matrixstack, (ITextComponent)noInput, (this.field_230708_k_ / 2 - 106), (this.field_230709_l_ / 2 + 22), -12255420);
          } 
        } 


        
         String marker = "_";
        
         marker = (System.currentTimeMillis() / 500L % 2L == 0L) ? "" : "_";
        
         this.field_230712_o_.func_238421_b_(matrixstack, "> " + this.waypointName + marker, (this.field_230708_k_ / 2 - 106), (this.field_230709_l_ / 2 + 36), -12255420);
      } else {
        
         TranslationTextComponent error = DMTranslationKeys.GUI_WAYPOINT_WRITER_ERROR;
         this.field_230712_o_.func_243248_b(matrixstack, (ITextComponent)error, (this.field_230708_k_ / 2 - 106), (this.field_230709_l_ / 2 - 48), -12255420);
         TranslationTextComponent tryLater = DMTranslationKeys.GUI_WAYPOINT_WRITER_TRY_LATER;
         this.field_230712_o_.func_243248_b(matrixstack, (ITextComponent)tryLater, (this.field_230708_k_ / 2 - 106), (this.field_230709_l_ / 2 - 36), -12255420);
      } 
    } else {
       TranslationTextComponent moduleUsed = DMTranslationKeys.GUI_WAYPOINT_WRITER_MODULE_USED;
       this.field_230712_o_.func_243248_b(matrixstack, (ITextComponent)moduleUsed, (this.field_230708_k_ / 2 - 106), (this.field_230709_l_ / 2 - 48), -12255420);
       TranslationTextComponent useGold1 = DMTranslationKeys.GUI_WAYPOINT_WRITER_USE_GOLD_LINE_1;
       this.field_230712_o_.func_243248_b(matrixstack, (ITextComponent)useGold1, (this.field_230708_k_ / 2 - 106), (this.field_230709_l_ / 2 - 36), -12255420);
       TranslationTextComponent useGold2 = DMTranslationKeys.GUI_WAYPOINT_WRITER_USE_GOLD_LINE_2;
       this.field_230712_o_.func_243248_b(matrixstack, (ITextComponent)useGold2, (this.field_230708_k_ / 2 - 106), (this.field_230709_l_ / 2 - 24), -12255420);
       TranslationTextComponent notReusable = DMTranslationKeys.GUI_WAYPOINT_WRITER_NOT_REUSABLE;
       this.field_230712_o_.func_243248_b(matrixstack, (ITextComponent)notReusable, (this.field_230708_k_ / 2 - 106), (this.field_230709_l_ / 2), -12255420);
    } 
    
     super.func_230430_a_(matrixstack, x, y, p_render_3_);
  }

  
  public boolean func_231177_au__() {
     return false;
  }

  
  public boolean func_231042_a_(char codePoint, int modifiers) {
     if (this.writable && 
       this.waypointName.length() < 25 && 
       SharedConstants.func_71566_a(codePoint)) {
       this.waypointName += codePoint;
    }

    
     return super.func_231042_a_(codePoint, modifiers);
  }


  
  public boolean func_231046_a_(int keyCode, int scanCode, int modifiers) {
     boolean close = false;
     boolean isCUIOn = ModList.get().isLoaded("worldeditcuife3");
    
     if (this.writable) {
       switch (keyCode) {
        
        case 259:
           if (this.waypointName.length() > 0) this.waypointName = StringUtils.chop(this.waypointName); 
          break;
        case 257:
        case 335:
           if (this.waypointName.length() > 0) {
             NetworkHandler.sendServerPacket(new PacketEjectWaypointCartridge(this.waypointName, this.position));
             if (!this.waypointName.startsWith("/"))
               if (isCUIOn) { close = true; }
               else { func_231175_as__(); }
               
             this.waypointName = "";
          } 
          break;
      } 

    
    }
     return super.func_231046_a_(close ? 256 : keyCode, scanCode, modifiers);
  }
}


