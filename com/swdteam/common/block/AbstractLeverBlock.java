package com.swdteam.common.block;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.Property;
import net.minecraft.state.StateContainer;

public abstract class AbstractLeverBlock extends AbstractRotateableWaterLoggableBlock {
   public static final BooleanProperty PULLED = BooleanProperty.func_177716_a("pulled");

  protected AbstractLeverBlock(AbstractBlock.Properties properties) {
     super(properties);
     func_180632_j((BlockState)getDefaultState().func_206870_a((Property)PULLED, Boolean.valueOf(false)));
  }


  protected void func_206840_a(StateContainer.Builder<Block, BlockState> builder) {
     super.func_206840_a(builder);
     builder.func_206894_a(new Property[] { (Property)PULLED });
  }


  public BlockRenderType func_149645_b(BlockState p_149645_1_) {
     return BlockRenderType.MODEL;
  }
}


