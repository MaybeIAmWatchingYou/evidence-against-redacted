package com.swdteam.common.block;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.AbstractGlassBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.Property;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;

public class WaterLoggableGlassBlock extends AbstractGlassBlock implements IWaterLoggable {
   public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

  public WaterLoggableGlassBlock(AbstractBlock.Properties properties) {
     super(properties);
     func_180632_j((BlockState)getDefaultState().func_206870_a((Property)WATERLOGGED, Boolean.valueOf(false)));
  }


  public VoxelShape func_220071_b(BlockState p_220071_1_, IBlockReader p_220071_2_, BlockPos p_220071_3_, ISelectionContext p_220071_4_) {
     return VoxelShapes.func_197873_a(0.0D, 0.0D, 0.0D, 0.9999D, 1.0D, 0.9999D);
  }

  public BlockState func_196258_a(BlockItemUseContext context) {
     BlockPos blockpos = context.func_195995_a();
     FluidState fluidstate = context.func_195991_k().func_204610_c(blockpos);
     return (BlockState)super.func_196258_a(context).func_206870_a((Property)WATERLOGGED, Boolean.valueOf((fluidstate.func_206886_c() == Fluids.field_204546_a)));
  }

  public BlockState func_196271_a(BlockState p_196271_1_, Direction p_196271_2_, BlockState p_196271_3_, IWorld p_196271_4_, BlockPos p_196271_5_, BlockPos p_196271_6_) {
     if (((Boolean)p_196271_1_.get((Property)WATERLOGGED)).booleanValue()) {
       p_196271_4_.func_205219_F_().func_205360_a(p_196271_5_, Fluids.field_204546_a, Fluids.field_204546_a.func_205569_a((IWorldReader)p_196271_4_));
    }

     return super.func_196271_a(p_196271_1_, p_196271_2_, p_196271_3_, p_196271_4_, p_196271_5_, p_196271_6_);
  }

  public FluidState func_204507_t(BlockState state) {
     return ((Boolean)state.get((Property)WATERLOGGED)).booleanValue() ? Fluids.field_204546_a.func_207204_a(false) : super.func_204507_t(state);
  }

  protected void func_206840_a(StateContainer.Builder<Block, BlockState> state) {
     state.func_206894_a(new Property[] { (Property)WATERLOGGED });
  }
}


