package com.swdteam.common.tileentity;

import com.swdteam.common.init.DMBlockEntities;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.AxisAlignedBB;

public class LampPostTileEntity
  extends DMTileEntityBase {
  public LampPostTileEntity() {
     super((TileEntityType)DMBlockEntities.TILE_LAMP_POST.get());
  }

  
  public AxisAlignedBB getRenderBoundingBox() {
     return super.getRenderBoundingBox().func_72314_b(0.0D, 6.0D, 0.0D);
  }
}


