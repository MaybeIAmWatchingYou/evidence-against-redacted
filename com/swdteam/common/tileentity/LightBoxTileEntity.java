package com.swdteam.common.tileentity;

import com.swdteam.common.init.DMBlockEntities;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.AxisAlignedBB;

public class LightBoxTileEntity
  extends DMTileEntityBase {
  public LightBoxTileEntity() {
     super((TileEntityType)DMBlockEntities.TILE_LIGHT_BOX.get());
  }


  public AxisAlignedBB getRenderBoundingBox() {
     return super.getRenderBoundingBox().func_72314_b(0.0D, 1.0D, 0.0D);
  }
}


