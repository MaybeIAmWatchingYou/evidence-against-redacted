package com.swdteam.common.init;

import com.google.gson.reflect.TypeToken;
import com.swdteam.client.tardis.data.ClientTardisCache;
import com.swdteam.common.tardis.TardisData;
import com.swdteam.main.DalekMod;
import com.swdteam.network.NetworkHandler;
import com.swdteam.network.packets.PacketUpdateFlightMode;
import com.swdteam.util.TeleportUtil;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import net.minecraft.dispenser.IPosition;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.GameType;
import net.minecraft.world.storage.FolderName;


public class DMFlightMode
{
   private static Map<UUID, FlightModeData> FLIGHTS = new HashMap<>();

  public static void addFlight(PlayerEntity player, FlightModeData data) {
     addFlight(player.getGameProfile().getId(), data, player.world.isRemote);
  }

  public static void addFlight(UUID player, FlightModeData data, boolean isClient) {
     FLIGHTS.put(player, data);
     if (!isClient) {
       NetworkHandler.sendToAllClients(new PacketUpdateFlightMode(player, data.getTardisID(), data.getPosX(), data.getPosY(), data.getPosZ(), true));
    }
  }

  public static boolean isInFlight(int tardisID) {
     for (Map.Entry<UUID, FlightModeData> entry : FLIGHTS.entrySet()) {
       UUID key = entry.getKey();
       FlightModeData data = entry.getValue();
       if (data.getTardisID() == tardisID) {
         return true;
      }
    }

     return false;
  }

  public static void clearClient() {
     FLIGHTS.clear();
  }


  public static void syncFlightsToPlayer(PlayerEntity player) {
     if (player.world.isRemote) {
      return;
    }

     for (Map.Entry<UUID, FlightModeData> entry : FLIGHTS.entrySet()) {
       UUID key = entry.getKey();
       FlightModeData data = entry.getValue();
       NetworkHandler.sendTo((ServerPlayerEntity)player, new PacketUpdateFlightMode(key, data.getTardisID(), data.getPosX(), data.getPosY(), data.getPosZ(), true));
    }
  }

  public static void removeFlight(PlayerEntity player) {
     removeFlight(player.getGameProfile().getId(), player.world.isRemote);
  }

  public static void removeFlight(UUID player, boolean isClient) {
     FLIGHTS.remove(player);
     if (!isClient) {
       NetworkHandler.sendToAllClients(new PacketUpdateFlightMode(player, 0, 0.0D, 0.0D, 0.0D, false));
    }
  }

  public static void playerExitFlight(ServerPlayerEntity player) {
     FlightModeData flightData = getFlightModeData(player.getGameProfile().getId());

     player.abilities.isFlying = false;

     if (player.field_71134_c.func_73081_b() != GameType.CREATIVE) {
       player.abilities.allowFlying = false;
    }

     player.abilities.allowEdit = true;

     player.sendPlayerAbilities();

     player.getServer().execute(() -> TeleportUtil.teleportPlayer((Entity)player, DMDimensions.TARDIS, (IPosition)new Vector3d(flightData.getPosX(), flightData.getPosY(), flightData.getPosZ()), 90.0F));
  }



  public static boolean isInFlight(PlayerEntity player) {
     return isInFlight(player.getGameProfile().getId());
  }




  public static boolean isInFlight(UUID player) {
     return FLIGHTS.containsKey(player);
  }

  public static int getTardisID(UUID player) {
     return (FLIGHTS.get(player)).tardisID;
  }

  public static FlightModeData getFlightModeData(UUID player) {
     return FLIGHTS.get(player);
  }




  public static TardisData getData(UUID player) {
     TardisData data = ClientTardisCache.getTardisData((FLIGHTS.get(player)).tardisID);
     return data;
  }

  public static void save(MinecraftServer server) {
     File file = new File(server.func_240776_a_(FolderName.field_237253_i_) + "/tardis/flight_mode.json");
     if (!file.getParentFile().exists()) file.getParentFile().mkdirs();
    try {
       FileWriter writer = new FileWriter(file);
       String s = DalekMod.GSON.toJson(FLIGHTS);
       writer.write(s);
       writer.close();
     } catch (Exception e) {
       e.printStackTrace();
    }
  }

  public static void load(MinecraftServer server) {
     File file = new File(server.func_240776_a_(FolderName.field_237253_i_) + "/tardis/flight_mode.json");
     if (!file.exists()) {
       file.getParentFile().mkdirs();
       FLIGHTS = new HashMap<>();
       save(server);

      return;
    }
    try {
       BufferedReader reader = new BufferedReader(new FileReader(file));
       StringBuffer buffer = new StringBuffer();
       String line = null;
       for (; (line = reader.readLine()) != null; buffer.append(line));

       Type type = (new TypeToken<Map<UUID, FlightModeData>>() {  }).getType();
       FLIGHTS = (Map<UUID, FlightModeData>)DalekMod.GSON.fromJson(buffer.toString(), type);

       reader.close();
     } catch (Exception e) {
       e.printStackTrace();
    }
  }
  public static class FlightModeData { private int tardisID;
    private double posX;
    private double posY;
    private double posZ;

    public FlightModeData(int tid, double x, double y, double z) {
       this.tardisID = tid;
       this.posX = x;
       this.posY = y;
       this.posZ = z;
    }

    public double getPosX() {
       return this.posX;
    }
    public double getPosY() {
       return this.posY;
    }
    public double getPosZ() {
       return this.posZ;
    }
    public int getTardisID() {
       return this.tardisID;
    } }

}


