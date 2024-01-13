package com.swdteam.common.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.common.IForgeShearable;

public class LeavesBlock extends Block implements IForgeShearable {
  public LeavesBlock(AbstractBlock.Properties properties) {
     super(properties);
     func_180632_j((BlockState)this.field_176227_L.func_177621_b());
  }
  
  public int getOpacity(BlockState state, IBlockReader worldIn, BlockPos pos) {
     return 1;
  }
  
  public boolean causesSuffocation(BlockState state, IBlockReader worldIn, BlockPos pos) {
     return false;
  }
  
  public boolean canEntitySpawn(BlockState state, IBlockReader worldIn, BlockPos pos, EntityType<?> type) {
     return (type == EntityType.field_200781_U || type == EntityType.field_200783_W);
  }

  
  public boolean isFlammable(BlockState state, IBlockReader world, BlockPos pos, Direction face) {
     return true;
  }

  
  public int getFlammability(BlockState state, IBlockReader world, BlockPos pos, Direction face) {
     return 300;
  }

  
  public int getFireSpreadSpeed(BlockState state, IBlockReader world, BlockPos pos, Direction face) {
     return 5;
  }
}


