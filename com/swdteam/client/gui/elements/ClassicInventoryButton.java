package com.swdteam.client.gui.elements;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.swdteam.network.NetworkHandler;
import com.swdteam.network.packets.PacketPickClassicItem;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IItemProvider;

public class ClassicInventoryButton
{
  private int xPos;
  private int yPos;
  private Block block;
   private ItemRenderer renderItem = Minecraft.func_71410_x().func_175599_af();
  private ItemStack stack;

  public ClassicInventoryButton(Block block, int xPos, int yPos) {
     this.xPos = xPos;
     this.yPos = yPos;
     this.block = block;
     this.stack = new ItemStack((IItemProvider)block);
  }

  public void onClick() {
     NetworkHandler.sendServerPacket(new PacketPickClassicItem(this.stack));
  }

  public boolean inBounds(double x, double y) {
     return (x >= (this.xPos - 4) && x <= (this.xPos + 20) && y >= (this.yPos - 4) && y <= (this.yPos + 20));
  }


  public void render(MatrixStack matrixstack, float x, float y) {
     if (inBounds(x, y)) {
       Screen.func_238467_a_(matrixstack, this.xPos - 4, this.yPos - 4, this.xPos + 20, this.yPos + 20, -2002081110);
    }


     this.renderItem.func_175042_a(this.stack, this.xPos, this.yPos);
  }
}


