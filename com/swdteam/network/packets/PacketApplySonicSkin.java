package com.swdteam.network.packets;

import com.swdteam.common.init.DMSonicRegistry;
import com.swdteam.common.tileentity.tardis.SonicInterfaceTileEntity;
import com.swdteam.network.NetworkHandler;
import java.util.function.Supplier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.PacketDirection;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.registries.ForgeRegistries;




public class PacketApplySonicSkin
{
  private ResourceLocation loc;
  private BlockPos pos;

  public PacketApplySonicSkin(ResourceLocation loc, BlockPos pos) {
     this.loc = loc;
     this.pos = pos;
  }

  public static void encode(PacketApplySonicSkin msg, PacketBuffer buf) {
     buf.func_192572_a(msg.loc);
     buf.func_179255_a(msg.pos);
  }

  public static PacketApplySonicSkin decode(PacketBuffer buf) {
     return new PacketApplySonicSkin(buf.func_192575_l(), buf.func_179259_c());
  }


  public static void handle(PacketApplySonicSkin msg, Supplier<NetworkEvent.Context> ctx) {
     ((NetworkEvent.Context)ctx.get()).enqueueWork(() -> {
          if (((NetworkEvent.Context)ctx.get()).getNetworkManager().getDirection() == PacketDirection.SERVERBOUND) {
            ServerPlayerEntity serverPlayerEntity = ((NetworkEvent.Context)ctx.get()).getSender();

            TileEntity te = serverPlayerEntity.getEntityWorld().getTileEntity(msg.pos);

            if (te != null && te instanceof SonicInterfaceTileEntity) {
              SonicInterfaceTileEntity panel = (SonicInterfaceTileEntity)te;
              if (panel.isUnlocked(msg.loc)) {
                Item item = (Item)ForgeRegistries.ITEMS.getValue(msg.loc);
                if (item instanceof com.swdteam.common.item.sonics.SonicScrewdriverCustomizedItem) {
                  ItemStack stack = new ItemStack((IItemProvider)item);
                  stack.readShareTag(panel.getScrewdriver().func_77978_p());
                  if (panel.getScrewdriver().func_82837_s()) {
                    stack.func_200302_a(panel.getScrewdriver().func_200301_q());
                  }
                  stack.func_196085_b(panel.getScrewdriver().func_77952_i());
                  panel.setScrewdriver(stack);
                  panel.sendUpdates();
                  NetworkHandler.sendTo(serverPlayerEntity, new PacketOpenGui(msg.pos, -1));
                }
              } else {
                DMSonicRegistry.SonicData data = DMSonicRegistry.getSonic(msg.loc);
                if (data != null) {
                  if (data.getXpValue() <= ((PlayerEntity)serverPlayerEntity).experienceTotal || serverPlayerEntity.isCreative()) {
                    if (!serverPlayerEntity.isCreative()) {
                      serverPlayerEntity.func_195068_e(-data.getXpValue());
                    }
                    panel.unlockSkin(msg.loc);
                    NetworkHandler.sendTo(serverPlayerEntity, new PacketUnlockSonicResponse(true));
                  } else {
                    NetworkHandler.sendTo(serverPlayerEntity, new PacketUnlockSonicResponse(false));
                  }
                }
              }
            }
          }
        });
     ((NetworkEvent.Context)ctx.get()).setPacketHandled(true);
  }
}


