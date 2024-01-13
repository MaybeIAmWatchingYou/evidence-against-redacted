package com.swdteam.common.block;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.Property;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

public class GearBlock extends AbstractRotateableWaterLoggableBlock {
   protected static final VoxelShape LADDER_EAST_AABB = Block.func_208617_a(0.0D, 0.0D, 0.0D, 3.0D, 16.0D, 16.0D);
   protected static final VoxelShape LADDER_WEST_AABB = Block.func_208617_a(13.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
   protected static final VoxelShape LADDER_SOUTH_AABB = Block.func_208617_a(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 3.0D);
   protected static final VoxelShape LADDER_NORTH_AABB = Block.func_208617_a(0.0D, 0.0D, 13.0D, 16.0D, 16.0D, 16.0D);
  
  public GearBlock(AbstractBlock.Properties properties) {
     super(properties);
  }

  
  public VoxelShape func_220053_a(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
     switch ((Direction)state.get((Property)FACING)) { case NORTH:
         return LADDER_NORTH_AABB;
       case SOUTH: return LADDER_SOUTH_AABB;
       case WEST: return LADDER_WEST_AABB; }
      return LADDER_EAST_AABB;
  }

  
  private boolean canAttachTo(IBlockReader p_196471_1_, BlockPos p_196471_2_, Direction p_196471_3_) {
     BlockState blockstate = p_196471_1_.getBlockState(p_196471_2_);
     return blockstate.func_224755_d(p_196471_1_, p_196471_2_, p_196471_3_);
  }
  
  public boolean func_196260_a(BlockState p_196260_1_, IWorldReader p_196260_2_, BlockPos p_196260_3_) {
     Direction direction = (Direction)p_196260_1_.get((Property)FACING);
     return canAttachTo((IBlockReader)p_196260_2_, p_196260_3_.func_177972_a(direction.getOpposite()), direction);
  }


  
  public BlockState func_196258_a(BlockItemUseContext context) {
     if (!context.func_196012_c()) {
       BlockState blockstate = context.func_195991_k().getBlockState(context.func_195995_a().func_177972_a(context.func_196000_l().getOpposite()));
       if (blockstate.func_203425_a(this) && blockstate.get((Property)FACING) == context.func_196000_l()) {
         return null;
      }
    } 
    
     BlockState blockstate1 = getDefaultState();
     World world = context.func_195991_k();
     BlockPos blockpos = context.func_195995_a();
     FluidState fluidstate = context.func_195991_k().func_204610_c(context.func_195995_a());
    
     for (Direction direction : context.func_196009_e()) {
       if (direction.func_176740_k().func_176722_c()) {
         blockstate1 = (BlockState)blockstate1.func_206870_a((Property)FACING, (Comparable)direction.getOpposite());
         if (blockstate1.func_196955_c((IWorldReader)world, blockpos)) {
           return (BlockState)blockstate1.func_206870_a((Property)WATERLOGGED, Boolean.valueOf((fluidstate.func_206886_c() == Fluids.field_204546_a)));
        }
      } 
    } 
    
     return null;
  }
  
  public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
     Direction direction = (Direction)state.get((Property)FACING);
     return canAttachTo((IBlockReader)worldIn, pos.func_177972_a(direction.getOpposite()), direction);
  }



  
  public void func_196262_a(BlockState state, World worldIn, BlockPos pos, Entity entityIn) {
     double motion = 0.0D;
     double gainedMotion = 0.1D;
     switch ((Direction)state.get((Property)FACING)) {
      case SOUTH:
         if (entityIn.getPosX() - pos.getX() <= 0.5D) { motion = gainedMotion; break; }
          motion = -gainedMotion;
        break;
      case NORTH:
         if (entityIn.getPosX() - pos.getX() <= 0.5D) { motion = -gainedMotion; break; }
          motion = gainedMotion;
        break;
      case EAST:
         if (entityIn.getPosZ() - pos.getZ() <= 0.5D) { motion = -gainedMotion; break; }
          motion = gainedMotion;
        break;
      case WEST:
         if (entityIn.getPosZ() - pos.getZ() <= 0.5D) { motion = gainedMotion; break; }
          motion = -gainedMotion;
        break;
    } 
    
     entityIn.addVelocity(0.0D, motion / 2.0D, 0.0D);
     entityIn.fallDistance = 0.0F;
    
     super.func_196262_a(state, worldIn, pos, entityIn);
  }
  
  public BlockState func_196271_a(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
     if (facing.getOpposite() == stateIn.get((Property)FACING) && !stateIn.func_196955_c((IWorldReader)worldIn, currentPos)) {
       return Blocks.AIR.getDefaultState();
    }
     if (((Boolean)stateIn.get((Property)WATERLOGGED)).booleanValue()) {
       worldIn.func_205219_F_().func_205360_a(currentPos, Fluids.field_204546_a, Fluids.field_204546_a.func_205569_a((IWorldReader)worldIn));
    }
    
     return super.func_196271_a(stateIn, facing, facingState, worldIn, currentPos, facingPos);
  }


  
  public boolean isLadder(BlockState state, IWorldReader world, BlockPos pos, LivingEntity entity) {
     return false;
  }
}


