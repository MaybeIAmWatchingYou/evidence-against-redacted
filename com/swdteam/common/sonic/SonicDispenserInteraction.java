package com.swdteam.common.sonic;

import com.swdteam.common.init.DMSonicRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;


public class SonicDispenserInteraction
  implements DMSonicRegistry.ISonicInteraction
{
  public void interact(World world, PlayerEntity player, ItemStack stack, Object entityOrPostion) {
     if (entityOrPostion instanceof BlockPos) {
       BlockPos p = (BlockPos)entityOrPostion;
       BlockState state = world.getBlockState(p);
       world.func_205220_G_().func_205360_a(p, state.getBlock(), 1);
    }
  }



  public int scanTime() {
     return 4;
  }



  public boolean disableDefaultInteraction(World world, PlayerEntity player, ItemStack stack, Object entityOrPostion) {
     return true;
  }


  public SonicCategory getCategory() {
     return SonicCategory.REDSTONE;
  }
}


