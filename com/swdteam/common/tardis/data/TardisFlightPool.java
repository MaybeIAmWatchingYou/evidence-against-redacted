package com.swdteam.common.tardis.data;

import com.google.gson.reflect.TypeToken;
import com.swdteam.common.tardis.Location;
import com.swdteam.common.tardis.TardisData;
import com.swdteam.common.tardis.TardisFlightData;
import com.swdteam.main.DalekMod;
import com.swdteam.network.NetworkHandler;
import com.swdteam.network.packets.PacketSendFlightData;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Direction;
import net.minecraft.util.RegistryKey;
import net.minecraft.world.World;
import net.minecraft.world.storage.FolderName;



public class TardisFlightPool
{
   private static Map<Integer, TardisFlightData> pool = new HashMap<>();

  public static void addFlight(TardisData data) {
     if (data != null) {
       if (!pool.containsKey(Integer.valueOf(data.getGlobalID()))) {

         TardisFlightData flight = new TardisFlightData();
         flight.setDimensionKey(data.getCurrentLocation().dimensionWorldKey());
         flight.setPos(data.getCurrentLocation().getPosition().func_82615_a(), Direction.Axis.X);
         flight.setPos(data.getCurrentLocation().getPosition().func_82617_b(), Direction.Axis.Y);
         flight.setPos(data.getCurrentLocation().getPosition().func_82616_c(), Direction.Axis.Z);
         flight.setEnteredFlight(true);
         flight.setWaypoints(null);
         pool.put(Integer.valueOf(data.getGlobalID()), flight);
      } else {
         TardisFlightData flight = pool.get(Integer.valueOf(data.getGlobalID()));
         flight.setEnteredFlight(true);
         if (flight.dimensionWorldKey() == null) {
           flight.setDimensionKey(data.getCurrentLocation().dimensionWorldKey());
        }
         flight.setWaypoints(null);
      }
       NetworkHandler.sendToAllClients(new PacketSendFlightData(data.getGlobalID(), pool.get(Integer.valueOf(data.getGlobalID()))));
    }
  }

  public static TardisFlightData getFlightData(TardisData data) {
     if (!pool.containsKey(Integer.valueOf(data.getGlobalID()))) {
       updateFlight(data, data.getCurrentLocation().getPosition().func_82615_a(), data.getCurrentLocation().getPosition().func_82617_b(), data.getCurrentLocation().getPosition().func_82616_c(), data.getCurrentLocation().dimensionWorldKey());
    }
     if (data != null && 
       pool.containsKey(Integer.valueOf(data.getGlobalID()))) {
       return pool.get(Integer.valueOf(data.getGlobalID()));
    }

     return null;
  }

  public static void completeFlight(MinecraftServer server, TardisData data) {
     if (data != null && 
       pool.containsKey(Integer.valueOf(data.getGlobalID()))) {
       pool.remove(Integer.valueOf(data.getGlobalID()));
       save(server);
    }
  }


  public static void updateFlight(TardisData data, double x, double y, double z, RegistryKey<World> key) {
     if (data != null) {
       if (pool.containsKey(Integer.valueOf(data.getGlobalID()))) {
         TardisFlightData flight = pool.get(Integer.valueOf(data.getGlobalID()));
         flight.setDimensionKey(key);
         flight.setPos(x, Direction.Axis.X);
         flight.setPos(y, Direction.Axis.Y);
         flight.setPos(z, Direction.Axis.Z);
         flight.setWaypoints(null);
      } else {
         TardisFlightData flight = new TardisFlightData();
         flight.setDimensionKey(key);
         flight.setPos(x, Direction.Axis.X);
         flight.setPos(y, Direction.Axis.Y);
         flight.setPos(z, Direction.Axis.Z);
         flight.setWaypoints(null);
         pool.put(Integer.valueOf(data.getGlobalID()), flight);
      }
       NetworkHandler.sendToAllClients(new PacketSendFlightData(data.getGlobalID(), pool.get(Integer.valueOf(data.getGlobalID()))));
    }
  }

  public static void updateFlight(TardisData data, Location loc) {
     updateFlight(data, loc.getPosition().func_82615_a(), loc.getPosition().func_82617_b(), loc.getPosition().func_82616_c(), loc.dimensionWorldKey());
  }

  public static boolean inFlight(TardisData data) {
     if (data == null) {
       return false;
    }
     return (pool.containsKey(Integer.valueOf(data.getGlobalID())) && ((TardisFlightData)pool.get(Integer.valueOf(data.getGlobalID()))).hasEnteredFlight());
  }

  public static void save(MinecraftServer server) {
     File file = new File(server.func_240776_a_(FolderName.field_237253_i_) + "/tardis/flight_pool.json");

     if (!file.getParentFile().exists()) {
       file.getParentFile().mkdirs();
    }

     String json = DalekMod.GSON.toJson(pool);

    try {
       FileWriter writer = new FileWriter(file);
       writer.write(json);
       writer.close();
     } catch (Exception e) {
       e.printStackTrace();
    }
  }

  public static void load(MinecraftServer server) {
     if (pool != null) {
       pool.clear();
    }

     File file = new File(server.func_240776_a_(FolderName.field_237253_i_) + "/tardis/flight_pool.json");

     if (!file.getParentFile().exists()) {
       file.getParentFile().mkdirs();
    }

     if (!file.exists()) {
      return;
    }


    try {
       BufferedReader reader = new BufferedReader(new FileReader(file));
       String s = null;
       StringBuffer sb = new StringBuffer();

       while ((s = reader.readLine()) != null) {
         sb.append(s);
      }

       Type typeOfHashMap = (new TypeToken<Map<Integer, TardisFlightData>>() {  }).getType();
       Map<Integer, TardisFlightData> newMap = (Map<Integer, TardisFlightData>)DalekMod.GSON.fromJson(sb.toString(), typeOfHashMap);

       if (newMap != null) {
         pool = newMap;
      }
    }
     catch (Exception e) {
       e.printStackTrace();
    }
  }

  public static void sendUpdates(int globalID) {
     if (pool.containsKey(Integer.valueOf(globalID)))
       NetworkHandler.sendToAllClients(new PacketSendFlightData(globalID, pool.get(Integer.valueOf(globalID)))); 
  }
}


