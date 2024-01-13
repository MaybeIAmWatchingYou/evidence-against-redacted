package com.swdteam.common.block;

import java.util.function.Supplier;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.state.Property;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;

public class LampBlock extends RotatableTileEntityBase.WaterLoggable {
   public static final VoxelShape SHAPE = VoxelShapes.func_216384_a(VoxelShapes.func_197873_a(0.1875D, 3.0D, 0.1875D, 0.8125D, 4.0D, 0.8125D), new VoxelShape[] { VoxelShapes.func_197873_a(0.125D, 3.0625D, 0.125D, 0.875D, 3.9375D, 0.875D), VoxelShapes.func_197873_a(0.40625D, 1.0D, 0.40625D, 0.59375D, 3.0D, 0.59375D), VoxelShapes.func_197873_a(0.3125D, 0.0D, 0.3125D, 0.6875D, 1.0D, 0.6875D), VoxelShapes.func_197873_a(0.0D, 2.5D, 0.40625D, 1.0D, 2.6875D, 0.59375D) });
   public static final VoxelShape SHAPE_ROTATED = VoxelShapes.func_216384_a(VoxelShapes.func_197873_a(0.1875D, 3.0D, 0.1875D, 0.8125D, 4.0D, 0.8125D), new VoxelShape[] { VoxelShapes.func_197873_a(0.125D, 3.0625D, 0.125D, 0.875D, 3.9375D, 0.875D), VoxelShapes.func_197873_a(0.40625D, 1.0D, 0.40625D, 0.59375D, 3.0D, 0.59375D), VoxelShapes.func_197873_a(0.3125D, 0.0D, 0.3125D, 0.6875D, 1.0D, 0.6875D), VoxelShapes.func_197873_a(0.40625D, 2.5D, 0.0D, 0.59375D, 2.6875D, 1.0D) });

   public static final VoxelShape COLLISION_SHAPE = VoxelShapes.func_216384_a(VoxelShapes.func_197873_a(0.3125D, 0.0D, 0.3125D, 0.6875D, 4.0D, 0.6875D), new VoxelShape[0]);


  public LampBlock(Supplier<TileEntity> tileEntitySupplier, AbstractBlock.Properties properties, int light) {
     super(tileEntitySupplier, properties.func_235838_a_(state -> light));
  }




  public VoxelShape func_220053_a(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
     Direction facing = (Direction)state.get((Property)FACING);
     if (facing == Direction.EAST || facing == Direction.WEST) return SHAPE_ROTATED;
     return SHAPE;
  }


  public VoxelShape func_220071_b(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
     return COLLISION_SHAPE;
  }
}


