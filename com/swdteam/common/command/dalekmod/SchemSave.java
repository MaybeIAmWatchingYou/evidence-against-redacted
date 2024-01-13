package com.swdteam.common.command.dalekmod;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.swdteam.main.DalekMod;
import com.swdteam.util.world.Schematic;
import com.swdteam.util.world.SchematicUtils;
import com.swdteam.util.world.TileData;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.block.BlockState;
import net.minecraft.command.CommandSource;
import net.minecraft.command.arguments.MessageArgument;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import org.apache.logging.log4j.Level;





public class SchemSave
{
  public static class CommandSchemData
  {
     private static Map<PlayerEntity, Data> SCHEM_DATA = new HashMap<>();
    
    public static void setPos1(PlayerEntity player) {
       Data data = checkAndGet(player);
       data.POS_1 = player.getPosition();
    }
    
    public static void setPos2(PlayerEntity player) {
       Data data = checkAndGet(player);
       data.POS_2 = player.getPosition();
    }
    
    public static void setSchematic(PlayerEntity player, Schematic schem) {
       Data data = checkAndGet(player);
       data.SCHEMATIC = schem;
    }
    
    public static Data getData(PlayerEntity player) {
       if (SCHEM_DATA.containsKey(player)) {
         return SCHEM_DATA.get(player);
      }
       return null;
    }

    
    private static Data checkAndGet(PlayerEntity player) {
       if (!SCHEM_DATA.containsKey(player)) {
         SCHEM_DATA.put(player, new Data());
      }
      
       return SCHEM_DATA.get(player);
    }

    
    public static class Data
    {
      public BlockPos POS_1;
      public BlockPos POS_2;
      public Schematic SCHEMATIC;
    }
  }
  
  public static int saveSchem(CommandContext<CommandSource> commandContext) throws CommandSyntaxException {
     String name = MessageArgument.func_197124_a(commandContext, "save-name").getString();
     DalekMod.LOGGER.log(Level.INFO, "Saving DalekMod Schematic: " + name);
    
     CommandSchemData.Data data = CommandSchemData.getData((PlayerEntity)((CommandSource)commandContext.getSource()).func_197035_h());
    
     if (data != null && data.POS_1 != null && data.POS_2 != null) {
      
       BlockPos blockpos = data.POS_1;
       BlockPos blockpos1 = data.POS_2;
      
       BlockPos blockpos2 = new BlockPos(Math.min(blockpos.getX(), blockpos1.getX()), Math.min(blockpos.getY(), blockpos1.getY()), Math.min(blockpos.getZ(), blockpos1.getZ()));
       BlockPos blockpos3 = new BlockPos(Math.max(blockpos.getX(), blockpos1.getX()), Math.max(blockpos.getY(), blockpos1.getY()), Math.max(blockpos.getZ(), blockpos1.getZ()));
       int i = (blockpos3.getX() - blockpos2.getX() + 1) * (blockpos3.getY() - blockpos2.getY() + 1) * (blockpos3.getZ() - blockpos2.getZ() + 1);
      
       int[] blockMap = new int[i];
       List<TileData> tiles = new ArrayList<>();
      
       int index = 0;
       int x = blockpos3.getX() - blockpos2.getX();
       int y = blockpos3.getY() - blockpos2.getY();
       int z = blockpos3.getZ() - blockpos2.getZ();
      
       Schematic schem = new Schematic(x, y, z);
      
       if (blockpos2.getY() >= 0 && blockpos3.getY() < 256) {
         ServerWorld serverWorld = ((CommandSource)commandContext.getSource()).func_197023_e();
        
         for (int i1 = blockpos2.getY(); i1 <= blockpos3.getY(); i1++) {
           for (int j1 = blockpos2.getX(); j1 <= blockpos3.getX(); j1++) {
             for (int l = blockpos2.getZ(); l <= blockpos3.getZ(); l++) {
               BlockPos pos = new BlockPos(j1, i1, l);
               BlockState block = serverWorld.getBlockState(pos);
              
               blockMap[index] = schem.getBlockID(block);
              
               TileEntity te = serverWorld.getTileEntity(pos);
               if (te != null) {
                 CompoundNBT tag = new CompoundNBT();
                 te.func_189515_b(tag);
                 TileData tileData = new TileData();
                
                 tileData.setPos(new int[] { x - blockpos3.getX() - pos.getX(), y - blockpos3.getY() - pos.getY(), z - blockpos3.getZ() - pos.getZ() });
                 tileData.setNbtData(tag);
                 tiles.add(tileData);
              } 
              
               index++;
            } 
          } 
        } 
        
         schem.setBlockMap(blockMap);
         schem.setTileData(tiles);
        
         SchematicUtils.saveSchematic(schem, name);
         ((CommandSource)commandContext.getSource()).func_197030_a((ITextComponent)new StringTextComponent(TextFormatting.GREEN + "Schematic " + name + " Saved"), false);
         data.POS_1 = null;
         data.POS_2 = null;
      } 
    } else {
       ((CommandSource)commandContext.getSource()).func_197021_a((ITextComponent)new StringTextComponent("Set both positions with /dalekmod schem pos1 and /dalekmod schem pos2"));
    } 
    
     return 1;
  }
  public static class Data {
    public BlockPos POS_1; public BlockPos POS_2; public Schematic SCHEMATIC; }
  
  public static int loadSchem(CommandContext<CommandSource> commandContext) throws CommandSyntaxException {
     String name = MessageArgument.func_197124_a(commandContext, "load-name").getString();
    
     File f = new File("schematics/dalekmod/" + name + ".schm");
     if (f.exists()) {
       CommandSchemData.setSchematic((PlayerEntity)((CommandSource)commandContext.getSource()).func_197035_h(), SchematicUtils.loadSchematic(f.getAbsolutePath(), SchematicUtils.FileLocation.EXTERNAL));
       ((CommandSource)commandContext.getSource()).func_197030_a((ITextComponent)new StringTextComponent(TextFormatting.GREEN + "Schematic " + name + " loaded. Place with /dalekmod schem paste"), false);
    } else {
       ((CommandSource)commandContext.getSource()).func_197021_a((ITextComponent)new StringTextComponent("Schematic " + name + " does not exist"));
    } 
    
     return 1;
  }
  
  public static int pos1(CommandContext<CommandSource> commandContext) throws CommandSyntaxException {
     CommandSchemData.setPos1((PlayerEntity)((CommandSource)commandContext.getSource()).func_197035_h());
     CommandSchemData.Data data = CommandSchemData.getData((PlayerEntity)((CommandSource)commandContext.getSource()).func_197035_h());
     ((CommandSource)commandContext.getSource()).func_197030_a((ITextComponent)new StringTextComponent(TextFormatting.LIGHT_PURPLE + "First position set to: (" + data.POS_1.getX() + ", " + data.POS_1.getY() + ", " + data.POS_1.getZ() + ")"), false);
    
     return 1;
  }
  
  public static int pos2(CommandContext<CommandSource> commandContext) throws CommandSyntaxException {
     CommandSchemData.setPos2((PlayerEntity)((CommandSource)commandContext.getSource()).func_197035_h());
     CommandSchemData.Data data = CommandSchemData.getData((PlayerEntity)((CommandSource)commandContext.getSource()).func_197035_h());
     ((CommandSource)commandContext.getSource()).func_197030_a((ITextComponent)new StringTextComponent(TextFormatting.LIGHT_PURPLE + "Second position set to: (" + data.POS_2.getX() + ", " + data.POS_2.getY() + ", " + data.POS_2.getZ() + ")"), false);
    
     return 1;
  }
  
  public static int paste(CommandContext<CommandSource> commandContext) throws CommandSyntaxException {
     CommandSchemData.Data data = CommandSchemData.getData((PlayerEntity)((CommandSource)commandContext.getSource()).func_197035_h());
     if (data != null) {
       if (data.SCHEMATIC != null) {
         BlockPos pos = ((CommandSource)commandContext.getSource()).func_197035_h().getPosition();
         SchematicUtils.generateSchematic(SchematicUtils.GenerationQueue.DEFAULT, (World)((CommandSource)commandContext.getSource()).func_197023_e(), pos, data.SCHEMATIC);
         ((CommandSource)commandContext.getSource()).func_197030_a((ITextComponent)new StringTextComponent(TextFormatting.GREEN + "Schematic pasted at: (" + pos.getX() + ", " + pos.getY() + ", " + pos.getZ() + ")"), false);
      } else {
         ((CommandSource)commandContext.getSource()).func_197021_a((ITextComponent)new StringTextComponent("No schematic loaded. Use /dalekmod schem load [name]"));
      } 
    }
     return 1;
  }
}


