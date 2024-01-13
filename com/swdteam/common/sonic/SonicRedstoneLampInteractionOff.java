package com.swdteam.common.sonic;

import com.swdteam.common.init.DMSonicRegistry;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;


public class SonicRedstoneLampInteractionOff
  implements DMSonicRegistry.ISonicInteraction
{
  public void interact(World world, PlayerEntity player, ItemStack stack, Object entityOrPostion) {
     if (entityOrPostion instanceof BlockPos) {
       BlockPos p = (BlockPos)entityOrPostion;
       world.setBlockState(p, Blocks.field_150379_bu.getDefaultState());
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


