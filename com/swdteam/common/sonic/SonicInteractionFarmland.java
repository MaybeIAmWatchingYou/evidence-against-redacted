package com.swdteam.common.sonic;

import com.swdteam.common.init.DMSonicRegistry;
import net.minecraft.block.BeetrootBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CropsBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.Property;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SonicInteractionFarmland
  implements DMSonicRegistry.ISonicInteraction
{
  public void interact(World world, PlayerEntity player, ItemStack stack, Object entityOrPostion) {
     if (entityOrPostion instanceof BlockPos) {
       int i; BlockPos p = (BlockPos)entityOrPostion;
       BlockState state = world.getBlockState(p);


       if (state.getBlock() == Blocks.field_185773_cZ) { i = ((Integer)state.get((Property)BeetrootBlock.field_185531_a)).intValue(); }
       else { i = ((Integer)state.get((Property)CropsBlock.field_176488_a)).intValue(); }

       if (i < ((CropsBlock)state.getBlock()).func_185526_g()) {
         i++;
      }

       if (state.getBlock() == Blocks.field_185773_cZ) { world.setBlockState(p, (BlockState)state.func_206870_a((Property)BeetrootBlock.field_185531_a, Integer.valueOf(i))); }
       else { world.setBlockState(p, (BlockState)state.func_206870_a((Property)CropsBlock.field_176488_a, Integer.valueOf(i))); }

    }
  }

  public int scanTime() {
     return 2;
  }



  public boolean disableDefaultInteraction(World world, PlayerEntity player, ItemStack stack, Object entityOrPostion) {
     return false;
  }


  public SonicCategory getCategory() {
     return SonicCategory.FARMLAND;
  }
}


