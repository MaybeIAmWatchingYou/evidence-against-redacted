package com.swdteam.common.tileentity.tardis;

import com.swdteam.client.tardis.data.ClientTardisCache;
import com.swdteam.common.init.DMBlockEntities;
import com.swdteam.common.init.DMDimensions;
import com.swdteam.common.tardis.TardisData;
import com.swdteam.common.tileentity.DMTileEntityBase;
import net.minecraft.tileentity.TileEntityType;

public class LightAlternatorTileEntity extends DMTileEntityBase {
   private long lastTime = 0L;
  private TardisData data;

  public LightAlternatorTileEntity() {
     super((TileEntityType)DMBlockEntities.TILE_LIGHT_ALTERNATOR.get());
  }

  public void renderCallUpdate() {
     if (this.world.getDimensionKey().equals(DMDimensions.TARDIS) && 
       System.currentTimeMillis() / 1000L % 2L == 0L && this.lastTime != System.currentTimeMillis() / 1000L) {
       this.lastTime = System.currentTimeMillis() / 1000L;
       this.data = ClientTardisCache.getTardisData(getPos());
    }
  }


  public TardisData getTardisData() {
     return this.data;
  }
}


