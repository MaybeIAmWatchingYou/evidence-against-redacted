package com.swdteam.common.tileentity.tardis;

import com.swdteam.common.init.DMBlockEntities;
import com.swdteam.common.init.DMDimensions;
import com.swdteam.common.init.DMFlightMode;
import com.swdteam.common.init.DMTardis;
import com.swdteam.common.tardis.TardisData;
import com.swdteam.common.tardis.actions.TardisActionList;
import com.swdteam.common.tileentity.DMTileEntityBase;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.vector.Vector3d;

public class TardisDoorHitboxTileEntity
  extends DMTileEntityBase
  implements ITickableTileEntity
{
   public static Map<Entity, TardisData> TELEPORT_REQUESTS = new HashMap<>();

  public TardisDoorHitboxTileEntity() {
     super((TileEntityType)DMBlockEntities.TILE_TARDIS_DOOR_HITBOX.get());






     this.t = 0;
  }

  public void tick() {
     if (!this.world.isRemote &&
       this.world.getDimensionKey() == DMDimensions.TARDIS) {
       bounds = (new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D)).grow(0.0D);
       List<Entity> entities = this.world.getEntitiesWithinAABB(Entity.class, bounds.func_186670_a(getPos()));

       Predicate<Entity> isRiding = entity -> entity.func_184218_aH();


       entities.removeIf(isRiding);
       if (!entities.isEmpty()) {
         Entity e = entities.get(0);
         if (this.t > 0) {

           Vector3d dist = new Vector3d(getPos().getX() + 0.5D - e.getPosX(), getPos().getY() - e.getPosY(), getPos().getZ() + 0.5D - e.getPosZ());

           Vector3d velo = new Vector3d(e.getPosX() - this.xo, e.getPosY() - this.yo, e.getPosZ() - this.zo);

           Vector3d dpve = dist.func_178787_e(velo);

           if (this.t > 100 || dist.func_72433_c() < dpve.func_72433_c()) {
             TardisData data = DMTardis.getTardisFromInteriorPos(getPos());
             TardisActionList.addForceField(this.world.getServer().getWorld(data.getCurrentLocation().dimensionWorldKey()), data.getCurrentLocation().getBlockPosition());
             if (data != null && !data.isInFlight() && !data.isLocked() && !DMFlightMode.isInFlight(data.getGlobalID()) &&
               !TELEPORT_REQUESTS.containsKey(e)) {
               TELEPORT_REQUESTS.put(e, data);
               this.t = 0;
            }
          }
        } else {

           this.xo = e.getPosX();
           this.yo = e.getPosY();
           this.zo = e.getPosZ();
        }
         this.t++;
      } else {
         this.t = 0;
      }
    }
  }

  public static AxisAlignedBB bounds = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
  private double xo;
  private double yo;
  private double zo;
  private int t;
}


