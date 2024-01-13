package com.swdteam.common.block;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.DirectionalBlock;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.Property;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class RotorBlock extends DirectionalBlock implements IWaterLoggable {
   public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

  public RotorBlock(AbstractBlock.Properties properties, int light) {
     super(properties.func_235838_a_(state -> light));


     func_180632_j((BlockState)((BlockState)getDefaultState().func_206870_a((Property)field_176387_N, (Comparable)Direction.UP)).func_206870_a((Property)WATERLOGGED, Boolean.valueOf(false)));
  }



  public VoxelShape func_220053_a(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
     return VoxelShapes.func_197873_a(0.0D, 0.0D, 0.0D, 0.9999D, 1.0D, 0.9999D);
  }

  public BlockState func_196271_a(BlockState p_196271_1_, Direction p_196271_2_, BlockState p_196271_3_, IWorld p_196271_4_, BlockPos p_196271_5_, BlockPos p_196271_6_) {
     if (((Boolean)p_196271_1_.get((Property)WATERLOGGED)).booleanValue()) {
       p_196271_4_.func_205219_F_().func_205360_a(p_196271_5_, Fluids.field_204546_a, Fluids.field_204546_a.func_205569_a((IWorldReader)p_196271_4_));
    }

     return super.func_196271_a(p_196271_1_, p_196271_2_, p_196271_3_, p_196271_4_, p_196271_5_, p_196271_6_);
  }

  public BlockState func_185499_a(BlockState state, Rotation rotation) {
     return (BlockState)state.func_206870_a((Property)field_176387_N, (Comparable)rotation.func_185831_a((Direction)state.get((Property)field_176387_N)));
  }

  public BlockState func_185471_a(BlockState state, Mirror mirrorType) {
     return (BlockState)state.func_206870_a((Property)field_176387_N, (Comparable)mirrorType.func_185803_b((Direction)state.get((Property)field_176387_N)));
  }

  public FluidState func_204507_t(BlockState state) {
     return ((Boolean)state.get((Property)WATERLOGGED)).booleanValue() ? Fluids.field_204546_a.func_207204_a(false) : super.func_204507_t(state);
  }

  public BlockState func_196258_a(BlockItemUseContext context) {
     Direction direction = context.func_196000_l();
     BlockPos blockpos = context.func_195995_a();
     FluidState fluidstate = context.func_195991_k().func_204610_c(blockpos);
     BlockState blockstate = context.func_195991_k().getBlockState(context.func_195995_a().func_177972_a(direction.getOpposite()));

     return (blockstate.func_203425_a((Block)this) && blockstate.get((Property)field_176387_N) == direction) ? (BlockState)((BlockState)getDefaultState().func_206870_a((Property)field_176387_N, (Comparable)direction.getOpposite())).func_206870_a((Property)WATERLOGGED, Boolean.valueOf((fluidstate.func_206886_c() == Fluids.field_204546_a))) : (BlockState)((BlockState)getDefaultState().func_206870_a((Property)field_176387_N, (Comparable)direction)).func_206870_a((Property)WATERLOGGED, Boolean.valueOf((fluidstate.func_206886_c() == Fluids.field_204546_a)));
  }

  protected void func_206840_a(StateContainer.Builder<Block, BlockState> state) {
     state.func_206894_a(new Property[] { (Property)field_176387_N, (Property)WATERLOGGED });
  }

  public boolean func_200123_i(BlockState state, IBlockReader reader, BlockPos pos) {
     return true;
  }

  public VoxelShape func_230322_a_(BlockState p_230322_1_, IBlockReader p_230322_2_, BlockPos p_230322_3_, ISelectionContext p_230322_4_) {
     return VoxelShapes.func_197880_a();
  }

  @OnlyIn(Dist.CLIENT)
  public float func_220080_a(BlockState p_220080_1_, IBlockReader p_220080_2_, BlockPos p_220080_3_) {
     return 1.0F;
  }
}


