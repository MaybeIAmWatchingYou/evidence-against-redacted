package com.swdteam.common.tileentity.tardis;

import com.swdteam.common.init.DMBlockEntities;
import com.swdteam.common.init.DMSoundEvents;
import com.swdteam.common.tardis.TardisDoor;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.SoundEvent;

public class PoliceBoxDoorsTileEntity
  extends DoubleDoorsTileEntity {
  public PoliceBoxDoorsTileEntity() {
     super((TileEntityType)DMBlockEntities.TILE_POLICE_BOX_DOOR.get());
  }


  public SoundEvent getDoorSound(TardisDoor door, boolean opening) {
     return opening ? (SoundEvent)DMSoundEvents.TARDIS_POLICE_BOX_DOOR_OPEN.get() : (SoundEvent)DMSoundEvents.TARDIS_POLICE_BOX_DOOR_CLOSE.get();
  }
}


