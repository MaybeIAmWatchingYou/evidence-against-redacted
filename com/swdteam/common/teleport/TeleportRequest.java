package com.swdteam.common.teleport;

import com.swdteam.common.tardis.Location;
import net.minecraft.entity.Entity;


public class TeleportRequest
{
  private Location location;
  private boolean shouldRemove = false;
  private TeleportTask task;
  
  public TeleportRequest(Location location) {
     this.location = location;
  }
  
  public Location getLocation() {
     return this.location;
  }
  
  public TeleportRequest(Location location, TeleportTask task) {
     this.location = location;
     this.task = task;
  }
  
  public TeleportTask getTask() {
     return this.task;
  }
  
  public void markRemoved() {
     this.shouldRemove = true;
  }
  
  public boolean shouldRemove() {
     return this.shouldRemove;
  }
  
  public static interface TeleportTask {
    void onTeleport(Entity param1Entity, Location param1Location);
  }
}


