package com.swdteam.common.tileentity.tardis;

import com.swdteam.client.tardis.data.ClientTardisCache;
import com.swdteam.client.tardis.data.ClientTardisFlightCache;
import com.swdteam.common.init.DMBlockEntities;
import com.swdteam.common.init.DMDimensions;
import com.swdteam.common.init.DMNBTKeys;
import com.swdteam.common.tardis.TardisData;
import com.swdteam.common.tardis.TardisFlightData;
import com.swdteam.common.tileentity.DMTileEntityBase;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntityType;

public class CoordPanelTileEntity
  extends DMTileEntityBase {
   private long lastTime = 0L;
  private TardisData data;
  private TardisFlightData flightData;
   public int incrementValue = 1;
  
  public CoordPanelTileEntity() {
     super((TileEntityType)DMBlockEntities.TILE_COORD_PANEL.get());
  }

  
  public void read(BlockState state, CompoundNBT compound) {
     if (compound.func_74764_b(DMNBTKeys.COORD_INCREMENT)) {
       this.incrementValue = compound.getInt(DMNBTKeys.COORD_INCREMENT);
    }
     super.read(state, compound);
  }

  
  public CompoundNBT func_189515_b(CompoundNBT compound) {
     compound.putInt(DMNBTKeys.COORD_INCREMENT, this.incrementValue);
     return super.func_189515_b(compound);
  }
  
  public void renderCallUpdate() {
     if (this.world.getDimensionKey().equals(DMDimensions.TARDIS) &&
       System.currentTimeMillis() / 1000L % 2L == 0L && this.lastTime != System.currentTimeMillis() / 1000L) {
       this.lastTime = System.currentTimeMillis() / 1000L;
       this.data = ClientTardisCache.getTardisData(getPos());
       if (this.data != null) setFlightData(ClientTardisFlightCache.getTardisFlightData(this.data.getGlobalID()));
    
    } 
  }
  
  public TardisData getTardisData() {
     return this.data;
  }
  
  public TardisFlightData getFlightData() {
     return this.flightData;
  }
  
  public void setFlightData(TardisFlightData flightData) {
     this.flightData = flightData;
  }
}


