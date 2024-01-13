package com.swdteam.common.block;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.Property;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;

public abstract class AbstractFacingBlock extends Block {
   public static final DirectionProperty FACING = HorizontalBlock.field_185512_D;
  
  protected AbstractFacingBlock(AbstractBlock.Properties properties) {
     super(properties);
     func_180632_j((BlockState)((BlockState)this.field_176227_L.func_177621_b()).func_206870_a((Property)FACING, (Comparable)Direction.NORTH));
  }
  
  public BlockState func_196258_a(BlockItemUseContext context) {
     return (BlockState)getDefaultState().func_206870_a((Property)FACING, (Comparable)context.func_195992_f().getOpposite());
  }






  
  public BlockState func_185499_a(BlockState state, Rotation rot) {
     return (BlockState)state.func_206870_a((Property)FACING, (Comparable)rot.func_185831_a((Direction)state.get((Property)FACING)));
  }





  
  public BlockState func_185471_a(BlockState state, Mirror mirrorIn) {
     return state.func_185907_a(mirrorIn.func_185800_a((Direction)state.get((Property)FACING)));
  }

  
  protected void func_206840_a(StateContainer.Builder<Block, BlockState> builder) {
     builder.func_206894_a(new Property[] { (Property)FACING });
  }
}


