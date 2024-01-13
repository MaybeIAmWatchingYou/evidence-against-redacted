package com.swdteam.common.block;

import java.util.function.Supplier;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;

public class StatueBlock
  extends RotatableTileEntityBase.WaterLoggable {
   public static final VoxelShape SHAPE = VoxelShapes.func_197873_a(0.0D, 0.0D, 0.0D, 0.9999D, 2.0D, 0.9999D);

  public StatueBlock(Supplier<TileEntity> tileEntitySupplier, AbstractBlock.Properties properties) {
     super(tileEntitySupplier, properties);
  }


  public VoxelShape func_220053_a(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
     return SHAPE;
  }


  public boolean collisionExtendsVertically(BlockState state, IBlockReader world, BlockPos pos, Entity collidingEntity) {
     return true;
  }
}


