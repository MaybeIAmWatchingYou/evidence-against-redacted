package com.swdteam.common.block;

import com.swdteam.common.init.DMBlocks;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ClassicGrassBlock extends AbstractGrassBlock {
  public ClassicGrassBlock(AbstractBlock.Properties properties) {
     super(properties);
  }

  
  public BlockState getDirtBlock(World w, BlockPos p, BlockState state) {
     return ((Block)DMBlocks.CLASSIC_DIRT.get()).getDefaultState();
  }
}


