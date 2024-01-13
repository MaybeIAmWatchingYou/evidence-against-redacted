package com.swdteam.client.init;

import com.swdteam.client.gui.GuiChameleonCircuit;
import com.swdteam.client.gui.GuiHologram;
import com.swdteam.client.gui.GuiInteriorSelection;
import com.swdteam.client.gui.GuiKerblam;
import com.swdteam.client.gui.GuiSonicWorkbench;
import com.swdteam.client.gui.GuiTardisExteriorSelection;
import com.swdteam.client.gui.GuiTardisPrompt;
import com.swdteam.client.gui.GuiWaypointWriter;
import com.swdteam.common.tileentity.HologramTileEntity;
import com.swdteam.common.tileentity.tardis.SonicInterfaceTileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class DMGuiHandler {
  public static final int GUI_TARDIS_COMMAND_BLOCK = 0;
  public static final int GUI_TARDIS_CHAMELEON_CIRCUIT = 1;
  public static final int GUI_TARDIS_WAYPOINT_WRITER = 2;
  public static final int GUI_TARDIS_EXTERIOR_SELECTION = 3;
  public static final int GUI_SONIC_WORKBENCH = 4;
  public static final int GUI_INTERIOR_SELECTION = 5;
  public static final int GUI_HOLOGRAM = 6;
  public static final int GUI_TARDIS_PROMPT = 7;
  public static final int GUI_KERBLAM = 8;

  @OnlyIn(Dist.CLIENT)
  private static Screen getGui(int guiID, BlockPos p, PlayerEntity player) {
    TileEntity tile;
    TileEntity tile2;
     switch (guiID) {
      case 1:
         return (Screen)new GuiChameleonCircuit(p);

      case 2:
         return (Screen)new GuiWaypointWriter(p);

      case 3:
         return (Screen)new GuiTardisExteriorSelection(p);

      case 4:
         tile = player.world.getTileEntity(p);
         if (tile instanceof SonicInterfaceTileEntity) {
           return (Screen)new GuiSonicWorkbench((SonicInterfaceTileEntity)tile);
        }
      case 5:
         return (Screen)new GuiInteriorSelection(p);

      case 8:
         return (Screen)new GuiKerblam();

      case 6:
         tile2 = player.world.getTileEntity(p);
         if (tile2 instanceof HologramTileEntity) {
           return (Screen)new GuiHologram((HologramTileEntity)tile2);
        }

      case 7:
         return (Screen)new GuiTardisPrompt();
    }

     return null;
  }


  @OnlyIn(Dist.CLIENT)
  public static void openGui(int guiID, BlockPos p, PlayerEntity player) {
     Minecraft.func_71410_x().func_147108_a(getGui(guiID, p, player));
  }
}


