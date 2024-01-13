package com.swdteam.common.sonic;

import com.swdteam.common.init.DMBlocks;
import com.swdteam.common.init.DMSonicRegistry;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SonicRedstoneLampInteractionOn
  implements DMSonicRegistry.ISonicInteraction
{
  public void interact(World world, PlayerEntity player, ItemStack stack, Object entityOrPostion) {
     if (entityOrPostion instanceof BlockPos) {
       BlockPos p = (BlockPos)entityOrPostion;
       world.setBlockState(p, ((Block)DMBlocks.POWERED_REDSTONE_LAMP.get()).getDefaultState());
    } 
  }


  
  public int scanTime() {
     return 5;
  }


  
  public boolean disableDefaultInteraction(World world, PlayerEntity player, ItemStack stack, Object entityOrPostion) {
     return false;
  }

  
  public SonicCategory getCategory() {
     return SonicCategory.REDSTONE;
  }
}


