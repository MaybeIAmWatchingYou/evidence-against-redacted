package com.swdteam.network.packets;

import com.swdteam.common.init.DMBlocks;
import com.swdteam.common.init.DMFlightMode;
import com.swdteam.common.init.DMTardis;
import com.swdteam.common.tardis.TardisData;
import com.swdteam.common.tardis.TardisState;
import com.swdteam.common.tileentity.TardisTileEntity;
import java.util.function.Supplier;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.PacketDirection;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkEvent;







public class PacketLandTardisFlightMode
{
  private float rotation;

  public PacketLandTardisFlightMode(float rotation) {
     this.rotation = rotation;
  }

  public static void encode(PacketLandTardisFlightMode msg, PacketBuffer buf) {
     buf.writeFloat(msg.rotation);
  }

  public static PacketLandTardisFlightMode decode(PacketBuffer buf) {
     return new PacketLandTardisFlightMode(buf.readFloat());
  }


  public static void handle(PacketLandTardisFlightMode msg, Supplier<NetworkEvent.Context> ctx) {
     ((NetworkEvent.Context)ctx.get()).enqueueWork(() -> {
          if (((NetworkEvent.Context)ctx.get()).getNetworkManager().getDirection() == PacketDirection.SERVERBOUND) {
            ServerPlayerEntity player = ((NetworkEvent.Context)ctx.get()).getSender();

            if (!DMFlightMode.isInFlight(player.getGameProfile().getId())) {
              return;
            }

            DMFlightMode.FlightModeData flightData = DMFlightMode.getFlightModeData(player.getGameProfile().getId());

            TardisData data = DMTardis.getTardis(flightData.getTardisID());

            boolean flag = player.func_71121_q().getBlockState(player.getPosition()).func_185904_a().func_76222_j();

            if (!flag) {
              player.func_146105_b((ITextComponent)new StringTextComponent(TextFormatting.RED + "Cannot land TARDIS in this location"), true);
            } else {
              if (data != null) {
                BlockPos playerPos = player.getPosition();

                ServerWorld serverWorld = (ServerWorld)player.world;

                serverWorld.setBlockState(playerPos, ((Block)DMBlocks.TARDIS.get()).getDefaultState());

                TardisTileEntity tardis = (TardisTileEntity)serverWorld.getTileEntity(playerPos);

                tardis.globalID = data.getGlobalID();

                tardis.rotation = msg.rotation;

                tardis.setState(TardisState.NEUTRAL);

                data.setCurrentLocation(playerPos, serverWorld.getDimensionKey());

                data.getCurrentLocation().setFacing(tardis.rotation);
                data.depleatFluidLink((PlayerEntity)player, serverWorld.rand);
                data.save();
              }
              DMFlightMode.playerExitFlight(player);
              DMFlightMode.removeFlight(player.getGameProfile().getId(), false);
            }
          }
        });
     ((NetworkEvent.Context)ctx.get()).setPacketHandled(true);
  }
}


