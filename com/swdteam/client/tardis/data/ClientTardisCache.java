package com.swdteam.client.tardis.data;

import com.swdteam.common.init.DMTardis;
import com.swdteam.common.tardis.TardisData;
import com.swdteam.network.NetworkHandler;
import com.swdteam.network.packets.PacketRequestTardis;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.util.math.BlockPos;



public class ClientTardisCache
{
   public static double GLOBAL_TARDIS_LIGHTING = 0.0D;
   public static double GLOBAL_TARDIS_LIGHTING_LEFT = 0.0D;
   public static double GLOBAL_TARDIS_LIGHTING_RIGHT = 0.0D;

   private static Map<Integer, TardisData> data = new HashMap<>();

   public static TardisData DEFAULT_DATA = new TardisData(0);


  public static void requestData(int tardisID) {
     NetworkHandler.sendServerPacket(new PacketRequestTardis(tardisID));
  }


  public static TardisData getTardisData(int id) {
     if (data.containsKey(Integer.valueOf(id))) {
       return data.get(Integer.valueOf(id));
    }
     requestData(id);
     data.put(Integer.valueOf(id), DEFAULT_DATA);
     return DEFAULT_DATA;
  }


  public static boolean hasTardisData(int id) {
     return data.containsKey(Integer.valueOf(id));
  }

  public static boolean hasTardisData(BlockPos p) {
     return hasTardisData(DMTardis.getIDForXZ(p.getX(), p.getZ()));
  }

  public static TardisData getTardisData(BlockPos p) {
     return getTardisData(DMTardis.getIDForXZ(p.getX(), p.getZ()));
  }

  public static void addTardisData(TardisData tData) {
     if (tData != null)
       data.put(Integer.valueOf(tData.getGlobalID()), tData);
  }

  public static void clearTardisData() {
     data.clear();
  }

  public static Map<Integer, TardisData> getCache() {
     return data;
  }
}


