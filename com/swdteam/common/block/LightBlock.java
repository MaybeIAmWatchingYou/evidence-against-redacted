package com.swdteam.common.block;

import java.util.function.Supplier;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.FluidState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockDisplayReader;

public class LightBlock
  extends RotatableTileEntityBase {
  public LightBlock(Supplier<TileEntity> tileEntitySupplier, AbstractBlock.Properties properties, int light) {
     super(tileEntitySupplier, properties.func_235838_a_(state -> light));
  }



  public LightBlock(Supplier<TileEntity> tileEntitySupplier, AbstractBlock.Properties properties) {
     super(tileEntitySupplier, properties);
  }


  public boolean shouldDisplayFluidOverlay(BlockState state, IBlockDisplayReader world, BlockPos pos, FluidState fluidState) {
     return false;
  }
}


