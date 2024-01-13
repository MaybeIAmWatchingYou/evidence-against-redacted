package com.swdteam.common.block;

import com.swdteam.common.init.DMItems;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropsBlock;
import net.minecraft.state.Property;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;

public class CeleryBlock extends CropsBlock {
   private static final VoxelShape[] SHAPE_BY_AGE = new VoxelShape[] { Block.func_208617_a(0.0D, 0.0D, 0.0D, 16.0D, 2.0D, 16.0D), Block.func_208617_a(0.0D, 0.0D, 0.0D, 16.0D, 4.0D, 16.0D), Block.func_208617_a(0.0D, 0.0D, 0.0D, 16.0D, 6.0D, 16.0D), Block.func_208617_a(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D), Block.func_208617_a(0.0D, 0.0D, 0.0D, 16.0D, 10.0D, 16.0D), Block.func_208617_a(0.0D, 0.0D, 0.0D, 16.0D, 12.0D, 16.0D), Block.func_208617_a(0.0D, 0.0D, 0.0D, 16.0D, 14.0D, 16.0D), Block.func_208617_a(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D) };
  
  public CeleryBlock(AbstractBlock.Properties properties) {
     super(properties);
  }
  
  protected IItemProvider func_199772_f() {
     return (IItemProvider)DMItems.CELERY.get();
  }
  
  public VoxelShape func_220053_a(BlockState state, IBlockReader reader, BlockPos pos, ISelectionContext context) {
     return SHAPE_BY_AGE[((Integer)state.get((Property)func_185524_e())).intValue()];
  }
}


