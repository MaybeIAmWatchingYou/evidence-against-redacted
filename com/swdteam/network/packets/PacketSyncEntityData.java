package com.swdteam.network.packets;

import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;


public class PacketSyncEntityData
{
  public int entityID;
  public CompoundNBT tag;
  
  public PacketSyncEntityData(int entityID, CompoundNBT tag) {
     this.entityID = entityID;
     this.tag = tag;
  }
  
  public static void encode(PacketSyncEntityData msg, PacketBuffer buf) {
     buf.writeInt(msg.entityID);
     buf.func_150786_a(msg.tag);
  }
  
  public static PacketSyncEntityData decode(PacketBuffer buf) {
     return new PacketSyncEntityData(buf.readInt(), buf.func_150793_b());
  }
  
  public static void handle(PacketSyncEntityData msg, Supplier<NetworkEvent.Context> ctx) {
     if (((NetworkEvent.Context)ctx.get()).getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
       ((NetworkEvent.Context)ctx.get()).enqueueWork(() -> clientCode(msg, ctx));
    }


    
     ((NetworkEvent.Context)ctx.get()).setPacketHandled(true);
  }


  
  @OnlyIn(Dist.CLIENT)
  public static void clientCode(PacketSyncEntityData msg, Supplier<NetworkEvent.Context> ctx) {
     Entity e = (Minecraft.func_71410_x()).field_71441_e.func_73045_a(msg.entityID);
     if (e != null) {
       e.func_70020_e(msg.tag);
    }
     ((NetworkEvent.Context)ctx.get()).setPacketHandled(true);
  }
}


