package com.swdteam.common.block;

import com.google.common.collect.Lists;
import java.util.Queue;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.IBucketPickupHandler;
import net.minecraft.block.material.Material;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.ITag;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.Tuple;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public class ClassicSpongeBlock extends Block {
  public ClassicSpongeBlock(AbstractBlock.Properties properties) {
     super(properties);
  }

  public void func_220082_b(BlockState state, World worldIn, BlockPos pos, BlockState oldState, boolean isMoving) {
     if (oldState.getBlock() != state.getBlock()) {
       tryAbsorb(worldIn, pos);
    }
  }


  public void func_220069_a(BlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
     tryAbsorb(worldIn, pos);
     super.func_220069_a(state, worldIn, pos, blockIn, fromPos, isMoving);
  }

  protected void tryAbsorb(World worldIn, BlockPos pos) {
     if (absorb(worldIn, pos)) {
       worldIn.func_217379_c(2001, pos, Block.func_196246_j(Blocks.WATER.getDefaultState()));
    }
  }


  private boolean absorb(World worldIn, BlockPos pos) {
     Queue<Tuple<BlockPos, Integer>> queue = Lists.newLinkedList();
     queue.add(new Tuple(pos, Integer.valueOf(0)));
     int i = 0;

     while (!queue.isEmpty()) {
       Tuple<BlockPos, Integer> tuple = queue.poll();
       BlockPos blockpos = (BlockPos)tuple.func_76341_a();
       int j = ((Integer)tuple.func_76340_b()).intValue();

       for (Direction direction : Direction.values()) {
         BlockPos blockpos1 = blockpos.func_177972_a(direction);
         BlockState blockstate = worldIn.getBlockState(blockpos1);
         FluidState ifluidstate = worldIn.func_204610_c(blockpos1);
         Material material = blockstate.func_185904_a();
         if (ifluidstate.func_206884_a((ITag)FluidTags.field_206959_a)) {
           if (blockstate.getBlock() instanceof IBucketPickupHandler && ((IBucketPickupHandler)blockstate.getBlock()).func_204508_a((IWorld)worldIn, blockpos1, blockstate) != Fluids.field_204541_a) {
             i++;
             if (j < 6) {
               queue.add(new Tuple(blockpos1, Integer.valueOf(j + 1)));
            }
           } else if (blockstate.getBlock() instanceof net.minecraft.block.FlowingFluidBlock) {
             worldIn.setBlockState(blockpos1, Blocks.AIR.getDefaultState(), 3);
             i++;
             if (j < 6) {
               queue.add(new Tuple(blockpos1, Integer.valueOf(j + 1)));
            }
           } else if (material == Material.field_203243_f || material == Material.field_204868_h) {
             TileEntity tileentity = blockstate.getBlock().hasTileEntity(blockstate) ? worldIn.getTileEntity(blockpos1) : null;
             func_220059_a(blockstate, (IWorld)worldIn, blockpos1, tileentity);
             worldIn.setBlockState(blockpos1, Blocks.AIR.getDefaultState(), 3);
             i++;
             if (j < 6) {
               queue.add(new Tuple(blockpos1, Integer.valueOf(j + 1)));
            }
          }
        }
      }

       if (i > 64) {
        break;
      }
    }

     return (i > 0);
  }
}


