package com.swdteam.common.block.tardis;
import com.swdteam.common.block.AbstractLeverBlock;
import com.swdteam.common.init.DMDimensions;
import com.swdteam.common.init.DMSoundEvents;
import com.swdteam.common.init.DMTardis;
import com.swdteam.common.init.DMTranslationKeys;
import com.swdteam.common.tardis.TardisData;
import com.swdteam.common.tardis.data.TardisFlightPool;
import com.swdteam.util.ChatUtil;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.Property;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

public class FastReturnLeverBlock extends AbstractLeverBlock {
   protected static final VoxelShape SHAPE_NS = VoxelShapes.func_197878_a(Block.func_208617_a(4.0D, 0.0D, 0.0D, 12.0D, 1.0D, 16.0D), Block.func_208617_a(4.0D, 1.0D, 4.0D, 12.0D, 5.0D, 13.0D), IBooleanFunction.field_223244_o_);
   protected static final VoxelShape SHAPE_EW = VoxelShapes.func_197878_a(Block.func_208617_a(1.0D, 0.0D, 4.0D, 16.0D, 1.0D, 12.0D), Block.func_208617_a(4.0D, 1.0D, 4.0D, 13.0D, 5.0D, 12.0D), IBooleanFunction.field_223244_o_);

  public FastReturnLeverBlock(AbstractBlock.Properties properties) {
     super(properties);
  }



  public ActionResultType func_225533_a_(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult p_225533_6_) {
     if (handIn == Hand.MAIN_HAND) {
       worldIn.setBlockState(pos, (BlockState)state.func_206870_a((Property)PULLED, Boolean.valueOf(!((Boolean)state.get((Property)PULLED)).booleanValue())));
       worldIn.playSound((PlayerEntity)null, pos.getX(), pos.getY(), pos.getZ(), (SoundEvent)DMSoundEvents.TARDIS_CONTROLS_LEVER.get(), SoundCategory.BLOCKS, 1.0F, 1.0F);
       if (worldIn.getDimensionKey().equals(DMDimensions.TARDIS) &&
         !worldIn.isRemote) {
         TardisData data = DMTardis.getTardisFromInteriorPos(pos);

         if (data != null && data.getPreviousLocation() != null) {
           TardisFlightPool.updateFlight(data, data.getPreviousLocation());
        }
         ChatUtil.sendCompletedMsg(player, (IFormattableTextComponent)DMTranslationKeys.TARDIS_FAST_RETURN_SET, ChatUtil.MessageType.STATUS_BAR);
      }
    }

     return ActionResultType.CONSUME;
  }


  public VoxelShape func_220053_a(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
     switch ((Direction)state.get((Property)FACING)) { case EAST: case WEST:
         return SHAPE_EW; }
      return SHAPE_NS;
  }



  public VoxelShape func_220071_b(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
     switch ((Direction)state.get((Property)FACING)) { case EAST: case WEST:
         return SHAPE_EW; }
      return SHAPE_NS;
  }


  public BlockState func_196271_a(BlockState state1, Direction dir, BlockState state2, IWorld world, BlockPos pos1, BlockPos pos2) {
     return (dir == Direction.DOWN && !func_196260_a(state2, (IWorldReader)world, pos1)) ? Blocks.AIR.getDefaultState() : super.func_196271_a(state1, dir, state2, world, pos1, pos2);
  }

  public boolean func_196260_a(BlockState state, IWorldReader reader, BlockPos pos) {
     return func_220055_a(reader, pos.func_177977_b(), Direction.UP);
  }
}


