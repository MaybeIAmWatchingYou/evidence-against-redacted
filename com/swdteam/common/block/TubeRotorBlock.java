package com.swdteam.common.block;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.AbstractGlassBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.Property;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class TubeRotorBlock extends AbstractGlassBlock {
   public static final EnumProperty<Direction.Axis> AXIS = BlockStateProperties.field_208148_A;

  public TubeRotorBlock(AbstractBlock.Properties properties, int light) {
     super(properties.func_235838_a_(state -> light));


     func_180632_j((BlockState)getDefaultState().func_206870_a((Property)AXIS, (Comparable)Direction.Axis.Y));
  }

  public BlockState func_185499_a(BlockState state, Rotation rotation) {
     switch (rotation) {
      case COUNTERCLOCKWISE_90:
      case CLOCKWISE_90:
         switch ((Direction.Axis)state.get((Property)AXIS)) { case COUNTERCLOCKWISE_90:
             return (BlockState)state.func_206870_a((Property)AXIS, (Comparable)Direction.Axis.Z);
           case CLOCKWISE_90: return (BlockState)state.func_206870_a((Property)AXIS, (Comparable)Direction.Axis.X); }
          return state;
    }
     return state;
  }


  protected void func_206840_a(StateContainer.Builder<Block, BlockState> state) {
     state.func_206894_a(new Property[] { (Property)AXIS });
  }

  public BlockState func_196258_a(BlockItemUseContext context) {
     return (BlockState)getDefaultState().func_206870_a((Property)AXIS, (Comparable)context.func_196000_l().func_176740_k());
  }

  public boolean func_200123_i(BlockState state, IBlockReader reader, BlockPos pos) {
     return true;
  }

  @OnlyIn(Dist.CLIENT)
  public boolean func_200122_a(BlockState thisState, BlockState otherState, Direction direction) {
     return (otherState.getBlock() instanceof TubeRotorBlock);
  }
}


