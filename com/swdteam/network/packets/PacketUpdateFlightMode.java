package com.swdteam.network.packets;

import com.swdteam.common.init.DMFlightMode;
import java.util.UUID;
import java.util.function.Supplier;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.PacketDirection;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkEvent;


public class PacketUpdateFlightMode
{
  private UUID player;
  private int tardisID;
  private boolean putInFlight;
  private double x;
  private double y;
  private double z;
  
  public PacketUpdateFlightMode(UUID player, int tardisID, double x, double y, double z, boolean putInFlight) {
     this.player = player;
     this.tardisID = tardisID;
     this.x = x;
     this.y = y;
     this.z = z;
     this.putInFlight = putInFlight;
  }
  
  public static void encode(PacketUpdateFlightMode msg, PacketBuffer buf) {
     buf.func_179252_a(msg.player);
     buf.writeInt(msg.tardisID);
     buf.writeDouble(msg.x);
     buf.writeDouble(msg.y);
     buf.writeDouble(msg.z);
     buf.writeBoolean(msg.putInFlight);
  }
  
  public static PacketUpdateFlightMode decode(PacketBuffer buf) {
     return new PacketUpdateFlightMode(buf.func_179253_g(), buf.readInt(), buf.readDouble(), buf.readDouble(), buf.readDouble(), buf.readBoolean());
  }
  
  public static void handle(PacketUpdateFlightMode msg, Supplier<NetworkEvent.Context> ctx) {
     if (((NetworkEvent.Context)ctx.get()).getNetworkManager().getDirection() == PacketDirection.CLIENTBOUND) {
       clientCode(msg, ctx);
    }
  }

  
  @OnlyIn(Dist.CLIENT)
  public static void clientCode(PacketUpdateFlightMode msg, Supplier<NetworkEvent.Context> ctx) {
     ((NetworkEvent.Context)ctx.get()).enqueueWork(() -> {
          if (msg.putInFlight) {
            DMFlightMode.addFlight(msg.player, new DMFlightMode.FlightModeData(msg.tardisID, msg.x, msg.y, msg.z), true);
          } else {
            DMFlightMode.removeFlight(msg.player, true);
          } 
        });

    
     ((NetworkEvent.Context)ctx.get()).setPacketHandled(true);
  }
}


