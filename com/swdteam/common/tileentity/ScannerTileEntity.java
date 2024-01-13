package com.swdteam.common.tileentity;

import com.swdteam.client.render.ScannerPages;
import com.swdteam.client.tardis.data.ClientTardisCache;
import com.swdteam.common.init.DMBlockEntities;
import com.swdteam.common.init.DMDimensions;
import com.swdteam.common.init.DMNBTKeys;
import com.swdteam.common.tardis.TardisData;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntityType;

public class ScannerTileEntity extends DMTileEntityBase {
  private int screen;
  private long lastTime;
  private TardisData data;
  private TardisFlightData flightData;
  
   public ScannerTileEntity() { super((TileEntityType)DMBlockEntities.TILE_SCANNER.get());







    
     this.lastTime = 0L; } public ScannerTileEntity(TileEntityType<?> tileEntityTypeIn) { super(tileEntityTypeIn); this.lastTime = 0L; }



  
  public TardisData getTardisData() {
     return this.data;
  }
  
  public void setTardisData(TardisData data) {
     this.data = data;
  }

  
  public CompoundNBT func_189515_b(CompoundNBT compound) {
     compound.putInt(DMNBTKeys.SCANNER_SCREEN, this.screen);
     return super.func_189515_b(compound);
  }

  
  public void read(BlockState state, CompoundNBT compound) {
     int i = compound.getInt(DMNBTKeys.SCANNER_SCREEN);
     if (i > ScannerPages.PAGES.length - 1 || i < 0) { this.screen = 0; }
     else { this.screen = i; }
    
     super.read(state, compound);
  }
  
  public int getScreen() {
     return this.screen;
  }
  
  public void changeScreenRelative(int num) {
     this.screen += num;
     if (this.screen >= ScannerPages.PAGES.length) this.screen = 0; 
     if (this.screen < 0) this.screen = ScannerPages.PAGES.length - 1; 
  }
  
  public void renderCallUpdate() {
     if (this.world.getDimensionKey().equals(DMDimensions.TARDIS) && 
       System.currentTimeMillis() / 1000L % 2L == 0L && this.lastTime != System.currentTimeMillis() / 1000L) {
       this.lastTime = System.currentTimeMillis() / 1000L;
       this.data = ClientTardisCache.getTardisData(getPos());
       if (this.data != null)
         this.flightData = ClientTardisFlightCache.getTardisFlightData(this.data.getGlobalID()); 
    } 
  }
}


