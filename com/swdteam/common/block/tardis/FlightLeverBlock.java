package com.swdteam.common.block.tardis;

import com.swdteam.common.block.AbstractLeverBlock;
import com.swdteam.common.init.DMDimensions;
import com.swdteam.common.init.DMSoundEvents;
import com.swdteam.common.init.DMTardis;
import com.swdteam.common.tardis.TardisData;
import com.swdteam.common.tardis.actions.TardisActionList;
import com.swdteam.util.ChatUtil;
import com.swdteam.util.ItemUtils;
import java.util.List;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.Property;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

public class FlightLeverBlock extends AbstractLeverBlock {
   protected static final VoxelShape SHAPE = Block.func_208617_a(2.0D, 0.0D, 2.0D, 14.0D, 12.0D, 14.0D);
  
  public FlightLeverBlock(AbstractBlock.Properties properties) {
     super(properties);
  }


  
  public ActionResultType func_225533_a_(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult p_225533_6_) {
     if (handIn == Hand.MAIN_HAND && !worldIn.isRemote) {
       if (worldIn.getDimensionKey().equals(DMDimensions.TARDIS)) {
         TardisData data = DMTardis.getTardisFromInteriorPos(pos);
         if (data.isInFlight()) {
           if (data.timeLeft() == 0.0D) {
             if (TardisActionList.remat(player, worldIn, data)) {
               worldIn.playSound((PlayerEntity)null, pos.getX(), pos.getY(), pos.getZ(), (SoundEvent)DMSoundEvents.TARDIS_REMAT.get(), SoundCategory.BLOCKS, 1.0F, 1.0F);
               switchLever(state, worldIn, pos);
            } 
          } else {
             int seconds = (int)data.timeLeft();
             String s = seconds + "s";
             ChatUtil.sendError(player, (IFormattableTextComponent)new TranslationTextComponent("notice.dalekmod.tardis.traveling", new Object[] { new StringTextComponent(s) }), ChatUtil.MessageType.CHAT);
          }
        
         } else if (TardisActionList.demat(player, worldIn, data)) {
           worldIn.playSound((PlayerEntity)null, pos.getX(), pos.getY(), pos.getZ(), (SoundEvent)DMSoundEvents.TARDIS_DEMAT.get(), SoundCategory.BLOCKS, 1.0F, 1.0F);
           switchLever(state, worldIn, pos);
        } 
      } else {
        
         switchLever(state, worldIn, pos);
      } 
    }
    
     return ActionResultType.CONSUME;
  }
  
  public void switchLever(BlockState state, World worldIn, BlockPos pos) {
     worldIn.setBlockState(pos, (BlockState)state.func_206870_a((Property)PULLED, Boolean.valueOf(!((Boolean)state.get((Property)PULLED)).booleanValue())));
     worldIn.playSound((PlayerEntity)null, pos.getX(), pos.getY(), pos.getZ(), (SoundEvent)DMSoundEvents.TARDIS_CONTROLS_LEVER.get(), SoundCategory.BLOCKS, 1.0F, 1.0F);
  }

  
  public VoxelShape func_220053_a(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
     return SHAPE;
  }

  
  public VoxelShape func_220071_b(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
     return SHAPE;
  }
  
  public void appendHoverText(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
     func_190948_a(stack, (IBlockReader)worldIn, tooltip, flagIn);
     ItemUtils.addText(tooltip, "TARDIS Controls", TextFormatting.BLUE);
  }
  
  public BlockState func_196271_a(BlockState state1, Direction dir, BlockState state2, IWorld world, BlockPos pos1, BlockPos pos2) {
     return (dir == Direction.DOWN && !func_196260_a(state2, (IWorldReader)world, pos1)) ? Blocks.AIR.getDefaultState() : super.func_196271_a(state1, dir, state2, world, pos1, pos2);
  }
  
  public boolean func_196260_a(BlockState state, IWorldReader reader, BlockPos pos) {
     return func_220055_a(reader, pos.func_177977_b(), Direction.UP);
  }
}


