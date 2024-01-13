package com.swdteam.common.block;

import com.swdteam.common.init.DMBlocks;
import com.swdteam.common.init.DMTags;
import com.swdteam.common.init.DMTranslationKeys;
import com.swdteam.util.ChatUtil;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.Property;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;


public interface IRust
{
   public static final BooleanProperty WAXED = BooleanProperty.func_177716_a("waxed");
   public static final Map<Block, Block> rustedMap = new HashMap<>();
  
  static void addRustedVariants() {
     rustedMap.put(DMBlocks.STEEL_BLOCK.get(), DMBlocks.RUSTED_STEEL_BLOCK.get());
    
     rustedMap.put(DMBlocks.WEAK_STEEL_BLOCK.get(), DMBlocks.RUSTED_WEAK_STEEL_BLOCK.get());
     rustedMap.put(DMBlocks.STEEL_GRATE.get(), DMBlocks.RUSTED_STEEL_GRATE.get());
     rustedMap.put(DMBlocks.STEEL_BEAMS.get(), DMBlocks.RUSTED_STEEL_BEAMS.get());
     rustedMap.put(DMBlocks.STEEL_STAIRS.get(), DMBlocks.RUSTED_STEEL_STAIRS.get());
     rustedMap.put(DMBlocks.STEEL_SLAB.get(), DMBlocks.RUSTED_STEEL_SLAB.get());
     rustedMap.put(DMBlocks.STEEL_GRATE_SLAB.get(), DMBlocks.RUSTED_STEEL_GRATE_SLAB.get());
     rustedMap.put(DMBlocks.STEEL_REINFORCED_WALLING.get(), DMBlocks.RUSTED_STEEL_REINFORCED_WALLING.get());
     rustedMap.put(DMBlocks.STEEL_WALL.get(), DMBlocks.RUSTED_STEEL_WALL.get());
     rustedMap.put(DMBlocks.STEEL_ROTOR.get(), DMBlocks.RUSTED_STEEL_ROTOR.get());
     rustedMap.put(DMBlocks.STEEL_BEAMS_ROUNDEL.get(), DMBlocks.RUSTED_STEEL_BEAMS_ROUNDEL.get());
     rustedMap.put(DMBlocks.FILLED_STEEL_BEAMS_ROUNDEL.get(), DMBlocks.FILLED_RUSTED_STEEL_BEAMS_ROUNDEL.get());
     rustedMap.put(DMBlocks.STEEL_REINFORCED_WALLING_ROUNDEL.get(), DMBlocks.RUSTED_STEEL_REINFORCED_WALLING_ROUNDEL.get());
  }
  
  default BlockState getRustedState(BlockState state) {
     if (rustedMap.get(state.getBlock()) == null) return null; 
     return ((Block)rustedMap.get(state.getBlock())).getDefaultState();
  }
  
  default boolean wax(World world, BlockPos pos, PlayerEntity player, Hand hand) {
     BlockState state = world.getBlockState(pos);
     ItemStack itemstack = player.getHeldItem(hand);
    
     if (((Boolean)state.get((Property)WAXED)).booleanValue()) {
       if (!world.isRemote) ChatUtil.sendError(player, (IFormattableTextComponent)DMTranslationKeys.BLOCK_WAX_ALREADY_WAXED, ChatUtil.MessageType.CHAT); 
       return false;
    } 
    
     if (!world.isRemote) ChatUtil.sendCompletedMsg(player, (IFormattableTextComponent)DMTranslationKeys.BLOCK_WAX_COMPLETED, ChatUtil.MessageType.CHAT);
    
     if (!player.isCreative()) itemstack.func_190918_g(1);
    
     world.setBlockState(pos, (BlockState)state.func_206870_a((Property)WAXED, Boolean.valueOf(true)));
     world.playSound(null, pos, SoundEvents.field_187615_H, SoundCategory.BLOCKS, 0.5F, 1.0F);
    
     return true;
  }

  
  default void rustTick(IRust rustState, BlockState state, ServerWorld world, BlockPos pos, Random rand) {
     if (!world.isRemote && 
       rustState.getRustedState(state) != null) {
       boolean isWaxed = ((Boolean)state.get((Property)WAXED)).booleanValue();
      
       if (!isWaxed) {
         boolean isRainedOn = world.func_175727_C(pos.func_177984_a());
         boolean isWaterLogged = (state.func_235901_b_((Property)BlockStateProperties.WATERLOGGED) && ((Boolean)state.get((Property)BlockStateProperties.WATERLOGGED)).booleanValue());
        
         if (isRainedOn || isWaterLogged) {
           world.setBlockState(pos, rustState.getRustedState(state));
          
          return;
        } 
         for (Direction dir : Direction.values()) {
           BlockPos neighborPos = pos.func_177972_a(dir);
           Block neighborBlock = world.getBlockState(neighborPos).getBlock();
          
           boolean nextToRust = DMTags.Blocks.RUSTED_BLOCKS.func_230235_a_(neighborBlock);
           boolean nextToWater = FluidTags.field_206959_a.func_230235_a_(world.func_204610_c(neighborPos).func_206886_c());
          
           if (nextToRust || nextToWater) {
             world.setBlockState(pos, rustState.getRustedState(state));
            return;
          } 
        } 
      } 
    } 
  }
}


