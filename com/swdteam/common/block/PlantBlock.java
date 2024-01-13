package com.swdteam.common.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.BushBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;

public class PlantBlock extends BushBlock {
   protected static final VoxelShape SHAPE = Block.func_208617_a(5.0D, 0.0D, 5.0D, 11.0D, 10.0D, 11.0D);

  public PlantBlock(AbstractBlock.Properties properties) {
     super(properties);
  }


  public VoxelShape func_220053_a(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
     Vector3d vec3d = state.func_191059_e(worldIn, pos);
     return SHAPE.func_197751_a(vec3d.x, vec3d.y, vec3d.z);
  }




  public AbstractBlock.OffsetType func_176218_Q() {
     return AbstractBlock.OffsetType.XZ;
  }
}


