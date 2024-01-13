package com.swdteam.util;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;



public class WorldUtils
{
  public static boolean canPlace(World worldIn, BlockPos pos) {
     return canPlace(worldIn, pos, true, true);
  }

  public static boolean canPlace(World worldIn, BlockPos pos, boolean includeAir) {
     return canPlace(worldIn, pos, includeAir, true);
  }

  public static boolean canPlace(World worldIn, BlockPos pos, boolean includeAir, boolean includeLiquids) {
     BlockState blockstate = worldIn.getBlockState(pos);
     Material material = blockstate.func_185904_a();
     Block block = blockstate.getBlock();
     return (material == Material.field_151582_l || material == Material.field_151585_k || block == Blocks.field_150433_aE || (includeAir && worldIn



       .func_175623_d(pos)) || (includeLiquids && block instanceof net.minecraft.block.FlowingFluidBlock));
  }



  public static List<BlockWithPos> getBlocksWithinBounds(World w, AxisAlignedBB box) {
     List<BlockWithPos> blocks = new ArrayList<>();
     for (int x = (int)box.minX; x <= box.maxX; x++) {
       for (int y = (int)box.field_72338_b; y <= box.field_72337_e; y++) {
         for (int z = (int)box.minZ; z <= box.maxZ; z++) {
           BlockPos pos = new BlockPos(x, y, z);
           Block b = w.getBlockState(pos).getBlock();
           blocks.add(new BlockWithPos(b, pos));
        }
      }
    }
     return blocks;
  }

  public static class BlockWithPos {
    private BlockPos p;
    private Block b;

    public BlockWithPos(Block b, BlockPos p) {
       setBlock(b);
       setBlockPos(p);
    }

    public Block getBlock() {
       return this.b;
    }

    public BlockPos getBlockPos() {
       return this.p;
    }

    public void setBlock(Block b) {
       this.b = b;
    }

    public void setBlockPos(BlockPos p) {
       this.p = p;
    }
  }
}


