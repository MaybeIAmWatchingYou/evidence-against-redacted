package com.swdteam.common.block;

import java.util.Random;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.state.Property;
import net.minecraft.state.StateContainer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.server.ServerWorld;

public class RustableRotorBlock extends RotorBlock implements IRust {
  public RustableRotorBlock(AbstractBlock.Properties properties, int light) {
     super(properties.func_200944_c(), light);
     func_180632_j((BlockState)getDefaultState().func_206870_a((Property)WAXED, Boolean.valueOf(false)));
  }

  
  public BlockState getRustedState(BlockState state) {
     return (BlockState)((BlockState)super.getRustedState(state)
       .func_206870_a((Property)field_176387_N, state.get((Property)field_176387_N)))
       .func_206870_a((Property)WATERLOGGED, state.get((Property)WATERLOGGED));
  }

  
  public void func_225542_b_(BlockState state, ServerWorld world, BlockPos pos, Random rand) {
     rustTick(this, state, world, pos, rand);
  }
  
  protected void func_206840_a(StateContainer.Builder<Block, BlockState> state) {
     super.func_206840_a(state);
     state.func_206894_a(new Property[] { (Property)WAXED });
  }

  
  public float getSlipperiness(BlockState state, IWorldReader world, BlockPos pos, Entity entity) {
     if (((Boolean)state.get((Property)WAXED)).booleanValue()) return 0.9F;
     return super.getSlipperiness(state, world, pos, entity);
  }
}


