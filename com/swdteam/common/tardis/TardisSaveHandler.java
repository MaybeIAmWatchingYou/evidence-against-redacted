package com.swdteam.common.tardis;

import com.swdteam.common.init.DMTardis;
import com.swdteam.common.tardis.data.TardisDataPool;
import com.swdteam.common.tardis.data.UserTardises;
import com.swdteam.main.DalekMod;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.storage.FolderName;




public class TardisSaveHandler
{
  public static void loadTardisPool(MinecraftServer server) {
     File file = new File(server.func_240776_a_(FolderName.field_237253_i_) + "/tardis/pool.dat");
     if (!file.getParentFile().exists()) file.getParentFile().mkdirs();

     if (!file.exists()) {
       TardisDataPool.INSTANCE = new TardisDataPool();
       TardisDataPool.INSTANCE.setServer(server);
       saveTardisPool(server);
    } else {


      try {
         FileInputStream stream = new FileInputStream(file);
         ObjectInputStream ois = new ObjectInputStream(stream);
         Object o = ois.readObject();

         if (o instanceof TardisDataPool) {
           TardisDataPool.INSTANCE = (TardisDataPool)o;
           TardisDataPool.INSTANCE.setServer(server);
        }

         ois.close();
       } catch (Exception e) {
         e.printStackTrace();
         TardisDataPool.INSTANCE = new TardisDataPool();
      }
    }

     File lookfile = new File(server.func_240776_a_(FolderName.field_237253_i_) + "/tardis/ownerlookup");
     if (!lookfile.exists()) generateNewOwnerLookup(server); 
  }

  public static void generateNewOwnerLookup(MinecraftServer server) {
     Map<UUID, UserTardises> ownerLookups = getLookupFromFiles(server);

     ownerLookups.forEach((uuid, usrTar) -> saveUserTardises(uuid, usrTar));
  }



  public static Map<UUID, UserTardises> getLookupFromFiles(MinecraftServer server) {
     Map<UUID, UserTardises> ownerLookups = new HashMap<>();

     File folder = new File(server.func_240776_a_(FolderName.field_237253_i_) + "/tardis/data");
     if (folder.exists())
       for (File datafile : folder.listFiles()) { try {
           BufferedReader reader = new BufferedReader(new FileReader(datafile));
           StringBuffer buffer = new StringBuffer();
           String line = null;
           for (; (line = reader.readLine()) != null; buffer.append(line));

           TardisData data = (TardisData)DalekMod.GSON.fromJson(buffer.toString(), TardisData.class);

           UUID uuid = data.getOwner_uuid();

           if (!ownerLookups.containsKey(uuid)) {
             ownerLookups.put(uuid, new UserTardises());
          }

           ((UserTardises)ownerLookups.get(uuid)).addTardis(data.getGlobalID());

           reader.close();
         } catch (Exception e) {
           e.printStackTrace();
        }  }

     return ownerLookups;
  }

  public static void saveUserTardises(UUID uuid, UserTardises usrTar) {
     MinecraftServer server = TardisDataPool.INSTANCE.getServer();
     File file = new File(server.func_240776_a_(FolderName.field_237253_i_) + "/tardis/ownerlookup/" + uuid + ".json");
     if (!file.getParentFile().exists()) file.getParentFile().mkdirs(); 
    try {
       FileWriter writer = new FileWriter(file);
       writer.write(DalekMod.GSON.toJson(usrTar));
       writer.close();
     } catch (Exception e) {
       e.printStackTrace();
    }
  }

  public static void loadUserTardises(UUID uuid) {
     MinecraftServer server = TardisDataPool.INSTANCE.getServer();

     File file = new File(server.func_240776_a_(FolderName.field_237253_i_) + "/tardis/ownerlookup/" + uuid + ".json");

     if (!file.getParentFile().exists()) file.getParentFile().mkdirs();

     if (!file.exists()) {

      try {

         FileWriter writer = new FileWriter(file);
         writer.write(DalekMod.GSON.toJson(new UserTardises()));
         writer.close();
        return;
       } catch (Exception e) {
         e.printStackTrace();
      }
    }

     UserTardises usrTar = null;

    try {
       BufferedReader reader = new BufferedReader(new FileReader(file));
       StringBuffer buffer = new StringBuffer();
       String line = null;
       for (; (line = reader.readLine()) != null; buffer.append(line));

       usrTar = (UserTardises)DalekMod.GSON.fromJson(buffer.toString(), UserTardises.class);

       reader.close();
     } catch (Exception e) {
       e.printStackTrace();
    }
     if (usrTar != null) {
       DMTardis.getOwnerLookup().put(uuid, usrTar);
    }
  }

  public static void saveTardisPool(MinecraftServer server) {
     File file = new File(server.func_240776_a_(FolderName.field_237253_i_) + "/tardis/pool.dat");
     if (!file.getParentFile().exists()) file.getParentFile().mkdirs();

    try {
       file.createNewFile();
       FileOutputStream out = new FileOutputStream(file, false);
       ObjectOutputStream stream = new ObjectOutputStream(out);
       stream.writeObject(TardisDataPool.INSTANCE);
       stream.close();
     } catch (Exception e) {
       e.printStackTrace();
    }

     DMTardis.saveOwnerLookup();
  }

  public static void saveTardisData(TardisData data) {
     if (data == null)
       return;  MinecraftServer server = TardisDataPool.INSTANCE.getServer();
     File file = new File(server.func_240776_a_(FolderName.field_237253_i_) + "/tardis/data/tardis_" + data.getGlobalID() + ".json");
     if (!file.getParentFile().exists()) file.getParentFile().mkdirs();

    try {
       FileWriter writer = new FileWriter(file);
       writer.write(DalekMod.GSON.toJson(data));
       writer.close();
     } catch (Exception e) {
       e.printStackTrace();
    }

     saveUserTardises(data.getOwner_uuid(), DMTardis.getUserTardises(data.getOwner_uuid()));
  }

  public static void loadTardisData(int globalID) {
     if (DMTardis.getLoadedTardises().containsKey(Integer.valueOf(globalID)))
       return;  MinecraftServer server = TardisDataPool.INSTANCE.getServer();
     File file = new File(server.func_240776_a_(FolderName.field_237253_i_) + "/tardis/data/tardis_" + globalID + ".json");
     if (!file.getParentFile().exists()) file.getParentFile().mkdirs(); 
     if (!file.exists())
      return;
    try {
       BufferedReader reader = new BufferedReader(new FileReader(file));
       StringBuffer buffer = new StringBuffer();
       String line = null;
       for (; (line = reader.readLine()) != null; buffer.append(line));

       TardisData data = (TardisData)DalekMod.GSON.fromJson(buffer.toString(), TardisData.class);
       if (data != null) DMTardis.getLoadedTardises().put(Integer.valueOf(data.getGlobalID()), data);

       reader.close();
     } catch (Exception e) {
       e.printStackTrace();
    }
  }
}


