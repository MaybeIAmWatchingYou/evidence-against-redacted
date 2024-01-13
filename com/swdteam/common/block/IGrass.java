package com.swdteam.common.block;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IGrass {
  BlockState getDirtBlock(World paramWorld, BlockPos paramBlockPos, BlockState paramBlockState);
}


