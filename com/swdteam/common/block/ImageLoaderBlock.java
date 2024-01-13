package com.swdteam.common.block;

import com.swdteam.common.tileentity.ImageLoaderTileEntity;
import java.util.function.Supplier;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.Property;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class ImageLoaderBlock extends RotatableTileEntityBase {
   protected static final VoxelShape LADDER_EAST_AABB = Block.func_208617_a(0.0D, 0.0D, 0.0D, 3.0D, 16.0D, 16.0D);
   protected static final VoxelShape LADDER_WEST_AABB = Block.func_208617_a(13.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
   protected static final VoxelShape LADDER_SOUTH_AABB = Block.func_208617_a(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 3.0D);
   protected static final VoxelShape LADDER_NORTH_AABB = Block.func_208617_a(0.0D, 0.0D, 13.0D, 16.0D, 16.0D, 16.0D);

  public ImageLoaderBlock(Supplier<TileEntity> tileEntitySupplier, AbstractBlock.Properties properties) {
     super(tileEntitySupplier, properties);
  }



  public VoxelShape func_220053_a(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
     switch ((Direction)state.get((Property)FACING)) { default:
         return LADDER_NORTH_AABB;
       case EAST: return LADDER_EAST_AABB;
       case SOUTH: return LADDER_SOUTH_AABB;
       case WEST: break; }  return LADDER_WEST_AABB;
  }





  public void func_180633_a(World p_180633_1_, BlockPos p_180633_2_, BlockState p_180633_3_, LivingEntity p_180633_4_, ItemStack p_180633_5_) {
     if (!p_180633_1_.isRemote) {
       TileEntity te = p_180633_1_.getTileEntity(p_180633_2_);
       if (te != null && te instanceof ImageLoaderTileEntity &&
         p_180633_5_.func_82837_s()) {
         ((ImageLoaderTileEntity)te).setImgName(p_180633_5_.func_200301_q().getString());
      }
    }


     super.func_180633_a(p_180633_1_, p_180633_2_, p_180633_3_, p_180633_4_, p_180633_5_);
  }


  public VoxelShape func_220071_b(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
     return Block.func_208617_a(0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D);
  }
}


