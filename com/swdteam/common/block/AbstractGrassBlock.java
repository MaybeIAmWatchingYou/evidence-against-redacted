package com.swdteam.common.block;
import java.util.Random;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.ITag;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.lighting.LightEngine;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.ToolType;

public abstract class AbstractGrassBlock extends Block implements IGrass {
  public AbstractGrassBlock(Material material) {
     super(AbstractBlock.Properties.func_200945_a(material).harvestLevel(0).harvestTool(ToolType.PICKAXE));
  }

  public AbstractGrassBlock(Material material, int lightLevel) {
     super(AbstractBlock.Properties.func_200945_a(material).harvestLevel(0).harvestTool(ToolType.PICKAXE).func_235838_a_(state -> lightLevel));
  }



  public AbstractGrassBlock(Material material, SoundType sound) {
     super(AbstractBlock.Properties.func_200945_a(material).harvestLevel(0).harvestTool(ToolType.PICKAXE).func_200947_a(sound));
  }

  public AbstractGrassBlock(AbstractBlock.Properties properties) {
     super(properties);
  }



  public boolean canSustainPlant(BlockState state, IBlockReader world, BlockPos pos, Direction facing, IPlantable plantable) {
     return true;
  }




  public void func_225534_a_(BlockState state, ServerWorld worldIn, BlockPos pos, Random rand) {
     if (!func_220257_b(state, (IWorldReader)worldIn, pos)) {
       if (!worldIn.isAreaLoaded(pos, 3))
         return;  worldIn.setBlockState(pos, getDirtBlock((World)worldIn, pos, state));
    }
     else if (worldIn.func_205052_D(pos.func_177984_a()) >= 9.0F) {
       BlockState blockstate = getDefaultState();

       for (int i = 0; i < 4; i++) {
         BlockPos blockpos = pos.func_177982_a(rand.nextInt(3) - 1, rand.nextInt(5) - 3, rand.nextInt(3) - 1);
         if (worldIn.getBlockState(blockpos) == getDirtBlock((World)worldIn, blockpos, state) && func_220256_c(blockstate, (IWorldReader)worldIn, blockpos)) {
           worldIn.setBlockState(blockpos, blockstate);
        }
      }
    }



     super.func_225534_a_(state, worldIn, pos, rand);
  }

  private static boolean func_220257_b(BlockState p_220257_0_, IWorldReader p_220257_1_, BlockPos p_220257_2_) {
     BlockPos blockpos = p_220257_2_.func_177984_a();
     BlockState blockstate = p_220257_1_.getBlockState(blockpos);

     int i = LightEngine.func_215613_a((IBlockReader)p_220257_1_, p_220257_0_, p_220257_2_, blockstate, blockpos, Direction.UP, blockstate.func_200016_a((IBlockReader)p_220257_1_, blockpos));
     return (i < p_220257_1_.func_201572_C());
  }


  private static boolean func_220256_c(BlockState p_220256_0_, IWorldReader p_220256_1_, BlockPos p_220256_2_) {
     BlockPos blockpos = p_220256_2_.func_177984_a();
     return (func_220257_b(p_220256_0_, p_220256_1_, p_220256_2_) && !p_220256_1_.func_204610_c(blockpos).func_206884_a((ITag)FluidTags.field_206959_a));
  }
}


