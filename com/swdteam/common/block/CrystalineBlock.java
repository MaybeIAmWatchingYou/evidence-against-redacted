package com.swdteam.common.block;

import com.swdteam.common.init.DMBlocks;
import com.swdteam.util.SWDMathUtils;
import java.util.Random;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;

public class CrystalineBlock
  extends Block
{
  public CrystalineBlock(AbstractBlock.Properties properties) {
     super(properties);
  }


  public void func_225534_a_(BlockState state, ServerWorld worldIn, BlockPos pos, Random rand) {
     if (state == ((Block)DMBlocks.CRYSTALINE_BLOCK.get()).getDefaultState()) {

       BlockPos blockBelow = pos.func_177977_b();
       if (worldIn.getBlockState(blockBelow) == ((Block)DMBlocks.CRYSTALINE_BLOCK.get()).getDefaultState()) {


         BlockPos point = pos.func_177963_a(SWDMathUtils.randomDouble(0.0D, 1.0D), SWDMathUtils.randomDouble(0.0D, 3.0D), SWDMathUtils.randomDouble(0.0D, 1.0D));
         worldIn.setBlockState(point, ((Block)DMBlocks.CRYSTALINE_BLOCK.get()).getDefaultState(), 1);
      }
    }
  }
}


