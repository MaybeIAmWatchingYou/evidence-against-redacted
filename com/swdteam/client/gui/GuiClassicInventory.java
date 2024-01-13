package com.swdteam.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.swdteam.client.gui.elements.ClassicInventoryButton;
import com.swdteam.common.init.DMBlocks;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

public class GuiClassicInventory
  extends Screen {
  public GuiClassicInventory() {
     super((ITextComponent)new StringTextComponent("gui_classic"));


    
     this.blockList = new ArrayList<>();
  }

  
  public List<ClassicInventoryButton> blockList;
  
  public void func_231160_c_() {
     int xPos = this.field_230708_k_ / 2 - 112;
     int yPos = this.field_230709_l_ / 2 - 48;
    
     this.blockList.clear();
    
     this.blockList.add(new ClassicInventoryButton((Block)DMBlocks.CLASSIC_STONE.get(), xPos, yPos));
     this.blockList.add(new ClassicInventoryButton((Block)DMBlocks.CLASSIC_COBBLE.get(), xPos + 26, yPos));
     this.blockList.add(new ClassicInventoryButton((Block)DMBlocks.CLASSIC_BRICKS.get(), xPos + 52, yPos));
    
     this.blockList.add(new ClassicInventoryButton(Blocks.field_150346_d, xPos + 78, yPos));
     this.blockList.add(new ClassicInventoryButton((Block)DMBlocks.CLASSIC_PLANKS.get(), xPos + 104, yPos));
     this.blockList.add(new ClassicInventoryButton((Block)DMBlocks.CLASSIC_LOG.get(), xPos + 130, yPos));
     this.blockList.add(new ClassicInventoryButton((Block)DMBlocks.CLASSIC_LEAVES.get(), xPos + 156, yPos));
     this.blockList.add(new ClassicInventoryButton((Block)DMBlocks.CLASSIC_GLASS.get(), xPos + 182, yPos));
     this.blockList.add(new ClassicInventoryButton(Blocks.field_222401_hJ, xPos + 208, yPos));
     this.blockList.add(new ClassicInventoryButton((Block)DMBlocks.CLASSIC_COBBLE_MOSSY.get(), xPos, yPos + 26));
    
     this.blockList.add(new ClassicInventoryButton(Blocks.field_196674_t, xPos + 26, yPos + 26));
     this.blockList.add(new ClassicInventoryButton((Block)DMBlocks.CLASSIC_YELLOW_FLOWER.get(), xPos + 52, yPos + 26));
     this.blockList.add(new ClassicInventoryButton((Block)DMBlocks.CLASSIC_RED_FLOWER.get(), xPos + 78, yPos + 26));
     this.blockList.add(new ClassicInventoryButton(Blocks.field_150338_P, xPos + 104, yPos + 26));
     this.blockList.add(new ClassicInventoryButton(Blocks.field_150337_Q, xPos + 130, yPos + 26));
     this.blockList.add(new ClassicInventoryButton((Block)DMBlocks.CLASSIC_SAND.get(), xPos + 156, yPos + 26));
     this.blockList.add(new ClassicInventoryButton((Block)DMBlocks.CLASSIC_GRAVEL.get(), xPos + 182, yPos + 26));
     this.blockList.add(new ClassicInventoryButton((Block)DMBlocks.CLASSIC_SPONGE.get(), xPos + 208, yPos + 26));
    
     this.blockList.add(new ClassicInventoryButton((Block)DMBlocks.RED_CLASSIC_WOOL.get(), xPos, yPos + 52));
     this.blockList.add(new ClassicInventoryButton((Block)DMBlocks.ORANGE_CLASSIC_WOOL.get(), xPos + 26, yPos + 52));
     this.blockList.add(new ClassicInventoryButton((Block)DMBlocks.YELLOW_CLASSIC_WOOL.get(), xPos + 52, yPos + 52));
     this.blockList.add(new ClassicInventoryButton((Block)DMBlocks.LIME_CLASSIC_WOOL.get(), xPos + 78, yPos + 52));
     this.blockList.add(new ClassicInventoryButton((Block)DMBlocks.GREEN_CLASSIC_WOOL.get(), xPos + 104, yPos + 52));
     this.blockList.add(new ClassicInventoryButton((Block)DMBlocks.TEAL_CLASSIC_WOOL.get(), xPos + 130, yPos + 52));
     this.blockList.add(new ClassicInventoryButton((Block)DMBlocks.CYAN_CLASSIC_WOOL.get(), xPos + 156, yPos + 52));
     this.blockList.add(new ClassicInventoryButton((Block)DMBlocks.AQUA_CLASSIC_WOOL.get(), xPos + 182, yPos + 52));
     this.blockList.add(new ClassicInventoryButton((Block)DMBlocks.BLUE_CLASSIC_WOOL.get(), xPos + 208, yPos + 52));
    
     this.blockList.add(new ClassicInventoryButton((Block)DMBlocks.VIOLET_CLASSIC_WOOL.get(), xPos, yPos + 78));
     this.blockList.add(new ClassicInventoryButton((Block)DMBlocks.INDIGO_CLASSIC_WOOL.get(), xPos + 26, yPos + 78));
     this.blockList.add(new ClassicInventoryButton((Block)DMBlocks.PINK_CLASSIC_WOOL.get(), xPos + 52, yPos + 78));
     this.blockList.add(new ClassicInventoryButton((Block)DMBlocks.MAGENTA_CLASSIC_WOOL.get(), xPos + 78, yPos + 78));
     this.blockList.add(new ClassicInventoryButton((Block)DMBlocks.BLACK_CLASSIC_WOOL.get(), xPos + 104, yPos + 78));
     this.blockList.add(new ClassicInventoryButton((Block)DMBlocks.GRAY_CLASSIC_WOOL.get(), xPos + 130, yPos + 78));
     this.blockList.add(new ClassicInventoryButton((Block)DMBlocks.WHITE_CLASSIC_WOOL.get(), xPos + 156, yPos + 78));
     this.blockList.add(new ClassicInventoryButton(Blocks.field_150365_q, xPos + 182, yPos + 78));
     this.blockList.add(new ClassicInventoryButton(Blocks.field_150366_p, xPos + 208, yPos + 78));
    
     this.blockList.add(new ClassicInventoryButton(Blocks.field_150352_o, xPos, yPos + 104));
     this.blockList.add(new ClassicInventoryButton((Block)DMBlocks.CLASSIC_IRON.get(), xPos + 26, yPos + 104));
     this.blockList.add(new ClassicInventoryButton((Block)DMBlocks.CLASSIC_GOLD.get(), xPos + 52, yPos + 104));
     this.blockList.add(new ClassicInventoryButton(Blocks.field_150342_X, xPos + 78, yPos + 104));
     this.blockList.add(new ClassicInventoryButton((Block)DMBlocks.CLASSIC_TNT.get(), xPos + 104, yPos + 104));
     this.blockList.add(new ClassicInventoryButton(Blocks.field_150343_Z, xPos + 130, yPos + 104));
  }



  
  public void func_230430_a_(MatrixStack matrixstack, int x, int y, float p_render_3_) {
     func_238468_a_(matrixstack, this.field_230708_k_ / 2 - 130, this.field_230709_l_ / 2 - 80, this.field_230708_k_ / 2 + 130, this.field_230709_l_ / 2 + 80, -1878719232, -1070583712);

    
     func_238471_a_(matrixstack, this.field_230706_i_.field_71466_p, "Select block", this.field_230708_k_ / 2, this.field_230709_l_ / 2 - 66, 16777215);
     for (int i = 0; i < this.blockList.size(); i++) {
       ClassicInventoryButton tempButton = this.blockList.get(i);
       tempButton.render(matrixstack, x, y);
    } 
  }


  
  public boolean func_231044_a_(double x, double y, int p_mouseClicked_5_) {
     for (int i = 0; i < this.blockList.size(); i++) {
       ClassicInventoryButton tempButton = this.blockList.get(i);
       if (tempButton.inBounds(x, y)) {
         tempButton.onClick();
         this.field_230706_i_.func_147108_a(null);
      } 
    } 
    
     return super.func_231044_a_(x, y, p_mouseClicked_5_);
  }
}


