package com.swdteam.common.block.tardis;

import com.swdteam.common.block.TileEntityBaseBlock;
import java.util.function.Supplier;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.tileentity.TileEntity;

public class VisualizerPanelBlock
  extends TileEntityBaseBlock.WaterLoggable
{
  public VisualizerPanelBlock(Supplier<TileEntity> tileEntitySupplier, AbstractBlock.Properties properties) {
     super(tileEntitySupplier, properties);
  }


  
  public BlockRenderType func_149645_b(BlockState p_149645_1_) {
     return BlockRenderType.INVISIBLE;
  }
}


