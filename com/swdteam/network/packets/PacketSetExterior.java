package com.swdteam.network.packets;

import com.swdteam.common.init.DMItems;
import com.swdteam.common.init.DMNBTKeys;
import com.swdteam.common.init.DMTardis;
import com.swdteam.common.init.DMTardisRegistry;
import com.swdteam.common.tardis.TardisData;
import com.swdteam.common.tileentity.TardisTileEntity;
import com.swdteam.util.ChatUtil;
import java.util.function.Supplier;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.PacketDirection;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkEvent;


public class PacketSetExterior
{
  private ResourceLocation resourceLocation;
  private BlockPos blockPos;
  private int subSkin;

  public PacketSetExterior(ResourceLocation resourceLocation, BlockPos pos, int subSkin) {
     this.resourceLocation = resourceLocation;
     this.blockPos = pos;
     this.subSkin = subSkin;
  }

  public static void encode(PacketSetExterior msg, PacketBuffer buf) {
     buf.func_179255_a(msg.blockPos);
     buf.func_192572_a(msg.resourceLocation);
     buf.writeInt(msg.subSkin);
  }

  public static PacketSetExterior decode(PacketBuffer buf) {
     BlockPos pos = buf.func_179259_c();
     ResourceLocation rl = buf.func_192575_l();

     return new PacketSetExterior(rl, pos, buf.readInt());
  }


  public static void handle(PacketSetExterior msg, Supplier<NetworkEvent.Context> ctx) {
     ((NetworkEvent.Context)ctx.get()).enqueueWork(() -> {
          if (((NetworkEvent.Context)ctx.get()).getNetworkManager().getDirection() == PacketDirection.SERVERBOUND) {
            ServerPlayerEntity serverPlayerEntity = ((NetworkEvent.Context)ctx.get()).getSender();

            TileEntity te = serverPlayerEntity.getEntityWorld().getTileEntity(msg.blockPos);

            if (te instanceof TardisTileEntity) {
              TardisTileEntity tardis = (TardisTileEntity)te;

              TardisData data = tardis.tardisData;

              if (data.getOwner_uuid() == null) {
                data.setOwnerName(serverPlayerEntity.getGameProfile().getName());

                data.setOwnerUUID(serverPlayerEntity.getUniqueID());
              }
              if (data.hasPermission((PlayerEntity)serverPlayerEntity, TardisData.PermissionType.DEFAULT)) {
                if (!data.hasGenerated()) {
                  data.setExterior(DMTardisRegistry.getExterior(msg.resourceLocation).getRegName());
                  data.unlockExterior(msg.resourceLocation);
                  data.setGenerated(true);
                  data.setSkinID(msg.subSkin);
                  data.save();
                  DMTardis.generateInterior(serverPlayerEntity.getServer(), data);
                  eject(tardis.getWorld(), tardis.getPos(), tardis);
                  ChatUtil.sendCompletedMsg((PlayerEntity)serverPlayerEntity, "Your TARDIS is growing, please wait... ", ChatUtil.MessageType.STATUS_BAR);
                } else {
                  ChatUtil.sendError((PlayerEntity)serverPlayerEntity, "Already generated", ChatUtil.MessageType.STATUS_BAR);
                }
              } else {
                ChatUtil.sendError((PlayerEntity)serverPlayerEntity, "You do not have permission...", ChatUtil.MessageType.STATUS_BAR);
              }
            }
          }
        });
     ((NetworkEvent.Context)ctx.get()).setPacketHandled(true);
  }

  public static void eject(World worldIn, BlockPos pos, TardisTileEntity tet) {
     Direction dir = Direction.func_176733_a((tet.rotation - 180.0F));
     ItemEntity itemEnt = new ItemEntity(EntityType.ITEM, worldIn);
     itemEnt.func_70080_a((pos.getX() + 0.5F + dir.func_82601_c()), pos.getY(), (pos.getZ() + 0.5F + dir.func_82599_e()), 0.0F, 0.0F);
     itemEnt.setMotion((dir.func_82601_c() / 10.0F), 0.0D, (dir.func_82599_e() / 10.0F));

     CompoundNBT tag = new CompoundNBT();
     tag.putInt(DMNBTKeys.LINKED_ID, tet.tardisData.getGlobalID());
     ItemStack stack = new ItemStack((IItemProvider)DMItems.TARDIS_KEY.get());
     stack.func_77982_d(tag);
     itemEnt.setItem(stack);
     worldIn.addEntity((Entity)itemEnt);
  }
}


