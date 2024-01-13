package com.swdteam.common.block;

import java.util.Random;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SnowBlock;
import net.minecraft.state.Property;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.lighting.LightEngine;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.RegistryObject;

public class NonspreadingGrassBlock
  extends Block {
  public NonspreadingGrassBlock(AbstractBlock.Properties properties, RegistryObject<Block> block) {
     super(properties);
     this.dirt = block;
  }
  private RegistryObject<Block> dirt;
  private BlockState getDirtState(RegistryObject<Block> block) {
     return ((Block)block.get()).getDefaultState();
  }

  public boolean func_149653_t(BlockState state) {
     return true;
  }

  private static boolean canBeGrass(BlockState state, ServerWorld world, BlockPos pos) {
     BlockPos blockpos = pos.func_177984_a();
     BlockState blockstate = world.getBlockState(blockpos);

     if (blockstate.func_203425_a(Blocks.field_150433_aE) && ((Integer)blockstate.get((Property)SnowBlock.field_176315_a)).intValue() == 1)
       return true;
     if (blockstate.func_204520_s().func_206882_g() == 8) {
       return false;
    }
     int i = LightEngine.func_215613_a((IBlockReader)world, state, pos, blockstate, blockpos, Direction.UP, blockstate.func_200016_a((IBlockReader)world, blockpos));
     return (i < world.func_201572_C());
  }




  public void func_225542_b_(BlockState state, ServerWorld world, BlockPos pos, Random rand) {
     if (!canBeGrass(state, world, pos) && world.isAreaLoaded(pos, 3))
       world.setBlockState(pos, getDirtState(this.dirt));
  }
}


