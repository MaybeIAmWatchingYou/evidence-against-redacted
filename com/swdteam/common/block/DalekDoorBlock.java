package com.swdteam.common.block;

import com.swdteam.util.DoorPhase;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.Property;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class DalekDoorBlock extends DeadlockDoorBlock {
  boolean powered = false;

  public DalekDoorBlock(AbstractBlock.Properties properties) {
     super(properties);
  }

  public void changeDoor(World world, BlockState state, BlockPos pos, boolean powered) {
     this.powered = powered;
     updateDoor(world, (BlockState)state.func_206870_a((Property)POWERED, Boolean.valueOf(powered)), pos);
  }


  public void func_220069_a(BlockState state, World world, BlockPos pos, Block neighborBlock, BlockPos neighborPos, boolean isMoving) {
     if (neighborBlock == this)
       return;  if (state.get((Property)DOOR_STATE) == DoorPhase.MID || this.powered != ((Boolean)state.get((Property)POWERED)).booleanValue()) {
       this.powered = ((Boolean)state.get((Property)POWERED)).booleanValue();
       updateDoor(world, state, pos);
    }
  }
}


