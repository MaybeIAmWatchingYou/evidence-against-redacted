package com.swdteam.client.tardis.data;

import com.swdteam.common.init.DMTardis;
import com.swdteam.common.tardis.TardisFlightData;
import com.swdteam.network.NetworkHandler;
import com.swdteam.network.packets.PacketRequestTardisFlightData;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.util.math.BlockPos;



public class ClientTardisFlightCache
{
   private static Map<Integer, TardisFlightData> data = new HashMap<>();

   public static TardisFlightData DEFAULT_DATA = new TardisFlightData();


  public static void requestData(int tardisID) {
     NetworkHandler.sendServerPacket(new PacketRequestTardisFlightData(tardisID));
  }


  public static TardisFlightData getTardisFlightData(int id) {
     if (data.containsKey(Integer.valueOf(id))) {
       return data.get(Integer.valueOf(id));
    }
     requestData(id);
     data.put(Integer.valueOf(id), DEFAULT_DATA);
     return DEFAULT_DATA;
  }


  public static boolean hasTardisFlightData(int id) {
     return data.containsKey(Integer.valueOf(id));
  }

  public static boolean hasTardisFlightData(BlockPos p) {
     return hasTardisFlightData(DMTardis.getIDForXZ(p.getX(), p.getZ()));
  }

  public static TardisFlightData getTardisFlightData(BlockPos p) {
     return getTardisFlightData(DMTardis.getIDForXZ(p.getX(), p.getZ()));
  }

  public static void addTardisFlightData(int id, TardisFlightData tData) {
     if (tData != null)
       data.put(Integer.valueOf(id), tData);
  }

  public static void clearTardisFlightData() {
     data.clear();
  }
}


