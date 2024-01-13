package com.swdteam.common.sonic;

import com.swdteam.common.init.DMBlocks;
import com.swdteam.common.init.DMSonicRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SonicTNTInteraction
  implements DMSonicRegistry.ISonicInteraction {
  public void interact(World world, PlayerEntity player, ItemStack stack, Object entityOrPostion) {
     if (entityOrPostion instanceof BlockPos) {
       BlockPos p = (BlockPos)entityOrPostion;
       world.setBlockState(p, Blocks.AIR.getDefaultState());
       Blocks.TNT.catchFire(world.getBlockState(p), world, p, Direction.UP, (LivingEntity)player);
       ((Block)DMBlocks.CLASSIC_TNT.get()).catchFire(world.getBlockState(p), world, p, Direction.UP, (LivingEntity)player);
    }
  }



  public int scanTime() {
     return 10;
  }



  public boolean disableDefaultInteraction(World world, PlayerEntity player, ItemStack stack, Object entityOrPostion) {
     return false;
  }


  public SonicCategory getCategory() {
     return SonicCategory.TNT;
  }
}


