package com.swdteam.common.sonic;

import com.swdteam.common.block.DalekDoorBlock;
import com.swdteam.common.init.DMSonicRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemStack;
import net.minecraft.state.Property;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;


public class SonicDoorInteraction
  implements DMSonicRegistry.ISonicInteraction
{
  public void interact(World world, PlayerEntity player, ItemStack stack, Object entityOrPostion) {
     if (entityOrPostion instanceof BlockPos) {
       BlockPos p = (BlockPos)entityOrPostion;
       BlockState state = world.getBlockState(p);
       if (state.getBlock() instanceof net.minecraft.block.TrapDoorBlock || state.getBlock() instanceof net.minecraft.block.DoorBlock) {
         openDoor(world, state, p, !((Boolean)state.get((Property)BlockStateProperties.field_208193_t)).booleanValue());
       } else if (state.getBlock() instanceof DalekDoorBlock) {
         ((DalekDoorBlock)state.getBlock()).changeDoor(world, state, p, !((Boolean)state.get((Property)DalekDoorBlock.POWERED)).booleanValue());
      }
    }
  }


  public int scanTime() {
     return 10;
  }

  public void openDoor(World worldIn, BlockState state, BlockPos pos, boolean open) {
     if (state.func_203425_a(state.getBlock()) && ((Boolean)state.get((Property)BlockStateProperties.field_208193_t)).booleanValue() != open) {
       worldIn.setBlockState(pos, (BlockState)state.func_206870_a((Property)BlockStateProperties.field_208193_t, Boolean.valueOf(open)));

       worldIn.func_217379_c(open ? 1008 : 1014, pos, 0);
       worldIn.func_205219_F_().func_205360_a(pos, Fluids.field_204546_a, Fluids.field_204546_a.func_205569_a((IWorldReader)worldIn));
    }
  }



  public boolean disableDefaultInteraction(World world, PlayerEntity player, ItemStack stack, Object entityOrPostion) {
     return false;
  }


  public SonicCategory getCategory() {
     return SonicCategory.REDSTONE;
  }
}


