package com.swdteam.common.init;

import com.swdteam.common.tardis.Tardis;
import com.swdteam.common.tardis.TardisData;
import com.swdteam.common.tardis.TardisInterior;
import com.swdteam.common.tardis.TardisSaveHandler;
import com.swdteam.common.tardis.data.TardisDataPool;
import com.swdteam.common.tardis.data.UserTardises;
import com.swdteam.main.DalekMod;
import com.swdteam.network.NetworkHandler;
import com.swdteam.network.packets.PacketOpenGui;
import com.swdteam.util.math.Position;
import com.swdteam.util.world.Schematic;
import com.swdteam.util.world.SchematicUtils;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.apache.logging.log4j.Level;




public class DMTardis
{
   public static int INTERIOR_BOUNDS = 256;

   private static Map<Integer, TardisData> loadedTardises = new HashMap<>();
   private static Map<UUID, UserTardises> ownerLookups = new HashMap<>();


  public static TardisData addTardis(BlockPos pos, PlayerEntity player) {
     int tardisID = TardisDataPool.INSTANCE.generateNextFreeID();

     TardisData data = new TardisData(tardisID);
     if (player != null) {
       data.setOwnerName(player.getGameProfile().getName());
       data.setOwnerUUID(player.getGameProfile().getId());
       if (!ownerLookups.containsKey(player.getGameProfile())) {
         ownerLookups.put(player.getGameProfile().getId(), new UserTardises());
         saveOwnerLookup();
      }
       ((UserTardises)ownerLookups.get(player.getGameProfile().getId())).addTardis(tardisID);
    }



     data.setGenerated(false);

     loadedTardises.put(Integer.valueOf(data.getGlobalID()), data);

     if (player != null &&
       player instanceof ServerPlayerEntity) {
       NetworkHandler.sendTo((ServerPlayerEntity)player, new PacketOpenGui(pos, 3));
    }


     return data;
  }

  public static void generateInterior(MinecraftServer server, TardisData data) {
     Tardis interior = data.getTardisExterior();
     if (interior.getData() != null && interior.getData().getInterior() != null) {
       TardisInterior intt = interior.getData().getInterior();
       generateInterior(intt, server, data);
    }
  }

  public static void generateInterior(TardisInterior intt, MinecraftServer server, TardisData data) {
     BlockPos p = getXZForMap(data.getGlobalID());
     BlockPos pos2 = new BlockPos(p.getX() * INTERIOR_BOUNDS, 0, p.getZ() * INTERIOR_BOUNDS);

     Schematic schem = intt.getInterior();

     BlockPos generateAt = pos2.func_177982_a(INTERIOR_BOUNDS / 2 + intt.getBlockOffset().getX(), 128 + intt.getBlockOffset().getY(), INTERIOR_BOUNDS / 2 + intt.getBlockOffset().getZ()).func_177982_a(-(schem.getSchemDimX() / 2), -(schem.getSchemDimY() / 2), -(schem.getSchemDimZ() / 2));
     SchematicUtils.generateSchematic(SchematicUtils.GenerationQueue.TARDIS, (World)server.getWorld(DMDimensions.TARDIS), generateAt, schem);

     data.setInteriorSpawnPosition(new Position((generateAt.getX() + intt.getDoorOffset()[0]), (generateAt.getY() + intt.getDoorOffset()[1]), (generateAt.getZ() + intt.getDoorOffset()[2])));
     data.setInteriorSpawnRotation(intt.getDefaultSpawnRotation());
     data.setLighting(intt.getLighting().copy());

     data.save();
  }

  public static Map<Integer, TardisData> getLoadedTardises() {
     return loadedTardises;
  }

  public static Map<UUID, UserTardises> getOwnerLookup() {
     return ownerLookups;
  }


  public static BlockPos getXZForMap(int np) {
     int dx = 0;
     int dy = 1;

     int segment_length = 1;


     int x = 0;
     int y = 0;
     int segment_passed = 0;
     if (np == 0) {
       return new BlockPos(x, 0, y);
    }
     for (int n = 0; n < np; n++) {

       x += dx;
       y += dy;
       segment_passed++;

       if (segment_passed == segment_length) {

         segment_passed = 0;


         int buffer = dy;
         dy = -dx;
         dx = buffer;


         if (dx == 0) {
           segment_length++;
        }
      }
    }
     return new BlockPos(x, 0, y);
  }


  public static TardisData getTardisFromInteriorPos(BlockPos pos) {
     return getTardis(getIDForXZ(pos.getX(), pos.getZ()));
  }

  public static int getIDForXZ(int X, int Z) {
     int index = 0, x = 0, y = 0;
     int dx = 0;
     int dy = 1;

     int segment_length = 1;
     int segment_passed = 0;
     int fX = X;
     int fZ = Z;
     boolean found = false;
     long timecheck = System.currentTimeMillis();

    while (true) {
       if (System.currentTimeMillis() - timecheck > 10000L) {
         DalekMod.LOGGER.log(Level.ERROR, "Finding ID from XZ Coordinates is taking too long!");
        break;
      }
       if (fX >= x * INTERIOR_BOUNDS && fX <= INTERIOR_BOUNDS + x * INTERIOR_BOUNDS && fZ >= y * INTERIOR_BOUNDS && fZ <= INTERIOR_BOUNDS + y * INTERIOR_BOUNDS) {
         found = true;

        break;
      }
       x += dx;
       y += dy;
       segment_passed++;

       if (segment_passed == segment_length) {

         segment_passed = 0;


         int buffer = dy;
         dy = -dx;
         dx = buffer;


         if (dx == 0) {
           segment_length++;
        }
      }
       index++;
    }
     if (!found) index = 0;
     return index;
  }

  public TardisData getDatafromXZ(int X, int Z) {
     TardisData data = getTardis(getIDForXZ(X, Z));
     return data;
  }

  public static TardisData getTardis(int id) {
     if (loadedTardises.containsKey(Integer.valueOf(id))) {
       return loadedTardises.get(Integer.valueOf(id));
    }
     TardisSaveHandler.loadTardisData(id);
     return loadedTardises.get(Integer.valueOf(id));
  }


  public static void userTardisesSanityCheck(UUID uuid) {
     UserTardises usrTar = getUserTardises(uuid);
     if (usrTar == null)
       return;  if (usrTar.getTardises().size() < 1) TardisSaveHandler.loadUserTardises(uuid);
     for (Iterator<Integer> iterator = usrTar.getTardises().iterator(); iterator.hasNext(); ) { int id = ((Integer)iterator.next()).intValue();
       TardisData data = getTardis(id);
       if (!data.getOwner_uuid().equals(uuid)) {
         usrTar.removeTardis(id);
         getUserTardises(data.getOwner_uuid()).addTardis(id);
      }  }


     ownerLookups.put(uuid, usrTar);
  }

  public static void saveOwnerLookup() {
     ownerLookups.forEach((uuid, usrTar) -> TardisSaveHandler.saveUserTardises(uuid, usrTar));
  }



  public static UserTardises getUserTardises(UUID uuid) {
     if (ownerLookups.containsKey(uuid) && ownerLookups.get(uuid) != null) return ownerLookups.get(uuid);
     TardisSaveHandler.loadUserTardises(uuid);
     return ownerLookups.get(uuid);
  }

  public static void clearOwnerLookup() {
     ownerLookups.clear();
  }
}


