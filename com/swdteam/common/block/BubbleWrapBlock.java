package com.swdteam.common.block;
import com.swdteam.main.DalekMod;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.state.Property;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.common.extensions.IForgeBlock;

public class BubbleWrapBlock extends WaterLoggableGlassBlock implements IForgeBlock {
   protected static final VoxelShape SHAPE = Block.func_208617_a(0.0D, 0.0D, 0.0D, 16.0D, 1.0D, 16.0D);
   protected static final VoxelShape COLLIDE_SHAPE = Block.func_208617_a(0.0D, 0.0D, 0.0D, 16.0D, 4.0D, 16.0D);

  public BubbleWrapBlock(AbstractBlock.Properties prop) {
     super(prop);
  }


  public boolean func_200122_a(BlockState state, BlockState neighborState, Direction dir) {
     return super.func_200122_a(state, neighborState, dir);
  }

  public VoxelShape func_220053_a(BlockState p_220053_1_, IBlockReader p_220053_2_, BlockPos p_220053_3_, ISelectionContext p_220053_4_) {
     return SHAPE;
  }


  public VoxelShape func_220071_b(BlockState p_220053_1_, IBlockReader p_220053_2_, BlockPos p_220053_3_, ISelectionContext p_220053_4_) {
     return COLLIDE_SHAPE;
  }

  public BlockState func_196271_a(BlockState state, Direction dir, BlockState stateOn, IWorld world, BlockPos pos, BlockPos posOn) {
     return !state.func_196955_c((IWorldReader)world, pos) ? Blocks.AIR.getDefaultState() : super.func_196271_a(state, dir, stateOn, world, pos, posOn);
  }

  public boolean func_196260_a(BlockState state, IWorldReader world, BlockPos pos) {
     return !world.func_175623_d(pos.func_177977_b());
  }

  public static class Explosive
    extends BubbleWrapBlock {
    public Explosive(AbstractBlock.Properties prop) {
       super(prop);
    }
    public boolean func_149659_a(Explosion boom) {
       return false;
    }
    private void explode(World world, BlockPos pos, LivingEntity cause) {
       if (!world.isRemote) {
         world.setBlockState(pos, ((Boolean)world.getBlockState(pos).get((Property)BlockStateProperties.WATERLOGGED)).booleanValue() ? Blocks.WATER.getDefaultState() : Blocks.AIR.getDefaultState());
         world.func_230546_a_((Entity)null, DamageSource.func_188405_b(cause), null, pos.getX(), pos.getY(), pos.getZ(), 2.0F, false, Explosion.Mode.BREAK);
      }
    }



    public void func_176199_a(World world, BlockPos pos, Entity entity) {
       if (!world.isRemote && entity instanceof LivingEntity && DalekMod.RANDOM.nextInt(18) == 0) explode(world, pos, (LivingEntity)entity);
       super.func_176199_a(world, pos, entity);
    }


    public void func_220066_a(World world, BlockState state, BlockRayTraceResult result, ProjectileEntity projectile) {
       LivingEntity owner = null;
       if (projectile.func_234616_v_() instanceof LivingEntity) owner = (LivingEntity)projectile.func_234616_v_();
       explode(world, result.func_216350_a(), owner);
       super.func_220066_a(world, state, result, projectile);
    }


    public boolean removedByPlayer(BlockState state, World world, BlockPos pos, PlayerEntity player, boolean willHarvest, FluidState fluid) {
       explode(world, pos, (LivingEntity)player);
       return super.removedByPlayer(state, world, pos, player, willHarvest, fluid);
    }


    public void onBlockExploded(BlockState state, World world, BlockPos pos, Explosion explosion) {
       LivingEntity exploder = null;
       if (explosion.getExploder() instanceof LivingEntity) exploder = (LivingEntity)explosion.getExploder();
       if (!(new BlockPos(explosion.getPosition())).equals(pos)) explode(world, pos, exploder);
       super.onBlockExploded(state, world, pos, explosion);
    }
  }
}


