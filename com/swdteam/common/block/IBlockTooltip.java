package com.swdteam.common.block;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;

public interface IBlockTooltip {
  ITextComponent getName(BlockState paramBlockState, BlockPos paramBlockPos, Vector3d paramVector3d, PlayerEntity paramPlayerEntity);
}


