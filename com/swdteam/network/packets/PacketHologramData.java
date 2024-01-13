package com.swdteam.network.packets;

import com.mojang.authlib.GameProfile;
import com.swdteam.common.block.HologramBlock;
import com.swdteam.common.tileentity.HologramTileEntity;
import java.util.UUID;
import java.util.function.Supplier;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.PacketDirection;
import net.minecraft.state.Property;
import net.minecraft.tileentity.SkullTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent;

public class PacketHologramData {
  private BlockPos pos;
  private String username;
  private boolean smallArms;
  
  public PacketHologramData(BlockPos pos, String name, boolean smallArms, boolean locked, boolean solid, boolean showBase, boolean isAnimated) {
     this.pos = pos;
     this.smallArms = smallArms;
     this.locked = locked;
     this.solid = solid;
     this.username = name;
     this.showBase = showBase;
     this.isAnimated = isAnimated;
  }
  private boolean locked; private boolean solid; private boolean showBase; private boolean isAnimated;
  public static void encode(PacketHologramData msg, PacketBuffer buf) {
     buf.func_179255_a(msg.pos);
     buf.writeBoolean(msg.smallArms);
     buf.writeBoolean(msg.locked);
     buf.writeBoolean(msg.solid);
     buf.writeBoolean(msg.showBase);
     buf.writeBoolean(msg.isAnimated);
     buf.func_180714_a(msg.username);
  }
  
  public static PacketHologramData decode(PacketBuffer buf) {
     BlockPos pos = buf.func_179259_c();
     boolean smallArms = buf.readBoolean();
     boolean locked = buf.readBoolean();
     boolean solid = buf.readBoolean();
     boolean base = buf.readBoolean();
     boolean animated = buf.readBoolean();
     String name = buf.func_218666_n();
    
     return new PacketHologramData(pos, name, smallArms, locked, solid, base, animated);
  }

  
  public static void handle(PacketHologramData msg, Supplier<NetworkEvent.Context> ctx) {
     ((NetworkEvent.Context)ctx.get()).enqueueWork(() -> {
          if (((NetworkEvent.Context)ctx.get()).getNetworkManager().getDirection() == PacketDirection.SERVERBOUND) {
            ServerPlayerEntity serverPlayerEntity = ((NetworkEvent.Context)ctx.get()).getSender();
            
            TileEntity tileEntity = serverPlayerEntity.getEntityWorld().getTileEntity(msg.pos);
            
            if (tileEntity != null && tileEntity instanceof HologramTileEntity) {
              HologramTileEntity hologramTile = (HologramTileEntity)tileEntity;
              
              hologramTile.isSolid = msg.solid;
              hologramTile.smallArms = msg.smallArms;
              hologramTile.isAnimated = msg.isAnimated;
              ((PlayerEntity)serverPlayerEntity).world.setBlockState(msg.pos, (BlockState)((PlayerEntity)serverPlayerEntity).world.getBlockState(msg.pos).func_206870_a((Property)HologramBlock.HAS_BASE, Boolean.valueOf(msg.showBase)), 3);
              if (msg.username.length() > 0) {
                GameProfile profile = new GameProfile((UUID)null, msg.username);
                profile = SkullTileEntity.func_174884_b(profile);
                hologramTile.setOwner(profile);
              } else {
                hologramTile.setOwner(null);
              } 
              if (msg.locked) {
                hologramTile.setLockedBy(serverPlayerEntity.getGameProfile().getId());
              } else {
                hologramTile.setLockedBy(null);
              } 
              hologramTile.sendUpdates();
            } 
          } 
        });
     ((NetworkEvent.Context)ctx.get()).setPacketHandled(true);
  }
}


