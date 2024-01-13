package com.swdteam.common.block;

import java.util.function.Supplier;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.Property;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.registries.ForgeRegistries;

public class ForceFieldFloorBlock extends TileEntityBaseBlock {
   public static final IntegerProperty LIQUID = IntegerProperty.func_177719_a("liquid", 0, ForgeRegistries.FLUIDS.getValues().size() - 1);

  public ForceFieldFloorBlock(Supplier<TileEntity> tileEntitySupplier, AbstractBlock.Properties properties) {
     super(tileEntitySupplier, properties);
     func_180632_j((BlockState)getDefaultState().func_206870_a((Property)LIQUID, Integer.valueOf(0)));
  }


  public VoxelShape func_220053_a(BlockState state, IBlockReader reader, BlockPos pos, ISelectionContext context) {
     return VoxelShapes.func_197873_a(0.0D, 0.999D, 0.0D, 1.0D, 1.0D, 1.0D);
  }

  protected void func_206840_a(StateContainer.Builder<Block, BlockState> state) {
     super.func_206840_a(state);
     state.func_206894_a(new Property[] { (Property)LIQUID });
  }


  public BlockRenderType func_149645_b(BlockState p_149645_1_) {
     return BlockRenderType.MODEL;
  }


  public FluidState func_204507_t(BlockState state) {
     Fluid fluid = (Fluid)ForgeRegistries.FLUIDS.getValues().toArray()[((Integer)state.get((Property)LIQUID)).intValue()];
     return fluid.func_207188_f();
  }


  public boolean func_200123_i(BlockState state, IBlockReader reader, BlockPos pos) {
     return true;
  }
}


